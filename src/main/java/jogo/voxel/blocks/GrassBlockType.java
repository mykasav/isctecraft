
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

/**
 * Representa um bloco de relva com texturas específicas
 * para as faces superior, laterais e inferior.
 */

public class GrassBlockType extends VoxelBlockType {

    private Texture2D topTexture;
    private Texture2D sideTexture;
    private Texture2D bottomTexture;

    public GrassBlockType() {
        super("grass");
    }


    private void createTextures() {

        int size = 128;

        // ==========================================
        // TEXTURA SUPERIOR: RELVA COM MANCHAS DE TERRA
        // ==========================================

        Image topImage = new Image(
                Image.Format.RGBA8,
                size,
                size,
                ByteBuffer.allocateDirect(size * size * 4),
                ColorSpace.Linear
        );

        ImageRaster topRaster = ImageRaster.create(topImage);

        ColorRGBA grass = new ColorRGBA(0.25f, 0.48f, 0.14f, 1f);
        ColorRGBA grassLight = new ColorRGBA(0.34f, 0.57f, 0.19f, 1f);
        ColorRGBA dirt = new ColorRGBA(0.40f, 0.25f, 0.13f, 1f);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                int hash = (x / 8 * 73856093) ^ (y / 8 * 19349663);
                int detail = Math.floorMod(hash, 10);

                ColorRGBA color = grass;

                if (detail == 0) {
                    color = dirt;
                } else if (detail <= 2) {
                    color = grassLight;
                }

                topRaster.setPixel(x, y, color);
            }
        }

        topTexture = new Texture2D(topImage);
        topTexture.setMagFilter(Texture.MagFilter.Nearest);
        topTexture.setMinFilter(Texture.MinFilter.NearestNoMipMaps);
        topTexture.setWrap(Texture.WrapMode.Repeat);


        // ==========================================
        // TEXTURA INFERIOR: TERRA
        // ==========================================

        bottomTexture = ProcTextures.randomBlocks(
                128, 128, 8, 42,
                new ColorRGBA(0.38f, 0.22f, 0.11f, 1f),
                new ColorRGBA(0.29f, 0.16f, 0.08f, 1f),
                new ColorRGBA(0.45f, 0.28f, 0.15f, 1f)
        );


        // ==========================================
        // TEXTURA LATERAL: RELVA + TERRA
        // ==========================================

        Image sideImage = new Image(
                Image.Format.RGBA8,
                size,
                size,
                ByteBuffer.allocateDirect(size * size * 4),
                ColorSpace.Linear
        );

        ImageRaster sideRaster = ImageRaster.create(sideImage);

        ColorRGBA grassDark = new ColorRGBA(0.20f, 0.39f, 0.10f, 1f);
        ColorRGBA grassSideLight = new ColorRGBA(0.32f, 0.53f, 0.16f, 1f);
        ColorRGBA dirtBase = new ColorRGBA(0.40f, 0.25f, 0.13f, 1f);
        ColorRGBA dirtDark = new ColorRGBA(0.32f, 0.19f, 0.10f, 1f);
        ColorRGBA dirtLight = new ColorRGBA(0.46f, 0.30f, 0.17f, 1f);

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {

                int hash = (x / 8 * 73856093) ^ (y / 8 * 19349663);
                int detail = Math.floorMod(hash, 12);

                int grassLine = 108 + Math.floorMod(x / 8 * 7, 9);

                ColorRGBA color;

                if (y >= grassLine) {
                    color = grassDark;

                    if (detail < 4) {
                        color = grassSideLight;
                    }
                } else {
                    color = dirtBase;

                    if (detail == 0) {
                        color = dirtDark;
                    } else if (detail <= 2) {
                        color = dirtLight;
                    }
                }

                sideRaster.setPixel(x, y, color);
            }
        }

        sideTexture = new Texture2D(sideImage);
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
