/*
Henry Anderson
Advent of Code 2015 Day 23 https://adventofcode.com/2015/day/23
Input: https://adventofcode.com/2015/day/23/input
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
        // All of the instructions
        ArrayList<String[]> instructions = new ArrayList<>();
        // Take in all of the instructions
        while (sc.hasNext()){
            instructions.add(sc.nextLine().split(", | "));
        }
        // The value of register a
        long[] registers = new long[2];

        // Part 1 finds the value of register b after the program quits
        // Part 2 sets a to 1 first, then does the same
        if (PART == 2){
            registers[0] = 1;
        }

        // Loop until an instruction that isn't specified is looked for
        for (int index = 0; index >= 0 && index < instructions.size(); ++index){
            // Grab the instruction
            String[] instruction = instructions.get(index);
            // Switch based on the instruction
            switch (instruction[0]){
                // Half the value of the specified register
                case "hlf" -> {
                    registers[instruction[1].charAt(0) - 'a'] /= 2;
                }
                // Triple the value of the specified register
                case "tpl" -> {
                    registers[instruction[1].charAt(0) - 'a'] *= 3;
                }
                // Increment the value of the specified register
                case "inc" -> {
                    ++registers[instruction[1].charAt(0) - 'a'];
                }
                // Jump to the specified offset
                case "jmp" -> {
                    index += Integer.parseInt(instruction[1])-1;
                }
                // Jump to the specified offset if the specified
                // register's value is even
                case "jie" -> {
                    if (registers[instruction[1].charAt(0)-'a'] % 2 == 0){
                        index += Integer.parseInt(instruction[2])-1;
                    }
                }
                // Jump to the specified offset if the specified
                // register's value is 1
                case "jio" -> {
                    if (registers[instruction[1].charAt(0)-'a'] == 1){
                        index += Integer.parseInt(instruction[2])-1;
                    }
                }
            }
        }

        // Print the value of register b
        System.out.println(registers[1]);
    }
}