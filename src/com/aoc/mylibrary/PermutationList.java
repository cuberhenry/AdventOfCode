package com.aoc.mylibrary;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class PermutationList<E> implements Iterable<List<E>> {
    final private List<E> list;

    public PermutationList(){
        list = new ArrayList<>();
    }

    public void add(E item){
        list.add(item);
    }

    public void add(int index, E item){
        list.add(index,item);
    }

    public boolean contains(E item){
        return list.contains(item);
    }

    public int size(){
        return list.size();
    }

    public E get(int index){
        return list.get(index);
    }

    public int indexOf(E item){
        return list.indexOf(item);
    }

    public Iterator<List<E>> iterator() {
        List<List<E>> permutations = new ArrayList<>();
        permutations.add(new ArrayList<>());
        permutations.getFirst().add(list.getFirst());

        for (int i=1; i<list.size(); ++i){
            List<List<E>> newPerms = new ArrayList<>();

            for (int j=permutations.size()-1; j>=0; --j){
                for (int k=permutations.get(j).size(); k>=0; --k){
                    ArrayList<E> newPerm = new ArrayList<>(permutations.get(j));
                    newPerm.add(k,list.get(i));
                    newPerms.add(newPerm);
                }
            }

            permutations = newPerms;
        }

        return permutations.iterator();
    }
}