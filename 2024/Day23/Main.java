import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 23: LAN Party";
    public static void main(String[] args){
        // Take in the input as connections of strings
        String[][] input = Library.getStringMatrix(args,"-");

        // The connections for each computer
        HashMap<String,HashSet<String>> map = new HashMap<>();
        // Loop through each connection
        for (String[] connection : input){
            // Add the computers to the map if they're missing
            if (!map.containsKey(connection[0])){
                map.put(connection[0],new HashSet<>());
            }
            if (!map.containsKey(connection[1])){
                map.put(connection[1],new HashSet<>());
            }
            // Add the connection bidirectionally
            map.get(connection[0]).add(connection[1]);
            map.get(connection[1]).add(connection[0]);
        }

        // The list of all sets of interconnected computers
        ArrayList<HashSet<String>> sets = new ArrayList<>();
        // The largest set of connected computers
        HashSet<String> biggest = new HashSet<>();

        // The answer to the problem
        int part1 = 0;
        String part2 = "";

        // Loop through each computer
        for (String key : map.keySet()){
            // If the first character is a 't'
            if (key.charAt(0) == 't'){
                // Get the list of computers this computer is connected to
                ArrayList<String> connected = new ArrayList<>(map.get(key));
                // Loop through each other computer
                for (int i=0; i<connected.size(); ++i){
                    // Only count each set of three once
                    if (connected.get(i).charAt(0) == 't' && connected.get(i).compareTo(key) > 0){
                        continue;
                    }
                    // Loop through each later computer
                    for (int j=i+1; j<connected.size(); ++j){
                        // Only count each set of three once
                        if (connected.get(j).charAt(0) == 't' && connected.get(j).compareTo(key) > 0){
                            continue;
                        }
                        // If they're all connect, add
                        if (map.get(connected.get(i)).contains(connected.get(j))){
                            ++part1;
                        }
                    }
                }
            }

            // The new sets created by this key
            ArrayList<HashSet<String>> newSets = new ArrayList<>();
            // Loop through every existing set
            for (HashSet<String> set : sets){
                // Whether this computer is connected to every other in the set
                boolean matchAll = true;
                for (String item : set){
                    if (!map.get(key).contains(item)){
                        matchAll = false;
                        break;
                    }
                }
                if (matchAll){
                    // Create a new set that includes this computer
                    HashSet<String> newSet = new HashSet<>(set);
                    newSet.add(key);
                    // Save the biggest set
                    if (newSet.size() > biggest.size()){
                        biggest = newSet;
                    }
                    newSets.add(newSet);
                }
            }
            // Add a new set with just this computer
            HashSet<String> newSet = new HashSet<>();
            newSet.add(key);
            sets.add(newSet);
            sets.addAll(newSets);
        }

        // Sort the biggest set
        ArrayList<String> sorted = new ArrayList<>(biggest);
        Collections.sort(sorted);
        // Get the comma-delimited list
        part2 = sorted.toString().replaceAll(" |\\[|\\]","");

        // Print the answer
        Library.print(part1,part2,name);
    }
}