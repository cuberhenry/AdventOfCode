/*
Henry Anderson
Advent of Code 2017 Day 13 https://adventofcode.com/2017/day/13
Input: https://adventofcode.com/2017/day/13/input
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
        // The list of the scanners
        ArrayList<int[]> scanners = new ArrayList<>();
        // Take in all of the input
        while (sc.hasNext()){
            // Split the line
            String[] line = sc.nextLine().split(": ");
            // Create the new scanner
            int[] scanner = new int[2];
            // Save the information
            scanner[0] = Integer.parseInt(line[0]);
            scanner[1] = Integer.parseInt(line[1]);
            // Add the scanner to the list
            scanners.add(scanner);
        }

        // The severity of getting caught
        int severity = 0;
        // The delay before the packet gets sent
        int start = 0;
        // Loop through every scanner
        for (int i=0; i<scanners.size(); ++i){
            // Grab the scanner
            int[] scanner = scanners.get(i);
            // How many picoseconds the scanner is into its cycle
            int pico = (scanner[0]+start) % ((scanner[1]-1)*2);
            // This means the scanner is at position 0
            if (pico == 0){
                // Part 1 finds the total severity of leaving with no delay
                if (PART == 1){
                    // Add this layer's severity
                    severity += scanner[0] * scanner[1];
                }
                
                // Part 2 finds the smallest delay required before the packet
                // can leave without getting caught
                if (PART == 2){
                    // Increase the delay
                    ++start;
                    // Restart the checking
                    i = -1;
                }
            }
        }

        // Print the answer
        if (PART == 1){
            System.out.println(severity);
        }

        if (PART == 2){
            System.out.println(start);
        }
    }
}