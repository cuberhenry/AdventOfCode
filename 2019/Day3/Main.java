/*
Henry Anderson
Advent of Code 2019 Day 3 https://adventofcode.com/2019/day/3
Input: https://adventofcode.com/2019/day/3/input
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
        // Take in the directions for the first wire
        String[] wire = sc.nextLine().split(",");
        // The path the first wire takes
        HashMap<String,Integer> wirePath1 = new HashMap<>();
        // The current values for the first wire
        int x = 0;
        int y = 0;
        int dist = 0;

        // Loop through each input instruction
        for (int i=0; i<wire.length; ++i){
            // Take in the number of steps to take in the given direction
            int steps = Integer.parseInt(wire[i].substring(1));
            // Loop through each step
            for (int j=0; j<steps; ++j){
                // Increase distance
                ++dist;
                // Take a step
                switch(wire[i].charAt(0)){
                    case 'U' -> ++y;
                    case 'R' -> ++x;
                    case 'D' -> --y;
                    case 'L' -> --x;
                }
                // If unvisited, add the current distance
                if (!wirePath1.containsKey(x + " " + y)){
                    wirePath1.put(x + " " + y,dist);
                }
            }
        }

        // Take in the directions for the next wire
        wire = sc.nextLine().split(",");
        // The path the second wire takes
        HashMap<String,Integer> wirePath2 = new HashMap<>();
        // The current values for the first wire
        x = 0;
        y = 0;
        dist = 0;

        // The minimum distance to the interesection
        int minDist = Integer.MAX_VALUE;

        // Loop through each input instruction
        for (int i=0; i<wire.length; ++i){
            // Take in the number of steps to move in the given direction
            int steps = Integer.parseInt(wire[i].substring(1));
            // Loop through each step
            for (int j=0; j<steps; ++j){
                // Increase distance
                ++dist;
                // Take a step
                switch(wire[i].charAt(0)){
                    case 'U' -> ++y;
                    case 'R' -> ++x;
                    case 'D' -> --y;
                    case 'L' -> --x;
                }
                // If unvisited, add the current distance
                if (!wirePath2.containsKey(x + " " + y)){
                    wirePath2.put(x + " " + y,dist);
                }

                // If this is a crossing between the wires
                if (wirePath1.containsKey(x + " " + y)){
                    // Part 1 finds the minimum Manhattan distance to the intersection
                    if (PART == 1){
                        minDist = Math.min(minDist,Math.abs(x) + Math.abs(y));
                    }

                    // Part 2 finds the minimum wire distance to the intersection
                    if (PART == 2){
                        minDist = Math.min(minDist,wirePath1.get(x + " " + y) + wirePath2.get(x + " " + y));
                    }
                }
            }
        }

        // Print the answer
        System.out.println(minDist);
    }
}