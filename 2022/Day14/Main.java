/*
Henry Anderson
Advent of Code 2022 Day 14 https://adventofcode.com/2022/day/14
Input: https://adventofcode.com/2022/day/14/input
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
        // The area in which the sand falls
        boolean[][] grid = new boolean[1000][1000];
        // The number of fallen sand pieces
        int total = 0;
        // The largest y-value
        int max = 0;

        // While there's still input
        while (sc.hasNextLine()){
            // Split the input into points
            String[] list = sc.nextLine().split(" -> ");
            // Split the points into x and y
            int[][] points = new int[list.length][2];
            for (int i=0; i<list.length; ++i){
                String[] line = list[i].split(",");
                points[i][0] = Integer.parseInt(line[0]);
                points[i][1] = Integer.parseInt(line[1]);
                if (points[i][1] > max){
                    max = points[i][1];
                }
            }

            // The beginning of the wall
            int x = points[0][0];
            int y = points[0][1];

            // Set it to filled
            grid[x][y] = true;
            // Fill in between all sets of points
            for (int i=1; i<points.length; ++i){
                // Change x to match, filling in the wall
                while (x != points[i][0]){
                    x += (int)Math.signum(points[i][0]-x);
                    grid[x][y] = true;
                }
                // Change y to match, filling in the wall
                while (y != points[i][1]){
                    y += (int)Math.signum(points[i][1]-y);
                    grid[x][y] = true;
                }
            }
        }

        // Part 1 counts the number of sand pieces before it falls into the abyss

        // Part 2 adds a floor and counts the number of sand pieces before
        // the sand source gets filled
        if (PART == 2){
            max += 2;
            for (int i=0; i<grid.length; ++i){
                grid[i][max] = true;
            }
        }

        // Loop until an exit condition is found
        while (true){
            // The source of the sand
            int x = 500;
            int y = 0;
            // Drop each piece until it lands
            while (true){
                // If a piece has fallen into the abyss, for Part 1
                if (y+1 == grid.length){
                    System.out.println(total);
                    return;
                }
                // Fall straight down
                if (!grid[x][y+1]){
                    ++y;
                // Fall down and to the left
                }else if (!grid[x-1][y+1]){
                    ++y;
                    --x;
                // Fall down and to the right
                }else if (!grid[x+1][y+1]){
                    ++y;
                    ++x;
                // Landed
                }else{
                    grid[x][y] = true;
                    ++total;
                    // If the source has been filled, for Part 2
                    if (x == 500 && y == 0){
                        System.out.println(total);
                        return;
                    }
                    // Start a new piece of sand
                    break;
                }
            }
        }
    }
}