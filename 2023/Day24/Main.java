import com.aoc.mylibrary.Library;
import java.math.BigInteger;

public class Main {
    final private static String name = "Day 24: Never Tell Me The Odds";
    public static void main(String args[]) {
        // Take in the input
        long[][] hailstones = Library.getLongMatrix(args,", | @ ");
        
        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // Part 1 finds the number of intersections within a large 2D area
        // Loop through each pair of hailstones
        for (int i=0; i<hailstones.length; ++i){
            long[] first = hailstones[i];
            for (int j=i+1; j<hailstones.length; ++j){
                long[] other = hailstones[j];
                // Find the intersection between the two lines
                double firstT = (((double)other[4]/other[3])*(first[0]-other[0]) - first[1] + other[1]) / (first[4] - ((double)other[4] * first[3] / other[3]));
                double otherT = (first[0] + first[3] * firstT - other[0]) / other[3];
                double xInter = first[0] + first[3] * firstT;
                double yInter = first[1] + first[4] * firstT;
                // Add if the intersection is within the bounds
                if (xInter >= 200000000000000L && xInter <= 400000000000000L && yInter >= 200000000000000L && yInter <= 400000000000000L && firstT >= 0 && otherT >= 0){
                    ++part1;
                }
            }
        }

        // Part 2 finds the starting coordinates of a rock that will hit all hailstones
        // Find the positions and velocities of two hailstones relative to a third one
        BigInteger[] p1 = new BigInteger[] {
            new BigInteger(hailstones[1][0] - hailstones[0][0] + ""),
            new BigInteger(hailstones[1][1] - hailstones[0][1] + ""),
            new BigInteger(hailstones[1][2] - hailstones[0][2] + "")
        };
        BigInteger[] v1 = new BigInteger[] {
            new BigInteger(hailstones[1][3] - hailstones[0][3] + ""),
            new BigInteger(hailstones[1][4] - hailstones[0][4] + ""),
            new BigInteger(hailstones[1][5] - hailstones[0][5] + "")
        };
        BigInteger[] p2 = new BigInteger[] {
            new BigInteger(hailstones[2][0] - hailstones[0][0] + ""),
            new BigInteger(hailstones[2][1] - hailstones[0][1] + ""),
            new BigInteger(hailstones[2][2] - hailstones[0][2] + "")
        };
        BigInteger[] v2 = new BigInteger[] {
            new BigInteger(hailstones[2][3] - hailstones[0][3] + ""),
            new BigInteger(hailstones[2][4] - hailstones[0][4] + ""),
            new BigInteger(hailstones[2][5] - hailstones[0][5] + "")
        };

        // Use vector math to find the times both of the hailstones will have to be hit
        long t1 = -(Library.dotProduct(Library.crossProduct(p1,p2),v2).divide(Library.dotProduct(Library.crossProduct(v1,p2),v2))).longValue();
        long t2 = -(Library.dotProduct(Library.crossProduct(p1,p2),v1).divide(Library.dotProduct(Library.crossProduct(p1,v2),v1))).longValue();

        // Find the absolute positions of the collisions of the two hailstones from before
        long[] collision1 = new long[] {
            hailstones[1][0] + hailstones[1][3] * t1,
            hailstones[1][1] + hailstones[1][4] * t1,
            hailstones[1][2] + hailstones[1][5] * t1
        };
        long[] collision2 = new long[] {
            hailstones[2][0] + hailstones[2][3] * t2,
            hailstones[2][1] + hailstones[2][4] * t2,
            hailstones[2][2] + hailstones[2][5] * t2
        };

        // Find the velocities of the thrown rock
        long[] v0 = new long[] {
            (collision2[0] - collision1[0]) / (t2 - t1),
            (collision2[1] - collision1[1]) / (t2 - t1),
            (collision2[2] - collision1[2]) / (t2 - t1)
        };

        // Find and sum the initial position of the thrown rock
        part2 = (collision1[0] - v0[0] * t1)
              + (collision1[1] - v0[1] * t1)
              + (collision1[2] - v0[2] * t1);

        // Print the answer
        Library.print(part1,part2,name);
    }
}