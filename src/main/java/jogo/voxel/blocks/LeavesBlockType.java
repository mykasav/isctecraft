
package jogo.voxel.blocks;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.texture.Texture2D;

import jogo.util.ProcTextures;
import jogo.voxel.VoxelBlockType;

public class LeavesBlockType extends VoxelBlockType {

    private Texture2D leavesTexture;

    public LeavesBlockType() {
        super("leaves");
    }

    private void createTexture() {
        leavesTexture = ProcTextures.randomBlocks(
                128,
                128,
                8,
                73,
                new ColorRGBA(0.12f, 0.38f, 0.08f, 1f),
                new ColorRGBA(0.20f, 0.50f, 0.10f, 1f),
                new ColorRGBA(0.29f, 0.58f, 0.14f, 1f),
                new ColorRGBA(0.08f, 0.30f, 0.06f, 1f)
        );
    }

    @Override
    public Material getMaterial(AssetManager assetManager) {

        if (leavesTexture == null) {
            createTexture();
        }

        Material material = new Material(
                assetManager,
                "Common/MatDefs/Light/Lighting.j3md"
        );

        material.setTexture("DiffuseMap", leavesTexture);
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor("Specular", ColorRGBA.Black);

        // Por agora, as folhas são opacas.
        // A transparência será implementada numa etapa própria.
        material.getAdditionalRenderState().setFaceCullMode(
                RenderState.FaceCullMode.Off
        );

        return material;
    }

    @Override
    public boolean hasRandomizedUV() {
        return false;
    }
}
