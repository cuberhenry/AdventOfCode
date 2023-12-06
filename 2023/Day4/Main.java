/*
Henry Anderson
Advent of Code 2023 Day 4 https://adventofcode.com/2023/day/4
Input: https://adventofcode.com/2023/day/4/input
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
        // The answer to the problem
        int total = 0;
        // Used for Part 2, the number of copies of each scratch card
        ArrayList<Integer> copies = new ArrayList<>();

        // Loop through every scratch card
        for (int index = 0; sc.hasNext(); ++index){
            // Increase the number of the current card by one for the original
            if (PART == 2){
                if (index == copies.size()){
                    copies.add(1);
                }else{
                    copies.set(index,copies.get(index)+1);
                }
            }

            // Add a space to standardize number checking to three characters
            String line = sc.nextLine() + " ";
            // The score of the card
            int score = 0;
            // Loop through every number that you have
            for (int i = line.indexOf('|') + 2; i < line.length(); i += 3){
                // If there's a matching number
                if (line.indexOf(line.substring(i,i+3)) < line.indexOf('|')){
                    // Part 1 finds the total score of all cards
                    if (PART == 1){
                        // Increase the score
                        if (score == 0){
                            score = 1;
                        }else{
                            score *= 2;
                        }
                    }

                    // Part 2 gives card duplicates equal to the number of matches
                    if (PART == 2){
                        // Increase the score
                        ++score;
                    }
                }
            }
            
            if (PART == 1){
                // Add the score to the total
                total += score;
            }

            if (PART == 2){
                // Add the duplicate cards
                for (int i=1; i<=score; ++i){
                    if (index+i == copies.size()){
                        copies.add(copies.get(index));
                    }else{
                        copies.set(index+i,copies.get(index+i)+copies.get(index));
                    }
                }

                // Add the number of this scratch card owned
                total += copies.get(index);
            }
        }

        // Print the answer
        System.out.println(total);
    }
}