package com.aoc.mylibrary;
import java.util.HashSet;
import java.util.LinkedList;

public class HashQueue<T> {
    private HashSet<T> set = new HashSet<>();
    private LinkedList<T> queue = new LinkedList<>();

    public void add(T element){
        if (!set.contains(element)){
            set.add(element);
            queue.add(element);
        }
    }

    public T remove(){
        if (queue.isEmpty()){
            return null;
        }
        T element = queue.remove();
        set.remove(element);
        return element;
    }

    public boolean contains(T element){
        return set.contains(element);
    }

    public int size(){
        return queue.size();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }

    public void clear(){
        set.clear();
        queue.clear();
    }
}