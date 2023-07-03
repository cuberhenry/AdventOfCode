/*
Henry Anderson
Advent of Code 2016 Day 16 https://adventofcode.com/2016/day/16
Input: https://adventofcode.com/2016/day/16/input
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
        // The size of the disc to be filled
        int size = 0;

        // Part 1 fills a disc of size 272
        if (PART == 1){
            size = 272;
        }

        // Part 2 fills a disc of size 35651584
        if (PART == 2){
            size = 35651584;
        }

        // Take in the starting string
        String string = sc.next();

        // The current value
        ArrayList<Boolean> bools = new ArrayList<>();
        // Translate the string into the boolean array list
        for (int i=0; i<string.length(); ++i){
            bools.add(string.charAt(i) == '1');
        }

        // While the number isn't big enough
        while (bools.size() < size){
            // Add the middle zero
            bools.add(false);
            // Loop through every digit in the original number backwards
            for (int i=bools.size()-2; i>=0 && bools.size() < size; --i){
                // Add the negation of that value
                bools.add(!bools.get(i));
            }
        }

        // While the checksum is even
        while (bools.size() % 2 == 0){
            // Create a new array list
            ArrayList<Boolean> newBools = new ArrayList<>();
            // Loop through every pair of booleans
            for (int i=0; i<bools.size(); i+=2){
                // Add the new checksum value
                newBools.add(bools.get(i)==bools.get(i+1));
            }
            // Replace the previous number
            bools = newBools;
        }

        // Translate the array list back into a string
        String answer = "";
        for (boolean b : bools){
            answer += b ? 1 : 0;
        }

        // Print the answer
        System.out.println(answer);
    }
}