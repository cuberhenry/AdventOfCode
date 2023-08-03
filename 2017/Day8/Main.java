/*
Henry Anderson
Advent of Code 2017 Day 8 https://adventofcode.com/2017/day/8
Input: https://adventofcode.com/2017/day/8/input
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
        // The collection of all registers and their values
        HashMap<String,Integer> registers = new HashMap<>();
        // The maximum value
        int max = Integer.MIN_VALUE;
        // For every instruction
        while (sc.hasNextLine()){
            String[] instruction = sc.nextLine().split(" ");
            // Add the first register if it hasn't been used yet
            if (registers.get(instruction[0]) == null){
                registers.put(instruction[0],0);
            }
            // Add the other register if it hasn't baeen used yet
            if (registers.get(instruction[4]) == null){
                registers.put(instruction[4],0);
            }
            // The name of the other register
            int other = registers.get(instruction[4]);
            // The value to compare the other register to
            int condition = Integer.parseInt(instruction[6]);
            // The value of the condition
            boolean bool = false;
            // Perform the condition
            switch (instruction[5]){
                case ">" -> {bool = other > condition;}
                case "<" -> {bool = other < condition;}
                case ">=" -> {bool = other >= condition;}
                case "<=" -> {bool = other <= condition;}
                case "==" -> {bool = other == condition;}
                case "!=" -> {bool = other != condition;}
            }

            // If the condition is true
            if (bool){
                // Collect the value of the change
                int value = Integer.parseInt(instruction[2]);
                // Reverse it if the instruction is to decrease
                if (instruction[1].equals("dec")){
                    value *= -1;
                }
                // Update the register's value
                registers.replace(instruction[0],registers.get(instruction[0])+value);

                // Part 2 finds the highest value of any register at any point
                if (PART == 2){
                    // Compare and update the current register to the max
                    max = Math.max(max,registers.get(instruction[0]));
                }
            }
        }

        // Part 1 finds the highest value of any register at the end
        if (PART == 1){
            for (int i : registers.values()){
                // Compare and update the current register to the max
                max = Math.max(max,i);
            }
        }

        // Print the answer
        System.out.println(max);
    }
}