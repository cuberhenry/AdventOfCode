/*
Henry Anderson
Advent of Code 2017 Day 15 https://adventofcode.com/2017/day/15
Input: https://adventofcode.com/2017/day/15/input
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
        // Take in the two initial values
        long a = Integer.parseInt(sc.nextLine().split(" ")[4]);
        long b = Integer.parseInt(sc.nextLine().split(" ")[4]);
        // The number of matches
        int total = 0;
        // The number of pairs of numbers to compare
        int loops = 0;
        
        if (PART == 1){
            loops = 40_000_000;
        }

        if (PART == 2){
            loops = 5_000_000;
        }

        // Compare loop matches
        for (int i=0; i<loops; ++i){
            // Generate the next number
            a = a*16807 % 2147483647;
            b = b*48271 % 2147483647;

            // Part 2 requires multiples of specific numbers
            if (PART == 2){
                // Continue generating until a number of the correct multiple is found
                while (a%4 != 0){
                    a = a*16807 % 2147483647;
                }
                while (b%8 != 0){
                    b = b*48271 % 2147483647;
                }
            }
            
            // Copy the two numbers
            long x = a;
            long y = b;

            // If the last 16 digits match, increment the total
            if (a % 65536 == b % 65536){
                ++total;
            }
        }

        // Print the answer
        System.out.println(total);
    }
}