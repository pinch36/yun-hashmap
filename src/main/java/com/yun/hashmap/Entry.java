package com.yun.hashmap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/30/7:31
 * @Description:
 */
public class Entry<K,V> {
    private K key;
    private V value;
    private Entry<K,V> next;

    public Entry() {
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Entry<K, V> getNext() {
        return next;
    }

    public void setNext(Entry<K, V> next) {
        this.next = next;
    }
}
