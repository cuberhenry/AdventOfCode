/*
Henry Anderson
Advent of Code 2015 Day 17 https://adventofcode.com/2015/day/17
Input: https://adventofcode.com/2015/day/17/input
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
        // All of the containers
        ArrayList<Integer> containers = new ArrayList<>();
        // The answer to the problem
        int total = 0;

        // Add all containers to the list
        while (sc.hasNext()){
            containers.add(sc.nextInt());
        }

        // The current minimum number of containers
        int min = Integer.MAX_VALUE;

        // Loop through a number for every possible combination
        for (long i=0; i<(long)Math.pow(2,containers.size()); ++i){
            // The number of liters required filled with the given combination
            int liters = 0;
            // The number of containers used
            int numContainers = 0;
            // Loop through every contiainer
            for (int j=0; j<containers.size(); ++j){
                // If this number represents choosing this container
                if ((i>>j)%2 == 1){
                    // Use the container
                    liters += containers.get(j);
                    ++numContainers;
                }
            }
            // If this combination perfectly contains 150 liters
            if (liters == 150){
                // Part 1 finds the number of combinations that hold exactly 150 liters
                if (PART == 1){
                    // Increase the total
                    ++total;
                }

                // Part 2 finds the number of combinations that hold exactly 150 liters
                // using the minimum number of containers possible to do so
                if (PART == 2){
                    // If it uses the minimum number of containers
                    if (min == numContainers){
                        // Increase the total
                        ++total;
                    // If it uses a new minimum number
                    }else if (min > numContainers){
                        // Reset the count
                        total = 1;
                        // Save the new min
                        min = numContainers;
                    }
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}