/*
Henry Anderson
Advent of Code 2022 Day 10 https://adventofcode.com/2022/day/10
Input: https://adventofcode.com/2022/day/10/input
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
        // Total result for Part 1
        int total = 0;
        // Current position of the sprite
        int pos = 1;
        // Current clock cycle
        int clock = 0;

        // Loop through all lines of input
        while (sc.hasNextLine()){
            // Take in the line
            String line = sc.nextLine();
            // The number of times to perform a clock cycle
            int num = line.equals("noop") ? 1 : 2;

            for (int i=0; i<num; ++i){
                // Increase the clock cycle
                clock++;
                
                // Part 1 adds the signal strengths from the specified clock cycles
                if (PART == 1){
                    if (clock == 20 || clock == 60 || clock == 100 || clock == 140
                                    || clock == 180 || clock == 220){
                        total += pos*clock;
                    }
                }
                
                // Part 2 prints light if the sprite can be seen and dark if not
                if (PART == 2){
                    if (Math.abs(pos+1-clock%40) <= 1){
                        System.out.print('#');
                    }else{
                        System.out.print('.');
                    }
                    if (clock%40 == 0){
                        System.out.println();
                    }
                }
            }

            // Add the change to the position
            if (num == 2){
                pos += Integer.parseInt(line.substring(5));
            }
        }

        // Print the total signal strengths
        if (PART == 1){
            System.out.println(total);
        }
    }
}