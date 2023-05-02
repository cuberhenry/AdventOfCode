/*
Henry Anderson
Advent of Code 2015 Day 5 https://adventofcode.com/2015/day/5
Input: https://adventofcode.com/2015/day/5/input
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
        // Constants for finding matches
        String vowels = "aeiou";
        String forbidden = "ab cd pq xy";
        // The number of nice strings
        int total = 0;

        // While there's input
        while (sc.hasNext()){
            // Take in the next input
            String string = sc.next();
            // The number of vowels in the string
            int numVowels = 0;
            // The two conditions
            boolean first = false;
            boolean second = false;
            // All pairs of characters
            ArrayList<String> pairs = new ArrayList<>();

            // Loop through every character
            for (int i=0; i<string.length(); ++i){
                // Split depending one which part to find different conditions
                if (PART == 1){
                    // Contains at least three vowels
                    if (vowels.indexOf(string.charAt(i)) != -1){
                        ++numVowels;
                    }

                    if (i < string.length()-1){
                        // Contains a letter pair
                        if (string.charAt(i) == string.charAt(i+1)){
                            first = true;
                        }

                        // Doesn't contain one of the forbidden pairs
                        if (forbidden.indexOf(string.substring(i,i+2)) != -1){
                            second = true;
                            break;
                        }
                    }
                }

                if (PART == 2){
                    // Contains a duplicate pair not overlapping
                    if (!first && i < string.length()-1){
                        int index = pairs.indexOf(string.substring(i,i+2));
                        if (index != -1 && index != pairs.size()-1){
                            first = true;
                        }else{
                            pairs.add(string.substring(i,i+2));
                        }
                    }

                    // Contains a single-layer sandwich
                    if (!second && i < string.length()-2){
                        if (string.charAt(i) == string.charAt(i+2)){
                            second = true;
                        }
                    }
                }
            }

            // Increase total if the string is nice
            if (PART == 1){
                if (numVowels >= 3 && first && !second){
                    ++total;
                }
            }

            if (PART == 2){
                if (first && second){
                    ++total;
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}