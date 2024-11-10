import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 10: The Stars Align";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // How long until the message appears
        int part2;
        // The list of lights
        ArrayList<int[]> lights = new ArrayList<>();
        // The boundaries of the lights
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Take in all the input
        while (sc.hasNext()){
            // Take in the next line
            int[] point = Library.intSplit(sc.nextLine().substring(10),", |> velocity=<|>");
            // Add the point
            lights.add(point);
            // Record bounds
            minX = Math.min(minX,point[0]);
            maxX = Math.max(maxX,point[0]);
            minY = Math.min(minY,point[1]);
            maxY = Math.max(maxY,point[1]);
        }

        // Continue until the bounds don't get any smaller
        for (part2 = 0;; ++part2){
            // Record the next time's bounds
            int nextMinX = Integer.MAX_VALUE;
            int nextMaxX = Integer.MIN_VALUE;
            int nextMinY = Integer.MAX_VALUE;
            int nextMaxY = Integer.MIN_VALUE;

            // Loop through every light
            for (int i=0; i<lights.size(); ++i){
                int[] light = lights.get(i);
                nextMinX = Math.min(nextMinX,light[0]+light[2]*(part2+1));
                nextMaxX = Math.max(nextMaxX,light[0]+light[2]*(part2+1));
                nextMinY = Math.min(nextMinY,light[1]+light[3]*(part2+1));
                nextMaxY = Math.max(nextMaxY,light[1]+light[3]*(part2+1));
            }

            // If the next second gets the lights closer together
            if (nextMaxY-nextMinY+nextMaxX-nextMinX < maxY-minY+maxX-minX){
                // Record the next bounds
                minX = nextMinX;
                maxX = nextMaxX;
                minY = nextMinY;
                maxY = nextMaxY;
            }else{
                // Message found
                break;
            }
        }

        // Create a grid based on the bounds
        boolean[][] grid = new boolean[maxY-minY+1][maxX-minX+1];
        // Loop through every light, adding to the grid
        for (int[] light : lights){
            grid[light[1]+light[3]*part2-minY][light[0]+light[2]*part2-minX] = true;
        }
        // Get the text
        String part1 = Library.read(grid);
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}