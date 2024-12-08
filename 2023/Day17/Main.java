import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class Main {
    final private static String name = "Day 17: Clumsy Crucible";
    public static void main(String args[]) {
        // The heat loss map
        int[][] city = Library.getIntMatrix(args);

        // Find the two results
        int part1 = minHeatLoss(city,false);
        int part2 = minHeatLoss(city,true);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static int minHeatLoss(int[][] city, boolean ultra){
        // Start at the top left, either facing right or down
        ArrayState startDown = new ArrayState(new int[] {0,0,2});
        ArrayState startRight = new ArrayState(new int[] {0,0,1});
        // The minimum loss at specific locations and facings
        HashMap<ArrayState,Integer> loss = new HashMap<>();
        loss.put(startDown,0);
        loss.put(startRight,0);
        // The locations and directions to look
        LinkedHashSet<ArrayState> queue = new LinkedHashSet<>();
        queue.add(startDown);
        queue.add(startRight);

        // The answer to the problem
        int minSolution = Integer.MAX_VALUE;

        // Continue until all locations have been searched
        while (!queue.isEmpty()){
            // Get the information
            ArrayState state = queue.removeFirst();
            int[] array = state.getArray();
            int heatLoss = loss.get(state);

            // Skip when it can't be better
            if (heatLoss >= minSolution){
                continue;
            }

            // Turn both directions
            for (int i=1; i<=3; i+=2){
                // Get the new values
                int newX = array[0];
                int newY = array[1];
                int newDir = (array[2] + i) % 4;
                int moreHeatLoss = 0;
                // Include heat loss from the turn-restricted blocks
                for (int j=1; j<(ultra?4:0); ++j){
                    // Move in the indicated direction
                    switch (newDir){
                        case 0 -> --newY;
                        case 1 -> ++newX;
                        case 2 -> ++newY;
                        case 3 -> --newX;
                    }

                    // Quit if moving off the map
                    if (newX < 0 || newY < 0 || newX == city[0].length || newY == city.length){
                        break;
                    }

                    // Add the heat loss
                    moreHeatLoss += city[newY][newX];
                }

                // Check each subsequent location
                for (int j=ultra?4:1; j<=(ultra?10:3); ++j){
                    // Move in the indicated direction
                    switch (newDir){
                        case 0 -> --newY;
                        case 1 -> ++newX;
                        case 2 -> ++newY;
                        case 3 -> --newX;
                    }

                    // Quit if moving off the map
                    if (newX < 0 || newY < 0 || newX >= city[0].length || newY >= city.length){
                        break;
                    }

                    // Add the heat loss
                    moreHeatLoss += city[newY][newX];
                    // Create the new state
                    ArrayState newState = new ArrayState(new int[] {newX,newY,newDir%2});
                    // If the new location and direction hasn't been searched or is worse than current
                    if (!loss.containsKey(newState) || loss.get(newState) > heatLoss + moreHeatLoss){
                        // Check for a new best solution
                        if (newX == city[0].length-1 && newY == city.length-1){
                            minSolution = Math.min(minSolution,heatLoss + moreHeatLoss);
                        }
                        // Add the new or better value
                        loss.put(newState,heatLoss + moreHeatLoss);
                        // Search from there
                        queue.add(newState);
                    }
                }
            }
        }

        return minSolution;
    }
}