package com.yun.hashmap;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/10/07/10:22
 * @Description:
 */
public class ConcurrentHashMapTest {
    private Map<Integer, Integer> hashMap;

    @Before
    public void init() throws ClassNotFoundException {
        hashMap = new ConcurrentHashMap<>();
    }

    @Test
    public void testPut() throws IOException {
        int j = 0;
        new Thread(() -> {
            hashMap.put(1, 1);
            hashMap.put(2, 2);
            hashMap.put(7, 7);
        }, "thread-" + j++).start();
        new Thread(() -> {
            hashMap.put(15, 15);
            hashMap.put(5, 5);
            hashMap.put(6, 6);
        }, "thread-" + j++).start();
        new Thread(() -> {
            hashMap.put(7, 7);
            hashMap.put(8, 8);
            hashMap.put(9, 9);
        }, "thread-" + j++).start();
        new Thread(() -> {
            hashMap.put(23, 23);
            hashMap.put(31, 31);
            hashMap.put(89, 89);
        }, "thread-" + j++).start();
        System.in.read();
        int t = 1;
    }
    @Test
    public void testDelete() throws IOException {
        int j = 0;
        new Thread(() -> {
            hashMap.put(1, 1);
            hashMap.put(2, 2);
            hashMap.put(3, 3);
        }, "thread-" + j++).start();
        new Thread(() -> {
            hashMap.put(15, 15);
            hashMap.put(5, 5);
            hashMap.put(6, 6);
        }, "thread-" + j++).start();
        new Thread(() -> {
            hashMap.put(7, 7);
            hashMap.put(8, 8);
            hashMap.put(9, 9);
        }, "thread-" + j++).start();
        new Thread(() -> {
            hashMap.delete(7, 7);
            hashMap.delete(8, 8);
            hashMap.delete(9, 9);
        }, "thread-" + j++).start();
        System.in.read();
    }

}