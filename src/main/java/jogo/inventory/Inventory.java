package jogo.inventory;

import jogo.gameobject.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa o inventario de itens de um personagem ou entidade no jogo.
 * <p>
 * O inventario possui uma capacidade fixa em slots (minimo de 6 slots)
 * e faz a gestao dos itens recolhidos, lancando excecoes especificas
 * quando ocorrem erros (ex.: inventario cheio ou item em falta).
 * </p>
 */
public class Inventory {

    /**
     * Capacidade padrao inicial de slots do inventario.
     */
    public static final int DEFAULT_CAPACITY = 8;

    /**
     * Capacidade minima obrigatoria de slots.
     */
    public static final int MIN_CAPACITY = 6;

    /**
     * Capacidade maxima de itens que este inventario pode conter.
     */
    private final int capacity;

    /**
     * Lista interna onde estao guardados os itens guardados no inventario.
     */
    private final List<Item> items;

    /**
     * Constroi um inventario com uma capacidade especifica de slots.
     *
     * @param capacity numero maximo de slots (deve ser no minimo 6)
     * @throws IllegalArgumentException se a capacidade for inferior a {@link #MIN_CAPACITY}
     */
    public Inventory(int capacity) {
        if (capacity < MIN_CAPACITY) {
            throw new IllegalArgumentException("A capacidade do inventario deve ser de pelo menos "
                    + MIN_CAPACITY + " slots. Recebido: " + capacity);
        }
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    /**
     * Constroi um inventario com a capacidade padrao de 8 slots.
     */
    public Inventory() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Adiciona um item ao inventario.
     *
     * @param item o item a adicionar (nao pode ser nulo)
     * @throws InventoryFullException se o inventario ja tiver todos os slots ocupados
     * @throws IllegalArgumentException se o item fornecido for nulo
     */
    public void addItem(Item item) throws InventoryFullException {
        if (item == null) {
            throw new IllegalArgumentException("Nao e possivel adicionar um item nulo ao inventario.");
        }
        if (isFull()) {
            throw new InventoryFullException("O inventario esta cheio! Capacidade maxima: "
                    + capacity + " slots.");
        }
        items.add(item);
    }

    /**
     * Remove um determinado item do inventario.
     *
     * @param item o item a remover
     * @throws ItemNotFoundException se o item nao estiver presente no inventario
     * @throws IllegalArgumentException se o item fornecido for nulo
     */
    public void removeItem(Item item) throws ItemNotFoundException {
        if (item == null || !items.contains(item)) {
            throw new ItemNotFoundException("O item nao foi encontrado no inventario: "
                    + (item != null ? item.getName() : "null"));
        }
        items.remove(item);
    }

    /**
     * Remove e devolve o item presente no slot com o indice indicado.
     *
     * @param index o indice do slot (de 0 ate size - 1)
     * @return o item removido
     * @throws ItemNotFoundException se o indice for invalido ou nao existir item no slot
     */
    public Item removeItem(int index) throws ItemNotFoundException {
        if (index < 0 || index >= items.size()) {
            throw new ItemNotFoundException("Slot invalido ou vazio no inventario: " + index);
        }
        return items.remove(index);
    }

    /**
     * Devolve o item presente no indice especificado, sem o remover.
     *
     * @param index o indice do slot a consultar
     * @return o item correspondente ao slot
     * @throws ItemNotFoundException se o indice for invalido ou estiver vazio
     */
    public Item getItem(int index) throws ItemNotFoundException {
        if (index < 0 || index >= items.size()) {
            throw new ItemNotFoundException("Nao existe nenhum item no slot " + index);
        }
        return items.get(index);
    }

    /**
     * Verifica se o item especificado esta presente no inventario.
     *
     * @param item o item a procurar
     * @return true se o item estiver no inventario, false caso contrario
     */
    public boolean contains(Item item) {
        return item != null && items.contains(item);
    }

    /**
     * Verifica se o inventario atingiu a sua capacidade maxima.
     *
     * @return true se o inventario estiver cheio, false caso contrario
     */
    public boolean isFull() {
        return items.size() >= capacity;
    }

    /**
     * Verifica se o inventario esta completamente vazio.
     *
     * @return true se nao houver nenhum item, false caso contrario
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Devolve o numero total de slots do inventario.
     *
     * @return capacidade maxima de slots
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Devolve o numero de slots atualmente ocupados por itens.
     *
     * @return quantidade de itens no inventario
     */
    public int getSize() {
        return items.size();
    }

    /**
     * Devolve uma vista nao modificavel da lista de itens guardados.
     * <p>
     * Protege a lista interna contra alteracoes diretas sem passar
     * pelos metodos {@link #addItem(Item)} e {@link #removeItem(Item)}.
     * </p>
     *
     * @return lista imutavel com os itens atuais do inventario
     */
    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Remove todos os itens do inventario, esvaziando-o.
     */
    public void clear() {
        items.clear();
    }

    /**
     * Devolve uma representacao textual detalhada do inventario e dos seus slots.
     *
     * @return texto com o estado e conteudo de cada slot do inventario
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Inventario (").append(items.size()).append("/").append(capacity).append(" slots):\n");
        for (int i = 0; i < capacity; i++) {
            sb.append("  [Slot ").append(i).append("] ");
            if (i < items.size()) {
                sb.append(items.get(i).toString());
            } else {
                sb.append("(Vazio)");
            }
            if (i < capacity - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
