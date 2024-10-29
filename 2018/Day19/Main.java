/*
Henry Anderson
Advent of Code 2018 Day 19 https://adventofcode.com/2018/day/19
Input: https://adventofcode.com/2018/day/19/input
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
        long[] regs = new long[6];

        // Part 1 finds the value of register 0
        // Part 2 starts register 0 at 1
        if (PART == 2){
            regs[0] = 1;
        }

        // Take in every line of the sample program
        while (regs[ip] < program.size()){
            // Take in the operation as ints
            String[] split = program.get((int)regs[ip]);
            int[] opp = new int[4];
            for (int i=1; i<4; ++i){
                opp[i] = Integer.parseInt(split[i]);
            }

            // Part 2 is very inefficient, but it finds the sum of
            // all factors of register 5
            if (PART == 2){
                // The index of the register that bounds the search
                int bound = Integer.parseInt(program.get(4)[2]);
                // If the registers have been initialized
                if (regs[ip] == 1){
                    // Loop through every possible factor of the bound register
                    for (int i=1; i<= regs[bound]; ++i){
                        if (regs[bound] % i == 0){
                            regs[0] += i;
                        }
                    }
    
                    // That's all the code does, so break out
                    break;
                }
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