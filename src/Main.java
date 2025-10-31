import models.HashTable;
import models.HashTableA;
import models.HashTableB;
import utils.FileReader;

public class Main {
    public static void main(String[] args) {
        FileReader reader = new FileReader("female_names.txt", 5000);
        String[] names = reader.getNames();

        HashTable tableA = new HashTableA();
        HashTable tableB = new HashTableB();

        System.out.println("=== Inserindo dados na Tabela A (hash simples) ===");
        long startA = System.nanoTime();
        for (String name : names) {
            if (name != null)
                tableA.insert(name);
        }
        long endA = System.nanoTime();

        System.out.println("=== Inserindo dados na Tabela B (hash polinomial) ===");
        long startB = System.nanoTime();
        for (String name : names) {
            if (name != null)
                tableB.insert(name);
        }
        long endB = System.nanoTime();

        System.out.println("\n================== RELATÓRIO ==================\n");

        System.out.println("\n==================Tabela A - Função Hash Simples==================:");
        System.out.println("→ Colisões: " + tableA.countCollisions());
        System.out.println("→ Tempo de inserção: " + (endA - startA) / 1_000_000.0 + " ms");
        tableA.printDistribution();

        System.out.println("\n==================Tabela B - Função Hash Polinomial==================:");
        System.out.println("→ Colisões: " + tableB.countCollisions());
        System.out.println("→ Tempo de inserção: " + (endB - startB) / 1_000_000.0 + " ms");
        tableB.printDistribution();

        //teste de busca
        System.out.println("\n=== Testando buscas ===");
        String[] testNames = {"Mary", "Sophia", "Olivia", "Nonexistent"};
        for (String name : testNames) {
            long searchStartA = System.nanoTime();
            boolean foundA = tableA.search(name);
            long searchEndA = System.nanoTime();

            long searchStartB = System.nanoTime();
            boolean foundB = tableB.search(name);
            long searchEndB = System.nanoTime();

            System.out.println("Nome: " + name);
            System.out.println("→ Tabela A: " + (foundA ? "Encontrado" : "Não encontrado") +
                    " (" + (searchEndA - searchStartA) + " ns)");
            System.out.println("→ Tabela B: " + (foundB ? "Encontrado" : "Não encontrado") +
                    " (" + (searchEndB - searchStartB) + " ns)\n");
        }
    }
}
