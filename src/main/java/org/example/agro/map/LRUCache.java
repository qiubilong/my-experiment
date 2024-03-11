package org.example.agro.map;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    int size;

    public LRUCache(int size) {
        super(size,0.75f,true);
        this.size = size;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        if(this.size() > size){
            System.out.println("removeEldestEntry,"+eldest);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(3);
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            cache.put(i, random.nextInt());
            cache.getOrDefault(0,-1);
        }
        System.out.println(cache);
    }
}
