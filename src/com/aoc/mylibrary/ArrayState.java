package com.aoc.mylibrary;

import java.util.Arrays;

public class ArrayState {
    private int[] array;

    public ArrayState(int[] a){
        array = a;
    }

    public int[] getArray(){
        return array.clone();
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

    public String toString(){
        return Arrays.toString(array);
    }
}