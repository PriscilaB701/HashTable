package models;

public abstract class HashTable {
    protected int capacity = 32;
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

            String[] old = table[index];
            String[] newChain = new String[old.length + 1];
            for (int i = 0; i < old.length; i++) {
                newChain[i] = old[i];
            }
            newChain[old.length] = key;
            table[index] = newChain;
        }

        size++;
        if (getLoadFactor() > loadFactor){
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
        System.out.println("\n>>> Redimensionando tabela de " + capacity + " para " + (capacity * 2));
        String[][] oldTable = table;

        capacity *= 2;
        table = new String[capacity][];
        size = 0;

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
        for (int i = 0; i < capacity; i++) {
            int count = (table[i] == null) ? 0 : table[i].length;
            System.out.println("Posição " + i + ": " + count + " chave(s)");
        }
    }
}
