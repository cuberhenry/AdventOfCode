import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.Scanner;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 17: Reservoir Research";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The coordinates of every clay
        HashSet<ArrayState> clay = new HashSet<>();
        // The min and max clay coordinate
        int minY = Integer.MAX_VALUE;
        int maxY = 0;

        // Take in all input
        while (sc.hasNext()){
            // Split it
            String[] split = sc.nextLine().split(", |=|\\.\\.");
            // The coordinate of the unchanging dimension
            int first = Integer.parseInt(split[1]);
            // The range of the changing dimension
            int start = Integer.parseInt(split[3]);
            int end = Integer.parseInt(split[4]);

            // Save new mins and maxes
            if (split[0].equals("y")){
                minY = Math.min(minY,first);
                maxY = Math.max(maxY,first);
            }else{
                minY = Math.min(minY,start);
                maxY = Math.max(maxY,end);
            }

            // Loop through every spot in the range
            for (int i=start; i<=end; ++i){
                // Add the clay
                if (split[0].equals("y")){
                    clay.add(new ArrayState(new int[] {i,first}));
                }else{
                    clay.add(new ArrayState(new int[] {first,i}));
                }
            }
        }

        // Every spot with water
        HashSet<ArrayState> water = new HashSet<>();
        // Perform the simulation starting at the spring
        dropWater(500,minY,maxY,clay,water);

        // The answer to the problem
        int part1 = water.size();
        water.retainAll(clay);
        int part2 = water.size();

        // Print the answer
        Library.print(part1,part2,name);
    }

    // Simulates dropping water at a specific coordinate
    // Returns whether all of the water dropped has settled
    public static boolean dropWater(int x, int y, int maxY, HashSet<ArrayState> clay, HashSet<ArrayState> water){
        // The starting value of y
        int startY = y;
        // Continue until clay is hit
        while (!clay.contains(new ArrayState(new int[] {x,y+1}))){
            // Add water
            water.add(new ArrayState(new int[] {x,y}));
            // Move down
            ++y;
            // Any water dropping out of the scan is irrelevant
            if (y > maxY){
                return false;
            }
        }

        // Water has already been simulated here
        if (water.contains(new ArrayState(new int[] {x,y}))){
            return false;
        }
        water.add(new ArrayState(new int[] {x,y}));

        // Loop until a break point is reached
        while (true){
            // One tile in each direction
            int left = x-1;
            int right = x+1;

            // Keep moving until hitting clay or a hole to drop from
            while (!clay.contains(new ArrayState(new int[] {left,y})) && clay.contains(new ArrayState(new int[] {left,y+1}))){
                water.add(new ArrayState(new int[] {left,y}));
                --left;
            }

            // Keep moving until hitting water or a hole to drop from
            while (!clay.contains(new ArrayState(new int[] {right,y})) && clay.contains(new ArrayState(new int[] {right,y+1}))){
                water.add(new ArrayState(new int[] {right,y}));
                ++right;
            }

            // If its blocked in by clay
            if (clay.contains(new ArrayState(new int[] {left,y})) && clay.contains(new ArrayState(new int[] {right,y}))){
                // Turn all water into standing water (consider to be clay)
                for (int i=left+1; i<right; ++i){
                    clay.add(new ArrayState(new int[] {i,y}));
                }
            }else{
                // Whether either side has been filled
                boolean filled = false;
                // If the left side should be dropped from, drop from there
                if (!clay.contains(new ArrayState(new int[] {left,y})) && !clay.contains(new ArrayState(new int[] {left,y+1})) && !water.contains(new ArrayState(new int[] {left,y}))){
                    filled = dropWater(left,y,maxY,clay,water);
                }
                // If the right side should be dropped from, drop from there
                if (!clay.contains(new ArrayState(new int[] {right,y})) && !clay.contains(new ArrayState(new int[] {right,y+1})) && !water.contains(new ArrayState(new int[] {right,y}))){
                    filled = filled || dropWater(right,y,maxY,clay,water);
                }
                // If they both dropped fully, no need to return here
                if (!filled){
                    return false;
                }
            }

            // Move up one y value
            --y;
            // Only move up as much as the original drop
            if (y < startY){
                return true;
            }
        }
    }
}