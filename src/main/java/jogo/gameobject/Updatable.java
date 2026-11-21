package jogo.gameobject;

/**
 * Contrato para objetos de jogo que têm lógica a correr a cada frame
 * (ex.: inimigos que perseguem, aliados que seguem o jogador).
 * <p>
 * O motor chama {@link #update(float)} uma vez por frame em todos os
 * objetos registados que implementem esta interface.
 */
public interface Updatable {

    /**
     * Atualiza o estado do objeto.
     *
     * @param tpf segundos passados desde o último frame (time per frame)
     */
    void update(float tpf);
}