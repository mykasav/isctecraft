package jogo.gameobject.item;

/**
 * Representa um recurso de pedra recolhido no terreno da floresta.
 * <p>
 * A pedra e um material resistente utilizado no crafting de barreiras
 * defensivas e estruturas para suportar os ataques noturnos.
 * </p>
 */
public class StoneItem extends Item {

    /**
     * Quantidade de pedras nesta pilha de itens.
     */
    private int quantity;

    /**
     * Constroi um item de pedra com a quantidade indicada.
     *
     * @param quantity a quantidade inicial de pedra (deve ser maior ou igual a zero)
     */
    public StoneItem(int quantity) {
        super("Pedra");
        if (quantity < 0) {
            this.quantity = 0;
        } else {
            this.quantity = quantity;
        }
    }

    /**
     * Constroi um item de pedra com uma unidade por omissao.
     */
    public StoneItem() {
        this(1);
    }

    /**
     * Devolve a quantidade de pedras disponiveis.
     *
     * @return a quantidade de pedra
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Define a quantidade de pedra, impedindo valores negativos.
     *
     * @param quantity nova quantidade de pedra
     */
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            this.quantity = 0;
        } else {
            this.quantity = quantity;
        }
    }

    /**
     * Adiciona uma quantidade de pedra a esta pilha.
     *
     * @param amount quantidade a somar (deve ser positiva)
     */
    public void add(int amount) {
        if (amount > 0) {
            this.quantity += amount;
        }
    }

    /**
     * Acao executada quando o jogador interage com a pedra no mundo (tecla E).
     */
    @Override
    public void onInteract() {
        System.out.println("[Interacao] Recolheste " + quantity + " unidade(s) de " + getName() + ".");
    }

    /**
     * Devolve uma representacao textual do item de pedra.
     *
     * @return texto com o nome e a quantidade do item
     */
    @Override
    public String toString() {
        return getName() + " (Quantidade: " + quantity + ")";
    }
}
