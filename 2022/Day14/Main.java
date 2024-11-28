import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.Scanner;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 14: Regolith Reservoir";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The area in which the sand falls
        HashSet<ArrayState> covered = new HashSet<>();
        // The largest y-value
        int max = 0;

        // While there's still input
        while (sc.hasNextLine()){
            // Split the input into points
            String[] list = sc.nextLine().split(" -> ");
            // Split the points into x and y
            int[][] points = new int[list.length][2];
            points[0] = Library.intSplit(list[0],",");

            // Set the beginning to filled
            covered.add(new ArrayState(points[0].clone()));
            // Fill in between all sets of points
            for (int i=1; i<points.length; ++i){
                points[i] = Library.intSplit(list[i],",");
                max = Math.max(max,points[i][1]);
                // Change x to match, filling in the wall
                while (points[0][0] != points[i][0]){
                    points[0][0] += (int)Math.signum(points[i][0]-points[0][0]);
                    covered.add(new ArrayState(points[0].clone()));
                }
                // Change y to match, filling in the wall
                while (points[0][1] != points[i][1]){
                    points[0][1] += (int)Math.signum(points[i][1]-points[0][1]);
                    covered.add(new ArrayState(points[0].clone()));
                }
            }
        }

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop until an exit condition is found
        while (!covered.contains(new ArrayState(new int[] {500,0}))){
            // The source of the sand
            int x = 500;
            int y = 0;
            // Drop each piece until it lands
            while (!covered.contains(new ArrayState(new int[] {x,y}))){
                // If a piece has fallen into the abyss, for Part 1
                if (y+1 == max+2){
                    if (part1 == 0){
                        part1 = part2;
                    }
                    ++part2;
                    covered.add(new ArrayState(new int[] {x,y}));
                    break;
                }
                // Fall straight down
                if (!covered.contains(new ArrayState(new int[] {x,y+1}))){
                    ++y;
                // Fall down and to the left
                }else if (!covered.contains(new ArrayState(new int[] {x-1,y+1}))){
                    ++y;
                    --x;
                // Fall down and to the right
                }else if (!covered.contains(new ArrayState(new int[] {x+1,y+1}))){
                    ++y;
                    ++x;
                // Landed
                }else{
                    covered.add(new ArrayState(new int[] {x,y}));
                    ++part2;
                    // Start a new piece of sand
                    break;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}