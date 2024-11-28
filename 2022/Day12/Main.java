import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 12: Hill Climbing Algorithm";
    public static void main(String args[]) {
        // Get the height map
        char[][] grid = Library.getCharMatrix(args);
        // The distance from the start
        int[][] dist = new int[grid.length][grid[0].length];
        // The queue of positions to update
        LinkedList<ArrayState> queue = new LinkedList<>();
        
        // Important locations
        int startX = 0;
        int startY = 0;
        int finX = 0;
        int finY = 0;
        for (int i=0; i<grid.length; ++i){
            // Find the start
            int index = Library.indexOf(grid[i],'S');
            if (index != -1){
                startX = index;
                startY = i;
                grid[i][index] = 'a';
            }

            // Find the end
            index = Library.indexOf(grid[i],'E');
            if (index != -1){
                finX = index;
                finY = i;
                grid[i][index] = 'z';
            }

            // Initialize the distance to infinity
            for (int j=0; j<grid[i].length; ++j){
                dist[i][j] = Integer.MAX_VALUE;
            }
        }

        // End is already at the end
        dist[finY][finX] = 0;

        // Start the search from the end
        queue.add(new ArrayState(new int[] {finX,finY}));

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Breadth First Search
        while (!queue.isEmpty()){
            // Take the position values out of the queue
            int[] pos = queue.remove().getArray();
            int x = pos[0];
            int y = pos[1];

            // Check win conditions
            if (grid[y][x] == 'a'){
                if (x == startX && y == startY){
                    part1 = dist[startY][startX];
                }
                if (part2 == 0){
                    part2 = dist[y][x];
                }
                if (part1 != 0 && part2 != 0){
                    break;
                }
            }

            // Look in each direction
            for (int i=0; i<4; ++i){
                int newX = x;
                int newY = y;

                switch(i){
                    case 0 -> ++newX;
                    case 1 -> ++newY;
                    case 2 -> --newX;
                    case 3 -> --newY;
                }

                // Can't go off the edge
                if (newX < 0 || newY < 0 || newY == grid.length || newX == grid[0].length){
                    continue;
                }

                // Only if the new path is better and doesn't climb more than 1
                if (dist[newY][newX] <= dist[y][x] + 1 || grid[y][x] - grid[newY][newX] > 1){
                    continue;
                }

                // Update the state
                dist[newY][newX] = dist[y][x] + 1;
                queue.add(new ArrayState(new int[] {newX,newY}));
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}