package com.aoc.mylibrary;

import java.util.Iterator;
import java.util.Scanner;

public class Input<T> implements Iterable<T>{
    final private Scanner sc;

    public Input(Scanner s){
        sc = s;
    }

    public Iterator<T> iterator() {
        return null;
    }
}