/*
Henry Anderson
Advent of Code 2020 Day 9 https://adventofcode.com/2020/day/9
Input: https://adventofcode.com/2020/day/9/input
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
        // Take in the first 25 numbers
        ArrayList<Long> list = new ArrayList<>();
        for (int i=0; i<25; ++i){
            list.add(sc.nextLong());
        }

        // Continue until the end of the list is found
        while (sc.hasNext()){
            // Take in the next number
            long num = sc.nextLong();
            // Whether a sum has been found
            boolean good = false;
            // Loop through every pair from the previous 25 numbers
            for (int i=list.size()-25; i<list.size()-1 && !good; ++i){
                for (int j=i+1; j<list.size(); ++j){
                    // If the sum matches
                    if (num == list.get(i) + list.get(j)){
                        // Exit the loop
                        good = true;
                        break;
                    }
                }
            }
            // Add the new value
            list.add(num);

            // If no sum was found, break
            if (!good){
                break;
            }
        }

        // Part 1 finds the first number that's not the sum of two recent numbers
        if (PART == 1){
            System.out.println(list.getLast());
        }

        // Part 2 finds the encryption weakness using the number found in Part 1
        if (PART == 2){
            // Loop through every number in the list
            for (int i=0; i<list.size(); ++i){
                // Record three important values
                long sum = list.get(i);
                long max = sum;
                long min = sum;
                // Loop through every subsequent number in the list
                for (int j=i+1; j<list.size(); ++j){
                    // Update the three important values
                    sum += list.get(j);
                    max = Math.max(max,list.get(j));
                    min = Math.min(min,list.get(j));
                    // If the sum matches
                    if (sum == list.getLast()){
                        // Print the answer
                        System.out.println(min + max);
                        return;
                    }
                }
            }
        }
    }
}