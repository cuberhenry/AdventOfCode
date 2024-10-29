/*
Henry Anderson
Advent of Code 2019 Day 13 https://adventofcode.com/2019/day/13
Input: https://adventofcode.com/2019/day/13/input
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

        // The number of blocks
        int blocks = 0;
        // Where the ball is
        long ball = 0;
        // Where the paddle is
        long paddle = 0;
        // The current score
        long score = 0;

        // Part 1 finds the number of blocks placed
        // Part 2 finds the final score after destroying all the blocks
        if (PART == 2){
            program.updateProgram(0,2);
        }

        // Continue until the program is finished
        while (!program.isFinished()){
            // Get the output
            ArrayList<Long> output = program.run();

            // Loop through every three outputs
            for (int i=0; i<output.size(); i+=3){
                if (output.get(i) == -1){
                    // Score
                    score = output.get(i+2);
                }else if (output.get(i+2) == 4){
                    // Ball
                    ball = output.get(i);
                }else if (output.get(i+2) == 3){
                    // Paddle
                    paddle = output.get(i);
                }else if (output.get(i+2) == 2){
                    // Block
                    ++blocks;
                }
            }

            // Move the paddle closer to the ball
            if (ball < paddle){
                program.addInput(-1);
            }else if (ball > paddle){
                program.addInput(1);
            }else{
                program.addInput(0);
            }
        }

        // Print the answer
        if (PART == 1){
            System.out.println(blocks);
        }

        if (PART == 2){
            System.out.println(score);
        }
    }
}