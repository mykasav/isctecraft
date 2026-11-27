
package jogo.voxel;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import jogo.framework.math.Vec3;

public abstract class VoxelBlockType {

    public boolean isVisible() {
        return true;
    }

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

    // Material baseado na posição do bloco.
    public Material getMaterial(AssetManager assetManager, Vec3 blockPos) {
        return getMaterial(assetManager);
    }

    // Material baseado na face do bloco.
    public Material getMaterial(
            AssetManager assetManager,
            MeshBuilder.Face face
    ) {
        return getMaterial(assetManager);
    }

    // Material baseado na face e na posição.
    public Material getMaterial(
            AssetManager assetManager,
            MeshBuilder.Face face,
            Vec3 blockPos
    ) {
        return getMaterial(assetManager, face);
    }

    public boolean hasRandomizedUV() {
        return true;
    }
}

