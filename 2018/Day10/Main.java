/*
Henry Anderson
Advent of Code 2018 Day 10 https://adventofcode.com/2018/day/10
Input: https://adventofcode.com/2018/day/10/input
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
        // How long until the message appears
        int time;
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
            String line = sc.nextLine();
            // Dissect the position
            int lIndex = line.indexOf('<');
            int cIndex = line.indexOf(',');
            int gIndex = line.indexOf('>');
            int xPos = Integer.parseInt(line.substring(lIndex+1,cIndex).trim());
            int yPos = Integer.parseInt(line.substring(cIndex+1,gIndex).trim());
            line = line.substring(gIndex + 1);
            // Diesect the velocity
            lIndex = line.indexOf('<');
            cIndex = line.indexOf(',');
            gIndex = line.indexOf('>');
            int xVel = Integer.parseInt(line.substring(lIndex+1,cIndex).trim());
            int yVel = Integer.parseInt(line.substring(cIndex+1,gIndex).trim());
            // Add the light
            lights.add(new int[] {xPos,yPos,xVel,yVel});
            // Record bounds
            minX = Math.min(minX,xPos);
            maxX = Math.max(maxX,xPos);
            minY = Math.min(minY,yPos);
            maxY = Math.max(maxY,yPos);
        }

        // Continue until the bounds don't get any smaller
        for (time = 0;; ++time){
            // Record the next time's bounds
            int nextMinX = Integer.MAX_VALUE;
            int nextMaxX = Integer.MIN_VALUE;
            int nextMinY = Integer.MAX_VALUE;
            int nextMaxY = Integer.MIN_VALUE;

            // Loop through every light
            for (int i=0; i<lights.size(); ++i){
                int[] light = lights.get(i);
                nextMinX = Math.min(nextMinX,light[0]+light[2]*(time+1));
                nextMaxX = Math.max(nextMaxX,light[0]+light[2]*(time+1));
                nextMinY = Math.min(nextMinY,light[1]+light[3]*(time+1));
                nextMaxY = Math.max(nextMaxY,light[1]+light[3]*(time+1));
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

        // Part 1 finds the message that will appear
        if (PART == 1){
            // Create a grid based on the bounds
            boolean[][] grid = new boolean[maxY-minY+1][maxX-minX+1];
            // Loop through every light, adding to the grid
            for (int[] light : lights){
                grid[light[1]+light[3]*time-minY][light[0]+light[2]*time-minX] = true;
            }

            // Loop through the whole grid
            for (int i=0; i<grid.length; ++i){
                for (int j=0; j<grid[i].length; ++j){
                    // Print if there's a light there
                    if (grid[i][j]){
                        System.out.print('#');
                    }else{
                        System.out.print(' ');
                    }
                }
                System.out.println();
            }
        }

        // Part 2 finds how long the message takes to appear
        if (PART == 2){
            System.out.println(time);
        }
    }
}