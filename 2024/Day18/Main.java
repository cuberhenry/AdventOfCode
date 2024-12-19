import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 18: RAM Run";
    public static void main(String[] args){
        // Get the list of falling bytes
        int[][] bytes = Library.getIntMatrix(args,",");

        // The grid of coordinates
        boolean[][] grid = new boolean[71][71];

        // Add the first kilobyte
        for (int i=0; i<1024; ++i){
            grid[bytes[i][1]][bytes[i][0]] = true;
        }

        // The answer to the problem
        int part1 = 0;
        String part2 = "";

        // The list of distances for each set of coordinates
        HashMap<ArrayState,Integer> dists = new HashMap<>();
        // The list of coordinates to check
        LinkedList<ArrayState> queue = new LinkedList<>();
        // Start at 0,0
        ArrayState startState = new ArrayState(new int[] {0,0});
        dists.put(startState,0);
        queue.add(startState);

        // Continue until all coordinates have been checked
        while (!queue.isEmpty()){
            // Get the first state
            ArrayState state = queue.remove();
            // Look in each direction
            for (int i=0; i<4; ++i){
                int x = state.getArray()[0];
                int y = state.getArray()[1];
                switch(i){
                    case 0 -> ++x;
                    case 1 -> ++y;
                    case 2 -> --x;
                    case 3 -> --y;
                }
                // Don't go off the edge or onto a corroded spot
                if (x < 0 || y < 0 || x == 71 || y == 71 || grid[y][x]){
                    continue;
                }
                // Get the new state
                ArrayState newState = new ArrayState(new int[] {x,y});
                // If it isn't already found
                if (!dists.containsKey(newState)){
                    dists.put(newState,dists.get(state)+1);
                    queue.add(newState);
                }
            }
        }

        // Save the minimum distance
        part1 = dists.get(new ArrayState(new int[] {70,70}));

        // Loop until a blockade is found
        for (int i=1024; part2.isBlank(); ++i){
            // Drop the byte
            grid[bytes[i][1]][bytes[i][0]] = true;
            // Start without visiting any places
            HashSet<ArrayState> history = new HashSet<>();
            // Refresh the queue
            queue.clear();
            // Start at the top left
            history.add(startState);
            queue.add(startState);

            // Whether a path has been found
            boolean path = false;
            // Continue until all locations have been searched or a path was found
            while (!queue.isEmpty() && !path){
                // Get the next state
                ArrayState state = queue.remove();
                // Look in each direction
                for (int j=0; j<4; ++j){
                    int x = state.getArray()[0];
                    int y = state.getArray()[1];
                    switch(j){
                        case 0 -> ++x;
                        case 1 -> ++y;
                        case 2 -> --x;
                        case 3 -> --y;
                    }
                    // Don't go off the edge or onto a corroded spot
                    if (x < 0 || y < 0 || x == 71 || y == 71 || grid[y][x]){
                        continue;
                    }
                    // A path has been found to the end
                    if (x == 70 && y == 70){
                        path = true;
                        break;
                    }
                    // Get the new state
                    ArrayState newState = new ArrayState(new int[] {x,y});
                    // If it isn't already found
                    if (!history.contains(newState)){
                        history.add(newState);
                        queue.add(newState);
                    }
                }
            }

            // If no path was found
            if (!path){
                // Save the coordinates of the last byte
                part2 = bytes[i][0] + "," + bytes[i][1];
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}