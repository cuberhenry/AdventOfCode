/*
Henry Anderson
Advent of Code 2023 Day 13 https://adventofcode.com/2023/day/13
Input: https://adventofcode.com/2023/day/13/input
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
        // The number of rows above the mirror lines
        int rows = 0;
        // The number of columns left of the mirror lines
        int cols = 0;
        // The number of mistakes in the reflection
        int goal = 0;

        // Part 1 finds the reflection line in the mirrors
        // Part 2 has exactly one smudge on each mirror
        if (PART == 2){
            goal = 1;
        }

        // Loop through each grid
        while (sc.hasNext()){
            // The grid to be examined
            ArrayList<String> grid = new ArrayList<>();
            // Take in the next line of input
            String line = sc.nextLine();
            // As long as the grid isn't complete
            while (sc.hasNext() && !line.equals("")){
                // Add the next line
                grid.add(line);
                line = sc.nextLine();
            }
            // For the final grid, at the final line
            if (!sc.hasNext()){
                grid.add(line);
            }
            // Whether the mirror line has been found
            boolean found = false;
            // Mirror lines imply that the first or last will match something else
            // Look for a horizontal mirror line
            for (int i=0; i<grid.size() && !found; i+= grid.size()-1){
                // Search through every other row
                for (int j=0; j<grid.size() && !found; ++j){
                    // Mirror lines are only found between two rows
                    if (i == j || Math.abs(i-j)%2 != 1){
                        continue;
                    }
                    // The number of differences between the rows
                    int count = 0;
                    // Loop through every pair of rows between i and j
                    for (int k=0; k<=Math.abs(j-i)/2 && count <= 1; ++k){
                        // Loop through every pair of characters in the rows
                        for (int l=0; l<grid.get(0).length() && count <= 1; ++l){
                            // If the two characters don't match
                            if (grid.get(Math.max(i,j)-k).charAt(l) != grid.get(Math.min(i,j)+k).charAt(l)){
                                // Increase the number of mistakes
                                ++count;
                            }
                        }
                    }
                    // If the mirror line has been found
                    if (count == goal){
                        // Mark it as found
                        found = true;
                        // Increase the number of rows
                        rows += (Math.abs(j-i)/2+Math.min(i,j)+1);
                    }
                }
            }
            // Look for a vertical mirror line
            for (int i=0; i<grid.get(0).length() && !found; i+=grid.get(0).length()-1){
                // Search through every column
                for (int j=0; j<grid.get(0).length() && !found; ++j){
                    // Mirror lines are only found between two columns
                    if (i == j || Math.abs(i-j)%2 != 1){
                        continue;
                    }
                    // The number of differences between the rows
                    int count = 0;
                    // Loop through every pair of columns between i and j
                    for (int k=0; k<=Math.abs(j-i)/2 && count <= 1; ++k){
                        // Loop through every pair of characters in the columns
                        for (int l=0; l<grid.size() && count <= 1; ++l){
                            // If the two characters don't match
                            if (grid.get(l).charAt(Math.max(i,j)-k) != grid.get(l).charAt(Math.min(i,j)+k)){
                                // Increase the number of mistakes
                                ++count;
                            }
                        }
                    }
                    // If the mirror line has been found
                    if (count == goal){
                        // Mark it as found
                        found = true;
                        // Increase the number of columns
                        cols += (Math.abs(j-i)/2+Math.min(i,j)+1);
                    }
                }
            }
        }

        // Print the answer
        System.out.println(100 * rows + cols);
    }
}