/*
Henry Anderson
Advent of Code 2021 Day 17 https://adventofcode.com/2021/day/17
Input: https://adventofcode.com/2021/day/17/input
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
        // Split the line
        String[] split = sc.nextLine().split(", |\\.\\.|=");
        // Take in the minimum Y
        int minY = Integer.parseInt(split[4]);

        // Part 1 finds the maximum height that can be reached
        if (PART == 1){
            // The maximum y velocity is 1 less than the positive min Y
            int answer = (minY + 1) * -1;
            // Get the maximum y height
            answer = minY * (minY + 1) / 2;
            // Print the answer
            System.out.println(answer);
            return;
        }

        // Get the rest of the values
        int minX = Integer.parseInt(split[1]);
        int maxX = Integer.parseInt(split[2]);
        int maxY = Integer.parseInt(split[5]);

        // The number of valid velocity pairs
        int count = 0;
        // From a direct hit to barely making it
        for (int yVel=minY; yVel<Math.abs(minY); ++yVel){
            // From a nudge to a cannon shot
            for (int xVel=1; xVel <= maxX; ++xVel){
                // The starting position and velocity
                int x = 0;
                int y = 0;
                int dx = xVel;
                int dy = yVel;

                // Continue until out of range
                while (x <= maxX && y >= minY){
                    // Change position
                    x += dx;
                    y += dy;

                    // If it's in the range
                    if (x <= maxX && x >= minX && y <= maxY && y >= minY){
                        // It's a good pair
                        ++count;
                        break;
                    }

                    // Change velocity
                    if (dx > 0){
                        --dx;
                    }
                    --dy;
                }
            }
        }

        // Print the answer
        System.out.println(count);
    }
}