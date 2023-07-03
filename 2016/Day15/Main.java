/*
Henry Anderson
Advent of Code 2016 Day 15 https://adventofcode.com/2016/day/15
Input: https://adventofcode.com/2016/day/15/input
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
        // The array list of discs
        ArrayList<int[]> discs = new ArrayList<>();
        // Take in every line of input
        while (sc.hasNext()){
            // Split the line on spaces
            String[] line = sc.nextLine().split(" ");
            // The number of positions on this disc
            int number = Integer.parseInt(line[3]);
            // The starting position of this disc
            int pos = Integer.parseInt(line[11].substring(0,line[11].length()-1));
            // Add the disc
            discs.add(new int[] {number, pos});
        }

        // Part 1 finds the first time you can push the button and get the capsule
        // Part 2 adds another disc
        if (PART == 2){
            discs.add(new int[] {11,0});
        }

        // The time to push the button
        int time = 0;

        // Continue until a button press would result in a success
        while (true){
            // Loop through every disc
            for (int i=0; i<discs.size(); ++i){
                // If at this time the capsule wouldn't go through the disc
                if ((discs.get(i)[1]+time+i+1) % discs.get(i)[0] != 0){
                    // Move to the next time and start over
                    ++time;
                    i = -1;
                    continue;
                }
            }
            // Successful time found
            break;
        }

        // Print the answer
        System.out.println(time);
    }
}