/*
Henry Anderson
Advent of Code 2015 Day 1 https://adventofcode.com/2015/day/1
Input: https://adventofcode.com/2015/day/1/input
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
        String line = sc.nextLine();
        // The current floor
        int answer = 0;
        // Loop through ever parenthesis
        for (int i=0; i<line.length(); ++i){
            // Go up or down depending on the character
            if (line.charAt(i) == '('){
                ++answer;
            }else{
                --answer;
            }

            // Part 1 finds the final floor
            // Part 2 finds the first index at which Santa goes into the basement
            if (PART == 2){
                if (answer < 0){
                    answer = i+1;
                    break;
                }
            }
        }

        // Print the answer
        System.out.println(answer);
    }
}