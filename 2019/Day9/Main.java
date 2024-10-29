/*
Henry Anderson
Advent of Code 2019 Day 9 https://adventofcode.com/2019/day/9
Input: https://adventofcode.com/2019/day/9/input
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
        // Create the program
        IntCode program = new IntCode(sc.nextLine());

        // Part 1 finds the last output with input 1
        if (PART == 1){
            program.addInput(1);
        }
        // Part 2 finds the last output with input 2
        if (PART == 2){
            program.addInput(2);
        }

        // Print the answer
        System.out.println(program.run().getLast());
    }
}