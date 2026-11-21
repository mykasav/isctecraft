package jogo.gameobject.character;

import jogo.gameobject.Updatable;

/**
 * NPC de teste: anda para a frente e para trás no eixo X.
 * Serve só para confirmar que o ciclo de update e o rendering de NPCs funcionam.
 */
public class NpcTeste extends Character implements Updatable {

    private static final float VELOCIDADE = 2f;   // blocos por segundo
    private static final float DISTANCIA = 3f;    // até onde anda antes de virar

    private float origemX;
    private int direcao = 1;                      // 1 = direita, -1 = esquerda

    /** Cria o NPC de teste. */
    public NpcTeste() {
        super("NpcTeste");
    }

    /**
     * Define o ponto à volta do qual o NPC anda.
     */
    @Override
    public void setPosition(float x, float y, float z) {
        super.setPosition(x, y, z);
        this.origemX = x;
    }

    /** Move o NPC e vira quando chega ao limite. */
    @Override
    public void update(float tpf) {
        float novoX = position.x + direcao * VELOCIDADE * tpf;

        if (Math.abs(novoX - origemX) > DISTANCIA) {
            direcao = -direcao;   // chegou ao limite: vira
        }

        position.x = novoX;
    }
}
