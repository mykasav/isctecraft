
package jogo.voxel.blocks;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import jogo.voxel.VoxelBlockType;

public class IceBlockType extends VoxelBlockType {

    public IceBlockType() {
        super("ice");
    }

    @Override
    public Material getMaterial(AssetManager assetManager) {
        Material material = new Material(
                assetManager,
                "Common/MatDefs/Light/Lighting.j3md"
        );

        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", new ColorRGBA(0.55f, 0.85f, 1f, 1f));
        material.setColor("Specular", ColorRGBA.White);
        material.setFloat("Shininess", 64f);

        return material;
    }

    @Override
    public boolean hasRandomizedUV() {
        return false;
    }
}
