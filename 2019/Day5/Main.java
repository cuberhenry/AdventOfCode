/*
Henry Anderson
Advent of Code 2019 Day 5 https://adventofcode.com/2019/day/5
Input: https://adventofcode.com/2019/day/5/input
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
        // Take in the input
        String[] split = sc.nextLine().split(",");
        // The program
        int[] program = new int[split.length];
        for (int i=0; i<split.length; ++i){
            program[i] = Integer.parseInt(split[i]);
        }

        // The only input for the program
        int input = 1;
        // The output produced by the program
        ArrayList<Integer> output = new ArrayList<>();

        // Part 1 finds the last output with input 1
        // Part 2 finds the last output with input 2
        if (PART == 2){
            input = 5;
        }

        /*
         * Parameter Modes:
         * 
         * 0: Position Mode, position
         * 1: Immediate Mode, value
         */

        // Run the program
        int i = 0;
        // Halt (OP): OP == 99
        while (program[i] != 99){
            // Perform the operation
            switch(program[i]%100){
                // Add (BAOP A B C): OP == 01, C = A + B
                case 1 -> {
                    int A = program[i+1];
                    if (program[i] / 100 % 10 == 0){
                        A = program[A];
                    }
                    int B = program[i+2];
                    if (program[i] / 1000 % 10 == 0){
                        B = program[B];
                    }
                    program[program[i+3]] = A + B;
                    i += 4;
                }
                // Multiply (BAOP A B C): OP == 02, C = A * B
                case 2 -> {
                    int A = program[i+1];
                    if (program[i] / 100 % 10 == 0){
                        A = program[A];
                    }
                    int B = program[i+2];
                    if (program[i] / 1000 % 10 == 0){
                        B = program[B];
                    }
                    program[program[i+3]] = A * B;
                    i += 4;
                }
                // Load (OP A): OP == 03, A = input
                case 3 -> {
                    program[program[i+1]] = input;
                    i += 2;
                }
                // Output (AOP A): OP == 04, output = A
                case 4 -> {
                    int A = program[i+1];
                    if (program[i] / 100 % 10 == 0){
                        A = program[A];
                    }
                    output.add(A);
                    i += 2;
                }
                // Jump If True (BAOP A B): OP == 05, if A != 0 jumpto B
                case 5 -> {
                    int A = program[i+1];
                    if (program[i] / 100 % 10 == 0){
                        A = program[A];
                    }
                    int B = program[i+2];
                    if (program[i] / 1000 % 10 == 0){
                        B = program[B];
                    }
                    if (A != 0){
                        i = B;
                    }else{
                        i += 3;
                    }
                }
                // Jump If False (BAOP A B): OP == 06, if A == 0 jumpto B
                case 6 -> {
                    int A = program[i+1];
                    if (program[i] / 100 % 10 == 0){
                        A = program[A];
                    }
                    int B = program[i+2];
                    if (program[i] / 1000 % 10 == 0){
                        B = program[B];
                    }
                    if (A == 0){
                        i = B;
                    }else{
                        i += 3;
                    }
                }
                // Less Than (BAOP A B C): OP == 07, C = A < B
                case 7 -> {
                    int A = program[i+1];
                    if (program[i] / 100 % 10 == 0){
                        A = program[A];
                    }
                    int B = program[i+2];
                    if (program[i] / 1000 % 10 == 0){
                        B = program[B];
                    }
                    program[program[i+3]] = (A < B ? 1 : 0);
                    i += 4;
                }
                // Equal To (BAOP A B C): OP == 08, C = A == B
                case 8 -> {
                    int A = program[i+1];
                    if (program[i] / 100 % 10 == 0){
                        A = program[A];
                    }
                    int B = program[i+2];
                    if (program[i] / 1000 % 10 == 0){
                        B = program[B];
                    }
                    program[program[i+3]] = (A == B ? 1 : 0);
                    i += 4;
                }
                // Error: no opcode found
                default -> {
                    System.out.println("Error, no opcode found");
                    break;
                }
            }
        }

        // Print the answer
        System.out.println(output.getLast());
    }
}