/*
Henry Anderson
Advent of Code 2017 Day 3 https://adventofcode.com/2017/day/3
Input: https://adventofcode.com/2017/day/3/input
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
        // The input value
        int input = sc.nextInt();
        // The starting position
        int x = 0;
        int y = 0;
        // The starting value
        int i = 1;
        // The radius of the current square
        int square = 0;
        // The hashmap of location values, used for Part 2
        HashMap<String,Integer> hash = new HashMap<>();
        // Add the starting position's value
        hash.put("0 0",1);

        // The current state of movement around the square
        int state = 0;

        // Continue until the value is found
        while (i < input){
            // Part 1 finds the distance of the given square to the center
            if (PART == 1){
                // Increment the index of the square
                ++i;
            }
            // Part 2 finds the square of the lowest value greater than the input

            // Decide what to do based on the state
            switch (state){
                // Expanding the square
                case 0 -> {
                    // Increment the square
                    ++square;
                    // Move to the right
                    ++x;
                    // Change states
                    ++state;

                    if (PART == 2){
                        if (square != 1){
                            // Add the value to the upper left
                            i += hash.get(x-1 + " " + (y-1));
                        }
                    }
                }
                // Moving up
                case 1 -> {
                    // Move up
                    --y;

                    if (PART == 2){
                        // Add the value to the lower left
                        i += hash.get(x-1 + " " + (y+1));
                        // Add the value to the left
                        if (y != square * -1){
                            i += hash.get(x-1 + " " + y);
                        }
                        // Add the value to the upper left
                        if (y > square * -1 + 1){
                            i += hash.get(x-1 + " " + (y-1));
                        }
                    }

                    // Increment state if done moving up
                    if (y == square * -1){
                        ++state;
                    }
                }
                // Moving left
                case 2 -> {
                    // Move left
                    --x;

                    if (PART == 2){
                        // Add the value to the lower right
                        i += hash.get(x+1 + " " + (y+1));
                        // Add the value down
                        if (x != square * -1){
                            i += hash.get(x + " " + (y+1));
                        }
                        // Add the value to the lower left
                        if (x > square * -1 + 1){
                            i += hash.get(x-1 + " " + (y+1));
                        }
                    }

                    // Increment state if done moving left
                    if (x == square * -1){
                        ++state;
                    }
                }
                // Moving down
                case 3 -> {
                    // Move down
                    ++y;

                    if (PART == 2){
                        // Add the value to the upper right
                        i += hash.get(x+1 + " " + (y-1));
                        // Add the value to the right
                        if (y != square){
                            i += hash.get(x+1 + " " + y);
                        }
                        // Add the value to the lower right
                        if (y < square - 1){
                            i += hash.get(x+1 + " " + (y+1));
                        }
                    }

                    // Increment state if done moving down
                    if (y == square){
                        ++state;
                    }
                }
                // Moving right
                case 4 -> {
                    // Move right
                    ++x;

                    if (PART == 2){
                        // Add the values to the upper left and up
                        i += hash.get(x-1 + " " + (y-1)) + hash.get(x + " " + (y-1));
                        // Add the value to the upper right
                        if (x < square){
                            i += hash.get(x+1 + " " + (y-1));
                        }
                    }

                    // Expand the square if done moving right
                    if (x == square){
                        state = 0;
                    }
                }
            }

            // Add the current location to the hashmap
            if (PART == 2){
                hash.put(x+" "+y,i);
            }
        }

        if (PART == 1){
            // Find and print the Manhattan distance
            System.out.println(Math.abs(x) + Math.abs(y));
        }
        
        if (PART == 2){
            // Print the answer
            System.out.println(i);
        }
    }
}