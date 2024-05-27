package org.example.ok.agro.codetop;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxuegui
 * @since 2024/4/17
 * https://mp.weixin.qq.com/s/5trONGYWn0oiqSLqZqqvBw
 */
public class LRU缓存 {

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        lRUCache.get(1);    // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        lRUCache.get(2);    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        lRUCache.get(1);    // 返回 -1 (未找到)
        lRUCache.get(3);    // 返回 3
        lRUCache.get(4);    // 返回 4
    }

    public static class LRUCache{

        private int capacity;
        private Map<Integer,Entry> map;
        private Entry head;
        private Entry tail;

        public LRUCache(int capacity) {
            this.capacity = capacity;
            this.map = new HashMap<>();
            this.head = new Entry(0,0);
            this.tail = new Entry(0,0);

            this.head.next = this.tail;
            this.tail.prev = this.head;
        }

        public int get(int key){
            Entry entry = map.get(key);
            if(entry == null){
                return -1;
            }
            moveToHead(entry);
            System.out.println(entry);
            return entry.val;
        }
        public void put(int key, int val){
            Entry old = map.get(key);
            if(old == null){
                Entry entry = new Entry(key,val);
                map.put(key,entry);
                addToHead(entry);
                if(map.size() > capacity){
                    removeTail();
                }
            }else {
                old.val = val;
                moveToHead(old);
            }

            System.out.println(map.values());
        }

        private void moveToHead(Entry entry){
            Entry prev = entry.prev;
            if(prev!=null){
                prev.next = entry.next;
                entry.next.prev = prev;
            }
            addToHead(entry);
        }
        private void addToHead(Entry entry){
            entry.next = head.next;
            entry.prev = head;

            head.next.prev = entry;
            head.next = entry;
        }

        private void removeTail(){
            Entry before = tail.prev;
            map.remove(before.key);

            before.prev.next = tail;
            tail.prev = before.prev;
        }
    }

   @Data
   public static class Entry{
        private int key;
        private int val;
        private Entry prev;
        private Entry next;

       public Entry(Integer key, Integer val) {
           this.key = key;
           this.val = val;
       }

       @Override
       public String toString() {
           return "Entry{" +
                   "key=" + key +
                   ", val=" + val +
                   '}';
       }
   }
}
