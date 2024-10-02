package com.yun.hashmap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/10/02/8:13
 * @Description:
 */
public interface Map<K,V> {
    void put(K key,V value);
    void delete(K key,V value);
}
