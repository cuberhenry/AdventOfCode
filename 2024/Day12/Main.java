import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import com.aoc.mylibrary.DisjointSet;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 12: Garden Groups";
    public static void main(String[] args){
        // Get the map of characters
        char[][] grid = Library.getCharMatrix(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // The locations that have already been searched
        HashSet<ArrayState> searched = new HashSet<>();

        // Loop through each spot
        for (int i=0; i<grid.length; ++i){
            for (int j=0; j<grid[i].length; ++j){
                // Skip if already searched
                ArrayState state = new ArrayState(new int[] {j,i});
                if (searched.contains(state)){
                    continue;
                }
                // Find all of the parts of this region
                ArrayList<ArrayState> region = new ArrayList<>();
                region.add(state);
                searched.add(state);
                // The parts of each side
                DisjointSet<ArrayState> sides = new DisjointSet<>();
                // Loop through each plot for this region
                for (int k=0; k<region.size(); ++k){
                    int[] pos = region.get(k).getArray();
                    // Look in each direction
                    for (int l=0; l<4; ++l){
                        int x = pos[0];
                        int y = pos[1];
                        switch (l){
                            case 0 -> --x;
                            case 1 -> ++x;
                            case 2 -> --y;
                            case 3 -> ++y;
                        }

                        // If it doesn't match
                        if (x < 0 || y < 0 || x == grid[0].length || y == grid.length || grid[pos[1]][pos[0]] != grid[y][x]){
                            // Add the new side
                            ArrayState currSide = new ArrayState(new int[] {x,pos[0],y,pos[1],l/2});
                            sides.add(currSide);
                            // Check for continuing sides
                            if (l / 2 == 0){
                                // Look up and down for matching sides
                                ArrayState up = new ArrayState(new int[] {x,pos[0],y-1,pos[1]-1,l/2});
                                ArrayState down = new ArrayState(new int[] {x,pos[0],y+1,pos[1]+1,l/2});
                                if (sides.contains(up)){
                                    sides.union(currSide,up);
                                }
                                if (sides.contains(down)){
                                    sides.union(currSide,down);
                                }
                            }else{
                                // Look left and right for matching sides
                                ArrayState left = new ArrayState(new int[] {x-1,pos[0]-1,y,pos[1],l/2});
                                ArrayState right = new ArrayState(new int[] {x+1,pos[0]+1,y,pos[1],l/2});
                                if (sides.contains(left)){
                                    sides.union(currSide,left);
                                }
                                if (sides.contains(right)){
                                    sides.union(currSide,right);
                                }
                            }
                            // Don't look in this direction anymore
                            continue;
                        }
                        // Get the new state
                        ArrayState newState = new ArrayState(new int[] {x,y});
                        // If unsearched
                        if (!searched.contains(newState)){
                            // Add it to this region
                            region.add(newState);
                            searched.add(newState);
                        }
                    }
                }

                // Multiply the area by the perimeter
                part1 += region.size() * sides.totalSize();
                // Multiply the area by the number of sides
                part2 += region.size() * sides.size();
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}