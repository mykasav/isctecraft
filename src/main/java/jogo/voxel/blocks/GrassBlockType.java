
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

import java.nio.ByteBuffer;

public class GrassBlockType extends VoxelBlockType {

    private Texture2D topTexture;
    private Texture2D sideTexture;
    private Texture2D bottomTexture;

    public GrassBlockType() {
        super("grass");
    }

    private void createTextures() {

        // Textura superior: diferentes tons de verde.
        topTexture = ProcTextures.randomBlocks(
                128, 128, 8, 100,
                new ColorRGBA(0.20f, 0.48f, 0.12f, 1f),
                new ColorRGBA(0.27f, 0.58f, 0.16f, 1f),
                new ColorRGBA(0.16f, 0.40f, 0.09f, 1f)
        );

        // Textura inferior: diferentes tons de castanho.
        bottomTexture = ProcTextures.randomBlocks(
                128, 128, 8, 42,
                new ColorRGBA(0.38f, 0.22f, 0.11f, 1f),
                new ColorRGBA(0.29f, 0.16f, 0.08f, 1f),
                new ColorRGBA(0.45f, 0.28f, 0.15f, 1f)
        );

        // Textura lateral: relva na parte superior e terra na restante.
        int size = 128;

        Image image = new Image(
                Image.Format.RGBA8,
                size,
                size,
                ByteBuffer.allocateDirect(size * size * 4),
                ColorSpace.Linear
        );

        ImageRaster raster = ImageRaster.create(image);

        ColorRGBA[] grassColors = {
                new ColorRGBA(0.20f, 0.48f, 0.12f, 1f),
                new ColorRGBA(0.27f, 0.58f, 0.16f, 1f),
                new ColorRGBA(0.16f, 0.40f, 0.09f, 1f)
        };

        ColorRGBA[] dirtColors = {
                new ColorRGBA(0.38f, 0.22f, 0.11f, 1f),
                new ColorRGBA(0.29f, 0.16f, 0.08f, 1f),
                new ColorRGBA(0.45f, 0.28f, 0.15f, 1f)
        };

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                int hash = (x / 8 * 73856093)
                        ^ (y / 8 * 19349663);

                int index = Math.floorMod(hash, 3);

                ColorRGBA color;

                if (y >= size - 16) {
                    color = grassColors[index];
                } else {
                    color = dirtColors[index];
                }

                raster.setPixel(x, y, color);
            }
        }

        sideTexture = new Texture2D(image);
        sideTexture.setMagFilter(Texture.MagFilter.Nearest);
        sideTexture.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        sideTexture.setWrap(Texture.WrapMode.Repeat);
    }

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

        if (topTexture == null) {
            createTextures();
        }

        return createMaterial(assetManager, sideTexture);
    }

    @Override
    public Material getMaterial(
            AssetManager assetManager,
            MeshBuilder.Face face
    ) {

        if (topTexture == null) {
            createTextures();
        }

        return switch (face) {
            case PY -> createMaterial(assetManager, topTexture);
            case NY -> createMaterial(assetManager, bottomTexture);
            default -> createMaterial(assetManager, sideTexture);
        };
    }
}
