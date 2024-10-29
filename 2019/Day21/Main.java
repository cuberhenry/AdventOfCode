/*
Henry Anderson
Advent of Code 2019 Day 21 https://adventofcode.com/2019/day/21
Input: https://adventofcode.com/2019/day/21/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import com.aoc.mylibrary.IntCode;
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
        // Initialize the program
        IntCode program = new IntCode(sc.nextLine());

        // Jump if the landing block is solid and there's a hole between
        // (!C | !B | !A) & D
        program.addInput("NOT C J");
        program.addInput("NOT B T");
        program.addInput("OR T J");
        program.addInput("NOT A T");
        program.addInput("OR T J");
        program.addInput("AND D J");

        // Part 1 uses the walk instruction, which can see 4 ahead
        if (PART == 1){
            // (!C | !B | !A) & D
            program.addInput("WALK");
        }

        // Part 2 uses the run instruction, which can see 9 ahead
        if (PART == 2){
            // Jump if the landing block is solid, there's a hole between,
            // and there is a block accessible from the landing block
            // (!C | !B | !A) & D & (E | H)
            program.addInput("AND A T");
            program.addInput("OR E T");
            program.addInput("OR H T");
            program.addInput("AND T J");
            program.addInput("RUN");
        }

        // Print the answer
        System.out.println(program.run().getLast());
    }
}