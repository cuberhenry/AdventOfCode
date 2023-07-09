/*
Henry Anderson
Advent of Code 2016 Day 19 https://adventofcode.com/2016/day/19
Input: https://adventofcode.com/2016/day/19/input
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
        // The number of elves in the circle
        int number = sc.nextInt();
        
        // Part 1 has every elf steal from the next elf
        if (PART == 1){
            // Print the answer based on a pattern
            System.out.println((number-(int) Math.pow(2,Math.floor(Math.log(number)/Math.log(2))))*2+1);
        }

        // Part 2 has every elf steal from the opposite elf
        if (PART == 2){
            // The previous number counted to
            int previous = 1;
            // The number for the current number of elves
            int current = 1;
            // Count through every set of elves from 1 to number
            for (int i=1; i<number; ++i){
                // If the number of elves has been counted to
                if (current == i){
                    // Save the previous number
                    previous = current;
                    // Start over at 1
                    current = 1;
                // If the current number is less than the previous number
                }else if (current < previous){
                    // Increment
                    ++current;
                // If the current number is greater than the previous number
                }else{
                    // Increment by 2
                    current += 2;
                }
            }
            // Print the answer
            System.out.println(current);
        }
    }
}