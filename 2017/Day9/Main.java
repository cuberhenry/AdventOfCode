/*
Henry Anderson
Advent of Code 2017 Day 9 https://adventofcode.com/2017/day/9
Input: https://adventofcode.com/2017/day/9/input
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
        // The stream input
        String input = sc.nextLine();
        // The answer to the problem
        int total = 0;
        // The number of containing groups
        int depth = 0;
        // Whether the current index is within garbage
        boolean garbage = false;
        // Whether the current character should be ignored
        boolean ignore = false;
        // Loop through every character
        for (int i=0; i<input.length(); ++i){
            if (garbage){
                // If we're in garbage and the character isn't getting ignored
                if (!ignore){
                    if (input.charAt(i) == '!'){
                        // Ignore the next character
                        ignore = true;
                    }else if (input.charAt(i) == '>'){
                        // End the garbage
                        garbage = false;
                    }else{
                        // Part 2 finds the number of characters in garbage
                        if (PART == 2){
                            ++total;
                        }
                    }
                }else{
                    // Ignore the current character and reset the flag
                    ignore = false;
                }
            }else{
                // Reset the ignore flag
                ignore = false;
                if (input.charAt(i) == '{'){
                    // Go deeper
                    ++depth;
                }else if (input.charAt(i) == '}'){
                    // Part 1 finds the total score of all groups
                    if (PART == 1){
                        total += depth;
                    }

                    // Back out of a group
                    --depth;
                }else if (input.charAt(i) == '<'){
                    // Start garbage
                    garbage = true;
                }
            }
        }
        // Print the answer
        System.out.println(total);
    }
}