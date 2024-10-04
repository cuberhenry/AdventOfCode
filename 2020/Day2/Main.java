/*
Henry Anderson
Advent of Code 2020 Day 2 https://adventofcode.com/2020/day/2
Input: https://adventofcode.com/2020/day/2/input
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
        // The number of valid passwords
        int valid = 0;
        // Loop through every password
        while (sc.hasNext()){
            // Split the information from the line
            String[] split = sc.nextLine().split(" ");
            char letter = split[1].charAt(0);
            int min = Integer.parseInt(split[0].substring(0,split[0].indexOf('-')));
            int max = Integer.parseInt(split[0].substring(split[0].indexOf('-')+1));

            // Part 1 finds the number of passwords that have the given letter
            // within the range number of times
            if (PART == 1){
                // The number of times the letter appears
                int count = 0;
                // Loop through every character
                for (int i=0; i<split[2].length(); ++i){
                    // Record if it matches the letter
                    if (split[2].charAt(i) == letter){
                        ++count;
                    }
                }
                // If the count is within the bounds, it's valid
                if (count >= min && count <= max){
                    ++valid;
                }
            }

            // Part 2 finds the number of passwords where exactly one of the given
            // indexes points to the given letter
            if (PART == 2){
                if (split[2].charAt(min-1) == letter ^ split[2].charAt(max-1) == letter){
                    ++valid;
                }
            }
        }

        // Print the answer
        System.out.println(valid);
    }
}