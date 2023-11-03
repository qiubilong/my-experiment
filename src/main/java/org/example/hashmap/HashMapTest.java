package org.example.hashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HashMapTest {

    public static void main(String[] args) {
        HashMap<Integer,String> map = new HashMap<>();

        ConcurrentHashMap<Integer,String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put(22,"ddd");


        map.put(11,"aa");
        map.put(22,"bb");
        map.put(33,"cc");
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            if(entry.getKey() ==33){
                map.remove(22);
            }
            //map.put(55,"dd");
            System.out.println(entry);
        }
    }
}
