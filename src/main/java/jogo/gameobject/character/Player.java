package jogo.gameobject.character;

import jogo.inventory.Inventory;

/**
 * Representa o jogador controlado pelo utilizador no jogo.
 * <p>
 * O jogador possui um {@link Inventory} para armazenar os recursos recolhidos
 * (madeira, pedra), consumiveis (pocoes) e itens de combate.
 * </p>
 */
public class Player extends Character {

    /**
     * Inventario de itens do jogador.
     */
    private final Inventory inventory;

    /**
     * Constroi um novo jogador com um inventario inicial padrao.
     */
    public Player() {
        super("Player");
        this.inventory = new Inventory();
    }

    /**
     * Devolve o inventario de itens deste jogador.
     *
     * @return o inventario do jogador
     */
    public Inventory getInventory() {
        return inventory;
    }
}
