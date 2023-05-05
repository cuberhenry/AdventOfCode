/*
Henry Anderson
Advent of Code 2015 Day 14 https://adventofcode.com/2015/day/14
Input: https://adventofcode.com/2015/day/14/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import java.net.http.HttpResponse.ResponseInfo;
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
        // The answer to the problem
        int best = 0;
        // An array list of reindeer with an int array of:
        // [speed, burstTime, cycleTime, distance, score]
        ArrayList<int[]> reindeer = new ArrayList<>();

        // Loop through every reindeer
        while (sc.hasNext()){
            // Split the line
            String[] line = sc.nextLine().split(" ");
            // Create the new deer
            int[] deer = new int[5];
            // Collect the information
            deer[0] = Integer.parseInt(line[3]);
            deer[1] = Integer.parseInt(line[6]);
            deer[2] = Integer.parseInt(line[13]) + deer[1];
            deer[3] = 0;
            deer[4] = 0;
            // Add the reindeer
            reindeer.add(deer);
        }

        // Loop through every second of the race
        for (int i=0; i<2503; ++i){
            // The furthest reindeer's distance
            int max = -1;
            // The indexes of the furthest deer
            ArrayList<Integer> index = new ArrayList<>();
            // Loop through every deer
            for (int j=0; j<reindeer.size(); ++j){
                // Grab the deer
                int[] deer = reindeer.get(j);
                // If the deer is ready to move, move it
                if (i % deer[2] < deer[1]){
                    deer[3] += deer[0];
                }
                // If the deer is futher than other deer
                if (deer[3] > max){
                    // Other reindeer don't get the point
                    index.clear();
                    // Set the new maximum
                    max = deer[3];
                }
                // Add the deer to indexes if it gets a point
                if (deer[3] == max){
                    index.add(j);
                }
            }
            // Loop through every deer that deserves a point and give it one
            for (int j : index){
                ++reindeer.get(j)[4];
            }
        }

        // Loop through every deer
        for (int[] deer : reindeer){
            // Part 1 finds the furthest deer after 2503 seconds
            if (PART == 1){
                best = Math.max(best,deer[3]);
            }

            // Part 2 finds the deer with the most points after 2503 seconds
            if (PART == 2){
                best = Math.max(best,deer[4]);
            }
        }

        // Print the answer
        System.out.println(best);
    }
}