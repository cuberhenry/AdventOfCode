/*
Henry Anderson
Advent of Code 2019 Day 11 https://adventofcode.com/2019/day/11
Input: https://adventofcode.com/2019/day/11/input
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

        // List of white squares
        HashMap<String,Boolean> painted = new HashMap<>();
        // The coordinates of the robot
        int x = 0;
        int y = 0;
        int dir = 0;
        // The bounds of the white paint
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Part 1 finds the number of squares painted starting on black
        // Part 2 finds the message painted starting on white
        if (PART == 2){
            painted.put("0 0",true);
        }
        
        // Continue until the program finishes
        while (!program.isFinished()){
            // Give the robot whether it's on white or black
            program.addInput(painted.containsKey(x + " " + y) && painted.get(x + " " + y) ? 1 : 0);
            // Get the output
            ArrayList<Long> output = program.run();
            // Add whether the spot is painted white or black
            painted.put(x + " " + y,output.getFirst() == 1);
            // Move the droid in the given direction
            dir = (int)(dir + 2 * output.getLast() + 3) % 4;
            switch(dir){
                case 0 -> {++y;}
                case 1 -> {++x;}
                case 2 -> {--y;}
                case 3 -> {--x;}
            }

            // Save the new boundaries
            minX = Math.min(minX,x);
            maxX = Math.max(maxX,x);
            minY = Math.min(minY,y);
            maxY = Math.max(maxY,y);
        }

        if (PART == 1){
            // Print the answer
            System.out.println(painted.size());
        }

        if (PART == 2){
            // Print the message
            for (int i=maxY; i>=minY; --i){
                for (int j=minX; j<=maxX; ++j){
                    System.out.print(painted.containsKey(j + " " + i) && painted.get(j + " " + i) ? '#' : ' ');
                }
                System.out.println();
            }
        }
    }
}