/*
Henry Anderson
Advent of Code 2015 Day 11 https://adventofcode.com/2015/day/11
Input: https://adventofcode.com/2015/day/11/input
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
        // The current password we're examining
        char[] password = sc.next().toCharArray();

        // The number of times to find a new password
        int numTimes = 1;

        // Part 2 finds the second password
        if (PART == 2){
            numTimes = 2;
        }

        // Continue until a break has been found
        while (true){
            // Whether there's a straight
            boolean straight = false;
            // Whether there's a forbidden letter
            boolean forbidden = false;
            // The number of pairs of letters in the password
            int pairs = 0;

            // Used to increment the password
            int i = password.length-1;
            // Until a non-z has been found
            while (i >= 0 && password[i] == 'z'){
                // Set it to a and carry
                password[i] = 'a';
                --i;
            }
            // Increase the next letter
            if (i >= 0){
                ++password[i];
            }

            // To make sure the pairs aren't overlapping
            int pairIndex = -2;
            // Loop through every character
            for (i=0; i<password.length; ++i){
                // If the next three letters are a straight
                if (i < password.length-2 && password[i] + 1 == password[i+1]
                                          && password[i+1] + 1 == password[i+2]){
                    straight = true;
                }
                // If the current letter is a forbidden letter
                if (password[i] == 'i' || password[i] == 'o' || password[i] == 'l'){
                    forbidden = true;
                }
                // If the next two letters are a pair and the current letter
                // isn't part of a pair already
                if (i < password.length-1 && password[i] == password[i+1] && i - pairIndex >= 2){
                    ++pairs;
                    pairIndex = i;
                }
            }

            // If all conditions are met, password found
            if (straight && !forbidden && pairs >= 2){
                --numTimes;
                if (numTimes == 0){
                    break;
                }
            }
        }

        // Print the new password
        System.out.println(new String(password));
    }
}