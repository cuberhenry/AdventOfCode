package com.aoc.mylibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.aoc.mylibrary.JsonObject.ObjectType;

public class JsonObject implements Iterable<JsonObject> {
    public enum ObjectType {
        OBJECT,
        LIST,
        FLOAT,
        BOOLEAN,
        STRING
    }

    private ObjectType type;
    private HashMap<String,JsonObject> fields;
    private ArrayList<JsonObject> listItems;
    private float floatValue;
    private boolean boolValue;
    private String stringValue;

    public JsonObject(Scanner sc){
        String input = Library.trim(sc.next(),',');
        if (input.equals("{")){
            type = ObjectType.OBJECT;
            fields = new HashMap<>();
            input = Library.trim(sc.next(),',');

            while (!input.equals("}")){
                fields.put(Library.trim(Library.trim(input,':'),'"'),new JsonObject(sc));
                input = Library.trim(sc.next(),',');
            }
        }else if (input.equals("[")){
            type = ObjectType.LIST;
            listItems = new ArrayList<>();
            listItems.add(new JsonObject(sc));

            while (listItems.getLast().exists()){
                listItems.add(new JsonObject(sc));
            }
            listItems.removeLast();
        }else if (Character.isDigit(input.charAt(0))){
            type = ObjectType.FLOAT;
            floatValue = Float.parseFloat(input);
        }else if (input.equals("true") || input.equals("false")){
            type = ObjectType.BOOLEAN;
            boolValue = Boolean.parseBoolean(input);
        }else if (!input.equals("]")){
            type = ObjectType.STRING;
            stringValue = Library.trim(input,'"');
        }
    }

    public boolean exists(){
        return type != null;
    }

    public ObjectType getType(){
        return type;
    }

    public JsonObject get(String key){
        if (type != ObjectType.OBJECT){
            throw new RuntimeException("JsonObject is not Object, trying to access \"" + key + '"');
        }
        return fields.get(key);
    }

    public Iterator<JsonObject> iterator() {
        if (type != ObjectType.LIST){
            throw new RuntimeException("JsonObject is not List, trying to iterate");
        }
        return listItems.iterator();
    }

    public float getFloat(){
        if (type != ObjectType.FLOAT){
            throw new RuntimeException("JsonObject is not Float");
        }
        return floatValue;
    }

    public boolean getBoolean(){
        if (type != ObjectType.BOOLEAN){
            throw new RuntimeException("JsonObject is not Boolean");
        }
        return boolValue;
    }

    public String getString(){
        if (type != ObjectType.STRING){
            throw new RuntimeException("JsonObject is not String");
        }
        return stringValue;
    }
}