/*
Henry Anderson
Advent of Code 2018 Day 11 https://adventofcode.com/2018/day/11
Input: https://adventofcode.com/2018/day/11/input
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
        // Take in the input
        int serial = sc.nextInt();
        // Create the grid of power cells
        int[][] grid = new int[300][300];
        for (int i=0; i<300; ++i){
            for (int j=0; j<300; ++j){
                // Initialize each value to its power level
                grid[i][j] = ((j+11)*(i+1)+serial)*(j+11)/100%10-5;
            }
        }

        // Record the maximum amount and location
        int max = 0;
        int x = 0;
        int y = 0;
        int size = 0;

        // Part 1 finds the greatest power level in a 3x3
        if (PART == 1){
            // Loop through every available 3x3
            for (int i=0; i<297; ++i){
                for (int j=0; j<297; ++j){
                    // Get the power level of the 3x3
                    int total = grid[i][j] + grid[i][j+1] + grid[i][j+2]
                            + grid[i+1][j] + grid[i+1][j+1] + grid[i+1][j+2]
                            + grid[i+2][j] + grid[i+2][j+1] + grid[i+2][j+2];
                    // If it's the new greatest, save it
                    if (total > max){
                        max = total;
                        x = j;
                        y = i;
                    }
                }
            }

            // Print the answer
            System.out.println((x+1) + "," + (y+1));
        }

        // Part 2 finds the greatest power level in any square
        if (PART == 2){
            // Loop through every cell as the top left of the square
            for (int i=0; i<300; ++i){
                for (int j=0; j<300; ++j){
                    // Save the progressive total for this cell
                    int total = 0;
                    // Loop through every possible size
                    for (int k=0; k<300-Math.max(i,j); ++k){
                        // Add the corner
                        total += grid[i+k][j+k];
                        // Add each edge
                        for (int l=0; l<k; ++l){
                            total += grid[i+l][j+k] + grid[i+k][j+l];
                        }
                        // If it's the new greatest, save it
                        if (total > max){
                            max = total;
                            x = j;
                            y = i;
                            size = k;
                        }
                    }
                }
            }

            // Print the answer
            System.out.println((x+1) + "," + (y+1) + "," + (size+1));
        }
    }
}