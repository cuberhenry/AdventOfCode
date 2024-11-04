import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    final private static String name = "Day 20: Firewall Rules";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The list of all ranges
        ArrayList<long[]> ranges = new ArrayList<>();
        // Loop through every line of input
        while (sc.hasNext()){
            // Take in the next line
            long[] range = Library.longSplit(sc.nextLine(),"-");

            // Loop through every existing range
            for (int i=ranges.size()-1; i>=0; --i){
                // Split and save the other range
                long[] other = ranges.get(i);

                // If the two ranges intersect
                if (range[0]-1 <= other[1] && other[0]-1 <= range[1]){
                    // Merge the ranges
                    ranges.remove(i);
                    range[0] = Math.min(range[0],other[0]);
                    range[1] = Math.max(range[1],other[1]);
                }
            }

            // Add the final range
            ranges.add(range);
        }

        // Sort the ranges
        Collections.sort(ranges,(a,b) -> {
            return Long.compare(a[0],b[0]);
        });

        // The answer to the problem
        long part1 = ranges.getFirst()[1] + 1;
        long part2 = ranges.getFirst()[0] + ((long)Integer.MAX_VALUE + 1) * 2 - 1 - ranges.getLast()[1];

        // Add the area between each range
        for (int i=1; i<ranges.size(); ++i){
            part2 += ranges.get(i)[0] - ranges.get(i-1)[1] - 1;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}