import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 20: Race Condition";
    public static void main(String[] args){
        // Take in the map
        char[][] grid = Library.getCharMatrix(args);

        // Find the starting coordinates
        int currX = 0;
        int currY = 0;
        for (int i=0; i<grid.length; ++i){
            int index = Library.indexOf(grid[i],'S');
            if (index != -1){
                currX = index;
                currY = i;
                break;
            }
        }

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        
        // The distance from the start for each spot on the path
        HashMap<ArrayState,Integer> dist = new HashMap<>();

        // The current position on the path
        ArrayState currState = new ArrayState(new int[] {currX,currY});
        dist.put(currState,0);

        // Continue until the end is reached
        while (grid[currY][currX] != 'E'){
            // Look in each direction
            for (int i=0; i<4; ++i){
                int x = currX;
                int y = currY;
                switch(i){
                    case 0 -> --x;
                    case 1 -> ++x;
                    case 2 -> --y;
                    case 3 -> ++y;
                }
                // Don't walk into a wall
                if (grid[y][x] == '#'){
                    continue;
                }
                ArrayState newState = new ArrayState(new int[] {x,y});
                // If you didn't just come from there
                if (dist.containsKey(newState)){
                    continue;
                }
                // Add one to the distance
                dist.put(newState,dist.get(currState)+1);
                // Move to that spot
                currState = newState;
                currX = x;
                currY = y;
                break;
            }
        }

        // Loop through each inner spot
        for (int i=1; i<grid.length-1; ++i){
            for (int j=1; j<grid.length-1; ++j){
                // Skip walls
                if (grid[i][j] == '#'){
                    continue;
                }
                // The searched locations so far
                HashSet<ArrayState> history = new HashSet<>();
                // The list of actively searching locations
                LinkedList<ArrayState> queue = new LinkedList<>();

                // Start at the current location
                ArrayState startState = new ArrayState(new int[] {j,i});
                queue.add(startState);
                history.add(startState);

                // Take twenty steps ignoring walls
                for (int k=0; k<20; ++k){
                    // The next set of locations
                    LinkedList<ArrayState> newQ = new LinkedList<>();
                    // Continue until all locations have been searched
                    while (!queue.isEmpty()){
                        // Get the current position
                        ArrayState state = queue.remove();
                        int[] pos = state.getArray();
                        // Look in each direction
                        for (int l=0; l<4; ++l){
                            int x = pos[0];
                            int y = pos[1];
                            switch(l){
                                case 0 -> ++x;
                                case 1 -> --x;
                                case 2 -> ++y;
                                case 3 -> --y;
                            }
                            // Don't fall off the edge
                            if (x < 0 || y<0 || x == grid[0].length || y == grid.length){
                                continue;
                            }
                            ArrayState newState = new ArrayState(new int[] {x,y});
                            // If unvisited
                            if (!history.contains(newState)){
                                // Visit
                                history.add(newState);
                                newQ.add(newState);
                                // If it's not a wall and saves at least 100 picoseconds
                                if (grid[y][x] != '#' && dist.get(newState) - dist.get(startState) - k - 1 >= 100){
                                    // Count how many save after only two picoseconds
                                    if (k == 1){
                                        ++part1;
                                    }
                                    // Add
                                    ++part2;
                                }
                            }
                        }
                    }
                    queue = newQ;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}