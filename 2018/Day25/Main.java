/*
Henry Anderson
Advent of Code 2018 Day 25 https://adventofcode.com/2018/day/25
Input: https://adventofcode.com/2018/day/25/input
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
        // Part 2 doesn't require code
        if (PART == 2){
            System.out.println("Trigger the Underflow");
            return;
        }

        // All of the current constellations
        ArrayList<ArrayList<int[]>> constellations = new ArrayList<>();
        // Loop through every point
        while (sc.hasNext()){
            // Get the coordinates as integers
            String[] split = sc.nextLine().split(",");
            int[] point = new int[4];
            for (int i=0; i<4; ++i){
                point[i] = Integer.parseInt(split[i]);
            }

            // The list of the indexes of the constellations that this point is close to
            ArrayList<Integer> closeTo = new ArrayList<>();
            // Loop through every constellation
            for (int i=0; i<constellations.size(); ++i){
                // Whether it's within 3 of at least one point in this constellation
                boolean close = false;
                // Loop through every point
                for (int[] other : constellations.get(i)){
                    // If the distance is at most 3, add it
                    if (Math.abs(point[0]-other[0]) + Math.abs(point[1]-other[1]) + Math.abs(point[2]-other[2]) + Math.abs(point[3]-other[3]) <= 3){
                        close = true;
                        break;
                    }
                }
                // Add the index
                if (close){
                    closeTo.add(i);
                }
            }

            // If it's not close to any
            if (closeTo.isEmpty()){
                // Create a new constellation with just this one point
                ArrayList<int[]> newConst = new ArrayList<>();
                newConst.add(point);
                constellations.add(newConst);
            }else{
                // Combine all of the constellations into one
                while (closeTo.size() > 1){
                    constellations.get(closeTo.getFirst()).addAll(constellations.remove((int)closeTo.removeLast()));
                }
                // Add the new point
                constellations.get(closeTo.getFirst()).add(point);
            }
        }

        // Print the answer
        System.out.println(constellations.size());
    }
}