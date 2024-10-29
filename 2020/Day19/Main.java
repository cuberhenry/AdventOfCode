/*
Henry Anderson
Advent of Code 2020 Day 19 https://adventofcode.com/2020/day/19
Input: https://adventofcode.com/2020/day/19/input
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
        // The map representing the replacement rules
        HashMap<String,String> rules = new HashMap<>();

        // Take in the first line
        String line = sc.nextLine();
        // Continue until the end of the rules
        while (!line.equals("")){
            // Split and save the line
            String[] split = line.split(": ");
            rules.put(split[0],split[1]);
            line = sc.nextLine();
        }

        // Part 1 finds the number of strings that match rule 0
        // Part 2 replaces rules 8 and 11 to use recursion
        if (PART == 2){
            // 8: 42 | 42 8
            // Use the + to indicate one or more 42s
            rules.put("8","42 +");
            // 11: 42 31 | 42 11 31
            // Approximate the solution by saying this is "good enough"
            rules.put("11","42 ( 42 ( 42 ( 42 ( 42 ( 42 31 )? 31 )? 31 )? 31 )? 31 )? 31");
        }

        // Start with rule 0
        String regex = "0";
        // Continue until there are no more rule replacements
        while (true){
            // Whether no replacements have been done
            boolean done = true;
            // Split on any number of spaces
            String[] split = regex.replaceAll("  "," ").split(" ");

            // Loop through each token
            for (int i=0; i<split.length; ++i){
                // If it's a number
                if (Character.isDigit(split[i].charAt(0))){
                    // Replace it with its next rule
                    split[i] = "( " + rules.get(split[i]) + " )";
                    // A replacement has been done
                    done = false;
                }
            }

            // Return to a string
            regex = split[0];
            for (int i=1; i<split.length; ++i){
                regex += " " + split[i];
            }

            // No replacements means it's a valid regex
            if (done){
                break;
            }
        }
        // Remove unneeded characters
        regex = regex.replaceAll(" |\"","");

        // The number of matches
        int count = 0;
        // Loop through each string
        while (sc.hasNext()){
            // Count if it matches
            if (sc.nextLine().matches(regex)){
                ++count;
            }
        }
        
        // Print the answer
        System.out.println(count);
    }
}