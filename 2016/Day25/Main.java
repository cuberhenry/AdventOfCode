/*
Henry Anderson
Advent of Code 2016 Day 25 https://adventofcode.com/2016/day/25
Input: https://adventofcode.com/2016/day/25/input
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
        // Part 2 doesn't require code
        if (PART == 2){
            System.out.println("Transmit the Signal");
            return;
        }

        // The program to be run
        ArrayList<String> instructions = new ArrayList<>();

        // Take in the instructions
        while (sc.hasNext()){
            instructions.add(sc.nextLine());
        }

        // Start at 1, increasing until the input is found
        for (int number = 1; true; ++number){
            // History to find oscillation
            ArrayList<String> history = new ArrayList<>();
            // The expected next output
            boolean high = false;
            // The four registers
            int[] registers = new int[4];
            registers[0] = number;

            // Until the program ends
            for (int i=0; i<instructions.size(); ++i){
                // Grab the current instruction
                String[] instruction = instructions.get(i).split(" ");
                // Perform the instruction
                switch (instruction[0]){
                    // Copy
                    case "cpy" -> {
                        // Skip nonsense instructions
                        if (Character.isDigit(instruction[2].charAt(0))){
                            continue;
                        }
                        // Can handle either a register or a value
                        if (Character.isDigit(instruction[1].charAt(0)) || instruction[1].charAt(0) == '-'){
                            // Set the register to the value
                            registers[instruction[2].charAt(0)-'a'] = Integer.parseInt(instruction[1]);
                        }else{
                            // Set the register to the other register
                            int value = registers[instruction[1].charAt(0)-'a'];
                            registers[instruction[2].charAt(0)-'a'] = value;
                        }
                    }
                    // Increment or Decrement
                    case "inc", "dec" -> {
                        // Get the next instruction
                        String next = instructions.get(i+1);
                        // Check to see if it's being changed based on another number
                        if (instructions.size() > i+2 && instructions.get(i+2).substring(0,3).equals("jnz") && instructions.get(i+2).split(" ")[2].equals("-2")
                            && (next.substring(0,3).equals("inc") || next.substring(0,3).equals("dec"))){
                            // The register on which the current register is dependant
                            char jump = instructions.get(i+2).split(" ")[1].charAt(0);
                            // The register being changed
                            char other = instruction[1].charAt(0);
                            // Whether the current register is being increased or decreased
                            int multi = instruction[0].equals("inc") ? 1 : -1;
                            // If the current register is in charge of the jump
                            if (other == jump){
                                // Switch the registers
                                other = next.charAt(4);
                                multi = next.substring(0,3).equals("inc") ? 1 : -1;
                            }
                            // If it's a multiplication
                            if (instructions.size() > i+4 && instructions.get(i+4).substring(0,3).equals("jnz")
                                && (instructions.get(i+3).substring(0,3).equals("inc") || instructions.get(i+3).substring(0,3).equals("dec"))){
                                // Multiply the two registers
                                registers[jump-'a'] *= Math.abs(registers[instructions.get(i+3).charAt(4)-'a']);
                                // Empty the second register
                                registers[instructions.get(i+3).charAt(4)-'a'] = 0;
                                // Move the answer to the right register
                                registers[other-'a'] += registers[jump-'a'] * multi;
                                registers[jump-'a'] = 0;
                                // Skip the instructions that were condensed
                                i += 4;
                                // Continue to the next instruction
                                continue;
                            }
                            // Add the registers
                            registers[other-'a'] += registers[jump-'a'] * multi;
                            // Empty the other register
                            registers[jump-'a'] = 0;
                            // Skip the instructions that were condensed
                            i += 2;
                            // Continue to the next instruction
                            continue;
                        }
                        // Increment or decrement the value at the register
                        registers[instruction[1].charAt(0)-'a'] += instruction[0].equals("inc") ? 1 : -1;
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
                            int offset;
                            // Can handle either a register or a value
                            if (Character.isDigit(instruction[2].charAt(0)) || instruction[2].charAt(0) == '-'){
                                // Set the number to the value
                                offset = Integer.parseInt(instruction[2]);
                            }else{
                                // Set the number to the register
                                offset = registers[instruction[2].charAt(0)-'a'];
                            }
                            i += offset-1;
                        }
                    }
                    // Output
                    case "out" -> {
                        int out;
                        // Find the output value, whether register or number
                        if (Character.isDigit(instruction[1].charAt(0))){
                            out = Integer.parseInt(instruction[1]);
                        }else{
                            out = registers[instruction[1].charAt(0) - 'a'];
                        }

                        // If the output is the next expected clock value
                        if (high && out == 1 || !high && out == 0){
                            // If this has happened before, a clock cycle is guaranteed, so the number is found
                            if (history.contains(Arrays.toString(registers) + " " + i + " " + out)){
                                System.out.println(number);
                                return;
                            }
                            // Add to history and expect the next clock value
                            history.add(Arrays.toString(registers) + " " + i + " " + out);
                            high = !high;
                        }else{
                            // The output was wrong, this input is not valid
                            i = instructions.size();
                        }
                    }
                }
            }
        }
    }
}