import com.aoc.mylibrary.Library;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 18: Settlers of The North Pole";
    public static void main(String args[]) {
        // The forests, lumberyards, and clearings
        char[][] grid = Library.getCharMatrix(args);
        // History for skipping cycles
        HashMap<String,Integer> history = new HashMap<>();
        
        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop through every step
        for (int i=0; i<1000000000; ++i){
            if (i == 10){
                // The number of trees and lumberyards left
                int trees = 0;
                int lumberyards = 0;
                // Loop through every acre
                for (int j=0; j<50; ++j){
                    for (int k=0; k<50; ++k){
                        // Record if it's a resource
                        if (grid[j][k] == '|'){
                            ++trees;
                        }else if (grid[j][k] == '#'){
                            ++lumberyards;
                        }
                    }
                }
                part1 = trees * lumberyards;
            }

            // Craft the current state as a string
            String state = "";
            for (int j=0; j<50; ++j){
                state += String.valueOf(grid[j]);
            }
            // If this state has been visited before
            if (history.containsKey(state)){
                // Get the size of the cycle
                int cycle = i - history.get(state);
                // Skip ahead to the end of the last full cycle
                i += (1000000000-i) / cycle * cycle;
                // If we skipped all the way, don't do any more changes
                if (i == 1000000000){
                    break;
                }
                // Clear history so that we can proceed like normal
                history.clear();
            }else{
                // Otherwise, add to history
                history.put(state,i);
            }

            // Create the new grid, as all changes happen simultaneously
            char[][] newGrid = new char[50][50];
            // Loop through every acre
            for (int j=0; j<50; ++j){
                for (int k=0; k<50; ++k){
                    // Set the new spot to the same as the old spot
                    newGrid[j][k] = grid[j][k];
                    // Count the number of each adjacent acre type
                    int[] adjacents = new int[3];
                    // Look in each direction
                    for (int l=0; l<8; ++l){
                        int x = k;
                        int y = j;
                        switch(l){
                            case 0 -> {--x; --y;}
                            case 1 -> {--x;}
                            case 2 -> {--x; ++y;}
                            case 3 -> {++y;}
                            case 4 -> {++x; ++y;}
                            case 5 -> {++x;}
                            case 6 -> {++x; --y;}
                            case 7 -> {--y;}
                        }
                        // If it's within the bounds
                        if (x >= 0 && y >= 0 && x < 50 && y < 50){
                            // Add its value
                            switch(grid[y][x]){
                                case '.' -> {++adjacents[0];}
                                case '|' -> {++adjacents[1];}
                                case '#' -> {++adjacents[2];}
                            }
                        }
                    }
                    // Adjust the new acre based on the adjacent values
                    if (grid[j][k] == '.' && adjacents[1] >= 3){
                        newGrid[j][k] = '|';
                    }else if (grid[j][k] == '|' && adjacents[2] >= 3){
                        newGrid[j][k] = '#';
                    }else if (grid[j][k] == '#' && (adjacents[1] < 1 || adjacents[2] < 1)){
                        newGrid[j][k] = '.';
                    }
                }
            }

            // Move on to the next step
            grid = newGrid;
        }

        // The number of trees and lumberyards left
        int trees = 0;
        int lumberyards = 0;
        // Loop through every acre
        for (int i=0; i<50; ++i){
            for (int j=0; j<50; ++j){
                // Record if it's a resource
                if (grid[i][j] == '|'){
                    ++trees;
                }else if (grid[i][j] == '#'){
                    ++lumberyards;
                }
            }
        }
        part2 = trees * lumberyards;

        // Print the answer
        Library.print(part1,part2,name);
    }
}