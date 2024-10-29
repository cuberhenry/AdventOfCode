/*
Henry Anderson
Advent of Code 2021 Day 1 https://adventofcode.com/2021/day/1
Input: https://adventofcode.com/2021/day/1/input
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
        // The sliding window of depth
        int[] window = new int[1];

        // Part 1 finds the number of increasing depths
        // Part 2 finds the same with an average of three
        if (PART == 2){
            window = new int[3];
        }

        // The total depth of the window
        int depth = 0;
        // Fill the window
        for (int i=0; i<window.length; ++i){
            window[i] = sc.nextInt();
            depth += window[i];
        }

        // The number of increasing depths
        int answer = 0;
        while (sc.hasNext()){
            // Save the old depth
            int oldDepth = depth;
            // Drop one off the window
            depth -= window[0];
            // Move them all over
            for (int i=0; i<window.length-1; ++i){
                window[i] = window[i+1];
            }
            // Take on the new value
            window[window.length-1] = sc.nextInt();
            depth += window[window.length-1];
            
            // If increasing, add
            if (depth > oldDepth){
                ++answer;
            }
        }

        // Print the answer
        System.out.println(answer);
    }
}