/*
Henry Anderson
Advent of Code 2022 Day 2 https://adventofcode.com/2022/day/2
Input: https://adventofcode.com/2022/day/2/input
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
        // Total number of points
        int total = 0;

        // Loop through all lines of input
        while (sc.hasNext()){
            // Take in the first number as its point value
            int one = sc.next().charAt(0) - 'A' + 1;
            // Take in second value numbed 1-3
            int two = sc.next().charAt(0) - 'X' + 1;
            
            // Part 1 finds the score after selecting the second column
            if (PART == 1){
                // Points for which shape selected
                total += two;
                // Points for who won
                total += (two - one + 4) % 3 * 3;
            }
            
            // Part 2 finds the score after matching the outcome
            if (PART == 2){
                // Points for which shape selected
                total += (one + two) % 3 + 1;
                // Points for who won
                total += (two - 1) * 3;
            }
        }
        
        System.out.println(total);
    }
}