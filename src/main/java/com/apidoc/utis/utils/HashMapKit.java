package com.apidoc.utis.utils;

import java.util.HashMap;

/**
 * @Description: HashMap装备，小工具
 * @Author: peng.liu
 * @CreateDate: 2018/3/28 15:12
 */
public final class HashMapKit<K, V> extends HashMap<K, V> {

    public HashMapKit(int size) {
        super(size);
    }

    public HashMapKit() {
        super();
    }

    public HashMapKit add(K key, V value) {
        put(key, value);
        return this;
    }

}
