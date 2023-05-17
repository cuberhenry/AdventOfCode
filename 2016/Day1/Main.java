/*
Henry Anderson
Advent of Code 2016 Day 1 https://adventofcode.com/2016/day/1
Input: https://adventofcode.com/2016/day/1/input
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
        // The list of instructions
        String[] input = sc.nextLine().split(", ");
        // Current position
        int x = 0;
        int y = 0;
        // Current direction
        int direction = 0;

        // All visited positions
        ArrayList<String> positions = new ArrayList<>();
        // Add the starting position
        positions.add("0 0");

        // Loop through every instruction
        for (String instruction : input){
            // Turn in the desired direction
            if (instruction.charAt(0) == 'L'){
                direction = (direction+3)%4;
            }else{
                direction = (direction+1)%4;
            }

            // Grab the distance to be travelled
            int distance = Integer.parseInt(instruction.substring(1));

            // Part 1 finds the location at the end of the instructions
            if (PART == 1){
                // Move distance in direction
                switch (direction){
                    case 0 -> {y += distance;}
                    case 1 -> {x += distance;}
                    case 2 -> {y -= distance;}
                    case 3 -> {x -= distance;}
                }
            }

            // Part 2 finds the first location visited twice
            if (PART == 2){
                // Move distance in direction but keeping track of every position
                for (int i=0; i<distance; ++i){
                    switch (direction){
                        case 0 -> {++y;}
                        case 1 -> {++x;}
                        case 2 -> {--y;}
                        case 3 -> {--x;}
                    }

                    // If visited before
                    if (positions.contains(x+" "+y)){
                        // Print the distance
                        System.out.println(Math.abs(x)+Math.abs(y));
                        return;
                    }

                    // Add the current position
                    positions.add(x+" "+y);
                }
            }
        }

        // Print the distance
        System.out.println(Math.abs(x)+Math.abs(y));
    }
}