package jogo.voxel;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a chunk of the voxel world (e.g., 16x16x16 blocks).
 */
public class Chunk {
    public static final int SIZE = 16;
    private final int chunkX, chunkY, chunkZ;
    private final byte[][][] vox;
    private final Node node;

    private boolean dirty = true;

    private RigidBodyControl rigidBody;

    public Chunk(int chunkX, int chunkY, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;
        this.vox = new byte[SIZE][SIZE][SIZE];
        this.node = new Node("Chunk_" + chunkX + "," + chunkY + "," + chunkZ);
    }

    public Node getNode() { return node; }
    public byte get(int x, int y, int z) { return vox[x][y][z]; }
    public void set(int x, int y, int z, byte id) { vox[x][y][z] = id; }


    public void markDirty() { dirty = true; }
    public boolean isDirty() { return dirty; }
    public void clearDirty() { dirty = false; }

    // Build and attach mesh for this chunk

    public void buildMesh(AssetManager assetManager, VoxelPalette palette) {

        long start = System.nanoTime();

        node.detachAllChildren();

        // Um conjunto de MeshBuilders por tipo de bloco.
        // Dentro de cada conjunto existe um MeshBuilder por face.
        Map<Byte, EnumMap<MeshBuilder.Face, MeshBuilder>> builders = new HashMap<>();

        for (int i = 0; i < palette.size(); i++) {

            if (i == VoxelPalette.AIR_ID) continue;

            byte id = (byte) i;

            EnumMap<MeshBuilder.Face, MeshBuilder> faceBuilders =
                    new EnumMap<>(MeshBuilder.Face.class);

            for (MeshBuilder.Face face : MeshBuilder.Face.values()) {

                MeshBuilder mb = new MeshBuilder();

                // Randomize UVs apenas para o Dirt.
                if (id == VoxelPalette.DIRT_ID) {
                    mb.setRandomizeUV(true);
                }

                faceBuilders.put(face, mb);
            }

            builders.put(id, faceBuilders);
        }

        // Percorrer todos os voxels do Chunk.
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                for (int z = 0; z < SIZE; z++) {

                    byte id = vox[x][y][z];

                    if (id == VoxelPalette.AIR_ID) continue;
                    if (!palette.get(id).isSolid()) continue;

                    EnumMap<MeshBuilder.Face, MeshBuilder> faceBuilders =
                            builders.get(id);

                    int wx = chunkX * SIZE + x;
                    int wy = chunkY * SIZE + y;
                    int wz = chunkZ * SIZE + z;

                    // Face direita
                    if (!isSolid(wx + 1, wy, wz, palette)) {
                        faceBuilders.get(MeshBuilder.Face.PX)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.PX);
                    }

                    // Face esquerda
                    if (!isSolid(wx - 1, wy, wz, palette)) {
                        faceBuilders.get(MeshBuilder.Face.NX)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.NX);
                    }

                    // Face superior
                    if (!isSolid(wx, wy + 1, wz, palette)) {
                        faceBuilders.get(MeshBuilder.Face.PY)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.PY);
                    }

                    // Face inferior
                    if (!isSolid(wx, wy - 1, wz, palette)) {
                        faceBuilders.get(MeshBuilder.Face.NY)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.NY);
                    }

                    // Face frontal
                    if (!isSolid(wx, wy, wz + 1, palette)) {
                        faceBuilders.get(MeshBuilder.Face.PZ)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.PZ);
                    }

                    // Face traseira
                    if (!isSolid(wx, wy, wz - 1, palette)) {
                        faceBuilders.get(MeshBuilder.Face.NZ)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.NZ);
                    }
                }
            }
        }

        int geomCount = 0;

        // Construir uma Geometry por tipo de bloco e por face.
        for (Map.Entry<Byte, EnumMap<MeshBuilder.Face, MeshBuilder>> entry
                : builders.entrySet()) {

            byte id = entry.getKey();

            for (Map.Entry<MeshBuilder.Face, MeshBuilder> faceEntry
                    : entry.getValue().entrySet()) {

                MeshBuilder.Face face = faceEntry.getKey();

                Mesh mesh = faceEntry.getValue().build();

                if (mesh.getTriangleCount() > 0) {

                    Geometry g = new Geometry(
                            "chunk_" + chunkX + "_" + chunkY + "_" + chunkZ
                                    + "_" + id + "_" + face,
                            mesh
                    );

                    Material mat = palette.get(id).getMaterial(assetManager, face);

                    g.setMaterial(mat);

                    node.attachChild(g);

                    geomCount++;
                }
            }
        }

        long end = System.nanoTime();

        System.out.println(
                "Chunk [" + chunkX + "," + chunkY + "," + chunkZ
                        + "] mesh built in "
                        + ((end - start) / 1_000_000.0)
                        + " ms, geometries: " + geomCount
        );
    }


    /**
     * Updates the physics control for this chunk. Call after mesh rebuild.
     */
    public void updatePhysics(PhysicsSpace space) {
        if (rigidBody != null) {
            System.out.println("Removing old RigidBodyControl for chunk ["+chunkX+","+chunkY+","+chunkZ+"]");
            space.remove(rigidBody);
            node.removeControl(rigidBody);
            rigidBody = null;
        }
        if (node.getQuantity() > 0) { // Only add if chunk has geometry
            // Ensure node is attached to a parent (world node)
            if (node.getParent() == null) {
                System.out.println("Warning: Chunk node ["+chunkX+","+chunkY+","+chunkZ+"] not attached to world node before physics update!");
            }
            // Clone meshes for collision shape to avoid Bullet caching issues
            Node tempNode = node.clone(false); // shallow clone
            for (int i = 0; i < node.getQuantity(); i++) {
                if (node.getChild(i) instanceof Geometry g) {
                    Geometry gClone = g.clone(false);
                    gClone.setMesh(g.getMesh().deepClone());
                    tempNode.attachChild(gClone);
                }
            }
            CollisionShape shape = CollisionShapeFactory.createMeshShape(tempNode);
            rigidBody = new RigidBodyControl(shape, 0f);
            node.addControl(rigidBody);
            space.add(rigidBody);
            System.out.println("Added new RigidBodyControl for chunk ["+chunkX+","+chunkY+","+chunkZ+"]");
        }
    }

    // Helper for solid check in world coordinates
    private boolean isSolid(int wx, int wy, int wz, VoxelPalette palette) {
        if (wx < 0 || wy < 0 || wz < 0) return false;
        int max = SIZE * 1000; // arbitrary large world limit
        if (wx >= max || wy >= max || wz >= max) return false;
        int cx = wx / SIZE, cy = wy / SIZE, cz = wz / SIZE;
        int lx = wx % SIZE, ly = wy % SIZE, lz = wz % SIZE;
        if (cx != chunkX || cy != chunkY || cz != chunkZ) return false; // only check within this chunk
        if (lx < 0 || ly < 0 || lz < 0 || lx >= SIZE || ly >= SIZE || lz >= SIZE) return false;
        byte id = vox[lx][ly][lz];
        return id != VoxelPalette.AIR_ID && palette.get(id).isSolid();
    }
}
