/*
Henry Anderson
Advent of Code 2017 Day 2 https://adventofcode.com/2017/day/2
Input: https://adventofcode.com/2017/day/2/input
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
        // The checksum of the file
        int checksum = 0;

        // Loop through every row of input
        while (sc.hasNext()){
            // Take in and split the row
            String[] line = sc.nextLine().split("\t");

            // Part one finds a sum of differences
            if (PART == 1){
                // Collect the first value
                int max = Integer.parseInt(line[0]);
                int min = max;

                // Loop through every following number
                for (int i=1; i<line.length; ++i){
                    // Decide whether it's a new max or min
                    int num = Integer.parseInt(line[i]);
                    if (num > max){
                        max = num;
                    }else if (num < min){
                        min = num;
                    }
                }

                // Add the difference for the row into the checksum
                checksum += max - min;
            }

            // Part two finds a sum of quotients
            if (PART == 2){
                // Whether the divisible pair has been found
                boolean found = false;
                // Loop through every first value
                for (int i=0; i<line.length-1 && !found; ++i){
                    // Loop through every following value
                    for (int j=i+1; j<line.length && !found; ++j){
                        // Collect the two values
                        int first = Integer.parseInt(line[i]);
                        int second = Integer.parseInt(line[j]);
                        // Order the two values
                        int max = Math.max(first,second);
                        int min = Math.min(first,second);

                        // If it's divisible
                        if (max % min == 0){
                            // The pair has been found
                            found = true;
                            // Add the quotient for the row into the checksum
                            checksum += max / min;
                        }
                    }
                }
            }
        }

        // Print the answer
        System.out.println(checksum);
    }
}