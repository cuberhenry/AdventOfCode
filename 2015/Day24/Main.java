import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 24: It Hangs in the Balance";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The list of all packages
        ArrayList<Integer> packages = new ArrayList<>();
        // The total weight of the packages
        int weight = 0;
        // Loop through every package in the input
        while (sc.hasNext()){
            // Add the weight to the total weight and the packages
            int w = sc.nextInt();
            packages.add(w);
            weight += w;
        }

        // The minimum quantum entanglement
        long part1 = Long.MAX_VALUE;
        long part2 = part1;

        // Loop until a valid combination is found
        for (int i=1; part1 == Long.MAX_VALUE || part2 == Long.MAX_VALUE; ++i){
            // The indices of the packages being looked at
            int[] indices = new int[i];
            for (int j=1; j<indices.length; ++j){
                indices[j] = j;
            }

            long bestQE3 = Long.MAX_VALUE;
            long bestQE4 = Long.MAX_VALUE;

            // Continue until every combination of i packages has been examined
            while (indices[0] <= packages.size() - i){
                // The total weight of the current packages
                int total = 0;
                // The quantum entanglement of this combination
                long qe = 1;
                // Loop through every package
                for (int j=0; j<i; ++j){
                    // Calculate the values
                    total += packages.get(indices[j]);
                    qe *= packages.get(indices[j]);
                }

                // If it's a new best quantum entanglement
                if (total == weight / 3 && qe < bestQE3){
                    bestQE3 = qe;
                }
                if (total == weight / 4 && qe < bestQE4){
                    bestQE4 = qe;
                }

                // Change the combination starting at the end
                int j = i-1;
                // If it's already reached the end, go back one index
                while (j >= 0 && indices[j] == packages.size()-i+j){
                    --j;
                }

                // If all of the indices are maxed
                if (j < 0){
                    break;
                }

                // Increase the index
                ++indices[j];
                // Reset all following indices
                for (++j; j<i; ++j){
                    indices[j] = indices[j-1] + 1;
                }
            }

            // Save the QEs that work
            if (part1 == Long.MAX_VALUE){
                part1 = bestQE3;
            }
            if (part2 == Long.MAX_VALUE){
                part2 = bestQE4;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}