/*
Henry Anderson
Advent of Code 2016 Day 13 https://adventofcode.com/2016/day/13
Input: https://adventofcode.com/2016/day/13/input
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
        // The input
        int number = sc.nextInt();
        // The breadth first search queue
        ArrayList<String> queue = new ArrayList<>();
        // Add the starting position
        queue.add("1 1 0");

        // Loop through every position until a break point is found
        for (int i=0; i<queue.size(); ++i){
            // Grab the position and disect it
            String[] position = queue.get(i).split(" ");
            int a = Integer.parseInt(position[0]);
            int b = Integer.parseInt(position[1]);
            int dist = Integer.parseInt(position[2]);

            // Part 1 finds the distance to 31,39
            // Part 2 finds the number of locations within 50 steps
            if (PART == 2){
                // Once all 50 distance locations have been found, quit
                if (dist == 50){
                    break;
                }
            }

            // Loop through every direction
            for (int j=0; j<4; ++j){
                // Copy the x and y
                int x = a;
                int y = b;
                // Change direction based on j
                switch (j){
                    // Left
                    case 0 -> {
                        if (x == 0){
                            continue;
                        }
                        --x;
                    }
                    // Up
                    case 1 -> {
                        if (y == 0){
                            continue;
                        }
                        --y;
                    }
                    // Right
                    case 2 -> {++x;}
                    // Down
                    case 3 -> {++y;}
                }

                // Part 1's desired location
                if (x == 31 && y == 39){
                    System.out.println(dist+1);
                    return;
                }

                // The value to examine
                int val = x*x + 3*x + 2*x*y + y + y*y + number;
                // Whether the value contains an even number of 1's in binary
                boolean even = true;
                // Decipher how many 1's are in the number's binary representation
                while (val > 0){
                    // If there's a one, swap even
                    if (val % 2 == 1){
                        even = !even;
                    }
                    // Divide by 2
                    val /= 2;
                }

                // If it's an open spot
                if (even){
                    // Get the position
                    String str = x + " " + y + " " ;
                    // Whether the position has been found
                    boolean contains = false;
                    // Loop through every previous visited spot
                    for (String point : queue){
                        // If they have the same x and y
                        if (point.indexOf(str) == 0){
                            // Don't add it
                            contains = true;
                            break;
                        }
                    }
                    // Add the new location
                    if (!contains){
                        queue.add(str + (dist+1));
                    }
                }
            }
        }

        // Print Part 1's answer
        System.out.println(queue.size());
    }
}