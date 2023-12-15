/*
Henry Anderson
Advent of Code 2023 Day 15 https://adventofcode.com/2023/day/15
Input: https://adventofcode.com/2023/day/15/input
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
        long total = 0;
        // Take in the new line and split it into each section
        String[] input = sc.nextLine().split(",");

        // Part 1 finds the result of running the hash on each input string        
        if (PART == 1){
            // For every string in the input
            for (String str : input){
                // The current total
                int curr = 0;
                // Loop through every character
                for (int i=0; i<str.length(); ++i){
                    // Perform the calculations for this character
                    curr = (curr + str.charAt(i)) * 17 % 256;
                }
                // Add to the total
                total += curr;
            }
        }

        // Part 2 finds the focusing power of the lenses
        if (PART == 2){
            // The list of boxes, always 256 long
            ArrayList<ArrayList<String>> boxes = new ArrayList<>();
            for (int i=0; i<256; ++i){
                boxes.add(new ArrayList<String>());
            }

            // Loop through every input string
            for (String str : input){
                // The hash value of the letters
                int curr = 0;
                // The current character in the string
                int index = 0;
                // As long as the index points to a letter
                for (;Character.isAlphabetic(str.charAt(index)); ++index){
                    // Perform the calculations for this character
                    curr = (curr + str.charAt(index)) * 17 % 256;
                }
                // If the next character is a dash, remove the given lense
                if (str.charAt(index) == '-'){
                    // Loop through every existing lense in the curr box
                    for (int i=0; i<boxes.get(curr).size(); ++i){
                        // If it has the right label
                        if (boxes.get(curr).get(i).split(" ")[0].equals(str.substring(0,index))){
                            // Remove it
                            boxes.get(curr).remove(i);
                            break;
                        }
                    }
                // Otherwise, add or replace the given lense
                }else{
                    // Whether an existing lense has been found
                    boolean found = false;
                    // Loop through every existing lense in the curr box
                    for (int i=0; i<boxes.get(curr).size(); ++i){
                        // If it has the same label
                        if (boxes.get(curr).get(i).split(" ")[0].equals(str.substring(0,index))){
                            // Replace the lense with the new lense
                            boxes.get(curr).set(i,str.substring(0,index) + " " + str.substring(index+1));
                            // Existing lense found
                            found = true;
                            break;
                        }
                    }
                    // Otherwise
                    if (!found){
                        // Add the lense at the end
                        boxes.get(curr).add(str.substring(0,index) + " " + str.substring(index+1));
                    }
                }
            }

            // Calculate the focusing power of the lenses
            // Loop through every box and lense
            for (int i=0; i<boxes.size(); ++i){
                for (int j=0; j<boxes.get(i).size(); ++j){
                    // Add the focusing power of the lense
                    total += (1 + i) * (1 + j) * Integer.parseInt(boxes.get(i).get(j).split(" ")[1]);
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}