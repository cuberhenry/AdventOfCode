/*
Henry Anderson
Advent of Code 2021 Day 24 https://adventofcode.com/2021/day/24
Input: https://adventofcode.com/2021/day/24/input
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
        // The relevant input
        int[][] input = new int[14][2];

        // Loop through each set of instructions and get the input
        for (int i=0; i<14; ++i){
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            input[i][0] = Integer.parseInt(sc.nextLine().split(" ")[2]);
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            input[i][1] = Integer.parseInt(sc.nextLine().split(" ")[2]);
            sc.nextLine();
            sc.nextLine();
        }

        // Whether we're looking for the highest number
        boolean highest = true;
        
        // Part 2 finds the lowest valid number
        if (PART == 2){
            highest = false;
        }

        // The stack of pushed indices
        Stack<Integer> stack = new Stack<>();
        // The answer
        int[] answer = new int[14];
        // Loop through each instruction
        for (int i=0; i<14; ++i){
            // If the first input is 0, push
            if (input[i][0] > 0){
                stack.push(i);
            }else{
                // Get the previous index
                int index = stack.pop();
                // Get the change between the two parameters
                int delta = input[index][1] + input[i][0];
                // Find the lowest or highest value allowed for both indices
                answer[index] = highest ? Math.min(9,9-delta) : Math.max(1,1-delta);
                answer[i] = highest ? Math.min(9,9+delta) : Math.max(1,1+delta);
            }
        }

        // Print the answer
        for (int a : answer){
            System.out.print(a);
        }
        System.out.println();
    }
}