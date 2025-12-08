import com.aoc.mylibrary.DisjointSet;
import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 8: Playground";
    public static void main(String[] args){
        int[][] input = Library.getIntMatrix(args,",");

        // The different circuits of connected junctions
        DisjointSet<Integer> circuits = new DisjointSet<>();

        // Distances between junctions pointing to the two junctions
        HashMap<Double,int[]> distances = new HashMap<>();
        // Loop through each pair of junctions
        for (int i=0; i<input.length; ++i){
            circuits.add(i);
            for (int j=i+1; j<input.length; ++j){
                // No need to root the distance
                distances.put(Math.pow(input[j][0] - input[i][0],2)
                    + Math.pow(input[j][1] - input[i][1],2)
                    + Math.pow(input[j][2] - input[i][2],2),
                    new int[] {i,j});
            }
        }

        // Sort all of the distances
        ArrayList<Double> sortedDists = new ArrayList<>(distances.keySet());
        Collections.sort(sortedDists);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop through each possible connection ordered by distance
        for (int i=0; i<sortedDists.size(); ++i){
            // Get the two connecting junctions
            int[] junctions = distances.get(sortedDists.get(i));

            // Combine the circuits
            circuits.union(junctions[0],junctions[1]);

            // Part 1 finds the sizes of the largest circuits after 1000 connections
            if (i + 1 == 1000){
                // Get the sizes of all the circuits in descending order
                ArrayList<Integer> orderedSizes = circuits.orderedSizes();
                // Find the product of the largest three
                part1 = orderedSizes.get(0) * orderedSizes.get(1) * orderedSizes.get(2);
            }

            // Part 2 finds the last two junctions to connect before the circuit is complete
            if (circuits.size() == 1){
                // Find the product of the X coordinates of the last two junctions
                part2 = input[junctions[0]][0] * input[junctions[1]][0];
                break;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}