import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 6: Chronal Coordinates";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The coordinates
        ArrayList<int[]> points = new ArrayList<>();
        // The range of the limited rectangle
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Take in all the input
        while (sc.hasNextLine()){
            // Dissect the line
            int[] point = Library.intSplit(sc.nextLine(),", ");
            // Add the coordinate
            points.add(point);

            // Adjust ranges as needed
            if (point[0] < minX){
                minX = point[0];
            }
            if (point[0] > maxX){
                maxX = point[0];
            }
            if (point[1] < minY){
                minY = point[1];
            }
            if (point[1] > maxY){
                maxY = point[1];
            }
        }
        
        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        // The sizes of each of the groups
        int[] sizes = new int[points.size()];
        // Loop through every point in the limited rectangle
        for (int i=minX; i<=maxX; ++i){
            for (int j=minY; j<=maxY; ++j){
                // The closest point
                int closest = 0;
                int minDist = Integer.MAX_VALUE;
                // Loop through every coordinate
                for (int k=0; k<points.size(); ++k){
                    // Get the distance to the point
                    int dist = Math.abs(i-points.get(k)[0]) + Math.abs(j-points.get(k)[1]);
                    // Save the unique closest
                    if (dist < minDist){
                        closest = k;
                        minDist = dist;
                    }else if (dist == minDist){
                        closest = -1;
                    }
                }

                // If closest is unique
                if (closest != -1){
                    // If it's part of an infinite region, make the point ineligible
                    if (i == minX || i == maxX || j == minY || j == maxY){
                        sizes[closest] = Integer.MIN_VALUE;
                    }else{
                        // Otherwise, increase the region size
                        ++sizes[closest];
                    }
                }

                // The total distance from all coordinates
                int dist = 0;
                // Loop through every coordinate
                for (int k=0; k<points.size(); ++k){
                    // Add the distance
                    dist += Math.abs(i-points.get(k)[0]) + Math.abs(j-points.get(k)[1]);
                }
                // If it's close enough, add it
                if (dist < 10000){
                    ++part2;
                }
            }
        }

        // Find the maximum finite area
        for (int i : sizes){
            part1 = Math.max(part1,i);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}