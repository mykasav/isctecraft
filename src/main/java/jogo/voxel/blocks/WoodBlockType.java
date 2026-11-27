
package jogo.voxel.blocks;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Image;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture2D;
import com.jme3.texture.image.ColorSpace;
import com.jme3.texture.image.ImageRaster;

import jogo.util.ProcTextures;
import jogo.voxel.MeshBuilder;
import jogo.voxel.VoxelBlockType;
import jogo.framework.math.Vec3;

import java.nio.ByteBuffer;

/**
 * Bloco de madeira: casca nas laterais e anéis nas extremidades.
 */
public class WoodBlockType extends VoxelBlockType {

    private Texture2D barkTexture;
    private Texture2D endTexture;

    public WoodBlockType() {
        super("wood");
    }

    /**
     * Cria as texturas procedurais da madeira.
     */
    private void createTextures() {

        // Textura da casca.
        // Textura procedural da casca da madeira.
        barkTexture = createBarkTexture();

        // Textura das extremidades do tronco.
        int size = 128;

        Image image = new Image(
                Image.Format.RGBA8,
                size,
                size,
                ByteBuffer.allocateDirect(size * size * 4),
                ColorSpace.Linear
        );

        ImageRaster raster = ImageRaster.create(image);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                float dx = x - size / 2f;
                float dy = y - size / 2f;

                float distance = (float) Math.sqrt(dx * dx + dy * dy);

                int ring = ((int) distance / 5) % 2;

                ColorRGBA color;

                if (ring == 0) {
                    color = new ColorRGBA(0.66f, 0.43f, 0.23f, 1f);
                } else {
                    color = new ColorRGBA(0.43f, 0.25f, 0.12f, 1f);
                }

                raster.setPixel(x, y, color);
            }
        }

        endTexture = new Texture2D(image);
        endTexture.setMagFilter(Texture.MagFilter.Nearest);
        endTexture.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        endTexture.setWrap(Texture.WrapMode.Repeat);
    }

    /**
     * Cria um material com a textura indicada.
     */
    private Material createMaterial(
            AssetManager assetManager,
            Texture2D texture
    ) {

        Material material = new Material(
                assetManager,
                "Common/MatDefs/Light/Lighting.j3md"
        );

        material.setTexture("DiffuseMap", texture);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor(
                "Specular",
                ColorRGBA.White.mult(0.02f)
        );
        material.setFloat("Shininess", 16f);

        return material;
    }

    @Override
    public Material getMaterial(AssetManager assetManager) {

        if (barkTexture == null) {
            createTextures();
        }

        return createMaterial(assetManager, barkTexture);
    }




    private Texture2D createBarkTexture() {

        int size = 128;

        Image image = new Image(
                Image.Format.RGBA8,
                size,
                size,
                ByteBuffer.allocateDirect(size * size * 4),
                ColorSpace.Linear
        );

        ImageRaster raster = ImageRaster.create(image);

        ColorRGBA base = new ColorRGBA(0.42f, 0.25f, 0.12f, 1f);
        ColorRGBA dark = new ColorRGBA(0.29f, 0.15f, 0.07f, 1f);
        ColorRGBA light = new ColorRGBA(0.52f, 0.34f, 0.18f, 1f);
        ColorRGBA medium = new ColorRGBA(0.36f, 0.20f, 0.09f, 1f);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                int column = x / 4;
                int segment = y / 8;

                int localX = x % 4;

                int hash = column * 73856093 ^ segment * 19349663;
                hash ^= (hash >>> 13);

                ColorRGBA color = base;

                // Veios verticais irregulares.
                int groove = Math.floorMod(column * 7 + 3, 11);

                if (groove < 3 && localX == 0) {
                    color = dark;
                } else if (groove >= 8 && localX == 3) {
                    color = light;
                }

                // Pequenas manchas pixelizadas.
                int detail = Math.floorMod(hash, 23);

                if (detail == 0 || detail == 1) {
                    color = medium;
                } else if (detail == 2) {
                    color = light;
                }

                raster.setPixel(x, y, color);
            }
        }

        Texture2D texture = new Texture2D(image);

        texture.setMagFilter(Texture.MagFilter.Nearest);
        texture.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        texture.setWrap(Texture.WrapMode.Repeat);

        return texture;
    }




    /**
     * As extremidades mostram os anéis da madeira.
     */
    @Override
    public Material getMaterial(
            AssetManager assetManager,
            MeshBuilder.Face face,
            Vec3 blockPos
    ) {

        System.out.println("WOOD - Face recebida: " + face);

        if (barkTexture == null) {
            createTextures();
        }

        return switch (face) {
            case PY, NY -> createMaterial(assetManager, endTexture);
            default -> createMaterial(assetManager, barkTexture);
        };
    }

    /**
     * Mantém a orientação dos anéis e da casca.
     */
    @Override
    public boolean hasRandomizedUV() {
        return false;
    }
}
