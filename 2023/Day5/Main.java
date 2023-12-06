/*
Henry Anderson
Advent of Code 2023 Day 5 https://adventofcode.com/2023/day/5
Input: https://adventofcode.com/2023/day/5/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
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
        // Skip "seeds:"
        sc.next();
        // The list of seeds to test
        ArrayList<Long> seeds = new ArrayList<>();
        // Take in all of the seeds
        while (sc.hasNextLong()){
            seeds.add(sc.nextLong());
        }

        // The list of maps
        ArrayList<ArrayList<String>> maps = new ArrayList<>();
        // Skip two empty lines
        sc.nextLine(); sc.nextLine();
        // Loop through every map
        while (sc.hasNext()){
            // Skip the title
            sc.nextLine();
            // The current map
            ArrayList<String> map = new ArrayList<>();
            // Take in the first conversion
            String input = sc.nextLine();
            // Continue taking in and adding conversions until the end of the map
            while (!input.equals("")){
                map.add(input);
                if (sc.hasNext()){
                    input = sc.nextLine();
                }else{
                    break;
                }
            }
            // Add the map to the list of maps
            maps.add(map);
        }

        // Part 1 finds the seed that corresponds to the lowest location
        // Part 2 deals with ranges of seeds
        if (PART == 2){
            // The list of all seed candidates, which exist at the bottom of a range
            ArrayList<Long> possibleSeeds = new ArrayList<>();
            // Loop through every map backwards
            for (int m=maps.size()-1; m>=0; --m){
                // The new converted numbers
                ArrayList<Long> newPossible = new ArrayList<>();
                // Loop through every conversion in the current map
                for (String line : maps.get(m)){
                    // Dissect the data
                    String[] split = line.split(" ");
                    long dest = Long.parseLong(split[0]);
                    long source = Long.parseLong(split[1]);
                    long range = Long.parseLong(split[2]);

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

            // Save the final seeds
            seeds = possibleSeeds;
        }

        // The smallest location value
        long smallest = Long.MAX_VALUE;

        // Loop through every seed
        for (long seed : seeds){
            // Loop through each map
            for (ArrayList<String> map : maps){
                // Loop through every conversion
                for (String line : map){
                    // Dissect the data
                    String[] split = line.split(" ");
                    long dest = Long.parseLong(split[0]);
                    long source = Long.parseLong(split[1]);
                    long range = Long.parseLong(split[2]);

                    // If the value falls within the range, convert it
                    if (seed >= source && seed < source + range){
                        seed = seed - source + dest;
                        break;
                    }
                }
            }

            // Save any new smallest values
            if (seed < smallest){
                smallest = seed;
            }
        }

        // Print the answer
        System.out.println(smallest);
    }
}