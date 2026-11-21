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

    /** Whether this block is physically solid (collides/occludes). */
    public boolean isSolid() { return true; }

    /**
     * Returns the Material for this block type. Override in subclasses for custom materials.
     */
    public abstract Material getMaterial(AssetManager assetManager);

    /**
     * Returns the Material for this block type at a specific block position.
     * Default implementation ignores the position for backward compatibility.
     * Subclasses can override to use blockPos.
     */
    public Material getMaterial(AssetManager assetManager, Vec3 blockPos) {
        return getMaterial(assetManager);
    }

    /**
     * Devolve o material de uma face específica do bloco (ex.: relva em cima, terra em baixo).
     * Por omissão ignora a face e usa o material por posição, por isso os blocos
     * que não precisam de faces diferentes não têm de mudar nada.
     *
     * @param assetManager gestor de assets do motor
     * @param face         face do bloco que está a ser desenhada
     * @param blockPos     posição do primeiro bloco deste tipo no chunk
     * @return material a aplicar nessa face
     */
    public Material getMaterial(AssetManager assetManager, MeshBuilder.Face face, Vec3 blockPos) {
        return getMaterial(assetManager, blockPos);
    }

    /**
     * Indica se a textura deste bloco leva um deslocamento aleatório de UV,
     * para os blocos iguais não parecerem todos copiados. Por omissão: sim.
     * Blocos com textura orientada (ex.: a faixa de relva nas laterais) devem devolver {@code false}.
     *
     * @return {@code true} se o UV deve ser aleatório
     */
    public boolean hasRandomizedUV() { return true; }
}
