/*
Henry Anderson
Advent of Code 2015 Day 7 https://adventofcode.com/2015/day/7
Input: https://adventofcode.com/2015/day/7/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // The desired problem to solve
    static int PART;
    static Scanner sc;
    // The file containing the puzzle input
    static String FILE_NAME = "input.txt";
    public static void main(String args[]) {
        if (args.length < 1 || args.length > 2){
            System.out.println("Wrong number of arguments");
            return;
        }
        // Take in the part and file name
        try {
            PART = Integer.parseInt(args[0]);
        } catch (Exception e){}
        if (!(PART == 1 || PART == 2)){
            System.out.println("Part can only be 1 or 2");
            return;
        }
        if (args.length == 2){
            FILE_NAME = args[1];
        }
        try {
            sc = new Scanner(new File(FILE_NAME));
        }catch (Exception e){
            System.out.println("File not found");
            return;
        }
        // The current values of all wires
        HashMap<String,Integer> hashmap = new HashMap<>();
        // The list of wires still needing calculation
        ArrayList<String> gates = new ArrayList<>();

        // Take in all input
        while (sc.hasNext()){
            gates.add(sc.nextLine());
        }

        // Loop through every line
        for (int i=0; i<gates.size();){
            // Split the line
            String[] line = gates.get(i).split(" ");
            // Direct assignment
            if (line.length == 3){
                // The value of the wire
                int num;
                // Check where to get the value from
                if (Character.isDigit(line[0].charAt(0))){
                    // Number
                    num = Integer.parseInt(line[0]);
                }else if (hashmap.containsKey(line[0])){
                    // Wire
                    num = hashmap.get(line[0]);
                }else{
                    // Unknown value, delay calculation
                    gates.add(gates.remove(i));
                    continue;
                }
                // Add the value to the hashmap
                hashmap.put(line[2],num);
            // NOT gate
            }else if (line.length == 4){
                // The value of the wire
                int num;
                // Check where to get the value from
                if (Character.isDigit(line[1].charAt(0))){
                    // Number
                    num = Integer.parseInt(line[1]);
                }else if (hashmap.containsKey(line[1])){
                    // Wire
                    num = hashmap.get(line[1]);
                }else{
                    // Unknown value, delay calculation
                    gates.add(gates.remove(i));
                    continue;
                }
                // Add the new value to the hashmap
                hashmap.put(line[3],~num);
            }else{
                // The value of the first wire
                int one;
                // Check where to get the value from
                if (Character.isDigit(line[0].charAt(0))){
                    // Number
                    one = Integer.parseInt(line[0]);
                }else if (hashmap.containsKey(line[0])){
                    // Wire
                    one = hashmap.get(line[0]);
                }else{
                    // Unknown value, delay calculation
                    gates.add(gates.remove(i));
                    continue;
                }
                // The value of the second wire
                int two;
                // Check where to get the value from
                if (Character.isDigit(line[2].charAt(0))){
                    // Number
                    two = Integer.parseInt(line[2]);
                }else if (hashmap.containsKey(line[2])){
                    // Wire
                    two = hashmap.get(line[2]);
                }else{
                    // Unknown value, delay calculation
                    gates.add(gates.remove(i));
                    continue;
                }
                // Check the gate type
                if (line[1].equals("AND")){
                    // AND
                    hashmap.put(line[4], one & two);
                }else if (line[1].equals("OR")){
                    // OR
                    hashmap.put(line[4], one | two);
                }else if (line[1].equals("LSHIFT")){
                    // LSHIFT
                    hashmap.put(line[4], one << two);
                }else{
                    // RSHIFT with adjustment for ints that have been left-shifted
                    hashmap.put(line[4], (one & 65535) >>> two);
                }
            }
            // Value found, proceed to the next input
            ++i;
        }

        // Part 1 finds the value of a
        // Part 2 finds the value of a after assigning a to be and re-evaluating
        if (PART == 2){
            // Take the value of a
            int a = hashmap.get("a");
            // Clear all wires
            hashmap.clear();
            // Assign the value of a to b
            hashmap.put("b",a);
            // Loop through every gate because the gates have already been ordered
            // in a possible evaluation order
            for (String string : gates){
                // Split the line
                String[] line = string.split(" ");
                // b's assignment has already been overwritten
                if (line[line.length-1].equals("b")){
                    continue;
                }
                // Direct assignment
                if (line.length == 3){
                    // Check where to get the value from
                    if (Character.isDigit(line[0].charAt(0))){
                        // Assign the number
                        hashmap.put(line[2],Integer.parseInt(line[0]));
                    }else{
                        // Assign the wire
                        hashmap.put(line[2],hashmap.get(line[0]));
                    }
                // NOT gate
                }else if (line[0].equals("NOT")){
                    // Check where to get the value from
                    if (Character.isDigit(line[1].charAt(0))){
                        // Assign the number
                        hashmap.put(line[3],~Integer.parseInt(line[1]));
                    }else{
                        // Assign the wire
                        hashmap.put(line[3],~hashmap.get(line[1]));
                    }
                }else{
                    // The value of the first wire
                    int one;
                    // Check where to get the value from
                    if (Character.isDigit(line[0].charAt(0))){
                        // Number
                        one = Integer.parseInt(line[0]);
                    }else{
                        // Wire
                        one = hashmap.get(line[0]);
                    }
                    // The value of the second wire
                    int two;
                    // Check where to get the value from
                    if (Character.isDigit(line[2].charAt(0))){
                        // Number
                        two = Integer.parseInt(line[2]);
                    }else{
                        // Wire
                        two = hashmap.get(line[2]);
                    }
                    // Check the gate type
                    if (line[1].equals("AND")){
                        // AND
                        hashmap.put(line[4], one & two);
                    }else if (line[1].equals("OR")){
                        // OR
                        hashmap.put(line[4], one | two);
                    }else if (line[1].equals("LSHIFT")){
                        // LSHIFT
                        hashmap.put(line[4], one << two);
                    }else{
                        // RSHIFT
                        hashmap.put(line[4], (one & 65535) >>> two);
                    }
                }
            }
        }

        // Print the value of wire a
        System.out.println(hashmap.get("a") & 65535);
    }
}