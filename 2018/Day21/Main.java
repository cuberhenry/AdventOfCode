/*
Henry Anderson
Advent of Code 2018 Day 21 https://adventofcode.com/2018/day/21
Input: https://adventofcode.com/2018/day/21/input
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
        // The program to be executed
        ArrayList<String[]> program = new ArrayList<>();
        // The instruction pointer
        int ip = Integer.parseInt(sc.nextLine().split(" ")[1]);
        // Take in all the instructions
        while (sc.hasNext()){
            program.add(sc.nextLine().split(" "));
        }

        // The registers during the program
        int[] regs = new int[6];

        // History for finding repeats
        ArrayList<Integer> history = new ArrayList<>();

        // Take in every line of the program
        while (regs[ip] < program.size()){
            // Take in the operation as ints
            String[] split = program.get(regs[ip]);
            int[] opp = new int[4];
            for (int i=1; i<4; ++i){
                opp[i] = Integer.parseInt(split[i]);
            }

            // The only line that uses register 0, to decide whether to quit
            if (regs[ip] == 28){
                // Part 1 finds the input that breaks the earliest
                if (PART == 1){
                    System.out.println(regs[3]);
                    return;
                }

                // Part 2 finds the input that breaks the latest
                // Check for a repeat
                if (history.contains(regs[3])){
                    // If there's a repeat, print the previous input
                    System.out.println(history.getLast());
                    return;
                }
                history.add(regs[3]);
            }

            // Perform the given operation
            // O A B C
            // x represents a value rather than a register
            switch(split[0]){
                // C <- A + B
                case "addr" -> {regs[opp[3]] = regs[opp[1]] + regs[opp[2]];}
                // C <- A + x
                case "addi" -> {regs[opp[3]] = regs[opp[1]] + opp[2];}
                // C <- A * B
                case "mulr" -> {regs[opp[3]] = regs[opp[1]] * regs[opp[2]];}
                // C <- A * x
                case "muli" -> {regs[opp[3]] = regs[opp[1]] * opp[2];}
                // C <- A & B
                case "banr" -> {regs[opp[3]] = (regs[opp[1]] & regs[opp[2]]);}
                // C <- A & x
                case "bani" -> {regs[opp[3]] = (regs[opp[1]] & opp[2]);}
                // C <- A | B
                case "borr" -> {regs[opp[3]] = (regs[opp[1]] | regs[opp[2]]);}
                // C <- A | x
                case "bori" -> {regs[opp[3]] = (regs[opp[1]] | opp[2]);}
                // C <- A
                case "setr" -> {regs[opp[3]] = regs[opp[1]];}
                // C <- x
                case "seti" -> {regs[opp[3]] = opp[1];}
                // C <- x > B
                case "gtir" -> {regs[opp[3]] = opp[1] > regs[opp[2]] ? 1 : 0;}
                // C <- A > x
                case "gtri" -> {regs[opp[3]] = regs[opp[1]] > opp[2] ? 1 : 0;}
                // C <- A > B
                case "gtrr" -> {regs[opp[3]] = regs[opp[1]] > regs[opp[2]] ? 1 : 0;}
                // C <- x == B
                case "eqir" -> {regs[opp[3]] = opp[1] == regs[opp[2]] ? 1 : 0;}
                // C <- A == x
                case "eqri" -> {regs[opp[3]] = regs[opp[1]] == opp[2] ? 1 : 0;}
                // C <- A == B
                case "eqrr" -> {regs[opp[3]] = regs[opp[1]] == regs[opp[2]] ? 1 : 0;}
            }

            // Increment the instruction pointer
            ++regs[ip];
        }

        // Print the answer
        System.out.println(regs[0]);
    }
}