package jogo.gameobject.item;

/**
 * Representa uma pocao de vida consumivel pelo jogador.
 * <p>
 * Este item permite regenerar pontos de vida vitais para enfrentar
 * os inimigos durante as ondas noturnas na floresta.
 * </p>
 */
public class HealthPotion extends Item {

    /**
     * Quantidade de pontos de vida restaurados por esta pocao.
     */
    private int healAmount;

    /**
     * Indica se a pocao ja foi consumida pelo jogador.
     */
    private boolean used;

    /**
     * Constroi uma pocao de vida com um valor especifico de cura.
     *
     * @param healAmount pontos de vida a restaurar (deve ser positivo)
     */
    public HealthPotion(int healAmount) {
        super("Pocao de Vida");
        if (healAmount < 0) {
            this.healAmount = 0;
        } else {
            this.healAmount = healAmount;
        }
        this.used = false;
    }

    /**
     * Constroi uma pocao de vida padrao que cura 25 pontos de vida.
     */
    public HealthPotion() {
        this(25);
    }

    /**
     * Devolve a quantidade de cura proporcionada pela pocao.
     *
     * @return pontos de vida a curar
     */
    public int getHealAmount() {
        return healAmount;
    }

    /**
     * Define a quantidade de cura da pocao, nao permitindo valores negativos.
     *
     * @param healAmount novos pontos de vida a curar
     */
    public void setHealAmount(int healAmount) {
        if (healAmount < 0) {
            this.healAmount = 0;
        } else {
            this.healAmount = healAmount;
        }
    }

    /**
     * Indica se a pocao ja foi gasta.
     *
     * @return true se ja foi consumida, false caso ainda esteja pronta a usar
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * Define o estado de utilizacao da pocao.
     *
     * @param used true se foi consumida, false caso contrario
     */
    public void setUsed(boolean used) {
        this.used = used;
    }

    /**
     * Acao executada quando o jogador interage com a pocao no mundo (tecla E).
     * <p>
     * Se a pocao ainda nao tiver sido utilizada, aplica o seu efeito curativo
     * e altera o seu estado interno para consumida.
     * </p>
     */
    @Override
    public void onInteract() {
        if (used) {
            System.out.println("[Interacao] Este frasco de " + getName() + " ja esta vazio!");
        } else {
            used = true;
            System.out.println("[Interacao] Bebeste " + getName() + "! Recuperaste " + healAmount + " pontos de vida.");
        }
    }

    /**
     * Devolve uma representacao textual da pocao de vida.
     *
     * @return texto com o nome, poder de cura e estado de consumo
     */
    @Override
    public String toString() {
        return getName() + " (Cura: " + healAmount + " HP, Usada: " + (used ? "Sim" : "Nao") + ")";
    }
}
