/*
Henry Anderson
Advent of Code 2020 Day 15 https://adventofcode.com/2020/day/15
Input: https://adventofcode.com/2020/day/15/input
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
        // When each number was last spoken
        HashMap<Integer,Integer> history = new HashMap<>();
        // Take in the starting numbers
        String[] input = sc.nextLine().split(",");
        // The current turn
        int turn = 0;
        
        // Add each number except the last to history
        while (turn<input.length-1){
            history.put(Integer.parseInt(input[turn]),turn);
            ++turn;
        }
        // Save the last number without putting it in history
        int lastNum = Integer.parseInt(input[turn]);
        ++turn;

        // The number of turns to execute
        int turns = 2020;

        // Part 1 finds the 2020th number spoken
        // Part 2 finds the 30000000th number spoken
        if (PART == 2){
            turns = 30000000;
        }

        // Continue until the turnth turn
        while (turn < turns){
            // If the number was spoken before
            if (history.containsKey(lastNum)){
                // Find the difference
                int diff = turn - 1 - history.get(lastNum);
                // Record the new most recent turn
                history.put(lastNum,turn-1);
                // Say the difference
                lastNum = diff;
            }else{
                // Otherwise, add it to history and say 0
                history.put(lastNum,turn-1);
                lastNum = 0;
            }
            ++turn;
        }

        // Print the answer
        System.out.println(lastNum);
    }
}