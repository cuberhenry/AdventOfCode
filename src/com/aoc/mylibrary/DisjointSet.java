package com.aoc.mylibrary;

import java.util.HashMap;

public class DisjointSet<T> {
    private HashMap<T,T> parents = new HashMap<>();
    private HashMap<T,Integer> sizes = new HashMap<>();

    public T find(T a){
        if (!parents.containsKey(a)){
            return null;
        }
        if (parents.get(a).equals(a)){
            return a;
        }
        return find(parents.get(a));
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

    public boolean contains(T a){
        return parents.containsKey(a);
    }
}