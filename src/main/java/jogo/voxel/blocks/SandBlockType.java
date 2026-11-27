
package jogo.voxel.blocks;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture2D;

import jogo.util.ProcTextures;
import jogo.voxel.VoxelBlockType;

public class SandBlockType extends VoxelBlockType {

    private Texture2D sandTexture;

    public SandBlockType() {
        super("sand");
    }

    private void createTexture() {

        sandTexture = ProcTextures.randomBlocks(
                128,
                128,
                8,
                91,
                new ColorRGBA(0.85f, 0.76f, 0.52f, 1f),
                new ColorRGBA(0.78f, 0.68f, 0.43f, 1f),
                new ColorRGBA(0.92f, 0.83f, 0.62f, 1f),
                new ColorRGBA(0.72f, 0.62f, 0.39f, 1f)
        );
    }

    @Override
    public Material getMaterial(AssetManager assetManager) {

        if (sandTexture == null) {
            createTexture();
        }

        Material material = new Material(
                assetManager,
                "Common/MatDefs/Light/Lighting.j3md"
        );

        material.setTexture("DiffuseMap", sandTexture);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor("Specular", ColorRGBA.White.mult(0.02f));
        material.setFloat("Shininess", 8f);

        return material;
    }

    @Override
    public boolean hasRandomizedUV() {
        return true;
    }
}
