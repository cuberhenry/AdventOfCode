/*
Henry Anderson
Advent of Code 2015 Day 25 https://adventofcode.com/2015/day/25
Input: https://adventofcode.com/2015/day/25/input
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
        // Part 2 does not require any code to solve
        if (PART == 2){
            System.out.println("Start the Weather Machine");
            return;
        }

        // Get the input
        String[] line = sc.nextLine().split("[.]  |[.]|, | ");
        // Grab the row
        int row = Integer.parseInt(line[15]);
        // Grab the column
        int column = Integer.parseInt(line[17]);
        // Change it so each row has row number of values
        row += column - 1;

        // The first number
        long num = 20151125;

        // Loop through every following position up to row column
        for (int i=2; i<=row; ++i){
            for (int j=1; j<=i; ++j){
                // Calculate next number
                num *= 252533;
                num %= 33554393;
                // If this is the number we're looking for, stop
                if (i == row && j == column){
                    break;
                }
            }
        }

        // Print the answer
        System.out.println(num);
    }
}