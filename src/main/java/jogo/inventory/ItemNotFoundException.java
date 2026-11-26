package jogo.inventory;

/**
 * Excecao lancada quando se tenta aceder ou remover um item que nao existe no inventario.
 */
public class ItemNotFoundException extends InventoryException {

    /**
     * Constroi a excecao com a mensagem de erro explicativa.
     *
     * @param message descricao do item em falta
     */
    public ItemNotFoundException(String message) {
        super(message);
    }
}
