/*
Henry Anderson
Advent of Code 2020 Day 10 https://adventofcode.com/2020/day/10
Input: https://adventofcode.com/2020/day/10/input
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
        // All of the joltage values
        ArrayList<Integer> adapters = new ArrayList<>();
        // The wall plug
        adapters.add(0);
        // Take in all the adapters
        while (sc.hasNext()){
            adapters.add(sc.nextInt());
        }
        Collections.sort(adapters);
        // Your device's built-in adapter
        adapters.add(adapters.getLast()+3);
        
        // Part 1 finds the differences in joltage adapters
        if (PART == 1){
            // The number of changes by one
            int ones = 0;
            // The number of changes by three
            int threes = 0;
            // Loop through every adjacent adapter pair
            for (int i=1; i<adapters.size(); ++i){
                // Calculate the difference and record it
                int diff = adapters.get(i) - adapters.get(i-1);
                if (diff == 1){
                    ++ones;
                }else if (diff == 3){
                    ++threes;
                }
            }

            // Print the answer
            System.out.println(ones * threes);
        }

        // Part 2 finds the number of possible adapter arrangements
        if (PART == 2){
            // The number of arrangements starting at each adapter
            long[] arrangements = new long[adapters.size()];
            // The last adapter can only be plugged into the device
            arrangements[arrangements.length-2] = 1;
            // The second-to-last adapter can only plug into the last
            arrangements[arrangements.length-3] = 1;
            // For each adapter before the second-to-last
            for (int i=arrangements.length-4; i>=0; --i){
                // For each adapter that it can plug into
                for (int j=1; j<=3; ++j){
                    if (adapters.get(i+j) <= adapters.get(i)+3){
                        // Add all the possible arrangements
                        arrangements[i] += arrangements[i+j];
                    }else{
                        break;
                    }
                }
            }

            // Print the answer
            System.out.println(arrangements[0]);
        }
    }
}