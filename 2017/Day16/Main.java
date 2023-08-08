/*
Henry Anderson
Advent of Code 2017 Day 16 https://adventofcode.com/2017/day/16
Input: https://adventofcode.com/2017/day/16/input
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
        // The list of all moves
        String[] moves = sc.nextLine().split(",");
        // The positions of the programs
        int[] programs = new int[16];
        // Initialize the programs
        for (int i=1; i<programs.length; ++i){
            programs[i] = i;
        }
        // The positions visited before and during the first cycle
        ArrayList<String> visited = new ArrayList<>();
        // The initial position of the second cycle
        int first = -1;
        // The number of times the dance is performed
        int loops = 1;

        // Part 2 performs the dance 1 billion times
        if (PART == 2){
            loops = 1_000_000_000;
        }

        for (int z=0; z<loops; ++z){
            // Go through every move
            for (String move : moves){
                // Perform the specific move
                switch (move.charAt(0)){
                    // Spin
                    case 's' -> {
                        // The number of times to spin
                        int num = Integer.parseInt(move.substring(1));
                        // Repeat that many times
                        for (int i=0; i<num; ++i){
                            // Grab the last program
                            int help = programs[15];
                            // Shift all of the programs back
                            for (int j=15; j>0; --j){
                                programs[j] = programs[j-1];
                            }
                            // Put the last program at the front
                            programs[0] = help;
                        }
                    }
                    // Exchange
                    case 'x' -> {
                        // The two positions that are switching
                        String[] positions = move.substring(1).split("/");
                        int one = Integer.parseInt(positions[0]);
                        int two = Integer.parseInt(positions[1]);
                        // Switch the positions
                        int help = programs[one];
                        programs[one] = programs[two];
                        programs[two] = help;
                    }
                    // Partner
                    case 'p' -> {
                        // The two programs that are switching
                        String[] swaps = move.substring(1).split("/");
                        // Find the positions of the two programs
                        int one = -1;
                        int two = -1;
                        for (int i=0; i<programs.length; ++i){
                            if (swaps[0].charAt(0) == programs[i]+'a'){
                                one = i;
                            }
                            if (swaps[1].charAt(0) == programs[i]+'a'){
                                two = i;
                            }
                        }
                        // Switch the programs
                        int help = programs[one];
                        programs[one] = programs[two];
                        programs[two] = help;
                    }
                }
            }

            // Part 2 finds a cycle to save time
            if (PART == 2){
                // If a cycle hasn't been detected yet
                if (first == -1){
                    // If a repeated orientation has been found
                    if (visited.contains(Arrays.toString(programs))){
                        // Save the index
                        first = z;
                    }
                    // Add the position
                    visited.add(Arrays.toString(programs));
                }else{
                    // If the end of the second cycle has been found
                    if (visited.get(first).equals(Arrays.toString(programs))){
                        // Calculate the size of the cycle
                        int difference = z - first;
                        // Skip all of the full cycles
                        while (1_000_000_000 - difference > z){
                            z += difference;
                        }
                    }
                }
            }
        }

        // Print all of the characters in order
        for (int i=0; i<programs.length; ++i){
            System.out.print((char)('a'+programs[i]));
        }
        System.out.println();
    }
}