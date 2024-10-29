/*
Henry Anderson
Advent of Code 2015 Day 8 https://adventofcode.com/2015/day/8
Input: https://adventofcode.com/2015/day/8/input
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
        // The number of characters in the input
        int total = 0;
        // The number of characters of the encoding
        int size = 0;

        // Take in each line of input
        while (sc.hasNext()){
            // Take in the next line
            String line = sc.nextLine();
            // Increase total by the number of characters of this line
            total += line.length();

            // Add six for the quotation marks
            if (PART == 2){
                size += 6;
            }

            // Loop through every inner character
            for (int i=1; i<line.length()-1; ++i){
                // Increase the size
                ++size;

                // Part 1 finds the difference in actual characters to saved characters
                if (PART == 1){
                    // If there's an escape character
                    if (line.charAt(i) == '\\'){
                        // They count as one
                        ++i;
                        // An x indicates skipping more characters
                        if (line.charAt(i) == 'x'){
                            i += 2;
                        }
                    }
                }

                // Part 2 finds the number of characters required to encode the input
                if (PART == 2){
                    // Every \ and " requires an escape
                    if (line.charAt(i) == '\\' || line.charAt(i) == '"'){
                        ++size;
                    }
                }
            }
        }

        // Print the answer
        System.out.println(Math.abs(total - size));
    }
}