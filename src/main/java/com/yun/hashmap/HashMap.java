package com.yun.hashmap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/30/7:26
 * @Description:
 */
public class HashMap<K, V> {
    private Entry<K, V>[] map;
    private final int DEFAULT_CAPACITY = 8;
    private final float DEFAULT_LOAD_FACTOR = 0.75F;
    private int hash;
    private int size = 0;
    private int capacity;
    private int[] listSize;
    private float loadFactor;

    public HashMap() {
        capacity = DEFAULT_CAPACITY;
        map = new Entry[capacity];
        hash = capacity - 1;
        listSize = new int[capacity];
        loadFactor = DEFAULT_LOAD_FACTOR;
    }

    public void put(K key, V value) {
        if (key == null || value == null) {
            throw new RuntimeException("键值不能为空..");
        }
        int position = key.hashCode() & hash;
        put(map,key,value);
        listSize[position]++;
        size++;
        if (size >= loadFactor * capacity || listSize[position] >= 8) {
            rehash();
        }
    }

    private void put(Entry[] entries, K key, V value) {
        int position = key.hashCode() & hash;
        if (entries[position] == null) {
            entries[position] = new Entry<>();
        }
        Entry<K, V> cur = entries[position];
        while (cur.getNext() != null) {
            if (cur.getKey() == key) {
                cur.setValue(value);
                return;
            }
            cur = cur.getNext();
        }
        Entry<K, V> kvEntry = new Entry<>();
        kvEntry.setKey(key);
        kvEntry.setValue(value);
        cur.setNext(kvEntry);
    }

    public V get(K key) {
        if (key == null) {
            throw new RuntimeException("键不能为空..");
        }
        int position = key.hashCode() & hash;
        Entry<K, V> cur = map[position];
        if (cur == null) {
            return null;
        }
        while (cur.getKey() != key) {
            if (cur.getNext() == null) {
                return null;
            }
            cur = cur.getNext();
        }
        return cur.getValue();
    }

    public int size() {
        return size;
    }

    public void delete(K key, V value) {
        if (key == null || value == null) {
            throw new RuntimeException("键值不能为空..");
        }
        int position = key.hashCode() & hash;
        Entry<K, V> cur = map[position];
        Entry<K, V> pre = cur;
        if (cur == null) {
            return;
        }
        while (cur.getKey() != key) {
            if (cur.getNext() == null) {
                return;
            }
            pre = cur;
            cur = cur.getNext();
        }
        pre.setNext(cur.getNext());
        listSize[position]--;
        size--;
    }

    private void rehash() {
        int newCapacity = capacity * 2;
        int newHash = newCapacity - 1;
        listSize = new int[newCapacity];
        int newSize = 0;
        Entry<K, V>[] kvEntry = new Entry[newCapacity];
        for (int i = 0; i < capacity; i++) {
            if (map[i] == null) {
                continue;
            }
            Entry<K, V> pre = map[i];
            while (pre.getNext() != null) {
                Entry<K, V> cur = pre.getNext();
                int position = cur.getKey().hashCode() & newHash;
                if (kvEntry[position] == null){
                    kvEntry[position] = new Entry<>();
                }
                Entry<K, V> listCur = kvEntry[position];
                while (listCur.getNext() != null){
                    listCur = listCur.getNext();
                }
                listCur.setNext(cur);
                pre = pre.getNext();
                listSize[position]++;
                newSize++;
            }
        }
        map = kvEntry;
        capacity = newCapacity;
        size = newSize;
        hash = newHash;
    }

}
