package jogo.inventory;

/**
 * Excecao base para operacoes e erros relacionados com o inventario.
 * <p>
 * Serve como classe-mae para excecoes mais especificas, como
 * {@link InventoryFullException} e {@link ItemNotFoundException}.
 * </p>
 */
public class InventoryException extends Exception {

    /**
     * Constroi uma nova excecao de inventario com a mensagem de detalhe indicada.
     *
     * @param message descricao do erro ocorrido
     */
    public InventoryException(String message) {
        super(message);
    }
}
