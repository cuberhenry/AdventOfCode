import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 11: Plutonian Pebbles";
    public static void main(String[] args){
        // Get the input
        int[] input = Library.getIntArray(args," ");
        // The result for each 
        HashMap<Long,ArrayList<Long>> map = new HashMap<>();
        // The count of each sized rock
        HashMap<Long,Long> counts = new HashMap<>();
        // Add each input to counts
        for (long num : input){
            if (counts.containsKey(num)){
                counts.put(num,counts.get(num)+1);
            }else{
                counts.put(num,1L);
            }
        }

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // Count 75 blinks
        for (int i=0; i<75; ++i){
            // Save the counts at 25 blinks
            if (i == 25){
                for (long key : counts.keySet()){
                    part1 += counts.get(key);
                }
            }
            // The counts after this blink
            HashMap<Long,Long> newCounts = new HashMap<>();
            // Loop through each stone size
            for (long key : counts.keySet()){
                // Find the result
                if (!map.containsKey(key)){
                    ArrayList<Long> output = new ArrayList<>();
                    // 0 becomes 1
                    if (key == 0){
                        output.add(1L);
                    }else{
                        // Check the length
                        String stone = key + "";
                        if (stone.length() % 2 == 0){
                            // If even, split into two stones
                            output.add(Long.parseLong(stone.substring(0,stone.length()/2)));
                            output.add(Long.parseLong(stone.substring(stone.length()/2)));
                        }else{
                            // If odd, multiply by 2024
                            output.add(key * 2024);
                        }
                    }
                    // Add the results of this stone size
                    map.put(key,output);
                }

                // Loop through each result from the current stone
                for (long num : map.get(key)){
                    // Add the new stones
                    if (newCounts.containsKey(num)){
                        newCounts.put(num,newCounts.get(num)+counts.get(key));
                    }else{
                        newCounts.put(num,counts.get(key));
                    }
                }
            }
            // Save the new stones
            counts = newCounts;
        }

        // Count up the number of stones after 75 blinks
        for (long key : counts.keySet()){
            part2 += counts.get(key);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}