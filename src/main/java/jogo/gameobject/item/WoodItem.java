package jogo.gameobject.item;

/**
 * Representa um recurso de madeira recolhido na floresta.
 * <p>
 * A madeira e um material fundamental de crafting utilizado para construir
 * defesas basicas e ferramentas para sobreviver as noites.
 * </p>
 */
public class WoodItem extends Item {

    /**
     * Quantidade de unidades de madeira nesta pilha de itens.
     */
    private int quantity;

    /**
     * Constroi um item de madeira com a quantidade inicial indicada.
     *
     * @param quantity a quantidade inicial de madeira (deve ser maior ou igual a zero)
     */
    public WoodItem(int quantity) {
        super("Madeira");
        if (quantity < 0) {
            this.quantity = 0;
        } else {
            this.quantity = quantity;
        }
    }

    /**
     * Constroi um item de madeira com uma unidade por omissao.
     */
    public WoodItem() {
        this(1);
    }

    /**
     * Devolve a quantidade de madeira disponivel.
     *
     * @return a quantidade de madeira
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Define a quantidade de madeira, garantindo que nao fica negativa.
     *
     * @param quantity nova quantidade de madeira
     */
    public void setQuantity(int quantity) {
        if (quantity < 0) {
            this.quantity = 0;
        } else {
            this.quantity = quantity;
        }
    }

    /**
     * Adiciona uma determinada quantidade de madeira a esta pilha.
     *
     * @param amount quantidade a somar (deve ser positiva)
     */
    public void add(int amount) {
        if (amount > 0) {
            this.quantity += amount;
        }
    }

    /**
     * Acao executada quando o jogador interage com a madeira no mundo (tecla E).
     */
    @Override
    public void onInteract() {
        System.out.println("[Interacao] Apanhaste " + quantity + " unidade(s) de " + getName() + ".");
    }

    /**
     * Devolve uma representacao textual do item de madeira.
     *
     * @return texto com o nome e a quantidade do item
     */
    @Override
    public String toString() {
        return getName() + " (Quantidade: " + quantity + ")";
    }
}
