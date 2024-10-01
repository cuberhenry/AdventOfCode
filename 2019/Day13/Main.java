/*
Henry Anderson
Advent of Code 2019 Day 13 https://adventofcode.com/2019/day/13
Input: https://adventofcode.com/2019/day/13/input
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
        long[] program = new long[split.length];
        for (int i=0; i<split.length; ++i){
            program[i] = Long.parseLong(split[i]);
        }

        // HashMap for registers beyond the program
        HashMap<Long,Long> memory = new HashMap<>();

        // The number of blocks
        int blocks = 0;
        // The number of outputs
        int outputs = 0;
        // The x position of the most recent x input
        int x = 0;
        // Where the ball is
        int ballX = 0;
        // Where the paddle is
        int paddleX = 0;
        // The current score
        long score = 0;

        // Part 1 finds the number of blocks placed
        // Part 2 finds the final score after destroying all the blocks
        if (PART == 2){
            program[0] = 2;
        }

        /*
         * Parameter Modes:
         * 
         * 0: Position Mode, position
         * 1: Immediate Mode, value
         * 2: Relative Mode, relative position
         */

        // Run the program
        int i = 0;
        long relativeBase = 0;
        // Halt (OP): OP == 99
        while (program[i] != 99){
            // Perform the operation
            switch((int)(program[i]%100)){
                // Add (BAOP A B C): OP == 01, C = A + B
                case 1 -> {
                    long A = program[i+1];
                    if (program[i] / 100 % 10 != 1){
                        if (program[i] / 100 % 10 == 2){
                            A += relativeBase;
                        }
                        if (A >= program.length){
                            if (memory.containsKey(A)){
                                A = memory.get(A);
                            }else{
                                A = 0;
                            }
                        }else{
                            A = program[(int)A];
                        }
                    }
                    long B = program[i+2];
                    if (program[i] / 1000 % 10 != 1){
                        if (program[i] / 1000 % 10 == 2){
                            B += relativeBase;
                        }
                        if (B >= program.length){
                            if (memory.containsKey(B)){
                                B = memory.get(B);
                            }else{
                                B = 0;
                            }
                        }else{
                            B = program[(int)B];
                        }
                    }
                    long C = program[i+3];
                    if (program[i] / 10000 % 10 == 2){
                        C += relativeBase;
                    }
                    if (C < program.length){
                        program[(int)C] = A + B;
                    }else{
                        memory.put(C,A+B);
                    }
                    i += 4;
                }
                // Multiply (BAOP A B C): OP == 02, C = A * B
                case 2 -> {
                    long A = program[i+1];
                    if (program[i] / 100 % 10 != 1){
                        if (program[i] / 100 % 10 == 2){
                            A += relativeBase;
                        }
                        if (A >= program.length){
                            if (memory.containsKey(A)){
                                A = memory.get(A);
                            }else{
                                A = 0;
                            }
                        }else{
                            A = program[(int)A];
                        }
                    }
                    long B = program[i+2];
                    if (program[i] / 1000 % 10 != 1){
                        if (program[i] / 1000 % 10 == 2){
                            B += relativeBase;
                        }
                        if (B >= program.length){
                            if (memory.containsKey(B)){
                                B = memory.get(B);
                            }else{
                                B = 0;
                            }
                        }else{
                            B = program[(int)B];
                        }
                    }
                    long C = program[i+3];
                    if (program[i] / 10000 % 10 == 2){
                        C += relativeBase;
                    }
                    if (C < program.length){
                        program[(int)C] = A * B;
                    }else{
                        memory.put(C,A*B);
                    }
                    i += 4;
                }
                // Load (OP A): OP == 03, A = input
                case 3 -> {
                    long A = program[i+1];
                    if (program[i] / 100 % 10 == 2){
                        A += relativeBase;
                    }
                    
                    // Move the joystick to get under the ball
                    long input = 0;
                    if (ballX < paddleX){
                        input = -1;
                    }else if (ballX > paddleX){
                        input = 1;
                    }

                    if (A < program.length){
                        program[(int)A] = input;
                    }else{
                        memory.put(A,input);
                    }
                    i += 2;
                }
                // Output (AOP A): OP == 04, output = A
                case 4 -> {
                    long A = program[i+1];
                    if (program[i] / 100 % 10 != 1){
                        if (program[i] / 100 % 10 == 2){
                            A += relativeBase;
                        }
                        if (A >= program.length){
                            if (memory.containsKey(A)){
                                A = memory.get(A);
                            }else{
                                A = 0;
                            }
                        }else{
                            A = program[(int)A];
                        }
                    }

                    // If this is the last input
                    if (outputs % 3 == 2){
                        // Increase the number of blocks if it's a block
                        if (A == 2){
                            ++blocks;
                        }
                        // Update the corresponding data
                        if (x == -1){
                            score = A;
                        }else if (A == 4){
                            ballX = x;
                        }else if (A == 3){
                            paddleX = x;
                        }
                        outputs = 0;
                    }else{
                        // Save the x for later
                        if (outputs == 0){
                            x = (int)A;
                        }
                        ++outputs;
                    }

                    i += 2;
                }
                // Jump If True (BAOP A B): OP == 05, if A != 0 jumpto B
                case 5 -> {
                    long A = program[i+1];
                    if (program[i] / 100 % 10 != 1){
                        if (program[i] / 100 % 10 == 2){
                            A += relativeBase;
                        }
                        if (A >= program.length){
                            if (memory.containsKey(A)){
                                A = memory.get(A);
                            }else{
                                A = 0;
                            }
                        }else{
                            A = program[(int)A];
                        }
                    }
                    long B = program[i+2];
                    if (program[i] / 1000 % 10 != 1){
                        if (program[i] / 1000 % 10 == 2){
                            B += relativeBase;
                        }
                        if (B >= program.length){
                            if (memory.containsKey(B)){
                                B = memory.get(B);
                            }else{
                                B = 0;
                            }
                        }else{
                            B = program[(int)B];
                        }
                    }
                    if (A != 0){
                        i = (int)B;
                    }else{
                        i += 3;
                    }
                }
                // Jump If False (BAOP A B): OP == 06, if A == 0 jumpto B
                case 6 -> {
                    long A = program[i+1];
                    if (program[i] / 100 % 10 != 1){
                        if (program[i] / 100 % 10 == 2){
                            A += relativeBase;
                        }
                        if (A >= program.length){
                            if (memory.containsKey(A)){
                                A = memory.get(A);
                            }else{
                                A = 0;
                            }
                        }else{
                            A = program[(int)A];
                        }
                    }
                    long B = program[i+2];
                    if (program[i] / 1000 % 10 != 1){
                        if (program[i] / 1000 % 10 == 2){
                            B += relativeBase;
                        }
                        if (B >= program.length){
                            if (memory.containsKey(B)){
                                B = memory.get(B);
                            }else{
                                B = 0;
                            }
                        }else{
                            B = program[(int)B];
                        }
                    }
                    if (A == 0){
                        i = (int)B;
                    }else{
                        i += 3;
                    }
                }
                // Less Than (BAOP A B C): OP == 07, C = A < B
                case 7 -> {
                    long A = program[i+1];
                    if (program[i] / 100 % 10 != 1){
                        if (program[i] / 100 % 10 == 2){
                            A += relativeBase;
                        }
                        if (A >= program.length){
                            if (memory.containsKey(A)){
                                A = memory.get(A);
                            }else{
                                A = 0;
                            }
                        }else{
                            A = program[(int)A];
                        }
                    }
                    long B = program[i+2];
                    if (program[i] / 1000 % 10 != 1){
                        if (program[i] / 1000 % 10 == 2){
                            B += relativeBase;
                        }
                        if (B >= program.length){
                            if (memory.containsKey(B)){
                                B = memory.get(B);
                            }else{
                                B = 0;
                            }
                        }else{
                            B = program[(int)B];
                        }
                    }
                    long C = program[i+3];
                    if (program[i] / 10000 % 10 == 2){
                        C += relativeBase;
                    }
                    if (C < program.length){
                        program[(int)C] = (A < B ? 1 : 0);
                    }else{
                        memory.put(C,(A < B ? 1L : 0));
                    }
                    i += 4;
                }
                // Equal To (BAOP A B C): OP == 08, C = A == B
                case 8 -> {
                    long A = program[i+1];
                    if (program[i] / 100 % 10 != 1){
                        if (program[i] / 100 % 10 == 2){
                            A += relativeBase;
                        }
                        if (A >= program.length){
                            if (memory.containsKey(A)){
                                A = memory.get(A);
                            }else{
                                A = 0;
                            }
                        }else{
                            A = program[(int)A];
                        }
                    }
                    long B = program[i+2];
                    if (program[i] / 1000 % 10 != 1){
                        if (program[i] / 1000 % 10 == 2){
                            B += relativeBase;
                        }
                        if (B >= program.length){
                            if (memory.containsKey(B)){
                                B = memory.get(B);
                            }else{
                                B = 0;
                            }
                        }else{
                            B = program[(int)B];
                        }
                    }
                    long C = program[i+3];
                    if (program[i] / 10000 % 10 == 2){
                        C += relativeBase;
                    }
                    if (C < program.length){
                        program[(int)C] = (A == B ? 1 : 0);
                    }else{
                        memory.put(C,(A == B ? 1L : 0));
                    }
                    i += 4;
                }
                // Adjust Rel Base (AOP A): OP == 09, relativeBase += A
                case 9 -> {
                    long A = program[i+1];
                    if (program[i] / 100 % 10 != 1){
                        if (program[i] / 100 % 10 == 2){
                            A += relativeBase;
                        }
                        if (A >= program.length){
                            if (memory.containsKey(A)){
                                A = memory.get(A);
                            }else{
                                A = 0;
                            }
                        }else{
                            A = program[(int)A];
                        }
                    }
                    relativeBase += A;
                    i += 2;
                }
                // Error: no opcode found
                default -> {
                    System.out.println("Error, no opcode found");
                    break;
                }
            }
        }

        // Print the answer
        if (PART == 1){
            System.out.println(blocks);
        }

        if (PART == 2){
            System.out.println(score);
        }
    }
}