/*
Henry Anderson
Advent of Code 2015 Day 18 https://adventofcode.com/2015/day/18
Input: https://adventofcode.com/2015/day/18/input
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
        // Take in the first line of input
        String input = sc.nextLine();
        // The height of the grid
        int height = 1;
        // The width of the grid
        int width = input.length();

        // Add all of the rest of the input to the input string
        while (sc.hasNext()){
            input += sc.nextLine();
            // Increment the height for every line
            ++height;
        }
        // Create the grid
        int[][] grid = new int[width][height];

        // Fill the grid
        for (int i=0; i<input.length(); ++i){
            grid[i/height][i%height] = (input.charAt(i) == '#' ? 1 : 0);
        }

        // Part 1 finds the number of lights on at the end of 100 rounds
        // Part 2 finds the number of lights assuming the four corners always stay on
        if (PART == 2){
            // Turn on the four corners
            grid[0][0] = 1;
            grid[0][height-1] = 1;
            grid[width-1][0] = 1;
            grid[width-1][height-1] = 1;
        }

        // Loop through all 100 steps
        for (int i=0; i<100; ++i){
            // Create a new grid
            int[][] newGrid = new int[width][height];
            // Loop through every point in the grid
            for (int j=0; j<width; ++j){
                for (int k=0; k<height; ++k){
                    // Don't change the corners
                    if (PART == 2){
                        if ((j == 0 || j == width-1) && (k == 0 || k == height-1)){
                            newGrid[j][k] = 1;
                            continue;
                        }
                    }
                    // Check left
                    if (j > 0 && grid[j-1][k] == 1){
                        ++newGrid[j][k];
                    }
                    // Check up
                    if (k > 0 && grid[j][k-1] == 1){
                        ++newGrid[j][k];
                    }
                    // Check right
                    if (k < height-1 && grid[j][k+1] == 1){
                        ++newGrid[j][k];
                    }
                    // Check down
                    if (j < width-1 && grid[j+1][k] == 1){
                        ++newGrid[j][k];
                    }
                    // Check up left
                    if (j > 0 && k > 0 && grid[j-1][k-1] == 1){
                        ++newGrid[j][k];
                    }
                    // Check up right
                    if (j < width-1 && k > 0 && grid[j+1][k-1] == 1){
                        ++newGrid[j][k];
                    }
                    // Check down left
                    if (j > 0 && k < height-1 && grid[j-1][k+1] == 1){
                        ++newGrid[j][k];
                    }
                    // Check down right
                    if (j < width-1 && k < height-1 && grid[j+1][k+1] == 1){
                        ++newGrid[j][k];
                    }

                    // Find new value
                    if (grid[j][k] == 1){
                        // Check for turning off
                        if (newGrid[j][k] != 2 && newGrid[j][k] != 3){
                            newGrid[j][k] = 0;
                        }else{
                            newGrid[j][k] = 1;
                        }
                    }else{
                        // Check for turning on
                        if (newGrid[j][k] == 3){
                            newGrid[j][k] = 1;
                        }else{
                            newGrid[j][k] = 0;
                        }
                    }
                }
            }
            // Save the new grid
            grid = newGrid;
        }

        // Count the number of lights on at the end
        int total = 0;
        for (int i=0; i<width; ++i){
            for (int j=0; j<height; ++j){
                if (grid[i][j] == 1){
                    ++total;
                }
            }
        }
        // Print the answer
        System.out.println(total);
    }
}