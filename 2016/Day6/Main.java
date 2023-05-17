/*
Henry Anderson
Advent of Code 2016 Day 6 https://adventofcode.com/2016/day/6
Input: https://adventofcode.com/2016/day/6/input
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
        // The first line of input
        String line = sc.next();
        // The frequency of each letter
        int[][] chars = new int[line.length()][26];
        // While there's input
        while (sc.hasNext()){
            // Loop through every character
            for (int i=0; i<line.length(); ++i){
                // Add the character to the frequencies
                ++chars[i][line.charAt(i)-'a'];
            }
            // Take in the next line
            line = sc.next();
        }
        // Perform frequency check on the last line of input
        for (int i=0; i<line.length(); ++i){
            ++chars[i][line.charAt(i)-'a'];
        }

        // The message
        String message = "";
        // Loop through every character
        for (int i=0; i<line.length(); ++i){
            // The index of the character
            int character = 0;
            // Loop through every other character
            for (int j=1; j<26; ++j){
                // Part 1 finds the most common characters
                if (PART == 1){
                    // If the character occurs more than the current character
                    if (chars[i][j] > chars[i][character]){
                        character = j;
                    }
                }

                // Part 2 finds the least common but still occurring character
                if (PART == 2){
                    // If the character occurs less than the current character
                    if (chars[i][j] < chars[i][character] && chars[i][j] != 0){
                        character = j;
                    }
                }
            }

            // Add the character to the message
            message += (char)(character + 'a');
        }

        // Print the message
        System.out.println(message);
    }
}