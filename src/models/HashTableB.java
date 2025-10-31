package models;

public class HashTableB extends HashTable {
        @Override
        public int hash(String key) {
            int hash = 0;
            int p = 31;
            int m = capacity;

            for (int i = 0; i < key.length(); i++) {
                hash = (hash * p + key.charAt(i)) % m;
            }
            return hash;
        }
    }
