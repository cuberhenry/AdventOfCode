import com.aoc.mylibrary.Library;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 7: Laboratories";
    public static void main(String[] args){
        char[][] input = Library.getCharMatrix(args);

        // The answer to the problem
        int part1 = 0;
        long part2 = 0;

        // The amount of timelines each x position currently represents
        HashMap<Integer,Long> timelines = new HashMap<>();

        // Find the starting position
        for (int i=0; i<input[0].length; ++i){
            if (input[0][i] == 'S'){
                timelines.put(i,1L);
                break;
            }
        }

        // The current y position
        int y = 1;
        // Continue until hitting the bottom of the 
        while (y < input.length){
            // A new map of timelines based on the new y level
            HashMap<Integer,Long> newTimelines = new HashMap<>();

            // Loop through each x position
            for (int key : timelines.keySet()){
                // If it's a splitter
                if (input[y][key] == '^'){
                    // Part 1 finds the number of splitters hit
                    ++part1;
                    // Add the number of timelines to the x position to the left
                    if (newTimelines.containsKey(key-1)){
                        newTimelines.put(key-1,newTimelines.get(key-1)+timelines.get(key));
                    }else{
                        newTimelines.put(key-1,timelines.get(key));
                    }
                    // Add the number of timelines to the x position to the right
                    if (newTimelines.containsKey(key+1)){
                        newTimelines.put(key+1,newTimelines.get(key+1)+timelines.get(key));
                    }else{
                        newTimelines.put(key+1,timelines.get(key));
                    }
                }else{
                    // Add the number of timelines to the current position
                    if (newTimelines.containsKey(key)){
                        newTimelines.put(key,newTimelines.get(key)+timelines.get(key));
                    }else{
                        newTimelines.put(key,timelines.get(key));
                    }
                }
            }

            // Pass the new timelines on
            timelines = newTimelines;
            // Move down one
            ++y;
        }
        
        // Part 2 finds the number of timelines
        for (int key : timelines.keySet()){
            part2 += timelines.get(key);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}