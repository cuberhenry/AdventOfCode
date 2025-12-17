import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 9: Movie Theater";
    public static void main(String[] args){
        int[][] input = Library.getIntMatrix(args,",");

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // The list of vertical edges at each y-level
        HashMap<Integer,HashSet<Integer>> xByY = new HashMap<>();
        // Every single green tile part of the edges
        HashSet<String> edges = new HashSet<>();

        // Loop through each adjacent pair of red tiles
        for (int i=0; i<input.length; ++i){
            int j = (i+1) % input.length;
            // Loop through each y-level
            for (int y=Math.min(input[i][1],input[j][1]); y<=Math.max(input[i][1],input[j][1]); ++y){
                if (!xByY.containsKey(y)){
                    xByY.put(y,new HashSet<Integer>());
                }
                int minX = Math.min(input[i][0],input[j][0]);
                int maxX = Math.max(input[i][0],input[j][0]);
                // Add the min and max X to the y-level
                xByY.get(y).add(minX);
                xByY.get(y).add(maxX);
                // Add the single edge if it's a vertical line
                if (y != input[i][1] && y != input[j][1]){
                    edges.add(input[i][0] + " " + y);
                }
            }
        }

        // Loop through each y-level
        for (int y : xByY.keySet()){
            // Sort the x-values
            ArrayList<Integer> sorted = new ArrayList<>(xByY.get(y));
            Collections.sort(sorted);

            // Whether the current position is inside the shape
            boolean inside = false;
            // Loop through each vertical edge
            for (int i=0; i<sorted.size(); ++i){
                // If it's a corner
                if (!edges.contains(sorted.get(i) + " " + y)){
                    // Check if the adjacent two edges go different directions vertically
                    if (edges.contains(sorted.get(i) + " " + (y+1)) ^ edges.contains(sorted.get(i+1) + " " + (y+1))){
                        // If inside, the left red tile won't change inside
                        // If outside, the right red won't change inside
                        if (inside){
                            xByY.get(y).remove(sorted.get(i));
                        }else{
                            xByY.get(y).remove(sorted.get(i+1));
                        }
                        // Toggle inside
                        inside = !inside;
                    }else if (inside){
                        // The whole edge merges with the surrounding "inside"
                        xByY.get(y).remove(sorted.get(i));
                        xByY.get(y).remove(sorted.get(i+1));
                    }
                    ++i;
                }else{
                    // Otherwise it's an edge, toggle inside
                    inside = !inside;
                }
            }
        }

        // Loop through each pair of points
        for (int i=0; i<input.length; ++i){
            for (int j=i+1; j<input.length; ++j){
                // Calculate the area of the rectangle between the two points
                long size = (Math.abs((long)input[i][0]-input[j][0])+1) * (Math.abs((long)input[i][1]-input[j][1])+1);
                // Part 1 finds the largest possible rectangle
                part1 = Math.max(part1,size);
                // Part 2 finds the largest rectangle entirely inside the traced shape
                if (size > part2){
                    // Whether the whole rectangle is inside the shape
                    boolean fullyInside = true;
                    // Loop through each y-level in the rectangle
                    for (int y=Math.min(input[i][1],input[j][1]); y<=Math.max(input[i][1],input[j][1]); ++y){
                        ArrayList<Integer> sorted = new ArrayList<>(xByY.get(y));
                        Collections.sort(sorted);
                        // Whether the current row of the rectangle is entirely within the shape
                        boolean rowInside = false;

                        // Loop through each pair of vertical edges
                        for (int k=0; k<sorted.size(); k+=2){
                            // The edges are bounds for being inside, make sure the rectangle falls within those bounds
                            if (sorted.get(k) <= Math.min(input[i][0],input[j][0])
                                    && sorted.get(k+1) >= Math.max(input[i][0],input[j][0])){
                                        rowInside = true;
                                break;
                            }
                        }
                        // If the row isn't inside, the whole rectangle isn't inside
                        if (!rowInside){
                            fullyInside = false;
                            break;
                        }
                    }
                    // If the rectangle is inside, it is the new biggest rectangle
                    if (fullyInside){
                        part2 = size;
                    }
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}