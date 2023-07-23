/*
Henry Anderson
Advent of Code 2017 Day 6 https://adventofcode.com/2017/day/6
Input: https://adventofcode.com/2017/day/6/input
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
        // Take in the input and split
        String[] input = sc.nextLine().split("\t");
        // The list of all previous states
        ArrayList<String> history = new ArrayList<>();
        // The current state of the blocks
        int[] blocks = new int[input.length];
        // Copy each block value from the input
        for (int i=0; i<input.length; ++i){
            blocks[i] = Integer.parseInt(input[i]);
        }

        // Continue until a duplicate is found
        while (!history.contains(Arrays.toString(blocks))){
            // Add the current state
            history.add(Arrays.toString(blocks));
            // The maximum size of the largest block
            int max = blocks[0];
            // The index of the largest block
            int index = 0;
            // Loop through every block
            for (int i=1; i<blocks.length; ++i){
                // If the current block is bigger, set it as the maximum
                if (blocks[i] > max){
                    max = blocks[i];
                    index = i;
                }
            }

            // Reset the largets block
            blocks[index] = 0;
            // Redistribute that maximum value starting at the next block
            for (int i=(index+1)%blocks.length; max > 0; i = (i+1)%blocks.length){
                ++blocks[i];
                --max;
            }
        }

        // Part 1 finds the number of steps it takes to find a duplicate
        if (PART == 1){
            System.out.println(history.size());
        }

        // Part 2 finds the size of the infinite cycle
        if (PART == 2){
            System.out.println(history.size() - history.indexOf(Arrays.toString(blocks)));
        }
    }
}