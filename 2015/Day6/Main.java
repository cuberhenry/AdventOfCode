/*
Henry Anderson
Advent of Code 2015 Day 6 https://adventofcode.com/2015/day/6
Input: https://adventofcode.com/2015/day/6/input
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
        // The grid of lights
        int[][] grid = new int[1000][1000];
        // The answer to the problem
        int total = 0;

        // While there's input
        while (sc.hasNext()){
            // Parse the input
            String instruction = sc.next();
            if (instruction.equals("turn")){
                instruction = sc.next();
            }
            String[] start = sc.next().split(",");
            int sx = Integer.parseInt(start[0]);
            int sy = Integer.parseInt(start[1]);
            sc.next();
            String[] end = sc.next().split(",");
            int ex = Integer.parseInt(end[0]);
            int ey = Integer.parseInt(end[1]);

            // Loop through every light within the limits
            for (int i=sx; i<=ex; ++i){
                for (int j=sy; j<=ey; ++j){
                    // Turn on
                    if (instruction.equals("on")){
                        // Turn the light on
                        if (PART == 1){
                            grid[i][j] = 1;
                        }
                        
                        // Increase the brightness
                        if (PART == 2){
                            ++grid[i][j];
                        }
                    // Turn off
                    }else if (instruction.equals("off")){
                        // Turn the light off
                        if (PART == 1){
                            grid[i][j] = 0;
                        }
                        
                        // Decrease the brightness with a minimum of zero
                        if (PART == 2){
                            if (grid[i][j] > 0){
                                --grid[i][j];
                            }
                        }
                    // Toggle
                    }else{
                        // Switch the light
                        if (PART == 1){
                            grid[i][j] = grid[i][j]*-1 + 1;
                        }
                        
                        // Increase the brightness by two
                        if (PART == 2){
                            grid[i][j] += 2;
                        }
                    }
                }
            }
        }

        // Part 1 counts the number of lights that are on
        // Part 2 finds the total brightness of all of the lights
        for (int i=0; i<grid.length; ++i){
            for (int j=0; j<grid[0].length; ++j){
                total += grid[i][j];
            }
        }

        // Print the result
        System.out.println(total);
    }
}