package com.yun.hashmap;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: __yun
 * @Date: 2024/09/30/10:01
 * @Description:
 */
public class HashMapTest{
    private HashMap<Integer,Integer> hashMap;
    @Before
    public void init() throws ClassNotFoundException {
        hashMap = new HashMap<>();
    }
    @Test
    public void testPut() {
        hashMap.put(1,1);
        hashMap.put(2,2);
        hashMap.put(7,7);
        hashMap.put(15,15);
        hashMap.put(5,5);
        hashMap.put(6,6);
        hashMap.put(7,7);
        hashMap.put(8,8);
        hashMap.put(9,9);
        for (int i = 0; i < hashMap.size(); i++) {
            System.out.println(hashMap.get(i + 1));
        }
    }
    @Test
    public void testDelete() {
        hashMap.put(1,1);
        hashMap.put(2,2);
        hashMap.put(3,3);
        for (int i = 0; i < hashMap.size(); i++) {
            System.out.println(hashMap.get(i + 1));
        }
        hashMap.delete(2,2);
        System.out.println(hashMap.get(1));
        System.out.println(hashMap.get(3));
    }
}