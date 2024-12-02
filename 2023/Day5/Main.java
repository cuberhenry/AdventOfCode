import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 5: If You Give A Seed A Fertilizer";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // Skip "seeds:"
        sc.next();
        // The list of seeds to test
        ArrayList<Long> seeds = new ArrayList<>();
        // Take in all of the seeds
        while (sc.hasNextLong()){
            seeds.add(sc.nextLong());
        }

        // The list of maps
        ArrayList<ArrayList<long[]>> maps = new ArrayList<>();
        // Loop through every map
        while (sc.hasNext()){
            // Skip extra lines
            sc.nextLine();
            sc.nextLine();
            sc.nextLine();
            // The current map
            ArrayList<long[]> map = new ArrayList<>();
            // Continue taking in and adding conversions until the end of the map
            while (sc.hasNextLong()){
                map.add(new long[] {sc.nextLong(),sc.nextLong(),sc.nextLong()});
            }
            // Add the map to the list of maps
            maps.add(map);
        }

        // The list of all seed candidates, which exist at the bottom of a range
        ArrayList<Long> possibleSeeds = new ArrayList<>();
        // Loop through every map backwards
        for (int m=maps.size()-1; m>=0; --m){
            // The new converted numbers
            ArrayList<Long> newPossible = new ArrayList<>();
            // Loop through every conversion in the current map
            for (long[] conversion : maps.get(m)){
                // Dissect the data
                long dest = conversion[0];
                long source = conversion[1];
                long range = conversion[2];

                // Add the lowest of the range
                if (!newPossible.contains(source)){
                    newPossible.add(source);
                }

                // Loop through all unconverted numbers
                for (int i=0; i<possibleSeeds.size(); ++i){
                    // While the current unconverted value can be converted by this conversion
                    while (i<possibleSeeds.size() && possibleSeeds.get(i) >= dest && possibleSeeds.get(i) < dest + range){
                        // Add the value if it doesn't already exist
                        if (!newPossible.contains(possibleSeeds.get(i)-dest+source)){
                            newPossible.add(possibleSeeds.get(i)-dest+source);
                        }
                        possibleSeeds.remove(i);
                    }
                    // Add the next unconvertable value
                    if (i < possibleSeeds.size() && !newPossible.contains(possibleSeeds.get(i))){
                        newPossible.add(possibleSeeds.get(i));
                    }
                }
            }

            // Save the new values
            possibleSeeds = newPossible;
        }

        // 0 is automatically the bottom of the overall range
        if (!possibleSeeds.contains(0L)){
            possibleSeeds.add(0L);
        }

        // Loop through every possible seed
        for (int i=0; i<possibleSeeds.size(); ++i){
            // Whether the seed exists within one of the seed ranges
            boolean found = false;
            // Loop through every seed range
            for (int j=0; j<seeds.size(); j+=2){
                // If the seed falls within the range
                if (possibleSeeds.get(i) >= seeds.get(j) && possibleSeeds.get(i) < seeds.get(j) + seeds.get(j+1)){
                    // Confirm
                    found = true;
                    break;
                }
            }
            // Delete unfound seeds
            if (!found){
                possibleSeeds.remove(i);
                --i;
            }
        }

        // The smallest location value
        long part1 = Long.MAX_VALUE;
        // Loop through every seed
        for (long seed : seeds){
            // Loop through each map
            for (ArrayList<long[]> map : maps){
                // Loop through every conversion
                for (long[] conversion : map){
                    // Dissect the data
                    long dest = conversion[0];
                    long source = conversion[1];
                    long range = conversion[2];

                    // If the value falls within the range, convert it
                    if (seed >= source && seed < source + range){
                        seed = seed - source + dest;
                        break;
                    }
                }
            }

            // Save any new smallest values
            if (seed < part1){
                part1 = seed;
            }
        }

        // The smallest location value
        long part2 = Long.MAX_VALUE;
        // Loop through every seed
        for (long seed : possibleSeeds){
            // Loop through each map
            for (ArrayList<long[]> map : maps){
                // Loop through every conversion
                for (long[] conversion : map){
                    // Dissect the data
                    long dest = conversion[0];
                    long source = conversion[1];
                    long range = conversion[2];

                    // If the value falls within the range, convert it
                    if (seed >= source && seed < source + range){
                        seed = seed - source + dest;
                        break;
                    }
                }
            }

            // Save any new smallest values
            if (seed < part2){
                part2 = seed;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}