/*
Henry Anderson
Advent of Code 2023 Day 9 https://adventofcode.com/2023/day/9
Input: https://adventofcode.com/2023/day/9/input
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

        // For every list of numbers
        while (sc.hasNext()){
            // The stack of numbers
            Stack<Integer> stack = new Stack<>();
            // Turn the string of numbers into an int array
            String[] split = sc.nextLine().split(" ");
            int[] numbers = new int[split.length];
            for (int i=0; i<split.length; ++i){
                numbers[i] = Integer.parseInt(split[i]);
            }

            // Whether enough information was found to extrapolate
            boolean finished = false;
            // Continue until finished
            while (!finished){
                // Create an array representing changes between the numbers
                int[] next = new int[numbers.length-1];
                
                finished = true;
                // For each new number
                for (int i=0; i<next.length; ++i){
                    // Set it to the difference between the two numbers in the old array
                    next[i] = numbers[i+1] - numbers[i];
                    // The old array does not have a constant slope
                    if (next[i] != next[0]){
                        finished = false;
                    }
                }

                // Put the relevant numbers on the stack
                // Part 1 finds the next value in the pattern
                if (PART == 1){
                    stack.push(numbers[numbers.length-1]);
                }

                // Part 2 finds the previous value in the pattern
                if (PART == 2){
                    stack.push(numbers[0]);
                }

                numbers = next;
            }

            // Get the change between the edge numbers and the extrapolated numbers
            int next = numbers[0];

            // Continue until the target is found
            while (!stack.isEmpty()){
                // Find the number after the saved number
                if (PART == 1){
                    next = stack.pop() + next;
                }
                // Find the number before the saved number
                if (PART == 2){
                    next = stack.pop() - next;
                }
            }

            // Add the number to the total
            total += next;
        }

        // Print the answer
        System.out.println(total);
    }
}