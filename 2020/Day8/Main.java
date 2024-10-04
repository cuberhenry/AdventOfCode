/*
Henry Anderson
Advent of Code 2020 Day 8 https://adventofcode.com/2020/day/8
Input: https://adventofcode.com/2020/day/8/input
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
        // Take in the entire program
        ArrayList<String> program = new ArrayList<>();
        while (sc.hasNext()){
            program.add(sc.nextLine());
        }

        // Part 1 finds the value of acc at the when the first loop finishes
        if (PART == 1){
            // Program counter
            int i = 0;
            // Accumulator
            int acc = 0;
            // The list of visited instructions
            ArrayList<Integer> visited = new ArrayList<>();
            // Continue until a loop is found
            while (!visited.contains(i)){
                // Add the instruction
                visited.add(i);
                // Split the instruction
                String[] split = program.get(i).split(" ");
                // Perform the instruction
                switch(split[0]){
                    case "nop" -> {++i;}
                    case "acc" -> {
                        acc += Integer.parseInt(split[1]);
                        ++i;
                    }
                    case "jmp" -> {
                        i += Integer.parseInt(split[1]);
                    }
                }
            }

            // Print the answer
            System.out.println(acc);
        }

        // Part 2 finds the value of acc after fixing the console
        if (PART == 2){
            // Loop through every instruction
            for (int j=0; j<program.size(); ++j){
                // Only jmp or nop can be broken
                if (program.get(j).split(" ")[0].equals("acc")){
                    continue;
                }
                // Swap the instruction
                boolean nop = program.get(j).split(" ")[0].equals("nop");
                if (nop){
                    program.set(j,"jmp" + program.get(j).substring(3));
                }else{
                    program.set(j,"nop" + program.get(j).substring(3));
                }

                // Program counter
                int i = 0;
                // Accumulator
                int acc = 0;
                // The list of visited instructions
                ArrayList<Integer> visited = new ArrayList<>();
                // Continue until a loop is found or the program exits
                while (!visited.contains(i) && i < program.size()){
                    // Add the instruction
                    visited.add(i);
                    // Split the instruction
                    String[] split = program.get(i).split(" ");
                    // Perform the instruction
                    switch(split[0]){
                        case "nop" -> {++i;}
                        case "acc" -> {
                            acc += Integer.parseInt(split[1]);
                            ++i;
                        }
                        case "jmp" -> {
                            i += Integer.parseInt(split[1]);
                        }
                    }
                }

                // If the program exited, print the answer
                if (i >= program.size()){
                    System.out.println(acc);
                    break;
                }
                // Return the instruction to normal
                if (nop){
                    program.set(j,"nop" + program.get(j).substring(3));
                }else{
                    program.set(j,"jmp" + program.get(j).substring(3));
                }
            }
        }
    }
}