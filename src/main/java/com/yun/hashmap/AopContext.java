package com.yun.hashmap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/10/02/10:27
 * @Description:
 */
public class AopContext<K,V> {
    private K key;
    private V value;

    public AopContext() {
    }

    public AopContext(K key, V value) {
        this.key = key;
        this.value = value;
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
}
