/*
Henry Anderson
Advent of Code 2019 Day 16 https://adventofcode.com/2019/day/16
Input: https://adventofcode.com/2019/day/16/input
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
        // Take in the initial input
        String input = sc.nextLine();

        // Part 1 finds the first 8 digits after descrambling
        // Part 2 finds the first 8 digits at the offset after descrambling
        if (PART == 2){
            // Grab the offset
            int offset = Integer.parseInt(input.substring(0,7));
            // The new input
            String newInput = "";
            // Continue adding until the input is at least the amount left
            while (newInput.length() < 10000 * input.length() - offset){
                newInput += input;
            }
            // Remove the unneeded front
            input = newInput.substring(newInput.length() - 10000 * input.length() + offset);
        }

        // Turn the input into a list
        int[] list = new int[input.length()];
        for (int i=0; i<list.length; ++i){
            list[i] = input.charAt(i) - '0';
        }
        
        // Loop through all 100 phases
        for (int i=0; i<100; ++i){
            // Create a new list
            int[] newList = new int[list.length];

            if (PART == 1){
                // Loop through every number
                for (int j=0; j<list.length; ++j){
                    // The current pointer, starting from the current number (skipping the first zero)
                    int pointer = j;
                    while (pointer < list.length){
                        // Add all the 1s
                        for (int k=0; k<=j && pointer < list.length; ++k){
                            newList[j] += list[pointer];
                            ++pointer;
                        }
                        // Skip 0s
                        pointer += j + 1;
                        // Add all the -1s
                        for (int k=0; k<=j && pointer < list.length; ++k){
                            newList[j] -= list[pointer];
                            ++pointer;
                        }
                        // Skip 0s
                        pointer += j + 1;
                    }
                    // Modulo 10
                    newList[j] = Math.abs(newList[j]) % 10;
                }
            }

            // Part 2 assumes that the offset is more than half of the length
            // (which it has to be in order to complete it in a reasonable amount
            // of memory and time)
            if (PART == 2){
                // The current sum
                int sum = 0;
                // Proceed backwards, adding all of the following numbers as the current one
                for (int j=list.length-1; j>=0; --j){
                    sum += list[j];
                    newList[j] = sum % 10;
                }
            }

            // Update the list
            list = newList;
        }

        // Print the first 8 digits
        for (int i=0; i<8; ++i){
            System.out.print(list[i]);
        }
        System.out.println();
    }
}