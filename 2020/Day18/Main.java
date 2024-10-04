/*
Henry Anderson
Advent of Code 2020 Day 18 https://adventofcode.com/2020/day/18
Input: https://adventofcode.com/2020/day/18/input
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
        // The sum of each expression
        long total = 0;
        // Loop through every expression
        while (sc.hasNext()){
            // Take in the input
            String line = sc.nextLine();

            // Used when entering or exiting parentheses
            Stack<Long> stack = new Stack<>();
            // The value of this expression
            long value = 0;
            // The operation to perform
            long op = 0;
            
            // Part 1 performs addition and mulitiplication left to right
            // Part 2 performs addition first, then multiplication
            // Parse the input
            for (int i=0; i<line.length(); ++i){
                // Perform an action based on the character
                switch(line.charAt(i)){
                    // Skip spaces
                    case ' ' -> {}
                    case '(' -> {
                        // Save pre-paren value and op
                        stack.push(value);
                        stack.push(op);

                        if (PART == 2){
                            // Add a paren flag
                            stack.push(-1L);
                        }

                        // Start from scratch inside parens
                        value = 0;
                        op = 0;
                    }
                    case ')' -> {
                        if (PART == 1){
                            // Combine paren value with pre-paren value
                            if (stack.pop() == 0){
                                value += stack.pop();
                            }else{
                                value *= stack.pop();
                            }
                        }

                        if (PART == 2){
                            // Perform all the queued multiplication
                            while (stack.pop() != -1){
                                value *= stack.pop();
                            }

                            // Combine paren value with pre-paren value
                            if (stack.pop() == 0){
                                value += stack.pop();
                            }else{
                                value *= stack.pop();
                            }
                        }
                    }
                    case '+' -> {
                        // Set the operator
                        op = 0;
                    }
                    case '*' -> {
                        if (PART == 1){
                            // Set the operator
                            op = 1;
                        }
                        
                        if (PART == 2){
                            // Make sure to do all addition first
                            stack.push(value);
                            stack.push(1L);
                            value = 0;
                            op = 0;
                        }
                    }
                    // Number
                    default -> {
                        // Add or multiply the new number
                        if (op == 0){
                            value += line.charAt(i) - '0';
                        }else{
                            value *= line.charAt(i) - '0';
                        }
                    }
                }
            }
            // Resolve any queued multiplication
            while (!stack.empty()){
                if (stack.pop() == 0){
                    value += stack.pop();
                }else{
                    value *= stack.pop();
                }
            }

            // Add the result to the total
            total += value;
        }

        // Print the answer
        System.out.println(total);
    }
}