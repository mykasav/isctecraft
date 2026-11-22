package jogo.gameobject.item;

import jogo.gameobject.GameObject;

/**
 * Classe abstrata base para todos os itens do jogo.
 * <p>
 * Estende {@link GameObject} e define o comportamento comum a qualquer item,
 * nomeadamente a capacidade de reagir a interacoes do jogador (tecla E).
 * Esta classe e neutra em relacao ao motor (nao depende de bibliotecas graficas).
 * </p>
 */
public abstract class Item extends GameObject {

    /**
     * Constroi um novo item com o nome especificado.
     *
     * @param name o nome identificador do item
     */
    protected Item(String name) {
        super(name);
    }

    /**
     * Metodo executado quando o jogador interage com este item no mundo (tecla E).
     * Pode ser sobrescrito pelas subclasses para implementar efeitos especificos
     * (por exemplo, recolha, cura ou ativacao).
     */
    public void onInteract() {
        // Ponto de extensao para as subclasses implementarem a sua logica
    }
}

