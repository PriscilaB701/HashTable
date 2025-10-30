package utils;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {
    private String[] names;
    private int count;

    public FileReader(String filename, int maxSize) {
        names = new String[maxSize];
        count = 0;
        readFile(filename);
    }

    private void readFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null && count < names.length) {
                line = line.trim();
                if (!line.isEmpty()) {
                    names[count++] = line;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public String[] getNames() {
        return names;
    }

    public int getCount() {
        return count;
    }
}
