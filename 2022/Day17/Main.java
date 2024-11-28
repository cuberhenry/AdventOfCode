import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 17: Pyroclastic Flow";
    public static void main(String args[]) {
        // The input
        String gas = Library.getString(args);
        // The combinations of indeces, used to find a cycle
        HashSet<ArrayState> combos = new HashSet<>();
        // The height of the tower
        long part1 = 0;
        long part2 = 0;
        // The index of the input
        int gasIndex = 0;
        // The index of the objects
        int objIndex = 0;
        // The number of blocks dropped so far
        long numBlocks = 0;
        // Save the index 
        long cycleIndex = 0;
        // The height of the tower at the beginning of the cycle
        long cycleStartHeight = 0;
        // The number of blocks dropped so far
        long cycleStartBlocks = 0;

        // The game board
        ArrayList<char[]> grid = new ArrayList<>();
        {
            // The bottom row
            char[] first = {'X','X','X','X','X','X','X','X','X'};
            grid.add(first);
        }

        // The rock formations falling from the sky
        int[][][] objects = new int[5][][];
        // The horizontal line
        objects[0] = new int[][] {{3,0},{4,0},{5,0},{6,0}};
        // The plus
        objects[1] = new int[][] {{4,2},{3,1},{4,1},{5,1},{4,0}};
        // The L
        objects[2] = new int[][] {{5,2},{5,1},{3,0},{4,0},{5,0}};
        // The vertical line
        objects[3] = new int[][] {{3,3},{3,2},{3,1},{3,0}};
        // The square
        objects[4] = new int[][] {{3,1},{4,1},{3,0},{4,0}};

        // Continue until a repeating position is found
        while (numBlocks != 1_000_000_000_000L){
            // Find the height after 2022 blocks
            if (numBlocks == 2022){
                part1 = part2 + grid.size() - 1;
            }
            // If the cycle has been found and completed
            if (cycleStartBlocks != 0 && cycleIndex == gasIndex){
                // The height that the tower increases during a cycle
                long cycleHeight = part2 + grid.size()-1 - cycleStartHeight;
                // The number of blocks that fall during a cycle
                long cycleBlocks = numBlocks - cycleStartBlocks;

                // The number of cycles that are done
                long numCycles = (1_000_000_000_000L - cycleStartBlocks) / cycleBlocks;

                // Skip the cycles by adding to numBlocks and height
                numBlocks += (numCycles-1) * cycleBlocks;
                part2 += (numCycles-1) * cycleHeight;
            }

            // The current state
            ArrayState state = new ArrayState(new int[] {gasIndex,objIndex});

            // If this combination has been found before
            if (combos.contains(state)){
                // Save the index
                cycleIndex = gasIndex;
                // The height of the tower before the cycle
                cycleStartHeight = grid.size()-1+part2;
                // The number of blocks fallen so far
                cycleStartBlocks = numBlocks;

                // Make sure this doesn't get triggered again
                combos.clear();
            }

            // Until the cycle has been found
            if (cycleStartBlocks == 0){
                // Save the gas and object index pair
                combos.add(state);
            }
            
            // Increase the number of blocks falling
            ++numBlocks;

            // Create a duplicate of the current falling object
            int[][] help = new int[objects[objIndex].length][2];
            for (int i=0; i<help.length; ++i){
                help[i][0] = objects[objIndex][i][0];
                // Set the y-value to be the actual y-value
                help[i][1] = grid.size() + 3 + objects[objIndex][i][1];
            }

            // Whether the rock is able to move in a given direction
            boolean good = true;
            while (good){
                // Check each coordinate of the rock
                for (int i=0; i<help.length && good; ++i){
                    if (help[i][1] < grid.size()){
                        // Makes sure it's not hitting a wall or another rock
                        if (gas.charAt(gasIndex) == '<'){
                            good = good && grid.get(help[i][1])[help[i][0]-1] != 'X';
                        }else{
                            good = good && grid.get(help[i][1])[help[i][0]+1] != 'X';
                        }
                    }else{
                        // Makes sure it's not going beyond the wall
                        if (gas.charAt(gasIndex) == '<'){
                            good = good && help[i][0] > 1;
                        }else{
                            good = good && help[i][0] < 7;
                        }
                    }
                }

                // If it can move, change all of the x-values by the correct direction
                if (good){
                    for (int i=0; i<help.length; ++i){
                        help[i][0] += gas.charAt(gasIndex) == '<' ? -1 : 1;
                    }
                }

                // Increase the gas index
                gasIndex = (++gasIndex) % gas.length();

                // Whether the rock is able to fall
                good = true;

                // Check each coordinate of the rock
                for (int i=0; i<help.length && good; ++i){
                    if (help[i][1]-1 < grid.size()){
                        good = good && grid.get(help[i][1]-1)[help[i][0]] != 'X';
                    }
                }

                // If it can fall
                if (good){
                    // Drop each coordinate down
                    for (int i=0; i<help.length; ++i){
                        --help[i][1];
                    }
                }else{
                    // Add layers until the rock can be added
                    while (help[0][1] >= grid.size()){
                        grid.add(new char[] {'X','.','.','.','.','.','.','.','X'});
                    }
                    // Add the rock
                    for (int i=0; i<help.length; ++i){
                        char[] array = grid.get(help[i][1]);
                        array[help[i][0]] = 'X';
                    }
                }
            }
            // Increase the object index
            objIndex = (1+objIndex) % 5;

            // Save space by removing unneeded layers
            if (grid.size() > 1000){
                for (int j=0; j<980; ++j){
                    grid.remove(1);
                    ++part2;
                }
            }
        }
        
        // Add the remaining bits of tower to the total height
        part2 += grid.size()-1;

        // Print the total height
        Library.print(part1,part2,name);
    }
}