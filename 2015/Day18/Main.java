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
        boolean[][] grid = new boolean[height][width];

        // Fill the grid
        for (int i=0; i<input.length(); ++i){
            grid[i/height][i%height] = input.charAt(i) == '#';
        }

        // Part 1 finds the number of lights on at the end of 100 rounds
        // Part 2 finds the number of lights assuming the four corners always stay on
        if (PART == 2){
            // Turn on the four corners
            grid[0][0] = true;
            grid[0][height-1] = true;
            grid[width-1][0] = true;
            grid[width-1][height-1] = true;
        }

        // Loop through all 100 steps
        for (int i=0; i<100; ++i){
            // Create a new grid
            boolean[][] newGrid = new boolean[width][height];
            // Loop through every point in the grid
            for (int j=0; j<height; ++j){
                for (int k=0; k<width; ++k){
                    // The number of on neighbors
                    int neighbors = 0;
                    // Check up
                    if (j > 0){
                        if (grid[j-1][k]){
                            ++neighbors;
                        }
                        // Check up left
                        if (k > 0 && grid[j-1][k-1]){
                            ++neighbors;
                        }
                        // Check up right
                        if (k < height-1 && grid[j-1][k+1]){
                            ++neighbors;
                        }
                    }
                    // Check down
                    if (j < height-1){
                        if (grid[j+1][k]){
                            ++neighbors;
                        }
                        // Check down left
                        if (k > 0 && grid[j+1][k-1]){
                            ++neighbors;
                        }
                        // Check down right
                        if (k < width-1 && grid[j+1][k+1]){
                            ++neighbors;
                        }
                    }
                    // Check left
                    if (k > 0 && grid[j][k-1]){
                        ++neighbors;
                    }
                    // Check right
                    if (k < height-1 && grid[j][k+1]){
                        ++neighbors;
                    }

                    // Find new value
                    newGrid[j][k] = neighbors == 3 || grid[j][k] && neighbors == 2;
                }
            }

            // Don't change the corners
            if (PART == 2){
                newGrid[0][0] = true;
                newGrid[height-1][0] = true;
                newGrid[0][width-1] = true;
                newGrid[height-1][width-1] = true;
            }

            // Save the new grid
            grid = newGrid;
        }

        // Count the number of lights on at the end
        int total = 0;
        for (boolean[] row : grid){
            for (boolean light : row){
                if (light){
                    ++total;
                }
            }
        }
        // Print the answer
        System.out.println(total);
    }
}