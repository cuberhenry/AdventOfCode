/*
Henry Anderson
Advent of Code 2019 Day 7 https://adventofcode.com/2019/day/7
Input: https://adventofcode.com/2019/day/7/input
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
        int[] programOriginal = new int[split.length];
        for (int i=0; i<split.length; ++i){
            programOriginal[i] = Integer.parseInt(split[i]);
        }

        // Find all permutations of the phase settings
        ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();
        permutations.add(new ArrayList<>());
        permutations.get(0).add(0);

        // Part 1 uses phase settings 0 - 4
        // Part 2 uses phase settings 5 - 9
        if (PART == 2){
            permutations.get(0).set(0,5);
        }

        for (int i=1; i<5; ++i){
            int setting = i;

            if (PART == 2){
                setting += 5;
            }

            ArrayList<ArrayList<Integer>> newPermutations = new ArrayList<>();
            for (int j=0; j<permutations.size(); ++j){
                for (int k=0; k<=permutations.get(j).size(); ++k){
                    ArrayList<Integer> list = new ArrayList<>(permutations.get(j));
                    list.add(k,setting);
                    newPermutations.add(list);
                }
            }
            permutations = newPermutations;
        }

        // The highest result
        int max = Integer.MIN_VALUE;
        // Loop through every permutation
        for (ArrayList<Integer> input : permutations){
            // The result of the amplification
            int output = 0;

            // Part 1 finds the highest amplification through one go
            if (PART == 1){
                while (!input.isEmpty()){
                    input.add(1,output);

                    int[] program = Arrays.copyOf(programOriginal,programOriginal.length);

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
                                program[program[i+1]] = input.remove(0);
                                i += 2;
                            }
                            // Output (AOP A): OP == 04, output = A
                            case 4 -> {
                                int A = program[i+1];
                                if (program[i] / 100 % 10 == 0){
                                    A = program[A];
                                }
                                output = A;
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
                }
            }

            // Part 2 finds the highest amplification from a feedback loop
            if (PART == 2){
                // Save five programs, one for each amplifier
                int[][] programs = new int[5][programOriginal.length];
                ArrayList<ArrayList<Integer>> inputs = new ArrayList<>();
                for (int i=0; i<5; ++i){
                    programs[i] = Arrays.copyOf(programOriginal,programOriginal.length);
                    inputs.add(new ArrayList<Integer>());
                    inputs.get(i).add(input.get(i));
                }
                int[] indexes = new int[5];
                // Loop until the first program finishes
                while (programs[0][indexes[0]] % 100 != 99){
                    // Loop through each amplifier
                    for (int j=0; j<5; ++j){
                        inputs.get(j).add(output);
                        // Load the information
                        int[] program = programs[j];
                        int i = indexes[j];
                        boolean keep = true;
                        // Continue until the program finishes or it's waiting on an input
                        while (program[i] % 100 != 99 && keep){
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
                                    if (inputs.get(j).isEmpty()){
                                        keep = false;
                                        break;
                                    }
                                    program[program[i+1]] = inputs.get(j).remove(0);
                                    i += 2;
                                }
                                // Output (AOP A): OP == 04, output = A
                                case 4 -> {
                                    int A = program[i+1];
                                    if (program[i] / 100 % 10 == 0){
                                        A = program[A];
                                    }
                                    output = A;
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
                                    System.out.println("Error, no opcode found: " + i + " " + program[i]);
                                    return;
                                }
                            }
                        }
                        // Save the current index
                        indexes[j] = i;
                    }
                }
            }

            // Save the highest output
            max = Math.max(max,output);
        }

        // Print the answer
        System.out.println(max);
    }
}