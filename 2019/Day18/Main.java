/*
Henry Anderson
Advent of Code 2019 Day 18 https://adventofcode.com/2019/day/18
Input: https://adventofcode.com/2019/day/18/input
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
        // The input map
        ArrayList<String> map = new ArrayList<>();
        // A breadth first search, prioritizing least steps
        PriorityQueue<String> bfs = new PriorityQueue<>((a,b) -> {
            return Integer.compare(Integer.parseInt(a.split(" ")[0]),Integer.parseInt(b.split(" ")[0]));
        });
        // The list of keys to collect
        int numKeys = 0;

        // Take in every line
        while (sc.hasNext()){
            String line = sc.nextLine();
            for (int i=0; i<line.length(); ++i){
                // Save initial position
                if (line.charAt(i) == '@'){
                    bfs.add("0 " + i + " " + map.size());
                }else if (Character.isLowerCase(line.charAt(i))){
                    // Add the key
                    ++numKeys;
                }
            }
            map.add(line);
        }

        // Block out dead ends
        // Loop through each inner square
        for (int i=1; i<map.size()-1; ++i){
            for (int j=1; j<map.get(i).length(); ++j){
                // If it's currently open
                if (map.get(i).charAt(j) == '.'){
                    // The number of adjacent walls
                    int count = 0;
                    // Look in each direction
                    for (int k=0; k<4; ++k){
                        int newI = i;
                        int newJ = j;
                        switch(k){
                            case 0 -> {++newI;}
                            case 1 -> {--newI;}
                            case 2 -> {++newJ;}
                            case 3 -> {--newJ;}
                        }
                        // It's a wall
                        if (map.get(newI).charAt(newJ) == '#'){
                            ++count;
                        }
                    }

                    // If it has three walls, it's a dead end; fill it in
                    if (count >= 3){
                        map.set(i,map.get(i).substring(0,j) + '#' + map.get(i).substring(j+1));
                        // Back up to check if this made a new dead end
                        --i;
                        --j;
                    }
                }
            }
        }

        // Part 1 finds the least steps to find all keys
        // Part 2 splits you into 4 coordinates
        if (PART == 2){
            // Get the initial coordinates
            String[] split = bfs.remove().split(" ");
            int x = Integer.parseInt(split[1]);
            int y = Integer.parseInt(split[2]);

            // Replace the center of the map as described in the problem
            map.set(y-1,map.get(y-1).substring(0,x-1) + ".#." + map.get(y-1).substring(x+2));
            map.set(y,map.get(y).substring(0,x-1) + "###" + map.get(y).substring(x+2));
            map.set(y+1,map.get(y+1).substring(0,x-1) + ".#." + map.get(y+1).substring(x+2));

            // Add the new first state, including all robots' coordinates
            bfs.add("0 " + (x-1) + " " + (y-1)
                    + " " + (x+1) + " " + (y-1)
                    + " " + (x-1) + " " + (y+1)
                    + " " + (x+1) + " " + (y+1));
        }

        // History as to not repeat
        HashSet<String> history = new HashSet<>();
        // Add the initial state into history
        history.add(bfs.peek());

        // Continue until all possibilities have been searched
        while (!bfs.isEmpty()){
            // Get the next state
            String[] split = bfs.remove().split(" ");
            // The number of steps taken so far
            int steps = Integer.parseInt(split[0]);
            // Get the coordinates of each being
            int[][] coordinates = new int[(split.length-1)/2][2];
            for (int i=0; i<coordinates.length; ++i){
                coordinates[i][0] = Integer.parseInt(split[i*2+1]);
                coordinates[i][1] = Integer.parseInt(split[i*2+2]);
            }
            // The keys found so far in this state
            String stateKeys = split.length % 2 == 1 ? "" : split[split.length-1];

            // If all keys have been found, this is the best state
            if (stateKeys.length() == numKeys){
                // Print the answer
                System.out.println(steps);
                return;
            }

            // Loop through each being
            for (int j=0; j<coordinates.length; ++j){
                // Do a breadth first search for this being
                LinkedList<String> innerBFS = new LinkedList<>();
                HashSet<String> innerHistory = new HashSet<>();
                int innerSteps = 0;
                // Utilize a basic breadth first search
                innerBFS.add(coordinates[j][0] + " " + coordinates[j][1] + " " + stateKeys);
                innerHistory.add(innerBFS.peek());

                // Continue until all options have been exhausted
                while (!innerBFS.isEmpty()){
                    // Create a new list to count steps
                    LinkedList<String> newInnerBFS = new LinkedList<>();
                    ++innerSteps;
                    // Continue until all options have been exhausted
                    while (!innerBFS.isEmpty()){
                        // Get the three values of this state
                        String[] innerSplit = innerBFS.remove().split(" ");
                        int x = Integer.parseInt(innerSplit[0]);
                        int y = Integer.parseInt(innerSplit[1]);
                        String keysFound = innerSplit.length == 2 ? "" : innerSplit[2];

                        // Look in each direction
                        for (int i=0; i<4; ++i){
                            int newX = x;
                            int newY = y;
                            String newKeys = keysFound;
                            switch(i){
                                case 0 -> {++newX;}
                                case 1 -> {--newX;}
                                case 2 -> {--newY;}
                                case 3 -> {++newY;}
                            }

                            // Get the new character
                            char c = map.get(newY).charAt(newX);
                            // Wall or closed door, skip
                            if (c == '#' || Character.isUpperCase(c) && !newKeys.contains(""+(char)(c+32))){
                                continue;
                            }

                            // Whether a new key was found this step
                            boolean newKey = false;
                            // If it's an unfound key
                            if (Character.isLowerCase(c) && !newKeys.contains(""+c)){
                                newKey = true;
                                newKeys += c;
                                // Sort for state matching
                                char[] array = newKeys.toCharArray();
                                Arrays.sort(array);
                                newKeys = new String(array);
                            }
                            // Create the new outer state
                            String newState = steps + innerSteps + " ";
                            for (int k=0; k<coordinates.length; ++k){
                                // Save the new coordinates for the current being, old for others
                                if (k == j){
                                    newState += newX + " " + newY + " ";
                                }else{
                                    newState += coordinates[k][0] + " " + coordinates[k][1] + " ";
                                }
                            }
                            newState += newKeys;

                            // Inner state is much simpler
                            String innerState = newX + " " + newY + " " + newKeys;

                            // If it's a completely new state
                            if (!history.contains(newState) && !innerHistory.contains(innerState)){
                                // Add it
                                history.add(newState);
                                innerHistory.add(innerState);
                                if (newKey){
                                    bfs.add(newState);
                                }
                                newInnerBFS.add(innerState);
                                // If all keys are found, there's nothing else to do here
                                if (newKeys.length() == numKeys){
                                    newInnerBFS.clear();
                                    innerBFS.clear();
                                    break;
                                }
                            }
                        }
                    }
                    innerBFS = newInnerBFS;
                }
            }
        }
    }
}