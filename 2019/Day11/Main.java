/*
Henry Anderson
Advent of Code 2019 Day 11 https://adventofcode.com/2019/day/11
Input: https://adventofcode.com/2019/day/11/input
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

        // Whether the next action should be paint or move
        boolean move = false;
        // HashMap for the painted squares
        HashMap<String,Integer> painted = new HashMap<>();
        // The coordinates of the robot
        int x = 0;
        int y = 0;
        int dir = 0;
        // The bounds of the white paint
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Part 1 finds the number of squares painted starting on black
        // Part 2 finds the message painted starting on white
        if (PART == 2){
            painted.put("0 0",1);
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

                    long input = 0;
                    if (painted.containsKey(x + " " + y)){
                        input = painted.get(x + " " + y);
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

                    if (move){
                        dir = (int)(dir + 2 * A + 3) % 4;
                        switch(dir){
                            case 0 -> {++y;}
                            case 1 -> {++x;}
                            case 2 -> {--y;}
                            case 3 -> {--x;}
                        }
                    }else{
                        painted.put(x + " " + y,(int)A);
                        if (A == 1){
                            minX = Math.min(minX,x);
                            minY = Math.min(minY,y);
                            maxX = Math.max(maxX,x);
                            maxY = Math.max(maxY,y);
                        }
                    }
                    move = !move;

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

        if (PART == 1){
            // Print the answer
            System.out.println(painted.size());
        }

        if (PART == 2){
            // Find the message
            boolean[][] grid = new boolean[maxY-minY+1][maxX-minX+1];
            for (String key : painted.keySet()){
                if (painted.get(key) == 1){
                    String[] coords = key.split(" ");
                    grid[Integer.parseInt(coords[1])-minY][Integer.parseInt(coords[0])-minX] = true;
                }
            }

            // Print the message
            for (boolean[] line : grid){
                for (boolean bool : line){
                    if (bool){
                        System.out.print("#");
                    }else{
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
    }
}