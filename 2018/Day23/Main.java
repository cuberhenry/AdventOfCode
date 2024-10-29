/*
Henry Anderson
Advent of Code 2018 Day 23 https://adventofcode.com/2018/day/23
Input: https://adventofcode.com/2018/day/23/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // The desired problem to solve
    static int PART;
    static Scanner sc;
    // The file containing the puzzle input
    static String FILE_NAME = "input.txt";
    public static void main(String args[]) {
        if (args.length < 1 || args.length > 2){
            System.out.println("Wrong number of arguments");
            return;
        }
        // Take in the part and file name
        try {
            PART = Integer.parseInt(args[0]);
        } catch (Exception e){}
        if (!(PART == 1 || PART == 2)){
            System.out.println("Part can only be 1 or 2");
            return;
        }
        if (args.length == 2){
            FILE_NAME = args[1];
        }
        try {
            sc = new Scanner(new File(FILE_NAME));
        }catch (Exception e){
            System.out.println("File not found");
            return;
        }
        // The list of all nanobots [x, y, z, r]
        ArrayList<int[]> nanobots = new ArrayList<>();
        // The index of the nanobot with the largest range
        int index = 0;
        // Take in all nanobots
        while (sc.hasNext()){
            // Split the input
            String[] split = sc.nextLine().substring(5).split(">, r=|,");
            int[] nanobot = new int[4];
            // Add the values
            for (int i=0; i<4; ++i){
                nanobot[i] = Integer.parseInt(split[i]);
            }
            // Add the nanobot
            nanobots.add(nanobot);
            // Save this nanobot if it has a larger range
            if (nanobot[3] > nanobots.get(index)[3]){
                index = nanobots.size() - 1;
            }
        }

        // Part 1 finds the number of bots in the range of the largest-ranged bot
        if (PART == 1){
            // The number of nanobots
            int count = 0;
            // The nanobot
            int[] maxRange = nanobots.get(index);
            // Loop through every nanobot
            for (int[] nanobot : nanobots){
                // If it's within range
                if (Math.abs(nanobot[0]-maxRange[0]) + Math.abs(nanobot[1]-maxRange[1]) + Math.abs(nanobot[2]-maxRange[2]) <= maxRange[3]){
                    ++count;
                }
            }

            // Print the answer
            System.out.println(count);
        }

        // Part 2 finds the distance to the closest point in the range of the most bots
        if (PART == 2){
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

            // Continue until all cubes have been searched
            while (!cubes.isEmpty()){
                // Take the first cube off the queue
                int[] cube = cubes.remove();
                // Size 0 cube means this is the answer
                if (cube[4] == 0){
                    // Print the answer
                    System.out.println(Math.abs(cube[1]) + Math.abs(cube[2]) + Math.abs(cube[3]));
                    return;
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
        }
    }
}