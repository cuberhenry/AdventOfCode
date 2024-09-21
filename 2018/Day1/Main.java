/*
Henry Anderson
Advent of Code 2018 Day 1 https://adventofcode.com/2018/day/1
Input: https://adventofcode.com/2018/day/1/input
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
        // The current frequency
        int frequency = 0;

        // Part 1 finds the resulting frequency after all changes
        if (PART == 1){
            // Continue through every input
            while (sc.hasNext()){
                // Change the frequency
                frequency += sc.nextInt();
            }
        }

        // Part 2 finds the first repeated frequency
        if (PART == 2){
            // Store the changes in case you need to repeat
            ArrayList<Integer> changes = new ArrayList<>();
            // All of the visited frequencies
            ArrayList<Integer> reached = new ArrayList<>();

            // Record all of the changes
            while (sc.hasNext()){
                changes.add(sc.nextInt());
            }

            // Repeat until a repeat is found
            while (!reached.contains(frequency)){

                for (int i=0; i<changes.size(); ++i){
                    reached.add(frequency);

                    // Change the frequency
                    frequency += changes.get(i);

                    // Check for repeats
                    if (reached.contains(frequency)){
                        break;
                    }
                }
            }
        }

        // Print the answer
        System.out.println(frequency);
    }
}