/*
Henry Anderson
Advent of Code 2018 Day 17 https://adventofcode.com/2018/day/17
Input: https://adventofcode.com/2018/day/17/input
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
        // The coordinates of every clay
        HashSet<String> clay = new HashSet<>();
        // The min and max clay coordinate
        int minY = Integer.MAX_VALUE;
        int maxY = 0;

        // Take in all input
        while (sc.hasNext()){
            // Split it
            String[] split = sc.nextLine().split(", |=|\\.\\.");
            // The coordinate of the unchanging dimension
            int first = Integer.parseInt(split[1]);
            // The range of the changing dimension
            int start = Integer.parseInt(split[3]);
            int end = Integer.parseInt(split[4]);

            // Save new mins and maxes
            if (split[0].equals("y")){
                minY = Math.min(minY,first);
                maxY = Math.max(maxY,first);
            }else{
                minY = Math.min(minY,start);
                maxY = Math.max(maxY,end);
            }

            // Loop through every spot in the range
            for (int i=start; i<=end; ++i){
                // Add the clay
                if (split[0].equals("y")){
                    clay.add(i + " " + first);
                }else{
                    clay.add(first + " " + i);
                }
            }
        }

        // Every spot with water
        HashSet<String> water = new HashSet<>();
        // Perform the simulation starting at the spring
        dropWater(500,minY,maxY,clay,water);

        // Part 1 finds the amount of tiles that water passes through
        if (PART == 1){
            // Print the answer
            System.out.println(water.size());
        }

        // Part 2 finds the amount of tiles that water settles in
        if (PART == 2){
            // Get rid of all water that wasn't halted
            water.retainAll(clay);
            // Print the answer
            System.out.println(water.size());
        }
    }

    // Simulates dropping water at a specific coordinate
    // Returns whether all of the water dropped has settled
    public static boolean dropWater(int x, int y, int maxY, HashSet<String> clay, HashSet<String> water){
        // The starting value of y
        int startY = y;
        // Continue until clay is hit
        while (!clay.contains(x + " " + (y+1))){
            // Add water
            water.add(x + " " + y);
            // Move down
            ++y;
            // Any water dropping out of the scan is irrelevant
            if (y > maxY){
                return false;
            }
        }

        // Water has already been simulated here
        if (water.contains(x + " " + y)){
            return false;
        }
        water.add(x + " " + y);

        // Loop until a break point is reached
        while (true){
            // One tile in each direction
            int left = x-1;
            int right = x+1;

            // Keep moving until hitting clay or a hole to drop from
            while (!clay.contains(left + " " + y) && clay.contains(left + " " + (y+1))){
                water.add(left + " " + y);
                --left;
            }

            // Keep moving until hitting water or a hole to drop from
            while (!clay.contains(right + " " + y) && clay.contains(right + " " + (y+1))){
                water.add(right + " " + y);
                ++right;
            }

            // If its blocked in by clay
            if (clay.contains(left + " " + y) && clay.contains(right + " " + y)){
                // Turn all water into standing water (consider to be clay)
                for (int i=left+1; i<right; ++i){
                    clay.add(i + " " + y);
                }
            }else{
                // Whether either side has been filled
                boolean filled = false;
                // If the left side should be dropped from, drop from there
                if (!clay.contains(left + " " + y) && !clay.contains(left + " " + (y+1)) && !water.contains(left + " " + y)){
                    filled = dropWater(left,y,maxY,clay,water);
                }
                // If the right side should be dropped from, drop from there
                if (!clay.contains(right + " " + y) && !clay.contains(right + " " + (y+1)) && !water.contains(right + " " + y)){
                    filled = filled || dropWater(right,y,maxY,clay,water);
                }
                // If they both dropped fully, no need to return here
                if (!filled){
                    return false;
                }
            }

            // Move up one y value
            --y;
            // Only move up as much as the original drop
            if (y < startY){
                return true;
            }
        }
    }
}