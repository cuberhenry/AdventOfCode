import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 18: Lavaduct Lagoon";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The corners of the shape
        ArrayList<long[]> coordinates = new ArrayList<>();
        ArrayList<long[]> colors = new ArrayList<>();
        // The current coordinates
        long[] coords1 = {0,0};
        long[] coords2 = {0,0};
        // The perimeter of the shape
        long perimeter1 = 0;
        long perimeter2 = 0;

        // Take in every instruction
        while (sc.hasNext()){
            // Get the listed character and distance
            char dir = sc.next().charAt(0);
            long size = sc.nextLong();

            // Move the indicated distance and direction
            switch (dir){
                case 'R' -> coords1[0] += size;
                case 'D' -> coords1[1] += size;
                case 'L' -> coords1[0] -= size;
                case 'U' -> coords1[1] -= size;
            }
            // Add the new corner
            coordinates.add(coords1.clone());
            perimeter1 += size;

            // Get the listed color
            size = Long.parseLong(sc.next().substring(2,8),16);
            // Dissect the encoded distance and direction
            dir = (char)(size % 16 + '0');
            size /= 16;

            // Move the indicated distance and direction
            switch (dir){
                case '0' -> coords2[0] += size;
                case '1' -> coords2[1] += size;
                case '2' -> coords2[0] -= size;
                case '3' -> coords2[1] -= size;
            }
            // Add the new corner
            colors.add(coords2.clone());
            perimeter2 += size;
        }

        // Calculate the area of each polygon
        long part1 = area(coordinates,perimeter1);
        long part2 = area(colors,perimeter2);

        // Print the answer
        Library.print(part1,part2,name);
    }

    // Shoelace formula
    private static long area(ArrayList<long[]> coords, long perimeter){
        // The total area
        long area = 0;
        // Loop through each corner
        for (int i=0; i<coords.size(); ++i){
            // Get the current corner
            long[] split = coords.get(i);
            long thisX = split[0];
            long thisY = split[1];
            // Get the next corner
            split = coords.get((i+1)%coords.size());
            long nextX = split[0];
            long nextY = split[1];
            // Perform the calculation
            area += (thisX * nextY) - (nextX * thisY);
        }
        // Include the perimeter, as the coordinates cut out about half of each block
        return area / 2 + perimeter / 2 + 1;
    }
}