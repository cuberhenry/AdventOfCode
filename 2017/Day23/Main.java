/*
Henry Anderson
Advent of Code 2017 Day 23 https://adventofcode.com/2017/day/23
Input: https://adventofcode.com/2017/day/23/input
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
        // The array of registers
        long[] registers = new long[8];
        // The list of instructions
        ArrayList<String> assembly = new ArrayList<>();
        // Take in and save all of the instructions
        while (sc.hasNext()){
            assembly.add(sc.nextLine());
        }

        // Part 1 finds the number of times the multiply operation is called
        // Part 2 finds the final value of h when a is 1
        if (PART == 2){
            int num = Integer.parseInt(assembly.get(0).split(" ")[2]);
            int h = 0;
            for (int b=num*100+100000; b<=num*100+117000; b += 17){
                boolean prime = true;
                for (int d=2; d<b; ++d){
                    if (b%d == 0){
                        prime = false;
                        break;
                    }
                }
                if (!prime){
                    ++h;
                }
            }
            System.out.println(h);
            return;
        }

        // The answer to the problem
        long answer = 0;
        // Loop through all instructions until the index drops out
        for (int i=0; i<assembly.size() && i >= 0; ++i){
            // Take in the instruction
            String[] line = assembly.get(i).split(" ");
            // The first value
            long x;
            // Decide its value
            if (Character.isAlphabetic(line[1].charAt(0))){
                // Register
                x = registers[line[1].charAt(0)-'a'];
            }else{
                // Numeric value
                x = Long.parseLong(line[1]);
            }
            // The second value
            long y = 0;
            // Make sure there is a second value
            if (line.length > 2){
                // Decide its value
                if (Character.isAlphabetic(line[2].charAt(0))){
                    // Register
                    y = registers[line[2].charAt(0)-'a'];
                }else{
                    // Numeric value
                    y = Long.parseLong(line[2]);
                }
            }

            // Perform the instruction
            switch (line[0]){
                // Set a register to a value
                case "set" -> {
                    registers[line[1].charAt(0)-'a'] = y;
                }
                // Subtract a value from a register
                case "sub" -> {
                    registers[line[1].charAt(0)-'a'] -= y;
                }
                // Multiply a value to a register
                case "mul" -> {
                    registers[line[1].charAt(0)-'a'] *= y;
                    ++answer;
                }
                // Jump Not Zero
                case "jnz" -> {
                    // If the value is not equal to zero
                    if (x != 0){
                        // Change the instruction index
                        i += y-1;
                    }
                }
            }
        }

        // Print the answer
        if (PART == 1){
            System.out.println(answer);
        }
    }
}