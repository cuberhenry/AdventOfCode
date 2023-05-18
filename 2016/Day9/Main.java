/*
Henry Anderson
Advent of Code 2016 Day 9 https://adventofcode.com/2016/day/9
Input: https://adventofcode.com/2016/day/9/input
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
        // Take in the entire input
        String file = sc.nextLine();
        // The length of the decompressed file
        long length = 0;
        // The list of all currently active multipliers
        ArrayList<String> multipliers = new ArrayList<>();
        // The current value of all multipliers
        long multiplier = 1;

        // Loop through every character in the input file
        for (int i=0; i<file.length(); ++i){
            // Loop through every multiplier
            for (int j=0; j<multipliers.size(); ++j){
                // If the current one expires at this character
                while (j < multipliers.size() && i == Integer.parseInt(multipliers.get(j).split(" ")[0])){
                    // Get the number of repetitions that multiplier contributed
                    int numReps = Integer.parseInt(multipliers.remove(j).split(" ")[1]);
                    // Decrease the multiplier
                    multiplier /= numReps;
                }
            }
            // If it's a marker
            if (file.charAt(i) == '('){
                // Skip the parenthesis
                ++i;
                // Used for collecting information from the marker
                String num = "";
                // Collect the first number
                while (file.charAt(i) != 'x'){
                    num += file.charAt(i);
                    ++i;
                }
                // The number of characters to repeat
                int numChars = Integer.parseInt(num);
                // Reset for the second number
                ++i;
                num = "";
                // Collect the second number
                while (file.charAt(i) != ')'){
                    num += file.charAt(i);
                    ++i;
                }
                // The number of repetitions
                int numReps = Integer.parseInt(num);

                // Part 1 only performs decompression on non-repeated markers
                if (PART == 1){
                    // Skip the repeated characters
                    i += numChars;
                    // Increase the length
                    length += numChars * numReps;
                }

                // Part 2 performs repetitions on all markers
                if (PART == 2){
                    // Add the multiplier to the list
                    multipliers.add(numChars + i+1 + " " + numReps);
                    // Increase the multiplier
                    multiplier *= numReps;
                }
            }else{
                // Increase the length
                if (PART == 1){
                    ++length;
                }

                if (PART == 2){
                    length += multiplier;
                }
            }
        }

        // Print the answer
        System.out.println(length);
    }
}