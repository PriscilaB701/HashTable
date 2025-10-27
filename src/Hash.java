import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;


abstract class HashTable {
    protected final int capacity;
    protected final ArrayList<LinkedList<String>> buckets;
    protected long collisions;
    protected long insertTimeNano;
    protected long searchTimeNano;

    public HashTable(int capacity) {
        this.capacity = capacity;
        this.buckets = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) buckets.add(new LinkedList<>());
        this.collisions = 0;
        this.insertTimeNano = 0;
        this.searchTimeNano = 0;
    }


    protected abstract int hashFunction(String key);

    // indice final (garante intervalo 0..capacity-1)
    protected int indexFor(String key) {
        int h = hashFunction(key);
        return Math.floorMod(h, capacity);
    }


    public void insertAll(List<String> keys) {
        long start = System.nanoTime();
        for (String k : keys) insert(k);
        long end = System.nanoTime();
        this.insertTimeNano = end - start;
    }

    public void insert(String key) {
        int idx = indexFor(key);
        LinkedList<String> bucket = buckets.get(idx);
        if (!bucket.isEmpty()) collisions++;
        bucket.add(key);
    }

    public boolean contains(String key) {
        int idx = indexFor(key);
        LinkedList<String> bucket = buckets.get(idx);
        return bucket.contains(key);
    }

    public void measureSearchTime(List<String> queries) {
        long start = System.nanoTime();
        for (String q : queries) contains(q);
        long end = System.nanoTime();
        this.searchTimeNano = end - start;
    }

    public long getCollisions() { return collisions; }
    public long getInsertTimeNano() { return insertTimeNano; }
    public long getSearchTimeNano() { return searchTimeNano; }

    public int[] getDistribution() {
        int[] dist = new int[capacity];
        for (int i = 0; i < capacity; i++) dist[i] = buckets.get(i).size();
        return dist;
    }

    public void reset() {
        this.collisions = 0;
        this.insertTimeNano = 0;
        this.searchTimeNano = 0;
        for (LinkedList<String> b : buckets) b.clear();
    }
}


    // IMPLEMENTAR AS HASH TABLES


public class Hash {
    private static final int TABLE_CAPACITY = 32;

    private static List<String> readNames(String filename) {
        List<String> names = new ArrayList<>();
        Path path = Paths.get(filename);
        System.out.println("Tentando ler arquivo em: " + path.toAbsolutePath());
        if (!Files.exists(path)) {
            System.err.println("Arquivo não existe: " + path.toAbsolutePath());
            return names;
        }
        if (!Files.isReadable(path)) {
            System.err.println("Arquivo sem permissão de leitura: " + path.toAbsolutePath());
            return names;
        }
        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : lines) {
                String name = line.trim();  // percorre cada linha, faz trim e adiciona so as linhas n vazias na lista
                if (!name.isEmpty()) names.add(name);
            }
            System.out.println("Linhas lidas: " + lines.size() + " ; nomes válidos: " + names.size());
        } catch (IOException e) {
            System.err.println("Erro lendo arquivo: " + e.getMessage());
            e.printStackTrace();
        }
        return names;
    }



    //gera o conjunto de consultas (queries) que o programa usa para medir o tempo de busca, medem o desempenho de busca
    private static List<String> sampleQueries(List<String> names, int sampleSize, Random rnd) {
        List<String> sample = new ArrayList<>(sampleSize);
        if (names.isEmpty()) return sample;
        int n = names.size();
        if (sampleSize >= n) { sample.addAll(names); return sample; }
        Set<Integer> chosen = new HashSet<>();
        while (sample.size() < sampleSize) {
            int idx = rnd.nextInt(n);
            if (chosen.add(idx)) sample.add(names.get(idx));
        }
        return sample;
    }





    //relatorio





    public static void main(String[] args) {
        String filename = args.length > 0 ? args[0] : "female_names.txt";
        System.out.println("Lendo nomes do arquivo: " + filename);
        List<String> names = readNames(filename);
        if (names.isEmpty()) {
            System.err.println("Nenhum nome encontrado no arquivo. Verifique caminho e permissões.");
            System.exit(1);
        }

        //renomear dps de acordo com as tables q a gente criar
        HashTable1 ht1 = new HashTable1(TABLE_CAPACITY);
        HashTable2 ht2 = new HashTable2(TABLE_CAPACITY);

        System.out.println("Inserindo nomes nas tabelas (encadeamento exterior)...");
        //ht1 ht2.insertAll

        Random rnd = new Random(42);
        List<String> queries = sampleQueries(names, Math.min(1000, names.size()), rnd);

        System.out.println("Realizando buscas de teste...");
        //criar report p relatorio comparativo

    }
}
}