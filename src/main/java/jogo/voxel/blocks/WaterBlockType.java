
package jogo.voxel.blocks;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import jogo.voxel.VoxelBlockType;

public class WaterBlockType extends VoxelBlockType {

    public WaterBlockType() {
        super("water");
    }

    @Override
    public Material getMaterial(AssetManager assetManager) {
        Material material = new Material(
                assetManager,
                "Common/MatDefs/Light/Lighting.j3md"
        );

        material.setBoolean("UseMaterialColors", true);
        material.setColor(
                "Diffuse",
                new ColorRGBA(0.15f, 0.55f, 0.9f, 0.55f)
        );
        material.setColor("Specular", ColorRGBA.White);
        material.setFloat("Shininess", 80f);

        material.getAdditionalRenderState().setBlendMode(
                RenderState.BlendMode.Alpha
        );
        material.getAdditionalRenderState().setDepthWrite(false);

        return material;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean hasRandomizedUV() {
        return false;
    }
}
