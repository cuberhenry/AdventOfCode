import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 21: Step Counter";
    public static void main(String args[]) {
        // Take in the map of plots
        char[][] grid = Library.getCharMatrix(args);

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;
        // Parts of the whole
        long even = 0;
        long odd = 0;
        long middleEven = 0;
        long middleOdd = 0;
        // The coordinates of the start position
        int x = 0;
        int y = 0;

        // Loop through each row
        for (int i=0; i<grid.length; ++i){
            // Save the starting coordinates
            x = Library.indexOf(grid[i],'S');
            if (x != -1){
                y = i;
                grid[y][x] = '.';
                break;
            }
        }

        // Look through every open spot
        LinkedList<ArrayState> queue = new LinkedList<>();
        HashSet<ArrayState> history = new HashSet<>();
        // Start at the S
        ArrayState start = new ArrayState(new int[] {x,y});
        queue.add(start);
        history.add(start);
        // Continue until all spots have been identified
        for (int i=0; !queue.isEmpty(); ++i){
            // If it reaches the edge of the map
            if (i == x){
                // Count the odds and evens for all checked locations
                for (ArrayState hist : history){
                    int[] state = hist.getArray();
                    if ((state[0]+state[1]) % 2 == (x+y)%2){
                        ++middleEven;
                    }else{
                        ++middleOdd;
                    }
                }
            }

            // Count for 64 steps on the base map
            if (i % 2 == 0 && i <= 64){
                part1 += queue.size();
            }

            // Get all the next steps
            LinkedList<ArrayState> newQueue = new LinkedList<>();
            while (!queue.isEmpty()){
                // Get the position
                int[] pos = queue.remove().getArray();
                // Look in each adjacent location
                for (int j=0; j<4; ++j){
                    int thisX = pos[0];
                    int thisY = pos[1];
                    switch (j){
                        case 0 -> --thisX;
                        case 1 -> --thisY;
                        case 2 -> ++thisX;
                        case 3 -> ++thisY;
                    }

                    // Quit when outside the map
                    if (thisX < 0 || thisY < 0 || thisY == grid.length || thisX == grid[0].length){
                        continue;
                    }
                    ArrayState newState = new ArrayState(new int[] {thisX,thisY});
                    // Quit when not an open spot or already checked
                    if (grid[thisY][thisX] != '.' || history.contains(newState)){
                        continue;
                    }
                    // Add to the queue
                    newQueue.add(newState);
                    history.add(newState);
                }
            }
            queue = newQueue;
        }

        // Count all evens and odds
        for (ArrayState hist : history){
            int[] state = hist.getArray();
            if ((state[0]+state[1]) % 2 == (x + y)%2){
                ++even;
            }else{
                ++odd;
            }
        }

        // Count the number of full squares between the start and an edge
        long fullSquares = (26501365-x)/grid.length-1;
        // The possible ending locations in both odd and even squares
        long odds = fullSquares * fullSquares;
        long evens = (fullSquares+1) * (fullSquares+1);
        odds *= odd;
        evens *= even;

        // Add the 4 corners
        long corners = 2 * odd + 2 * middleOdd;
        // Add all the edges
        long edges = fullSquares * 3 * odd + fullSquares * middleOdd;
        edges += (fullSquares+1) * even - (fullSquares+1) * middleEven;

        // Add up all the parts of the answer
        part2 = odds + evens + corners + edges;

        // Print the answer
        Library.print(part1,part2,name);
    }
}