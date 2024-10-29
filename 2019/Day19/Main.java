/*
Henry Anderson
Advent of Code 2019 Day 19 https://adventofcode.com/2019/day/19
Input: https://adventofcode.com/2019/day/19/input
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
        // Take in the program
        String line = sc.nextLine();

        // Part 1 finds the number of squares within the 50x50 beam
        if (PART == 1){
            // The number of squares in the beam
            int num = 0;

            // Loop through every square in the first 50x50
            for (int i=0; i<50; ++i){
                for (int j=0; j<50; ++j){
                    // Run the program
                    IntCode program = new IntCode(line);
                    program.addInput(j);
                    program.addInput(i);
                    
                    // If it is being pulled, add
                    num += program.run().get(0);
                }
            }

            // Print the answer
            System.out.println(num);
        }

        // Part 2 finds the first place to fit a 100x100 within the beam
        if (PART == 2){
            // Must be at least 100 to the right
            int x = 100;
            int y = 0;

            // Whether the beam is pulling the current coordinates
            boolean isPulled = false;
            while (true){
                // Continue until the top right corner is in the beam
                while (!isPulled){
                    ++y;
                    // Run the program with the coordinates
                    IntCode program = new IntCode(line);
                    program.addInput(x);
                    program.addInput(y);
                    isPulled = program.run().get(0) == 1;
                }

                // Check the coordinate at the bottom left of the 100x100 square
                IntCode program = new IntCode(line);
                program.addInput(x-99);
                program.addInput(y+99);
                if (program.run().get(0) == 1){
                    // Print the answer
                    System.out.println((x-99) * 10000 + y);
                    break;
                }

                // Move to the right
                ++x;

                // Check whether it's being pulled
                program = new IntCode(line);
                program.addInput(x);
                program.addInput(y);
                isPulled = program.run().get(0) == 1;
            }
        }
    }
}