/*
Henry Anderson
Advent of Code 2016 Day 12 https://adventofcode.com/2016/day/12
Input: https://adventofcode.com/2016/day/12/input
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
        // The program to be run
        ArrayList<String> instructions = new ArrayList<>();
        // The four registers
        int[] registers = new int[4];

        // Part 1 finds the value of register a
        // Part 2 does the same but after giving c an initial value of 1
        if (PART == 2){
            registers[2] = 1;
        }

        // Take in the instructions
        while (sc.hasNext()){
            instructions.add(sc.nextLine());
        }

        // Until the program ends
        for (int i=0; i<instructions.size(); ++i){
            // Grab the current instruction
            String[] instruction = instructions.get(i).split(" ");
            // Perform the instruction
            switch (instruction[0]){
                // Copy
                case "cpy" -> {
                    // Can handle either a register or a value
                    if (Character.isDigit(instruction[1].charAt(0))){
                        // Set the register to the value
                        registers[instruction[2].charAt(0)-'a'] = Integer.parseInt(instruction[1]);
                    }else{
                        // Set the register to the other register
                        registers[instruction[2].charAt(0)-'a'] = registers[instruction[1].charAt(0)-'a'];
                    }
                }
                // Increment
                case "inc" -> {
                    // Increment the value at the register
                    ++registers[instruction[1].charAt(0)-'a'];
                }
                // Decrement
                case "dec" -> {
                    // Decrement the value at the register
                    --registers[instruction[1].charAt(0)-'a'];
                }
                // Jump Not Zero
                case "jnz" -> {
                    // The number to be compared to 0
                    int num;
                    // Can handle either a register or a value
                    if (Character.isDigit(instruction[1].charAt(0))){
                        // Set the number to the value
                        num = Integer.parseInt(instruction[1]);
                    }else{
                        // Set the number to the register
                        num = registers[instruction[1].charAt(0)-'a'];
                    }
                    // If the number isn't 0
                    if (num != 0){
                        // Jump using the given offset
                        i += Integer.parseInt(instruction[2])-1;
                    }
                }
            }
        }

        // Print the answer
        System.out.println(registers[0]);
    }
}