/*
Henry Anderson
Advent of Code 2022 Day 6 https://adventofcode.com/2022/day/6
Input: https://adventofcode.com/2022/day/6/input
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
        // The input as a string
        String input = sc.next();
        // The number of unique characters needed
        int numChars = 0;
        // The characters being compared
        ArrayList<Character> chars = new ArrayList<>();

        // Part 1 requires 4 unique characters
        if (PART == 1){
            numChars = 4;
        }

        // Part 2 requires 14 unique characters
        if (PART == 2){
            numChars = 14;
        }

        // The index of the current character
        int i = 0;
        // Until there are numChars unique characters
        while (chars.size() < numChars){
            // Save the index of the value
            int copy = chars.indexOf(input.charAt(i));
            // While there are characters before the duplicate
            while (copy < chars.size() && copy >= 0){
                // Remove them
                chars.remove(copy);
            }
            // Add the character
            chars.add(0,input.charAt(i));
            // increase the index
            ++i;
        }
        // Print out the number of chars read
        System.out.println(i);
    }
}