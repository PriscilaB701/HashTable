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


    }
}
