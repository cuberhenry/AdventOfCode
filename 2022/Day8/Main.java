import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashSet;
import java.util.Stack;

public class Main {
    final private static String name = "Day 8: Treetop Tree House";
    public static void main(String args[]) {
        // The grid of trees
        char[][] grid = Library.getCharMatrix(args);

        // List of all visible trees except those on the edge
        HashSet<ArrayState> visible = new HashSet<>();

        // The scenic scores of all trees, skipping the ends
        // because we already know their scores are 0
        int[][] scores = new int[grid.length][grid[0].length];
        // Initialize them all to 1
        for (int i=1; i<grid.length-1; ++i){
            for (int j=1; j<grid[0].length-1; ++j){
                scores[i][j] = 1;
            }
        }

        // Vertically
        for (int i=1; i<grid[0].length-1; ++i){
            // From the top
            int currHeight = grid[0][i];
            // Visible trees
            Stack<int[]> stack = new Stack<>();
            stack.push(new int[] {grid[0][i],0});
            for (int j=1; j<grid.length-1; ++j){
                // Only do something if there's a new tallest
                if (grid[j][i] > currHeight){
                    // Add to visible if not already known
                    visible.add(new ArrayState(new int[] {i,j}));
                    // Update the current biggest height
                    currHeight = grid[j][i];
                }
                // Look for the furthest visible tree
                while (!stack.isEmpty() && stack.peek()[0] < grid[j][i]){
                    stack.pop();
                }
                scores[j][i] *= j - (stack.isEmpty() ? 0 : stack.peek()[1]);
                // Add this tree to the stack
                if (!stack.isEmpty() && stack.peek()[0] == grid[j][i]){
                    stack.peek()[1] = j;
                }else{
                    stack.push(new int[] {grid[j][i],j});
                }
            }

            // From the bottom
            currHeight = grid[grid.length-1][i];
            stack.clear();
            stack.push(new int[] {grid[grid.length-1][i],grid.length-1});
            for (int j=grid.length-2; j>0; --j){
                // Only do something if there's a new tallest
                if (grid[j][i] > currHeight){
                    // Add to visible if not already known
                    visible.add(new ArrayState(new int[] {i,j}));
                    // Update the current biggest height
                    currHeight = grid[j][i];
                }
                // Look for the furthest visible tree
                while (!stack.isEmpty() && stack.peek()[0] < grid[j][i]){
                    stack.pop();
                }
                scores[j][i] *= (stack.isEmpty() ? grid.length-1 : stack.peek()[1]) - j;
                // Add this tree to the stack
                if (!stack.isEmpty() && stack.peek()[0] == grid[j][i]){
                    stack.peek()[1] = j;
                }else{
                    stack.push(new int[] {grid[j][i],j});
                }
            }
        }

        // Horizontally
        for (int j=1; j<grid.length-1; ++j){
            // From the left
            int currHeight = grid[j][0];
            // Visible trees
            Stack<int[]> stack = new Stack<>();
            stack.push(new int[] {grid[j][0],0});
            for (int i=1; i<grid[0].length-1; ++i){
                // Only do something if there's a new tallest
                if (grid[j][i] > currHeight){
                    // Add to visible if not already known
                    visible.add(new ArrayState(new int[] {i,j}));
                    // Update the current biggest height
                    currHeight = grid[j][i];
                }
                // Look for the furthest visible tree
                while (!stack.isEmpty() && stack.peek()[0] < grid[j][i]){
                    stack.pop();
                }
                scores[j][i] *= i - (stack.isEmpty() ? 0 : stack.peek()[1]);
                // Add this tree to the stack
                if (!stack.isEmpty() && stack.peek()[0] == grid[j][i]){
                    stack.peek()[1] = i;
                }else{
                    stack.push(new int[] {grid[j][i],i});
                }
            }

            // From the right
            currHeight = grid[j][grid[0].length-1];
            stack.clear();
            stack.push(new int[] {grid[j][grid[0].length-1],grid[0].length-1});
            for (int i=grid[0].length-2; i>0; --i){
                // Only do something if there's a new tallest
                if (grid[j][i] > currHeight){
                    // Add to visible if not already known
                    visible.add(new ArrayState(new int[] {i,j}));
                    // Update the current biggest height
                    currHeight = grid[j][i];
                }
                // Look for the furthest visible tree
                while (!stack.isEmpty() && stack.peek()[0] < grid[j][i]){
                    stack.pop();
                }
                scores[j][i] *= (stack.isEmpty() ? grid[0].length-1 : stack.peek()[1]) - i;
                // Add this tree to the stack
                if (!stack.isEmpty() && stack.peek()[0] == grid[j][i]){
                    stack.peek()[1] = i;
                }else{
                    stack.push(new int[] {grid[j][i],i});
                }
            }
        }

        // The answer to the problem
        int part1 = visible.size() + 2*grid.length + 2*grid[0].length - 4;
        int part2 = 0;

        // Find the best scenic score
        for (int i=1; i<scores.length-1; ++i){
            for (int j=1; j<scores[i].length-1; ++j){
                part2 = Math.max(part2,scores[i][j]);
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}