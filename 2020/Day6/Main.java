/*
Henry Anderson
Advent of Code 2020 Day 6 https://adventofcode.com/2020/day/6
Input: https://adventofcode.com/2020/day/6/input
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
        // The number of people in the current group
        int numPeople = 0;
        // The number of yesses to each question in the current group
        int[] yes = new int[26];
        // The total number of yesses
        int total = 0;

        // Loop through every person's answers
        while (sc.hasNext()){
            // Take in the next line
            String line = sc.nextLine();
            // If the group is finished
            if (line.equals("")){
                // Loop through every question
                for (int i=0; i<yes.length; ++i){
                    // Part 1 finds the number of questions answered yes within a group
                    if (PART == 1){
                        if (yes[i] > 0){
                            ++total;
                        }
                    }

                    // Part 2 finds the number of questions answered yes by an entire group
                    if (PART == 2){
                        if (yes[i] == numPeople){
                            ++total;
                        }
                    }
                }
                // Reset
                yes = new int[26];
                numPeople = 0;
                continue;
            }

            // Increase the current group size
            ++numPeople;
            // Add their yesses
            for (int i=0; i<line.length(); ++i){
                ++yes[line.charAt(i)-'a'];
            }
        }

        // Score the final group
        for (int i=0; i<yes.length; ++i){
            if (PART == 1){
                if (yes[i] > 0){
                    ++total;
                }
            }
            if (PART == 2){
                if (yes[i] == numPeople){
                    ++total;
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}