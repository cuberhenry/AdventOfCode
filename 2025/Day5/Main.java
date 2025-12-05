import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 5: Cafeteria";
    public static void main(String[] args){
        Scanner input = Library.getScanner(args);

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // The list of ranges of fresh ingredients
        ArrayList<long[]> ranges = new ArrayList<>();
        // Get the first range
        String line = input.nextLine();
        // Continue until all ranges are processed
        while (!line.equals("")){
            // Get the range as numbers
            long[] newRange = Library.longSplit(line,"-");
            // Loop through each existing range
            for (int i=ranges.size()-1; i>=0; --i){
                long[] range = ranges.get(i);
                // Check for overlaps
                if (range[0] <= newRange[1] && newRange[0] <= range[1]){
                    // Merge them together
                    newRange[0] = Math.min(range[0],newRange[0]);
                    newRange[1] = Math.max(range[1],newRange[1]);
                    ranges.remove(i);
                }
            }
            // Add the new range
            ranges.add(newRange);
            line = input.nextLine();
        }

        // Part 1 finds the number of available ingredients that are fresh
        // Loop through each available ingredient
        while (input.hasNext()){
            long i = input.nextLong();
            // Loop through each range
            for (long[] range : ranges){
                // If the ingredient is fresh, count it
                if (i <= range[1] && i >= range[0]){
                    ++part1;
                    break;
                }
            }
        }

        // Part 2 finds the total number of fresh ingredients
        // Loop through each range
        for (long[] range : ranges){
            // Add the inclusive range
            part2 += range[1] - range[0] + 1;
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}