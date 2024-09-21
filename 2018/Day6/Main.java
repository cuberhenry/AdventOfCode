/*
Henry Anderson
Advent of Code 2018 Day 1 https://adventofcode.com/2018/day/1
Input: https://adventofcode.com/2018/day/1/input
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
            String[] line = sc.nextLine().split(", ");
            int x = Integer.parseInt(line[0]);
            int y = Integer.parseInt(line[1]);

            // Add the coordinate
            points.add(new int[] {x,y});
            // Adjust ranges as needed
            if (x < minX){
                minX = x;
            }
            if (x > maxX){
                maxX = x;
            }
            if (y < minY){
                minY = y;
            }
            if (y > maxY){
                maxY = y;
            }
        }
        
        // The answer to the problem
        int answer = 0;
        // The sizes of each of the groups
        int[] sizes = new int[points.size()];
        // Loop through every point in the limited rectangle
        for (int i=minX; i<=maxX; ++i){
            for (int j=minY; j<=maxY; ++j){
                // Part 1 finds the largest finite area closest to a coordinate
                if (PART == 1){
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
                }

                // Part 2 finds the number of points within a sum of 10,000 distance from all coordinates
                if (PART == 2){
                    // The total distance from all coordinates
                    int dist = 0;
                    // Loop through every coordinate
                    for (int k=0; k<points.size(); ++k){
                        // Add the distance
                        dist += Math.abs(i-points.get(k)[0]) + Math.abs(j-points.get(k)[1]);
                    }
                    // If it's close enough, add it
                    if (dist < 10000){
                        ++answer;
                    }
                }
            }
        }

        // Find the maximum finite area
        if (PART == 1){
            for (int i : sizes){
                answer = Math.max(answer,i);
            }
        }

        // Print the answer
        System.out.println(answer);
    }
}