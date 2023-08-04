/*
Henry Anderson
Advent of Code 2017 Day 11 https://adventofcode.com/2017/day/11
Input: https://adventofcode.com/2017/day/11/input
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
        // Take in the list of moves
        String[] moves = sc.nextLine().split(",");
        // The synthetic x and y position of the child process
        int x = 0;
        int y = 0;
        // The maximum distance the child process gets from the start
        int max = 0;

        // Loop through every move
        for (String move : moves){
            // Change x and y according to the move
            switch (move){
                case "n" -> {
                    ++x;
                    ++y;
                }
                case "s" -> {
                    --x;
                    --y;
                }
                case "ne" -> {
                    ++x;
                }
                case "sw" -> {
                    --x;
                }
                case "se" -> {
                    --y;
                }
                case "nw" -> {
                    ++y;
                }
            }

            // Decide whether the current position is a new maximum distance
            if (PART == 2){
                // The distance
                int distance;
                // Calculate the distance based on whether x and y have matching signs
                if (x > 0 ^ y > 0){
                    distance = Math.abs(x) + Math.abs(y);
                }else{
                    distance = Math.max(Math.abs(x),Math.abs(y));
                }
                // Update the new max
                if (distance > max){
                    max = distance;
                }
            }
        }

        // Part 1 finds the displacement of the child process
        if (PART == 1){
            // Decide how to caluclate the distance based on
            // whether x and y have matching signs
            if (x > 0 ^ y > 0){
                // Add their distances
                System.out.println(Math.abs(x) + Math.abs(y));
            }else{
                // Take the maximum distance
                System.out.println(Math.max(Math.abs(x),Math.abs(y)));
            }
        }
        
        // Part 2 finds the maximum distance the child process gets
        if (PART == 2){
            // Print the answer
            System.out.println(max);
        }
    }
}