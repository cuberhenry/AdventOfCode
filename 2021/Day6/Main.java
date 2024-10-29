/*
Henry Anderson
Advent of Code 2021 Day 6 https://adventofcode.com/2021/day/6
Input: https://adventofcode.com/2021/day/6/input
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
        // The count of fish with each internal timer (0-8)
        long[] lanternfish = new long[9];

        // Count up the starting fish
        String[] split = sc.nextLine().split(",");
        for (int i=0; i<split.length; ++i){
            ++lanternfish[Integer.parseInt(split[i])];
        }

        // Part 1 finds the number of fish after 80 days
        int numDays = 80;

        // Part 2 finds the number of fish after 256 days
        if (PART == 2){
            numDays = 256;
        }

        // Loop through each day
        for (int i=0; i<numDays; ++i){
            long[] newFish = new long[9];

            // Fish reproduce
            newFish[8] = lanternfish[0];
            newFish[6] = lanternfish[0];
            // Fish age
            for (int j=0; j<8; ++j){
                newFish[j] += lanternfish[j+1];
            }
            lanternfish = newFish;
        }

        // Count up all fish at the end
        long sum = 0;
        for (long fish : lanternfish){
            sum += fish;
        }

        // Print the answer
        System.out.println(sum);
    }
}