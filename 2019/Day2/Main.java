/*
Henry Anderson
Advent of Code 2019 Day 2 https://adventofcode.com/2019/day/2
Input: https://adventofcode.com/2019/day/2/input
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
        String input = sc.nextLine();

        // Part 1 finds the result of the program
        if (PART == 1){
            IntCode program = new IntCode(input);
            // Set the initial values
            program.updateProgram(1,12);
            program.updateProgram(2,2);

            program.run();
    
            // Print the answer
            System.out.println(program.getValue(0));
        }

        // Part 2 finds the necessary inputs to result in an output of 19690720
        if (PART == 2){
            for (int i=0; i<100; ++i){
                for (int j=0; j<100; ++j){
                    // Create a copy of the program with the test values
                    IntCode program = new IntCode(input);
                    program.updateProgram(1,i);
                    program.updateProgram(2,j);
                    program.run();

                    // Print the answer
                    if (program.getValue(0) == 19690720){
                        System.out.println(100 * i + j);
                        return;
                    }
                }
            }
        }
    }
}