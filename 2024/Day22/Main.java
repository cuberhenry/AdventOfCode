import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 22: Monkey Market";
    public static void main(String[] args){
        // Take in the list of numbers
        int[] buyers = Library.getIntArray(args,"\n");

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // The number of bananas gotten for each set of changes
        HashMap<ArrayState,Long> results = new HashMap<>();
        // Loop through each buyer
        for (int buyer : buyers){
            // The set of changes that have already been sold at
            HashSet<ArrayState> sold = new HashSet<>();
            // The buyer's secret number
            long secret = buyer;
            // The most recent four changes
            int[] changes = new int[4];
            // Get the first three changes
            for (int i=0; i<3; ++i){
                // Save the current secret number
                long prev = secret;
                // Find the next secret number
                secret = ((secret * 64) ^ secret) % 16777216;
                secret = ((secret / 32) ^ secret) % 16777216;
                secret = ((secret * 2048) ^ secret) % 16777216;
                // Find the change between the prices
                changes[i+1] = (int)(secret % 10 - prev % 10);
            }
            // Loop through the rest of the changes
            for (int i=3; i<2000; ++i){
                // Move all the other changes backwards
                for (int j=0; j<3; ++j){
                    changes[j] = changes[j+1];
                }
                // Save the current secret number
                long prev = secret;
                // Find the next secret number
                secret = ((secret * 64) ^ secret) % 16777216;
                secret = ((secret / 32) ^ secret) % 16777216;
                secret = ((secret * 2048) ^ secret) % 16777216;
                // Find the change between the prices
                changes[3] = (int)(secret % 10 - prev % 10);
                ArrayState change = new ArrayState(changes.clone());
                // If this set hasn't already been sold for this buyer
                if (!sold.contains(change)){
                    // Add the set
                    sold.add(change);
                    // Add the selling price
                    if (results.containsKey(change)){
                        results.put(change,results.get(change) + secret % 10);
                    }else{
                        results.put(change,secret % 10);
                    }
                    // Find the maximum number of bananas you can get
                    part2 = Math.max(part2,results.get(change));
                }
            }

            // Add the 2000th secret number for this buyer
            part1 += secret;
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}