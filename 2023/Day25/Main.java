import com.aoc.mylibrary.Library;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Main {
    final private static String name = "Day 25: Snowverload";
    public static void main(String args[]) {
        // Take in all the input
        String[][] input = Library.getStringMatrix(args,": | ");

        // A hashset of all the names to get the number of unique components
        HashSet<String> names = new HashSet<>();
        // A map for each component to all of its connections
        HashMap<String,HashSet<String>> hash = new HashMap<>();
        // Loop through each line of input
        for (String[] line : input){
            // Add it if it doesn't exist
            if (!names.contains(line[0])){
                hash.put(line[0],new HashSet<String>());
                names.add(line[0]);
            }
            // Loop through every connection
            for (int i=1; i<line.length; ++i){
                // Add it if it doesn't exist
                if (!names.contains(line[i])){
                    hash.put(line[i],new HashSet<String>());
                    names.add(line[i]);
                }
                // Add the bidirectional connection
                hash.get(line[0]).add(line[i]);
                hash.get(line[i]).add(line[0]);
            }
        }

        // The answer to the problem
        long part1 = 0;

        // Continue looping until it's solved
        while (part1 == 0){
            // Duplicate the hashmap
            HashMap<String,ArrayList<String>> map = new HashMap<>();
            for (String key : hash.keySet()){
                ArrayList<String> connection = new ArrayList<>();
                connection.addAll(hash.get(key));
                map.put(key,connection);
            }

            // Use a randomizer to randomly generate the solution
            Random random = new Random();
            // Continue until there are only two groups
            while (map.size() > 2){
                // Get a list of all of the components
                ArrayList<String> keysAsArray = new ArrayList<String>(map.keySet());
                // Select any random component
                String key = keysAsArray.get(random.nextInt(map.size()));
                ArrayList<String> connects = map.remove(key);
                // Select any random connection for that component
                String other = connects.get(random.nextInt(connects.size()));
                ArrayList<String> oConnect = map.remove(other);

                // Merge the two connections into one
                while (connects.contains(other))
                    connects.remove(other);
                while (oConnect.contains(key))
                    oConnect.remove(key);
                for (String c : connects){
                    map.get(c).set(map.get(c).indexOf(key),key + " " + other);
                }
                for (String c : oConnect){
                    map.get(c).set(map.get(c).indexOf(other),key + " " + other);
                }
                connects.addAll(oConnect);

                // Put the merged components back in
                map.put(key + " " + other,connects);
            }

            // Check if the two groups only have three connections between each other
            if (map.get(map.keySet().iterator().next()).size() == 3){
                // Find the product of the sizes of the two groups
                part1 = (map.keySet().iterator().next().length()+1)/4;
                part1 *= names.size() - part1;
            }
        }

        // Part 2 requires no code
        String part2 = "Push The Big Red Button";

        // Print the answer
        Library.print(part1,part2,name);
    }
}