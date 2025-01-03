package com.aoc.mylibrary;

import java.util.Arrays;

public class ArrayState implements Comparable<ArrayState> {
    final private int[] array;

    public ArrayState(int[] a){
        array = a;
    }

    public int[] getArray(){
        return array.clone();
    }
    
    @Override
    public int compareTo(ArrayState other){
        int[] otherArray = other.getArray();
        for (int i=0; i<array.length; ++i){
            if (array[i] < otherArray[i]){
                return -1;
            }
            if (array[i] > otherArray[i]){
                return 1;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        return Arrays.equals(array,((ArrayState)o).getArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    public int distance(ArrayState other){
        return distance(other.getArray());
    }

    public int distance(int[] other){
        int distance = 0;
        for (int i=0; i<array.length; ++i){
            distance += Math.abs(array[i]-other[i]);
        }
        return distance;
    }

    public String toString(){
        return Arrays.toString(array);
    }
}