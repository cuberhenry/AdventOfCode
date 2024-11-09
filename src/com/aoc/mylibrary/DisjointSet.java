package com.aoc.mylibrary;

import java.util.HashMap;

public class DisjointSet<T> {
    private HashMap<T,T> parents = new HashMap<>();
    private HashMap<T,Integer> sizes = new HashMap<>();
    private int size = 0;

    public T find(T a){
        if (!parents.containsKey(a)){
            return null;
        }
        if (parents.get(a).equals(a)){
            return a;
        }
        return find(parents.get(a));
    }

    public void union(T a, T b){
        T aRoot = find(a);
        T bRoot = find(b);
        if (aRoot == null){
            if (bRoot == null){
                ++size;
                parents.put(a,a);
                if (!a.equals(b)){
                    parents.put(b,a);
                    sizes.put(a,2);
                }else{
                    sizes.put(a,1);
                }
            }else{
                parents.put(a,bRoot);
                sizes.put(bRoot,sizes.get(bRoot)+1);
            }
        }else if (bRoot == null){
            parents.put(b,aRoot);
            sizes.put(aRoot,sizes.get(aRoot)+1);
        }else if (!aRoot.equals(bRoot)){
            --size;
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
        return size;
    }

    public int setSize(T a){
        return sizes.get(find(a));
    }

    public boolean contains(T a){
        return parents.containsKey(a);
    }
}