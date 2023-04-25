/*
Henry Anderson
Advent of Code 2022 Day 6 https://adventofcode.com/2022/day/6
Input: https://adventofcode.com/2022/day/6/input
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
        // The input as a string
        String input = sc.next();

        // Loop through every character in the input
        for (int i=0; i<input.length(); ++i){
            // Whether the loops should continue searching
            boolean good = true;
            // Part 1 requires 4 unique characters
            // Part 2 requires 14 unique characters
            // Loop through every one of the 4/14 characters
            for (int j=0; j<PART * 10 - 7 && good; ++j){
                for (int k=j+1; k<PART * 10 - 6 && good; ++k){
                    // If it's a duplicate, the sequence has not been found
                    if (input.charAt((j+i) % input.length())
                        == input.charAt((k+i) % input.length())){
                        good = false;
                    }
                }
            }
            
            // If there's no duplicates, print how many characters were searched
            if (good){
                System.out.println(i + (PART * 10 - 6));
                break;
            }
        }
    }
}