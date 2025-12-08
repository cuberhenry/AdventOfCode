package com.aoc.mylibrary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class DisjointSet<T> {
    private HashMap<T,T> parents = new HashMap<>();
    private HashMap<T,Integer> sizes = new HashMap<>();

    public T find(T a){
        if (!parents.containsKey(a)){
            return null;
        }
        while (!parents.get(a).equals(a)){
            a = parents.get(a);
        }
        return a;
    }

    public void add(T a){
        if (!parents.containsKey(a)){
            parents.put(a,a);
            sizes.put(a,1);
        }
    }

    public void union(T a, T b){
        if (!parents.containsKey(a)){
            add(a);
        }
        if (!parents.containsKey(b)){
            add(b);
        }
        T aRoot = find(a);
        T bRoot = find(b);
        if (!aRoot.equals(bRoot)){
            parents.put(aRoot,bRoot);
            sizes.put(bRoot,sizes.get(aRoot) + sizes.get(bRoot));
            sizes.remove(aRoot);
        }
    }

    public boolean connected(T a, T b){
        T aRoot = find(a);
        T bRoot = find(b);
        return aRoot.equals(bRoot) && !(aRoot == null || bRoot == null);
    }

    public int size(){
        return sizes.size();
    }

    public int setSize(T a){
        return sizes.get(find(a));
    }

    public int totalSize(){
        return parents.size();
    }

    public boolean contains(T a){
        return parents.containsKey(a);
    }

    public ArrayList<Integer> orderedSizes(){
        ArrayList<Integer> orderedSizes = new ArrayList<>();
        HashSet<T> added = new HashSet<>();

        for (T key : sizes.keySet()){
            boolean unjoined = true;
            for (T add : added){
                if (connected(add,key)){
                    unjoined = false;
                    break;
                }
            }
            if (unjoined){
                orderedSizes.add(sizes.get(key));
                added.add(key);
            }
        }

        Collections.sort(orderedSizes);
        Collections.reverse(orderedSizes);
        return orderedSizes;
    }
}