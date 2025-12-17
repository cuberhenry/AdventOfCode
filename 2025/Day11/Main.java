import com.aoc.mylibrary.Library;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 11: Reactor";
    public static void main(String[] args){
        String[][] input = Library.getStringMatrix(args,": | ");

        // A set of destination locations from the source location
        HashMap<String,HashSet<String>> map = new HashMap<>();
        for (String[] line : input){
            HashSet<String> set = new HashSet<>();
            for (int i=1; i<line.length; ++i){
                set.add(line[i]);
            }
            map.put(line[0],set);
        }

        // Get the number of paths from each location to out, fft, and dac
        HashMap<String,Long> out = getPathsTo("out",map);
        HashMap<String,Long> fft = getPathsTo("fft",map);
        HashMap<String,Long> dac = getPathsTo("dac",map);

        // The answer to the problem
        // Part 1 finds the number of paths from you to out
        long part1 = out.get("you");
        // Part 2 finds the number of paths from svr to out that also goes through both fft and dac
        long part2 = 0;
        // Either dac goes to fft or fft goes to dac, if both were true there'd be infinite
        // paths and if neither was true there'd be no paths
        if (dac.containsKey("fft")){
            // Multiply the number of paths for leach leg
            part2 = fft.get("svr")
                * dac.get("fft")
                * out.get("dac");
        }else if (fft.containsKey("dac")){
            part2 = dac.get("svr")
                * fft.get("dac")
                * out.get("fft");
        }

        // Print the answer
        Library.print(part1,part2,name);
    }

    // Recursively count the number of paths from each prior location to the location dest
    private static HashMap<String,Long> getPathsTo(String dest, HashMap<String,HashSet<String>> map){
        HashMap<String,Long> pathsTo = new HashMap<>();
        // There is one path from any destination to itself of length 0
        pathsTo.put(dest,1L);
        // The locations to update
        HashSet<String> set = new HashSet<>();
        // Add all locations 1 back from dest
        for (String source : map.keySet()){
            if (map.get(source).contains(dest)){
                set.add(source);
            }
        }
        // Continue until all updates are stable and paths have been calculated
        while (!set.isEmpty()){
            // The next set of locations to update
            HashSet<String> newSet = new HashSet<>();
            // Loop through each location
            for (String key : set){
                // The total so far for the current location
                long total = 0;
                if (map.containsKey(key)){
                    // Loop through each location after key
                    for (String value : map.get(key)){
                        // Add the total paths
                        if (pathsTo.containsKey(value)){
                            total += pathsTo.get(value);
                        }
                    }
                }

                // Update if a new value is calculated
                if (!pathsTo.containsKey(key) || total > pathsTo.get(key)){
                    pathsTo.put(key,total);
                    // Update all of the previous locations from key
                    for (String source : map.keySet()){
                        if (map.get(source).contains(key)){
                            newSet.add(source);
                        }
                    }
                }
            }

            set = newSet;
        }

        // Return the answer
        return pathsTo;
    }
}