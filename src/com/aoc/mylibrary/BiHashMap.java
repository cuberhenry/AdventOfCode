package com.aoc.mylibrary;

import java.util.HashMap;
public class BiHashMap<T,E> {
    private HashMap<T,E> forward = new HashMap<>();
    private HashMap<E,T> backward = new HashMap<>();

    public void put(T key, E value){
        if (forward.containsKey(key)){
            backward.remove(forward.get(key));
        }
        if (backward.containsKey(value) && backward.get(value) != key){
            forward.remove(backward.get(value));
        }

        forward.put(key,value);
        backward.put(value,key);
    }

    public boolean containsKey(T key){
        return forward.containsKey(key);
    }

    public E get(T key){
        return forward.get(key);
    }

    public T getReverse(E key){
        return backward.get(key);
    }
}