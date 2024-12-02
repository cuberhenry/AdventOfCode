import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 8: Haunted Wasteland";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // The list of lefts and rights
        String directions = sc.next();
        // Skip newline characters
        sc.nextLine();
        sc.nextLine();
        
        // The next location based on current location and direction
        HashMap<String,String> hash = new HashMap<>();
        // The current positions
        String currPos1 = "";
        ArrayList<String> currPos2 = new ArrayList<>();

        // Take in every line of input
        while (sc.hasNext()){
            // Dissect the input
            String[] split = sc.nextLine().split(" ");
            String[] line = new String[3];
            line[0] = split[0];
            line[1] = split[2].substring(1,4);
            line[2] = split[3].substring(0,3);
            // Add each direction into the map
            hash.put(line[0]+"L",line[1]);
            hash.put(line[0]+"R",line[2]);

            // Start at AAA
            if (line[0].equals("AAA")){
                currPos1 = line[0];
            }
            
            // Start at all locations that end in A
            if (line[0].charAt(2) == 'A'){
                currPos2.add(line[0]);
            }
        }

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;
        
        // Continue traversing until the destination is found
        while (!currPos1.equals("ZZZ")){
            // Move forward one location
            currPos1 = hash.get(currPos1 + (directions.charAt((int)part1%directions.length())));
            // Increase the number of steps taken
            ++part1;
        }

        // All previous locations visited for each simultaneous position
        ArrayList<HashSet<String>> history = new ArrayList<>();
        // Add the starting position to each history
        for (String pos : currPos2){
            HashSet<String> hist = new HashSet<>();
            hist.add("0" + pos);
            history.add(hist);
        }

        // The number of steps taken for the loops
        int steps = 0;
        // Increase total for muliplicative purposes
        ++part2;
        // Continue until each loop has been found
        while (!currPos2.isEmpty()){
            // Loop through each position
            for (int i=0; i<currPos2.size(); ++i){
                // Move forward one location
                currPos2.set(i,hash.get(currPos2.get(i) + (directions.charAt(steps%directions.length()))));
                // If a loop has been found
                if (history.get(i).contains(steps%directions.length() + currPos2.get(i)) && currPos2.get(i).charAt(2) == 'Z'){
                    // The loop length is half of steps taken
                    // Find the least common multiple
                    part2 = Library.LCM(part2,steps/2+1);
                    // Remove the finished loop
                    currPos2.remove(i);
                    history.remove(i);
                    --i;
                }else{
                    // Add the location to the position's history
                    history.get(i).add(steps%directions.length() + currPos2.get(i));
                }
            }

            // Increment the number of steps taken
            ++steps;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}