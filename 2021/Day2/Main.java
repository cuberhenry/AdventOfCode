/*
Henry Anderson
Advent of Code 2021 Day 2 https://adventofcode.com/2021/day/2
Input: https://adventofcode.com/2021/day/2/input
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
        // The three relevant values
        int position = 0;
        int depth = 0;
        int aim = 0;
        
        // Loop through every instruction
        while (sc.hasNext()){
            // Part 1 moves up, down, and forward
            if (PART == 1){
                switch(sc.next()){
                    // Move forward
                    case "forward" -> {position += sc.nextInt();}
                    // Move down
                    case "down" -> {depth += sc.nextInt();}
                    // Move up
                    case "up" -> {depth -= sc.nextInt();}
                }
            }

            // Part 2 aims up and down and moves forward
            if (PART == 2){
                switch(sc.next()){
                    // Move forward
                    case "forward" -> {
                        int num = sc.nextInt();
                        position += num;
                        depth += num * aim;
                    }
                    // Aim down
                    case "down" -> {aim += sc.nextInt();}
                    // Aim up
                    case "up" -> {aim -= sc.nextInt();}
                }
            }
        }

        // Print the answer
        System.out.println(position * depth);
    }
}