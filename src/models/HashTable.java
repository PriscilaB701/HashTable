package models;

public abstract class HashTable {
    // capacidade inicial menor, e limite máximo de 32
    protected int capacity = 8;
    protected final int MAX_CAPACITY = 32;
    protected String[][] table;
    protected int size = 0;
    protected final double loadFactor = 0.75;

    public HashTable() {
        table = new String[capacity][];
    }

    public abstract int hash(String key);

    public void insert(String key) {
        int index = hash(key);

        if (table[index] == null) {
            table[index] = new String[1];
            table[index][0] = key;
        } else {
            // colisão: adiciona na "lista encadeada"
            String[] old = table[index];
            String[] newChain = new String[old.length + 1];
            for (int i = 0; i < old.length; i++) {
                newChain[i] = old[i];
            }
            newChain[old.length] = key;
            table[index] = newChain;
        }

        size++;

        // faz rehash se o fator de carga passar do limite e ainda não atingiu o máximo
        if (getLoadFactor() > loadFactor && capacity < MAX_CAPACITY) {
            rehash();
        }
    }

    public boolean search(String key) {
        int index = hash(key);
        if (table[index] == null) return false;
        for (String s : table[index]) {
            if (s.equals(key)) return true;
        }
        return false;
    }

    public double getLoadFactor() {
        return (double) size / capacity;
    }

    private void rehash() {
        if (capacity >= MAX_CAPACITY) {
            System.out.println("⚠️ Capacidade máxima atingida (" + capacity + "). Rehash ignorado.");
            return;
        }

        int newCapacity = Math.min(capacity * 2, MAX_CAPACITY);
        System.out.println("\n>>> Redimensionando tabela de " + capacity + " para " + newCapacity);

        String[][] oldTable = table;
        capacity = newCapacity;
        table = new String[capacity][];
        size = 0;

        // reinserindo os elementos antigos
        for (String[] chain : oldTable) {
            if (chain != null) {
                for (String key : chain) {
                    insert(key);
                }
            }
        }
    }

    public int countCollisions() {
        int collisions = 0;
        for (String[] chain : table) {
            if (chain != null && chain.length > 1) {
                collisions += chain.length - 1;
            }
        }
        return collisions;
    }

    public void printDistribution() {
        System.out.println("\nDistribuição das chaves (capacidade atual: " + capacity + "):");
        for (int i = 0; i < capacity; i++) {
            int count = (table[i] == null) ? 0 : table[i].length;
            System.out.println("Posição " + i + ": " + count + " chave(s)");
        }
    }
}
