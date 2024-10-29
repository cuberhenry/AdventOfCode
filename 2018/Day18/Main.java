/*
Henry Anderson
Advent of Code 2018 Day 18 https://adventofcode.com/2018/day/18
Input: https://adventofcode.com/2018/day/18/input
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
        // The forests, lumberyards, and clearings
        char[][] grid = new char[50][50];
        // History for skipping cycles
        ArrayList<String> history = new ArrayList<>();
        // Take in all the starting input
        for (int i=0; i<50; ++i){
            String line = sc.nextLine();
            for (int j=0; j<50; ++j){
                grid[i][j] = line.charAt(j);
            }
        }

        // The number of steps to perform
        int steps = 10;

        // Part 1 finds the resource value after 10 minutes
        // Part 2 finds the resource value after 1000000000 minutes
        if (PART == 2){
            steps = 1000000000;
        }

        // Loop through every step
        for (int i=0; i<steps; ++i){
            // Craft the current state as a string
            String state = "";
            for (int j=0; j<50; ++j){
                state += Arrays.toString(grid[j]);
            }
            // If this state has been visited before
            if (history.contains(state)){
                // Get the size of the cycle
                int cycle = i - history.indexOf(state);
                // Skip ahead to the end of the last full cycle
                i += (steps-i) / cycle * cycle;
                // If we skipped all the way, don't do any more changes
                if (i == steps){
                    break;
                }
                // Clear history so that we can proceed like normal
                history.clear();
            }else{
                // Otherwise, add to history
                history.add(state);
            }

            // Create the new grid, as all changes happen simultaneously
            char[][] newGrid = new char[50][50];
            // Loop through every acre
            for (int j=0; j<50; ++j){
                for (int k=0; k<50; ++k){
                    // Set the new spot to the same as the old spot
                    newGrid[j][k] = grid[j][k];
                    // Count the number of each adjacent acre type
                    int[] adjacents = new int[3];
                    // Look in each direction
                    for (int l=0; l<8; ++l){
                        int x = k;
                        int y = j;
                        switch(l){
                            case 0 -> {--x; --y;}
                            case 1 -> {--x;}
                            case 2 -> {--x; ++y;}
                            case 3 -> {++y;}
                            case 4 -> {++x; ++y;}
                            case 5 -> {++x;}
                            case 6 -> {++x; --y;}
                            case 7 -> {--y;}
                        }
                        // If it's within the bounds
                        if (x >= 0 && y >= 0 && x < 50 && y < 50){
                            // Add its value
                            switch(grid[y][x]){
                                case '.' -> {++adjacents[0];}
                                case '|' -> {++adjacents[1];}
                                case '#' -> {++adjacents[2];}
                            }
                        }
                    }
                    // Adjust the new acre based on the adjacent values
                    if (grid[j][k] == '.' && adjacents[1] >= 3){
                        newGrid[j][k] = '|';
                    }else if (grid[j][k] == '|' && adjacents[2] >= 3){
                        newGrid[j][k] = '#';
                    }else if (grid[j][k] == '#' && (adjacents[1] < 1 || adjacents[2] < 1)){
                        newGrid[j][k] = '.';
                    }
                }
            }

            // Move on to the next step
            grid = newGrid;
        }

        // The number of trees and lumberyards left
        int trees = 0;
        int lumberyards = 0;
        // Loop through every acre
        for (int i=0; i<50; ++i){
            for (int j=0; j<50; ++j){
                // Record if it's a resource
                if (grid[i][j] == '|'){
                    ++trees;
                }else if (grid[i][j] == '#'){
                    ++lumberyards;
                }
            }
        }

        // Print the answer
        System.out.println(trees * lumberyards);
    }
}