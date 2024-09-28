/*
Henry Anderson
Advent of Code 2019 Day 4 https://adventofcode.com/2019/day/4
Input: https://adventofcode.com/2019/day/4/input
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
        // Take in the input
        String[] split = sc.nextLine().split("-");
        int low = Integer.parseInt(split[0]);
        int high = Integer.parseInt(split[1]);
        // The number of valid passwords
        int count = 0;

        // Loop through every password in the range
        for (int i=low; i<high; ++i){
            // Whether the password meets the criteria
            boolean isValid = false;
            // Save the potential password as a string
            String num = "" + i;
            // Loop through every pair of characters
            for (int j=1; j<6; ++j){
                // Non-decreasing digits
                if (num.charAt(j) < num.charAt(j-1)){
                    isValid = false;
                    break;
                // Must have at least one consecutive pair of the same digit
                }else if (num.charAt(j) == num.charAt(j-1)){
                    // Part 1 requires two consecutive identical digits
                    if (PART == 1){
                        isValid = true;
                    }

                    // Part 2 requires that the pair not be in a group of more than 2
                    if (PART == 2){
                        if ((j == 1 || num.charAt(j-2) != num.charAt(j))
                            && (j == 5 || num.charAt(j+1) != num.charAt(j))){
                                isValid = true;
                            }
                    }
                }
            }
            // Include the password if it's valid
            if (isValid){
                ++count;
            }
        }
        
        // Print the answer
        System.out.println(count);
    }
}