import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 10: Hoof It";
    public static void main(String[] args){
        // Take in the map
        int[][] map = Library.getIntMatrix(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        
        // Loop through each spot
        for (int i=0; i<map.length; ++i){
            for (int j=0; j<map[i].length; ++j){
                // Only start at trailheads
                if (map[i][j] != 0){
                    continue;
                }
                // The list of positions from this trailhead
                ArrayList<ArrayState> queue = new ArrayList<>();
                queue.add(new ArrayState(new int[] {j,i}));
                // Loop through each ascending position
                for (int k=1; k<=9; ++k){
                    // The list of the next positions
                    ArrayList<ArrayState> newQueue = new ArrayList<>();
                    // Loop through each previous position
                    for (ArrayState state : queue){
                        int[] pos = state.getArray();
                        // Look in each direction
                        for (int l=0; l<4; ++l){
                            int x = pos[0];
                            int y = pos[1];
                            switch(l){
                                case 0 -> --x;
                                case 1 -> ++x;
                                case 2 -> --y;
                                case 3 -> ++y;
                            }
                            // Don't fall off the edge
                            if (x < 0 || y < 0 || x == map[0].length || y == map.length){
                                continue;
                            }
                            // Ascend exactly one
                            if (map[y][x] == k){
                                // Add the new location
                                newQueue.add(new ArrayState(new int[] {x,y}));
                            }
                        }
                    }
                    queue = newQueue;
                }
                // All unique hikes from the trailhead
                part2 += queue.size();
                // The number of destinations from the trailhead
                part1 += new HashSet<>(queue).size();
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}