import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Main {
    final private static String name = "Day 22: Sand Slabs";
    public static void main(String args[]) {
        // Get all the inputs
        int[][] bricks = Library.getIntMatrix(args,",|~");

        // Sort from least to greatest z
        Arrays.sort(bricks,(a,b) -> {
            return Math.min(a[2],a[5]) - Math.min(b[2],b[5]);
        });

        // Brick to position and position to brick maps
        HashMap<ArrayState,Integer> positionToBrick = new HashMap<>();
        HashMap<Integer,HashSet<ArrayState>> brickToPositions = new HashMap<>();

        // Loop through each brick
        for (int i=0; i<bricks.length; ++i){
            // Sort each dimension least to greatest
            for (int j=0; j<3; ++j){
                if (bricks[i][j] > bricks[i][j+3]){
                    int helper = bricks[i][j];
                    bricks[i][j] = bricks[i][j+3];
                    bricks[i][j+3] = helper;
                }
            }

            // The list of all positions of this brick
            HashSet<ArrayState> positions = new HashSet<>();
            // Loop through each position of the brick
            for (int j=bricks[i][0]; j<=bricks[i][3]; ++j){
                for (int k=bricks[i][1]; k<=bricks[i][4]; ++k){
                    for (int l=bricks[i][2]; l<=bricks[i][5]; ++l){
                        ArrayState state = new ArrayState(new int[] {j,k,l});
                        // Add it to the maps
                        positionToBrick.put(state,i);
                        positions.add(state);
                    }
                }
            }
            brickToPositions.put(i,positions);
        }

        // Map of bricks holding other bricks
        HashMap<Integer,HashSet<Integer>> upwards = new HashMap<>();
        HashMap<Integer,HashSet<Integer>> downwards = new HashMap<>();

        // Loop through each brick
        for (int i=0; i<bricks.length; ++i){
            // Add new hash sets
            upwards.put(i,new HashSet<Integer>());
            downwards.put(i,new HashSet<Integer>());
            // Keep dropping until it hits something
            boolean drop = true;
            while (drop){
                // Loop through each square of the brick
                for (ArrayState position : brickToPositions.get(i)){
                    int[] beneath = position.getArray();
                    // If it's on the floor, it can't fall
                    if (beneath[2] == 1){
                        drop = false;
                        break;
                    }
                    // Check one beneath
                    --beneath[2];
                    ArrayState underState = new ArrayState(beneath);
                    // If there's another brick under this square
                    if (positionToBrick.containsKey(underState) && i != positionToBrick.get(underState)){
                        // Add that the bricks are touching
                        upwards.get(positionToBrick.get(underState)).add(i);
                        downwards.get(i).add(positionToBrick.get(underState));
                        // This brick can't fall anymore
                        drop = false;
                    }
                }

                // If the brick can fall
                if (drop){
                    // The new list of positions for this brick
                    HashSet<ArrayState> newPositions = new HashSet<>();
                    // The sorted list of current positions
                    ArrayList<ArrayState> sortedPositions = new ArrayList<>(brickToPositions.get(i));
                    Collections.sort(sortedPositions,(a,b) -> {
                        return a.getArray()[2] - b.getArray()[2];
                    });

                    // Loop through each position
                    for (ArrayState position : sortedPositions){
                        // Get the position one below
                        int[] beneath = position.getArray();
                        --beneath[2];
                        ArrayState underState = new ArrayState(beneath);
                        // Update the brick at that position
                        positionToBrick.remove(position);
                        positionToBrick.put(underState,i);
                        // Add the position to this brick
                        newPositions.add(underState);
                    }
                    brickToPositions.put(i,newPositions);
                }
            }
        }

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop through each brick
        for (int i=0; i<bricks.length; ++i){
            // All of the bricks that would fall when disintegrating this brick
            LinkedHashSet<Integer> falling = new LinkedHashSet<>();
            falling.add(i);
            // Loop through each brick that is falling
            for (int j=0; j<falling.size(); ++j){
                int index = new ArrayList<Integer>(falling).get(j);
                // Check all the bricks that this brick is supporting
                for (int above : upwards.get(index)){
                    // If all bricks supporting the upper brick are falling
                    if (falling.containsAll(downwards.get(above))){
                        // Then this brick will fall too
                        falling.add(above);
                    }
                }
            }

            if (falling.size() == 1){
                // Count if the brick can be removed without dropping any others
                ++part1;
            }else{
                // Count the total number of bricks that will fall
                part2 += falling.size() - 1;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}