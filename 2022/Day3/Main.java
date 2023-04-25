/*
Henry Anderson
Advent of Code 2022 Day 3 https://adventofcode.com/2022/day/3
Input: https://adventofcode.com/2022/day/3/input
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
        PART = Integer.parseInt(args[0]);
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
        // Total priority value of all requested items
        int total = 0;
        
        // Part 1 finds the items found in both compartments of each sack
        if (PART == 1){
            // Loop through all lines of input
            while (sc.hasNext()){
                // The knapsack items
                String line = sc.next();
                // Whether the loops should continue searching
                boolean good = true;
                
                // Loop through all items in the first and second halves
                for (int j=0; j<line.length()/2 && good; ++j){
                    for (int k=line.length()/2; k<line.length() && good; ++k){
                        if (line.charAt(j) == line.charAt(k)){
                            // Add the priority value
                            total += (line.charAt(j) - 'A' + 27) % 58;
                            good = false;
                        }
                    }
                }
            }
        }
        
        // Part 2 finds the items found in three sacks
        if (PART == 2){
            // Loop through all sets of three lines of input
            while (sc.hasNext()){
                // The three sacks to search through
                String one = sc.next();
                String two = sc.next();
                String three = sc.next();
                
                // Search through all items of all three sacks
                outer:
                for (int j=0; j<one.length(); ++j){
                    for (int k=0; k<two.length(); ++k){
                        // All three items have to match, so skip if two don't
                        if (one.charAt(j) != two.charAt(k)){
                            continue;
                        }
                        for (int l=0; l<three.length(); ++l){
                            if (one.charAt(j) == three.charAt(l)){
                                // Add the priority value
                                total += (one.charAt(j) - 'A' + 27) % 58;
                                break outer;
                            }
                        }
                    }
                }
            }
        }
        
        System.out.println(total);
    }
}