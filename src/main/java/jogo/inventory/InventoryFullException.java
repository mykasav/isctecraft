package jogo.inventory;

/**
 * Excecao lancada quando se tenta adicionar um item a um inventario que ja atingiu a sua capacidade maxima.
 */
public class InventoryFullException extends InventoryException {

    /**
     * Constroi a excecao com a mensagem de erro explicativa.
     *
     * @param message descricao do erro de capacidade esgotada
     */
    public InventoryFullException(String message) {
        super(message);
    }
}
