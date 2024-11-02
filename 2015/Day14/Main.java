import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 14: Reindeer Olympics";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);
        
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

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop through every second of the race
        for (int i=0; i<2503; ++i){
            // The furthest reindeer's distance
            int max = -1;
            // The indexes of the furthest deer
            HashSet<Integer> index = new HashSet<>();
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
            part1 = Math.max(part1,deer[3]);
            part2 = Math.max(part2,deer[4]);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}