package com.aoc.mylibrary;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class SubsetList<E> implements Iterable<List<E>> {
    final private List<E> list;

    public SubsetList(){
        list = new ArrayList<>();
    }

    public SubsetList(List<E> l){
        list = l;
    }

    public void add(E item){
        list.add(item);
    }

    public Iterator<List<E>> iterator() {
        return new SubsetIterator();
    }
    
    private class SubsetIterator implements Iterator<List<E>> {
        private int index = 0;

        public boolean hasNext(){
            return index != (int)Math.pow(2,list.size());
        }

        public List<E> next(){
            List<E> subset = new ArrayList<>();
            for (int i=0; i<list.size(); ++i){
                if (index / (int)Math.pow(2,i) % 2 == 1){
                    subset.add(list.get(i));
                }
            }
            ++index;
            return subset;
        }
    }
}