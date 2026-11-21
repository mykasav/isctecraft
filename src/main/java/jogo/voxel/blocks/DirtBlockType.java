package jogo.voxel.blocks;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture2D;
import jogo.util.ProcTextures;
import jogo.voxel.VoxelBlockType;

/**
 * Bloco de terra: textura castanha com manchas aleatórias.
 */
public class DirtBlockType extends VoxelBlockType {

    /** Cria o tipo de bloco terra. */
    public DirtBlockType() {
        super("dirt");
    }

    /** Material castanho com variação de tons. */
    @Override
    public Material getMaterial(AssetManager assetManager) {

        Texture2D tex = ProcTextures.randomBlocks(
                128,
                128,
                8,
                42,
                new ColorRGBA(0.38f, 0.22f, 0.11f, 1f),
                new ColorRGBA(0.29f, 0.16f, 0.08f, 1f),
                new ColorRGBA(0.45f, 0.28f, 0.15f, 1f)
        );

        Material m = new Material(
                assetManager,
                "Common/MatDefs/Light/Lighting.j3md"
        );

        m.setTexture("DiffuseMap", tex);
        m.setBoolean("UseMaterialColors", true);
        m.setColor("Diffuse", ColorRGBA.White);
        m.setColor("Specular", ColorRGBA.White.mult(0.02f));
        m.setFloat("Shininess", 16f);

        return m;
    }
}
