/*
Henry Anderson
Advent of Code 2021 Day 7 https://adventofcode.com/2021/day/7
Input: https://adventofcode.com/2021/day/7/input
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
        // Take in all the crab positions
        String[] split = sc.nextLine().split(",");
        int[] positions = new int[split.length];
        // Start at the leftmost crab
        int position = Integer.MAX_VALUE;
        for (int i=0; i<split.length; ++i){
            positions[i] = Integer.parseInt(split[i]);
            position = Math.min(position,positions[i]);
        }
        
        // Add up the inital fuel required
        int fuel = 0;
        for (int pos : positions){
            // Part 1 adds one fuel per crab move
            if (PART == 1){
                fuel += pos - position;
            }

            // Part 2 adds fuel equal to each distance the crab moves to
            if (PART == 2){
                int diff = pos - position;
                fuel += (diff * (diff + 1)) / 2;
            }
        }

        // Continue until a greater fuel is found
        while (true){
            // Start with the previous fuel
            int newFuel = fuel;

            // Loop through each fuel
            for (int pos : positions){
                // If the new position is closer, subtract a fuel
                // Otherwise, add a fuel
                if (PART == 1){
                    if (pos > position){
                        --newFuel;
                    }else{
                        ++newFuel;
                    }
                }

                // If the new position is closer, subtract the distance
                // Otherwise, add the distance
                if (PART == 2){
                    if (pos > position){
                        newFuel -= pos - position;
                    }else{
                        newFuel += position - pos + 1;
                    }
                }
            }

            // If the fuel is greater, the minimum is found
            if (newFuel > fuel){
                break;
            }
            // Continue to the next position
            ++position;
            fuel = newFuel;
        }

        // Print the answer
        System.out.println(fuel);
    }
}