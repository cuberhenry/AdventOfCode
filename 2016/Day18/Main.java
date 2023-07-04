/*
Henry Anderson
Advent of Code 2016 Day 18 https://adventofcode.com/2016/day/18
Input: https://adventofcode.com/2016/day/18/input
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
        // Take in the first row of tiles
        String input = sc.nextLine();
        // Whether each tile in the current row is a trap
        boolean[] row = new boolean[input.length()];
        // The number of safe tiles
        int numSafe = 0;

        // The number of rows
        int numRows = 0;

        // Part 1 finds the number of safe tiles in 40 rows of tiles
        if (PART == 1){
            numRows = 40;
        }

        // Part 2 finds the number of safe tiles in 400000 rows of tiles
        if (PART == 2){
            numRows = 400000;
        }

        // Loop through every tile in the first row
        for (int i=0; i<row.length; ++i){
            // Record if it's a trap
            row[i] = input.charAt(i) == '^';
            // Add to numSafe if it's safe
            if (!row[i]){
                ++numSafe;
            }
        }

        // Loop through every consecutive row
        for (int i=1; i<numRows; ++i){
            // The next row of tiles
            boolean[] newRow = new boolean[row.length];
            // Each tile is exclusively orred for it's previous left and right tiles
            newRow[0] = row[1];
            newRow[newRow.length-1] = row[row.length-2];
            for (int j=1; j<newRow.length-1; ++j){
                newRow[j] = row[j-1] ^ row[j+1];
            }

            // Set the current row to the next row
            row = newRow;

            // Count the number of safe tiles in this new row
            for (int j=0; j<row.length; ++j){
                if (!row[j]){
                    ++numSafe;
                }
            }
        }

        // Print the answer
        System.out.println(numSafe);
    }
}