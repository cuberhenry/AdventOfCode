package com.aoc.mylibrary;

/**
 * A stack with a fixed size.
 * 
 * @author Henry Anderson
 * @version 1.0
 */
public class FixedStack<T> {
    /**
     * The stack of elements
     */
    private Object[] elements;
    /**
     * The number of elements in the stack
     */
    private int size = 0;

    /**
     * Constructor with a given capacity.
     */
    public FixedStack(int capacity){
        elements = new Object[capacity];
    }

    /**
     * Gets the capacity of the stack.
     * 
     * @return  the stack's capacity
     */
    public int capacity(){
        return elements.length;
    }
    
    /**
     * Adds a new element to the stack if not full.
     * 
     * @param element   the element to be added
     * @return          <code>true</code> if an element was added;
     *                  <code>false</code> otherwise
     */
    public boolean push(T element){
        if (size == elements.length){
            return false;
        }
        elements[size++] = element;
        return true;
    }

    /**
     * Retrieves the top element from the stack.
     * 
     * @return  the top element of the stack, or <code>null</code> if empty
     */
    @SuppressWarnings("unchecked")
    public T peek(){
        if (empty()){
            return null;
        }
        return (T)elements[size-1];
    }

    /**
     * Removes the top element from the stack.
     * 
     * @return  the top element of the stack, or <code>null</code> if empty
     */
    @SuppressWarnings("unchecked")
    public T pop(){
        if (empty()){
            return null;
        }
        return (T)elements[--size];
    }

    /**
     * Returns the size of the stack.
     * 
     * @return  the number of elements in the stack
     */
    public int size(){
        return size;
    }

    /**
     * Returns whether the stack is empty.
     * 
     * @return  whether the stack is empty
     */
    public boolean empty(){
        return size == 0;
    }

    /**
     * Returns whether the stack is full.
     * 
     * @return  whether size equals capacity
     */
    public boolean full(){
        return size == elements.length;
    }

    /**
     * Empties the stack.
     */
    public void clear(){
        size = 0;
    }
    
    /**
     * Returns whether the given element is contained in the stack.
     * 
     * @param element   the element to search for
     * @return          whether the stack contains the element
     */
    public boolean contains(T element){
        for (int i=0; i<size; ++i){
            if (elements[i].equals(element)){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether all elements in the stack are the same.
     * 
     * @return  whether all elements in the stack are the same
     */
    public boolean uniform(){
        for (int i=1; i<size; ++i){
            if (!elements[i].equals(elements[0])){
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the <code>String</code> representation of the stack.
     */
    @Override
    public String toString(){
        String s = "[";
        for (int i=0; i<size-1; ++i){
            s = s + elements[i] + ", ";
        }
        if (!empty()){
            s = s + elements[size-1];
        }
        return s + "]";
    }
}