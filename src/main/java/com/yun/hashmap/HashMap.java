package com.yun.hashmap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/30/7:26
 * @Description:
 */
public class HashMap<K,V> {
    private Entry<K,V>[] map;
    private final int DEFAULT_CAPACITY = 8;
    private final float BALANCE_FACTOR = 0.75F;
    private int hash;
    private int size = 0;
    private int capacity;

    public HashMap() {
        map = new Entry[DEFAULT_CAPACITY];
        capacity = DEFAULT_CAPACITY;
        hash = DEFAULT_CAPACITY - 1;
    }

    public void put(K key, V value){
        if (key == null || value == null){
            throw new RuntimeException("键值不能为空..");
        }
        int position = key.hashCode() & hash;
        if (map[position] == null){
            map[position] = new Entry<>();
        }
        Entry<K, V> cur = map[position];
        while (cur.getNext() != null){
            if (cur.getKey() == key){
                cur.setValue(value);
                return;
            }
            cur = cur.getNext();
        }
        Entry<K, V> kvEntry = new Entry<>();
        kvEntry.setKey(key);
        kvEntry.setValue(value);
        cur.setNext(kvEntry);
        size++;
    }
    public V get(K key){
        if (key == null){
            throw new RuntimeException("键不能为空..");
        }
        int position = key.hashCode() & hash;
        Entry<K, V> cur = map[position];
        if (cur == null){
            return null;
        }
        while (cur.getKey() != key){
            if (cur.getNext() == null){
                return null;
            }
            cur = cur.getNext();
        }
        return cur.getValue();
    }

    public int size(){
        return size;
    }
    public void delete(K key, V value){
        if (key == null || value == null){
            throw new RuntimeException("键值不能为空..");
        }
        int position = key.hashCode() & hash;
        Entry<K, V> cur = map[position];
        Entry<K, V> pre = cur;
        if (cur == null){
            return;
        }
        while (cur.getKey() != key){
            if (cur.getNext() == null){
                return;
            }
            pre = cur;
            cur = cur.getNext();
        }
        pre.setNext(cur.getNext());
        size--;
    }
}
