/*
Henry Anderson
Advent of Code 2015 Day 2 https://adventofcode.com/2015/day/2
Input: https://adventofcode.com/2015/day/2/input
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
        // The answer to the problem
        int total = 0;
        // For every present
        while (sc.hasNext()){
            // Take in and split the next present
            String[] line = sc.nextLine().split("x");
            int l = Integer.parseInt(line[0]);
            int w = Integer.parseInt(line[1]);
            int h = Integer.parseInt(line[2]);

            // Part 1 finds the surface area of the presents
            if (PART == 1){
                // Surface area
                total += 2 * (l*w + w*h + h*l);
                // Extra wrapping paper the size of the smallest size
                total += Math.min(Math.min(l*w,w*h),h*l);
            }

            // Part 2 finds the amount of ribbon required for the presents
            if (PART == 2){
                // The bow
                total += l*w*h;
                // Add two times the sum of the smallest dimensions
                total += 2 * (l+w+h);
                total -= 2 * (Math.max(Math.max(l,w),h));
            }
        }

        // Print the answer
        System.out.println(total);
    }
}