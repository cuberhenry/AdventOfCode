package com.aoc.mylibrary;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;

public class GameOfLife {
    private HashSet<ArrayState> alive;
    private ArrayList<int[]> bounds = new ArrayList<>();

    private HashSet<Integer> create = new HashSet<>();
    private HashSet<Integer> persist = new HashSet<>();

    private boolean expand = true;

    public GameOfLife(GameOfLife other){
        alive = other.getAlive();
        bounds = other.getBounds();
        create = other.getCreate();
        persist = other.getPersist();
        expand = other.getExpand();
    }

    public GameOfLife(int dims){
        alive = new HashSet<>();
        for (int i=0; i<dims; ++i){
            bounds.add(new int[] {Integer.MAX_VALUE,Integer.MIN_VALUE});
        }
    }

    public GameOfLife(HashSet<ArrayState> a, int dims){
        this(dims);
        alive = a;
    }

    public HashSet<ArrayState> getAlive(){
        return alive;
    }

    public ArrayList<int[]> getBounds(){
        return bounds;
    }

    public HashSet<Integer> getCreate(){
        return create;
    }

    public HashSet<Integer> getPersist(){
        return persist;
    }

    public boolean getExpand(){
        return expand;
    }

    public int size(){
        return alive.size();
    }

    public void setExpand(boolean e){
        expand = e;
    }

    public void add(ArrayState a){
        alive.add(a);
        int[] array = a.getArray();
        for (int i=0; i<array.length; ++i){
            bounds.get(i)[0] = Math.min(bounds.get(i)[0],array[i]);
            bounds.get(i)[1] = Math.max(bounds.get(i)[1],array[i]);
        }
    }

    public void add(int[] a){
        add(new ArrayState(a));
    }

    public void addCreate(int n){
        create.add(n);
    }

    public void addPersist(int n){
        persist.add(n);
    }

    public void step(){
        HashSet<ArrayState> newState = new HashSet<>();
        ArrayList<int[]> newBounds = new ArrayList<>();
        int[] currPoint = new int[bounds.size()];
        for (int i=0; i<bounds.size(); ++i){
            newBounds.add(new int[] {Integer.MAX_VALUE,Integer.MIN_VALUE});
            currPoint[i] = bounds.get(i)[0];
            if (expand){
                --currPoint[i];
            }
        }

        while (true){
            int neighbors = 0;
            int max = (int)Math.pow(3,currPoint.length);
            for (int i=0; i<max; ++i){
                if (i == max / 2){
                    continue;
                }
                int[] newPoint = currPoint.clone();
                int num = i;
                for (int j=0; j<newPoint.length; ++j){
                    newPoint[j] += num % 3 - 1;
                    num /= 3;
                }

                if (alive.contains(new ArrayState(newPoint))){
                    ++neighbors;
                }
            }

            ArrayState thisState = new ArrayState(currPoint.clone());
            boolean current = alive.contains(thisState);
            if (current && persist.contains(neighbors) || !current && create.contains(neighbors)){
                newState.add(thisState);
                for (int i=0; i<currPoint.length; ++i){
                    newBounds.get(i)[0] = Math.min(newBounds.get(i)[0],currPoint[i]);
                    newBounds.get(i)[1] = Math.max(newBounds.get(i)[1],currPoint[i]);
                }
            }

            int index = 0;
            while (index < currPoint.length && (expand ? (currPoint[index] > bounds.get(index)[1]) : (currPoint[index] == bounds.get(index)[1]))){
                currPoint[index] = bounds.get(index)[0];
                if (expand){
                    --currPoint[index];
                }
                ++index;
            }

            if (index == currPoint.length){
                break;
            }


            ++currPoint[index];
        }

        alive = newState;
        bounds = newBounds;
    }
}