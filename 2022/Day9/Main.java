/*
Henry Anderson
Advent of Code 2022 Day 9 https://adventofcode.com/2022/day/9
Input: https://adventofcode.com/2022/day/9/input
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
        PART = Integer.parseInt(args[0]);
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
        // List to keep track of visited positions
        ArrayList<String> positions = new ArrayList<>();
        // The array to keep track of the locations of each knot
        int[][] array = new int[0][0];
        
        // Part 1 finds the number of locations the second knot visits
        if (PART == 1){
            array = new int[2][2];
        }
        
        // Part 2 finds the number of locations the tenth knot visits
        if (PART == 2){
            array = new int[10][2];
        }
        
        // Add the current location as a visited one
        positions.add(array[array.length-1][0] + " " + array[array.length-1][1]);

        // Loop through all lines of input
        while (sc.hasNext()){
            // Take in the direction
            char dir = sc.next().charAt(0);
            // Take in the number of times moved
            int num = sc.nextInt();

            // Repeat num times
            for (int j=0; j<num; ++j){
                // Move the head in the indicated direction
                if (dir == 'U'){
                    ++array[0][1];
                }
                if (dir == 'R'){
                    ++array[0][0];
                }
                if (dir == 'D'){
                    --array[0][1];
                }
                if (dir == 'L'){
                    --array[0][0];
                }
                
                // Loop through every knot
                for (int k=1; k<array.length; ++k){
                    // If x or y of the previous knot is two spaces away
                    if (Math.abs(array[k][0]-array[k-1][0]) > 1
                     || Math.abs(array[k][1]-array[k-1][1]) > 1){
                        // Move both directions towards the previous knot
                        array[k][0] += (int)Math.signum(array[k-1][0]
                                                      - array[k][0]);
                        array[k][1] += (int)Math.signum(array[k-1][1]
                                                      - array[k][1]);
                    }
                }

                // If the tail hasn't been to this position yet
                if (!positions.contains(array[array.length-1][0] + " "
                                      + array[array.length-1][1])){
                    // Add the position to the list of visited positions
                    positions.add(array[array.length-1][0] + " "
                                + array[array.length-1][1]);
                }
            }
        }

        System.out.println(positions.size());
    }
}