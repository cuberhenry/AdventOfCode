/*
Henry Anderson
Advent of Code 2020 Day 13 https://adventofcode.com/2020/day/13
Input: https://adventofcode.com/2020/day/13/input
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
        // The earliest leave time
        int leaveTime = sc.nextInt();
        sc.nextLine();
        // Take in the buses
        String[] split = sc.nextLine().split(",");

        // Part 1 finds the earliest you can board a bus
        if (PART == 1){
            // Take in all the bus numbers
            ArrayList<Integer> buses = new ArrayList<>();
            for (String bus : split){
                if (!bus.equals("x")){
                    buses.add(Integer.parseInt(bus));
                }
            }

            // Loop through every time after leaveTime to find a bus
            for (int i=leaveTime;; ++i){
                for (int bus : buses){
                    // If there's a bus leaving at that time
                    if (i % bus == 0){
                        // Print the answer
                        System.out.println((i-leaveTime) * bus);
                        return;
                    }
                }
            }
        }

        // Part 2 finds the earliest time that a bus leaves every minute
        if (PART == 2){
            // Take in the bus numbers and indeces
            ArrayList<int[]> buses = new ArrayList<>();
            for (int i=0; i<split.length; ++i){
                if (!split[i].equals("x")){
                    buses.add(new int[] {Integer.parseInt(split[i]),i});
                }
            }

            // The product of the bus numbers so far
            long product = 1;
            // The timestamp
            long timestamp = 1;
            // Loop through every bus
            for (int[] bus : buses){
                // Keep searching until the buses leave sequentially
                while ((timestamp+bus[1]) % bus[0] != 0){
                    timestamp += product;
                }
                // Doing this ensures that the current buses will always leave sequentially
                product *= bus[0];
            }

            // Print the answer
            System.out.println(timestamp);
        }
    }
}