import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import com.aoc.mylibrary.DisjointSet;
import java.util.Scanner;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 25: Four-Dimensional Adventure";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // All of the current constellations
        DisjointSet<ArrayState> constellations = new DisjointSet<>();
        // All of the points
        HashSet<ArrayState> points = new HashSet<>();
        // Loop through every point
        while (sc.hasNext()){
            // Get the coordinates as integers
            ArrayState point = new ArrayState(Library.intSplit(sc.nextLine(),","));
            constellations.add(point);

            // Loop through every other point
            for (ArrayState other : points){
                if (point.distance(other) <= 3){
                    constellations.union(point,other);
                }
            }

            points.add(point);
        }

        int part1 = constellations.size();
        // Part 2 doesn't require code
        String part2 = "Trigger the Underflow";

        // Print the answer
        Library.print(part1,part2,name);
    }
}