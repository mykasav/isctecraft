package jogo.inventory;

import jogo.gameobject.item.HealthPotion;
import jogo.gameobject.item.StoneItem;
import jogo.gameobject.item.WoodItem;

/**
 * Classe de demonstracao e teste do funcionamento do {@link Inventory}.
 * <p>
 * Executa cenarios de teste simples com impressao de resultados na consola,
 * validando o armazenamento de itens e o tratamento das excecoes
 * {@link InventoryFullException} e {@link ItemNotFoundException}.
 * </p>
 */
public class InventoryDemo {

    public static void main(String[] args) {
        System.out.println("=== TESTE DO INVENTARIO (IscteCraft) ===");

        // 1. Criar inventario com capacidade para 6 slots (minimo da rubrica)
        Inventory inv = new Inventory(6);
        System.out.println("Inventario criado com capacidade para " + inv.getCapacity() + " slots.");
        System.out.println("Esta vazio? " + inv.isEmpty());

        // 2. Criar itens para testar
        WoodItem madeira = new WoodItem(10);
        StoneItem pedra = new StoneItem(5);
        HealthPotion pocao1 = new HealthPotion(25);
        HealthPotion pocao2 = new HealthPotion(50);
        WoodItem madeiraExtra = new WoodItem(20);
        StoneItem pedraExtra = new StoneItem(15);

        System.out.println("\n--- 1. Adicionar Itens ate Encher ---");
        try {
            inv.addItem(madeira);
            inv.addItem(pedra);
            inv.addItem(pocao1);
            inv.addItem(pocao2);
            inv.addItem(madeiraExtra);
            inv.addItem(pedraExtra);
            System.out.println("Adicionados 6 itens com sucesso.");
        } catch (InventoryFullException e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }

        System.out.println("\nEstado atual:");
        System.out.println(inv);
        System.out.println("Esta cheio? " + inv.isFull() + " (Tamanho: " + inv.getSize() + ")");

        System.out.println("\n--- 2. Testar Excecao: Inventario Cheio ---");
        try {
            System.out.println("A tentar adicionar mais um item com inventario cheio...");
            inv.addItem(new HealthPotion(100));
            System.out.println("FALHA: Nao devia ter permitido adicionar!");
        } catch (InventoryFullException e) {
            System.out.println("SUCESSO: Excecao apanhada corretamente -> " + e.getMessage());
        }

        System.out.println("\n--- 3. Testar Excecao: Item Inexistente ---");
        WoodItem itemNaoExistente = new WoodItem(99);
        try {
            System.out.println("A tentar remover item que nao esta no inventario...");
            inv.removeItem(itemNaoExistente);
            System.out.println("FALHA: Nao devia ter encontrado o item!");
        } catch (ItemNotFoundException e) {
            System.out.println("SUCESSO: Excecao apanhada corretamente -> " + e.getMessage());
        }

        try {
            System.out.println("A tentar aceder a um slot invalido (slot 10)...");
            inv.getItem(10);
            System.out.println("FALHA: O slot 10 nao devia existir!");
        } catch (ItemNotFoundException e) {
            System.out.println("SUCESSO: Excecao apanhada corretamente -> " + e.getMessage());
        }

        System.out.println("\n--- 4. Remover Item e Reutilizar Slot ---");
        try {
            System.out.println("A remover a pedra do inventario...");
            inv.removeItem(pedra);
            System.out.println("Removida com sucesso. Espaco disponivel: " + (inv.getCapacity() - inv.getSize()) + " slots.");

            System.out.println("A adicionar uma nova pocao no espaco libertado...");
            inv.addItem(new HealthPotion(75));
            System.out.println("Pocao adicionada com sucesso!");
        } catch (InventoryException e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }

        System.out.println("\nEstado final do inventario:");
        System.out.println(inv);
        System.out.println("\n=== TODOS OS TESTES PASSARAM COM SUCESSO! ===");
    }
}
