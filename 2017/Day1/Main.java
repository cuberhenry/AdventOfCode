/*
Henry Anderson
Advent of Code 2017 Day 1 https://adventofcode.com/2017/day/1
Input: https://adventofcode.com/2017/day/1/input
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
        // Collect the input
        String input = sc.nextLine();
        // The value to be returned
        int total = 0;

        // Loop through every digit in the input
        for (int i=0; i<input.length(); ++i){
            // Part 1 adds to the total digits that match the next digit
            if (PART == 1){
                if (input.charAt(i) == input.charAt((i+1)%input.length())){
                    total += input.charAt(i)-'0';
                }
            }

            // Part2 adds digits that match the digit halfway around the input
            if (PART == 2){
                if (input.charAt(i) == input.charAt((i+input.length()/2)%input.length())){
                    total += input.charAt(i)-'0';
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}