/*
Henry Anderson
Advent of Code 2020 Day 12 https://adventofcode.com/2020/day/12
Input: https://adventofcode.com/2020/day/12/input
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
        // The ship's position
        int x = 0;
        int y = 0;
        // The ship's direction
        int dir = 1;
        // The waypoint's position
        int wayX = 10;
        int wayY = 1;

        // Continue for each instruction
        while (sc.hasNext()){
            // Take in the next instruction and pull apart the information
            String input = sc.nextLine();
            char instruction = input.charAt(0);
            int num = Integer.parseInt(input.substring(1));

            // Part 1 reads the directions for the ship
            if (PART == 1){
                switch(instruction){
                    // Move north
                    case 'N' -> {y += num;}
                    // Move south
                    case 'S' -> {y -= num;}
                    // Move east
                    case 'E' -> {x += num;}
                    // Move west
                    case 'W' -> {x -= num;}
                    // Turn left
                    case 'L' -> {dir = (dir + 4 - num/90) % 4;}
                    // Turn right
                    case 'R' -> {dir = (dir + num/90) % 4;}
                    // Move forward
                    case 'F' -> {
                        switch(dir){
                            case 0 -> {y += num;}
                            case 1 -> {x += num;}
                            case 2 -> {y -= num;}
                            case 3 -> {x -= num;}
                        }
                    }
                }
            }

            // Part 2 reads the directions using a waypoint
            if (PART == 2){
                switch(instruction){
                    // Move the waypoint north
                    case 'N' -> {wayY += num;}
                    // Move the waypoint south
                    case 'S' -> {wayY -= num;}
                    // Move the waypoint east
                    case 'E' -> {wayX += num;}
                    // Move the waypoint west
                    case 'W' -> {wayX -= num;}
                    // Rotate the waypoint counterclockwise around the ship
                    case 'L' -> {
                        switch(num/90){
                            case 1 -> {
                                int helper = wayX;
                                wayX = x - (wayY-y);
                                wayY = y + (helper-x);
                            }
                            case 2 -> {
                                wayX = x - (wayX - x);
                                wayY = y - (wayY - y);
                            }
                            case 3 -> {
                                int helper = wayX;
                                wayX = x + (wayY-y);
                                wayY = y - (helper-x);
                            }
                        }
                    }
                    // Rotate the waypoint clockwise around the shipt
                    case 'R' -> {
                        switch(num/90){
                            case 1 -> {
                                int helper = wayX;
                                wayX = x + (wayY-y);
                                wayY = y - (helper-x);
                            }
                            case 2 -> {
                                wayX = x - (wayX - x);
                                wayY = y - (wayY - y);
                            }
                            case 3 -> {
                                int helper = wayX;
                                wayX = x - (wayY-y);
                                wayY = y + (helper-x);
                            }
                        }
                    }
                    // Move toward the waypoint
                    case 'F' -> {
                        int yDiff = (wayY - y) * num;
                        int xDiff = (wayX - x) * num;
                        y += yDiff;
                        wayY += yDiff;
                        x += xDiff;
                        wayX += xDiff;
                    }
                }
            }
        }

        // Print the ending Manhattan distance
        System.out.println(Math.abs(x) + Math.abs(y));
    }
}