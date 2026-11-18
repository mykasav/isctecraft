
package jogo.voxel;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import jogo.framework.math.Vec3;

public abstract class VoxelBlockType {

    private final String name;

    protected VoxelBlockType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSolid() {
        return true;
    }

    public abstract Material getMaterial(AssetManager assetManager);

    // Método original: permite obter o material com base na posição.
    public Material getMaterial(AssetManager assetManager, Vec3 blockPos) {
        return getMaterial(assetManager);
    }

    // Novo método: permite obter o material com base na face.
    public Material getMaterial(AssetManager assetManager, MeshBuilder.Face face) {
        return getMaterial(assetManager);
    }
}
