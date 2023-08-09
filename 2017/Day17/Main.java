/*
Henry Anderson
Advent of Code 2017 Day 17 https://adventofcode.com/2017/day/17
Input: https://adventofcode.com/2017/day/17/input
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
        // The number of steps to move forward each time
        int steps = sc.nextInt();
        // The current pointer
        int position = 0;

        // Part 1 finds the value after 2017 after inserting 2017 values
        if (PART == 1){
            // Create a data structure
            ArrayList<Integer> spinlock = new ArrayList<>();
            // Add the initial value
            spinlock.add(0);

            // Loop through every value to add
            for (int i=1; i<=2017; ++i){
                // Move forward step number of values
                position = (position + steps) % spinlock.size() + 1;
                // Insert the new value
                spinlock.add(position,i);
            }

            // Print the value after the final value that was added
            System.out.println(spinlock.get((position+1)%spinlock.size()));
        }

        // Part 2 finds the value after 0 after inserting 5000000 values
        if (PART == 2){
            // For Part 2, a data structure is more time consuming and not necessary
            // The size of the imaginary data structure
            int size = 1;
            // The second value of the imaginary data structure
            int answer = 1;

            // Loop through every value to add
            for (int i=1; i<=50_000_000; ++i){
                // Move forward step number of values
                position = (position + steps) % size + 1;
                // Increase the size
                ++size;
                // Update the second value if applicable
                if (position == 1){
                    answer = i;
                }
            }

            // Print the answer
            System.out.println(answer);
        }
    }
}