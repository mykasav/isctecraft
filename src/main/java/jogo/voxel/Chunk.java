
package jogo.voxel;

import jogo.framework.math.Vec3;
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
 * Represents a chunk of the voxel world.
 */
public class Chunk {

    public static final int SIZE = 16;

    private final int chunkX, chunkY, chunkZ;
    private final byte[][][] vox;
    private final Node node;
    private final Node collisionNode;

    private boolean dirty = true;
    private RigidBodyControl rigidBody;

    public Chunk(int chunkX, int chunkY, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;

        this.vox = new byte[SIZE][SIZE][SIZE];
        this.node = new Node("Chunk_" + chunkX + "," + chunkY + "," + chunkZ);
        this.collisionNode = new Node("CollisionChunk_" + chunkX + "," + chunkY + "," + chunkZ);
    }

    public Node getNode() {
        return node;
    }

    public byte get(int x, int y, int z) {
        return vox[x][y][z];
    }

    public void set(int x, int y, int z, byte id) {
        vox[x][y][z] = id;
    }

    public void markDirty() {
        dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void clearDirty() {
        dirty = false;
    }

    /**
     * Builds the mesh for this chunk.
     */
    public void buildMesh(
            AssetManager assetManager,
            VoxelPalette palette,
            VoxelWorld world
    ) {

        long start = System.nanoTime();

        node.detachAllChildren();
        collisionNode.detachAllChildren();

        Map<Byte, EnumMap<MeshBuilder.Face, MeshBuilder>> builders =
                new HashMap<>();

        // Create one MeshBuilder for each block type and face.
        for (int i = 0; i < palette.size(); i++) {

            if (i == VoxelPalette.AIR_ID) {
                continue;
            }

            byte id = (byte) i;

            EnumMap<MeshBuilder.Face, MeshBuilder> faceBuilders =
                    new EnumMap<>(MeshBuilder.Face.class);

            for (MeshBuilder.Face face : MeshBuilder.Face.values()) {

                MeshBuilder mb = new MeshBuilder();

                if (id == VoxelPalette.DIRT_ID) {
                    mb.setRandomizeUV(true);
                }

                faceBuilders.put(face, mb);
            }

            builders.put(id, faceBuilders);
        }

        // Traverse all blocks in this chunk.
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                for (int z = 0; z < SIZE; z++) {

                    byte id = vox[x][y][z];

                    if (id == VoxelPalette.AIR_ID) {
                        continue;
                    }

                    if (!palette.get(id).isVisible()) {
                        continue;
                    }

                    EnumMap<MeshBuilder.Face, MeshBuilder> faceBuilders =
                            builders.get(id);

                    int wx = chunkX * SIZE + x;
                    int wy = chunkY * SIZE + y;
                    int wz = chunkZ * SIZE + z;

                    // Right face
                    if (!world.isSolid(wx + 1, wy, wz)) {
                        faceBuilders.get(MeshBuilder.Face.PX)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.PX);
                    }

                    // Left face
                    if (!world.isSolid(wx - 1, wy, wz)) {
                        faceBuilders.get(MeshBuilder.Face.NX)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.NX);
                    }

                    // Top face
                    if (!world.isSolid(wx, wy + 1, wz)) {
                        faceBuilders.get(MeshBuilder.Face.PY)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.PY);
                    }

                    // Bottom face
                    if (!world.isSolid(wx, wy - 1, wz)) {
                        faceBuilders.get(MeshBuilder.Face.NY)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.NY);
                    }

                    // Front face
                    if (!world.isSolid(wx, wy, wz + 1)) {
                        faceBuilders.get(MeshBuilder.Face.PZ)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.PZ);
                    }

                    // Back face
                    if (!world.isSolid(wx, wy, wz - 1)) {
                        faceBuilders.get(MeshBuilder.Face.NZ)
                                .addVoxelFace(wx, wy, wz, MeshBuilder.Face.NZ);
                    }
                }
            }
        }

        int geomCount = 0;

        // Build one Geometry per block type and face.
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

                    Vec3 blockPos = new Vec3(
                            chunkX * SIZE,
                            chunkY * SIZE,
                            chunkZ * SIZE
                    );

                    Material mat = palette.get(id).getMaterial(
                            assetManager,
                            face,
                            blockPos
                    );

                    g.setMaterial(mat);
                    node.attachChild(g);

                    // Apenas blocos sólidos participam nas colisões
                    if (palette.get(id).isSolid()) {
                        Geometry collisionGeometry = g.clone(false);
                        collisionGeometry.setMesh(mesh.deepClone());

                        collisionNode.attachChild(collisionGeometry);
                    }

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
     * Updates the physics control for this chunk.
     */

    public void updatePhysics(PhysicsSpace space) {

        if (space == null) {
            return;
        }

        if (rigidBody != null) {
            space.remove(rigidBody);
            node.removeControl(rigidBody);
            rigidBody = null;
        }

        // Não criar colisões se não existirem blocos sólidos
        if (collisionNode.getQuantity() == 0) {
            return;
        }

        Node tempNode = collisionNode.clone(false);

        for (int i = 0; i < collisionNode.getQuantity(); i++) {

            if (collisionNode.getChild(i) instanceof Geometry g) {

                Geometry gClone = g.clone(false);
                gClone.setMesh(g.getMesh().deepClone());

                tempNode.attachChild(gClone);
            }
        }

        CollisionShape shape =
                CollisionShapeFactory.createMeshShape(tempNode);

        rigidBody = new RigidBodyControl(shape, 0f);

        node.addControl(rigidBody);
        space.add(rigidBody);
    }

}
