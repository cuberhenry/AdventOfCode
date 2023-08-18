/*
Henry Anderson
Advent of Code 2017 Day 18 https://adventofcode.com/2017/day/18
Input: https://adventofcode.com/2017/day/18/input
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
        long[] registers = new long[26];
        // The list of instructions
        ArrayList<String> assembly = new ArrayList<>();
        // Take in and save all of the instructions
        while (sc.hasNext()){
            assembly.add(sc.nextLine());
        }

        // The following are used for Part 2 only
        // Whether the first program is the one running
        boolean first = true;
        // The list of values sent by program 0
        ArrayList<Long> firstSent = new ArrayList<>();
        // The list of values sent by program 1
        ArrayList<Long> secondSent = new ArrayList<>();
        // The paused index of the other program
        int otherIndex = -1;
        // The array of registers for the second program
        long[] registers2 = new long[26];
        // Set p to the program ID
        registers2['p'-'a'] = 1;

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
                x = first ? registers[line[1].charAt(0)-'a'] : registers2[line[1].charAt(0)-'a'];
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
                    y = first ? registers[line[2].charAt(0)-'a'] : registers2[line[2].charAt(0)-'a'];
                }else{
                    // Numeric value
                    y = Long.parseLong(line[2]);
                }
            }

            // Perform the instruction
            switch (line[0]){
                // Sound / Send
                case "snd" -> {
                    // Part 1 plays a sound with frequency x
                    if (PART == 1){
                        answer = x;
                    }

                    // Part 2 sends value x to the other program
                    if (PART == 2){
                        // If it's the first program
                        if (first){
                            // Send the value
                            firstSent.add(x);
                        }else{
                            // Send the value
                            secondSent.add(x);
                            // Increase the number sent
                            ++answer;
                        }
                    }
                }
                // Set a register to a value
                case "set" -> {
                    if (first){
                        registers[line[1].charAt(0)-'a'] = y;
                    }else{
                        registers2[line[1].charAt(0)-'a'] = y;
                    }
                }
                // Add a value to a register
                case "add" -> {
                    if (first){
                        registers[line[1].charAt(0)-'a'] += y;
                    }else{
                        registers2[line[1].charAt(0)-'a'] += y;
                    }
                }
                // Multiply a value to a register
                case "mul" -> {
                    if (first){
                        registers[line[1].charAt(0)-'a'] *= y;
                    }else{
                        registers2[line[1].charAt(0)-'a'] *= y;
                    }
                }
                // Modulo a value from a register
                case "mod" -> {
                    if (first){
                        registers[line[1].charAt(0)-'a'] %= y;
                    }else{
                        registers2[line[1].charAt(0)-'a'] %= y;
                    }
                }
                // Recover / Receive
                case "rcv" -> {
                    // Part 1 recovers the last sound played
                    if (PART == 1){
                        // Only recover if the sound isn't 0
                        if (x != 0){
                            // Print the answer
                            System.out.println(answer);
                            return;
                        }
                    }
                    
                    // Part 2 receives a value from the other program
                    if (PART == 2){
                        if (first){
                            // If there are no values to receive
                            if (secondSent.isEmpty()){
                                // Switch to the other program
                                int helper = otherIndex;
                                otherIndex = i-1;
                                i = helper;
                                first = !first;
                            }else{
                                // Receive and store the value from the other program
                                registers[line[1].charAt(0)-'a'] = secondSent.remove(0);
                            }
                        }else{
                            // If there are no values to receive
                            if (firstSent.isEmpty()){
                                // If both programs haven't sent anything
                                if (secondSent.isEmpty()){
                                    // Print the number of times program 1 sent a value
                                    System.out.println(answer);
                                    return;
                                }
                                // Switch to the other program
                                int helper = otherIndex;
                                otherIndex = i-1;
                                i = helper;
                                first = !first;
                            }else{
                                // Receive and store the value from the other program
                                registers2[line[1].charAt(0)-'a'] = firstSent.remove(0);
                            }
                        }
                    }
                }
                // Jump Greater than Zero
                case "jgz" -> {
                    // If the value is greater than zero
                    if (x > 0){
                        // Change the instruction index
                        i += y-1;
                    }
                }
            }
        }

        // Print the answer
        System.out.println(answer);
    }
}