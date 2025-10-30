package models;

public class HashTableA extends HashTable {

    @Override
    public int hash(String key) {
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum += key.charAt(i);
        }
        return sum % capacity;
    }
}
