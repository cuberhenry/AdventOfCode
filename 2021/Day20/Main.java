/*
Henry Anderson
Advent of Code 2021 Day 20 https://adventofcode.com/2021/day/20
Input: https://adventofcode.com/2021/day/20/input
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
        // The key for which binary numbers are on or off
        String lookup = sc.nextLine();
        // Skip the newline
        sc.nextLine();

        boolean oscillate = lookup.charAt(0) == '#';

        // The list of which coordinates are lit
        ArrayList<String> lit = new ArrayList<>();
        // The bounds of the lit coordinates
        int minX = 0;
        int minY = 0;
        int maxX = 0;
        int maxY = 0;
        
        // Take in each line of input
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            maxX = line.length()-1;

            // Add all of the lit coords
            for (int i=0; i<line.length(); ++i){
                if (line.charAt(i) == '#'){
                    lit.add(i + " " + maxY);
                }
            }

            // Increase the maxY
            ++maxY;
        }
        // Make sure maxY is inclusive
        --maxY;

        // Part 1 counts the number of lit pixels after 2 rounds
        int numLoops = 2;

        // Part 2 counts the number of lit pixels after 50 rounds
        if (PART == 2){
            numLoops = 50;
        }

        // Loop through two times
        for (int i=0; i<numLoops; ++i){
            // Create new variables
            ArrayList<String> newLit = new ArrayList<>();
            int newMinX = Integer.MAX_VALUE;
            int newMaxX = Integer.MIN_VALUE;
            int newMinY = newMinX;
            int newMaxY = newMaxX;

            // Loop through every coordinate within the inclusive bounds
            for (int j=minY-1; j<=maxY+1; ++j){
                for (int k=minX-1; k<=maxX+1; ++k){
                    // The index to look up
                    int index = 0;
                    // Loop through each value in a 3x3 square
                    for (int l=-1; l<=1; ++l){
                        for (int m=-1; m<=1; ++m){
                            index <<= 1;
                            // Add the binary representation
                            if (lit.contains((k+m) + " " + (j+l)) || i%2 == 1 && oscillate
                                    && (k+m < minX || k+m > maxX || j+l < minY || j+l > maxY)){
                                ++index;
                            }
                        }
                    }

                    // Light up the point
                    if (lookup.charAt(index) == '#'){
                        // Save the bounds
                        newMinX = Math.min(newMinX,k);
                        newMinY = Math.min(newMinY,j);
                        newMaxX = Math.max(newMaxX,k);
                        newMaxY = Math.max(newMaxY,j);
                        newLit.add(k + " " + j);
                    }
                }
            }
            // Save the new variables
            lit = newLit;
            minX = newMinX;
            maxX = newMaxX;
            minY = newMinY;
            maxY = newMaxY;
        }

        // Print the answer
        System.out.println(lit.size());
    }
}