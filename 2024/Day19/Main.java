import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 19: Linen Layout";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        long part2 = 0;

        // Take in the towel options
        String[] input = sc.nextLine().split(", ");
        sc.nextLine();

        // The number of ways to make each combination so far
        HashMap<String,Long> counts = new HashMap<>();
        counts.put("",1L);

        // Take in each pattern
        while (sc.hasNext()){
            String pattern = sc.nextLine();
            long matches = matches(input,pattern,counts);
            // Count how many patterns can be made
            if (matches > 0){
                ++part1;
            }
            // Count how many ways to make the patterns
            part2 += matches;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }

    // Recursively find how many matches for each string
    private static long matches(String[] input, String pattern, HashMap<String,Long> counts){
        // Check if this pattern has been calculated already
        if (counts.containsKey(pattern)){
            return counts.get(pattern);
        }
        // The number of matches
        long count = 0;
        // Loop through each towel
        for (String towel : input){
            // If the towel matches the beginning of the pattern
            if (pattern.indexOf(towel) == 0){
                // See if the rest of the pattern can be matched
                count += matches(input,pattern.substring(towel.length()),counts);
            }
        }
        // Save the total for later
        counts.put(pattern,count);
        return count;
    }
}