import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {
    final private static String name = "Day 23: Experimental Emergency Teleportation";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The list of all nanobots [x, y, z, r]
        ArrayList<int[]> nanobots = new ArrayList<>();
        // The index of the nanobot with the largest range
        int index = 0;
        // Take in all nanobots
        while (sc.hasNext()){
            // Split the input
            int[] nanobot = Library.intSplit(sc.nextLine().substring(5),">, r=|,");
            // Add the nanobot
            nanobots.add(nanobot);
            // Save this nanobot if it has a larger range
            if (nanobot[3] > nanobots.get(index)[3]){
                index = nanobots.size() - 1;
            }
        }

        // The number of nanobots
        int part1 = 0;
        // The nanobot
        int[] maxRange = nanobots.get(index);
        // Loop through every nanobot
        for (int[] nanobot : nanobots){
            // If it's within range
            if (Math.abs(nanobot[0]-maxRange[0]) + Math.abs(nanobot[1]-maxRange[1]) + Math.abs(nanobot[2]-maxRange[2]) <= maxRange[3]){
                ++part1;
            }
        }

        // Get the starting bounds of the outermost cube
        int maxDist = 0;
        // Save the maximum coordinate from each bot
        for (int[] nanobot : nanobots){
            maxDist = Math.max(maxDist,Math.abs(nanobot[0]));
            maxDist = Math.max(maxDist,Math.abs(nanobot[1]));
            maxDist = Math.max(maxDist,Math.abs(nanobot[2]));
        }
        // Priority list of cube ranges
        PriorityQueue<int[]> cubes = new PriorityQueue<>((a,b) -> {
            // Sort by more bots within range
            if (a[0] > b[0]){
                return -1;
            }
            if (a[0] < b[0]){
                return 1;
            }
            // Sort by closer to 0,0,0
            int aDist = Math.min(Math.abs(a[1]),Math.abs(a[1]+a[4]))
                        + Math.min(Math.abs(a[2]),Math.abs(a[2]+a[4]))
                        + Math.min(Math.abs(a[3]),Math.abs(a[3]+a[4]));
            int bDist = Math.min(Math.abs(b[1]),Math.abs(b[1]+b[4]))
                        + Math.min(Math.abs(b[2]),Math.abs(b[2]+b[4]))
                        + Math.min(Math.abs(b[3]),Math.abs(b[3]+b[4]));
            if (aDist < bDist){
                return -1;
            }
            if (aDist > bDist){
                return 1;
            }
            // Sort by smaller size
            if (a[3] < b[3]){
                return -1;
            }
            if (a[3] > b[3]){
                return 1;
            }

            return 0;
        });
        // We want the bottom front left corner of the cube
        maxDist *= -1;
        // Save the starting cube
        cubes.add(new int[] {nanobots.size(),maxDist,maxDist,maxDist,maxDist*-2});

        // The best location
        int part2 = 0;

        // Continue until all cubes have been searched
        while (!cubes.isEmpty()){
            // Take the first cube off the queue
            int[] cube = cubes.remove();
            // Size 0 cube means this is the answer
            if (cube[4] == 0){
                // Print the answer
                part2 = Math.abs(cube[1]) + Math.abs(cube[2]) + Math.abs(cube[3]);
                break;
            }

            // Loop in each of the 8 sub-cubes
            for (int i=0; i<8; ++i){
                // Get the coordinates
                int x = cube[1];
                int y = cube[2];
                int z = cube[3];
                // Radius is half the previous cube's radius
                int r = cube[4] / 2;
                // Adjust the coordinates to be a unique sub-cube
                if (i % 2 == 1){
                    x += cube[4] - r;
                }
                if (i/2 % 2 == 1){
                    y += cube[4] - r;
                }
                if (i/4 % 2 == 1){
                    z += cube[4] - r;
                }

                // The number of bots within range of the cube
                int count = 0;
                // Loop through each nanobot
                for (int[] nanobot : nanobots){
                    // Get the maximum range from the cube to the nanobot and compare it to the nanobot's range
                    if ((Math.max(Math.abs(nanobot[0]-x),Math.abs(nanobot[0]-(x+r))) < r ? 0 : Math.min(Math.abs(nanobot[0]-x),Math.abs(nanobot[0]-(x+r))))
                        + (Math.max(Math.abs(nanobot[1]-y),Math.abs(nanobot[1]-(y+r))) < r ? 0 : Math.min(Math.abs(nanobot[1]-y),Math.abs(nanobot[1]-(y+r))))
                        + (Math.max(Math.abs(nanobot[2]-z),Math.abs(nanobot[2]-(z+r))) < r ? 0 : Math.min(Math.abs(nanobot[2]-z),Math.abs(nanobot[2]-(z+r))))
                        <= nanobot[3]){
                        ++count;
                    }
                }
                // If it's a relevant cube, save it
                if (count > 0){
                    cubes.add(new int[] {count,x,y,z,r});
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}