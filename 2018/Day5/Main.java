/*
Henry Anderson
Advent of Code 2018 Day 5 https://adventofcode.com/2018/day/5
Input: https://adventofcode.com/2018/day/5/input
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
        // Take in the polymer
        String polymer = sc.next();

        // Loop through every pair of characters in reverse
        for (int i=polymer.length()-2; i>=0; --i){
            // Ensure we aren't checking after the end of the polymer
            if (i > polymer.length()-2){
                continue;
            }
            // If the characters are the same letter of different cases
            if (Math.abs(polymer.charAt(i)-polymer.charAt(i+1)) == 32){
                // Remove both
                polymer = polymer.substring(0,i) + polymer.substring(i+2);
            }
        }

        // Save the length
        int answer = polymer.length();

        // Part 1 returns the length of the polymer
        // Part 2 returns the minimum length of the polymer after
        // removing all of one letter
        if (PART == 2){
            // Loop through every letter
            for (int i=0; i<26; ++i){
                // Save a new polymer
                String newPolymer = polymer;
                // Loop through every character
                for (int j=newPolymer.length()-1; j>=0; --j){
                    // If the letter is the current letter
                    if ((newPolymer.charAt(j)-i-'a')%32 == 0){
                        // Remove it
                        newPolymer = newPolymer.substring(0,j) + newPolymer.substring(j+1);
                    }
                }

                // Loop through every pair of characters in reverse
                for (int j=newPolymer.length()-2; j>=0; --j){
                    // Ensure we aren't checking after the end of the polymer
                    if (j > newPolymer.length()-2){
                        continue;
                    }
                    // If the characters are the same letter of different cases
                    if (Math.abs(newPolymer.charAt(j)-newPolymer.charAt(j+1)) == 32){
                        // Remove both
                        newPolymer = newPolymer.substring(0,j) + newPolymer.substring(j+2);
                    }
                }

                // If the polymer is smaller, save the new length
                if (newPolymer.length() < answer){
                    answer = newPolymer.length();
                }
            }
        }

        // Print the answer
        System.out.println(answer);
    }
}