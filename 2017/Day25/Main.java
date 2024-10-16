/*
Henry Anderson
Advent of Code 2017 Day 25 https://adventofcode.com/2017/day/25
Input: https://adventofcode.com/2017/day/25/input
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
        // Part 2 doesn't require code
        if (PART == 2){
            System.out.println("Reboot the Printer");
            return;
        }
        
        // The list of possible states
        ArrayList<int[][]> states = new ArrayList<>();
        // The current state, given by the first line of input
        int state = sc.nextLine().split(" ")[3].charAt(0) - 'A';
        // The number of steps to perform before the checksum
        int steps = Integer.parseInt(sc.nextLine().split(" ")[5]);
        
        // Continue until all states have been dissected
        while (sc.hasNextLine()){
            // Skip the consistent lines
            sc.nextLine();
            sc.nextLine();

            // The instructions to perform in this state
            int[][] instructions = new int[2][3];
            // Loop through each possible value (0 or 1)
            for (int i=0; i<2; ++i){
                sc.nextLine();
                // The value to write at the current position
                instructions[i][0] = sc.nextLine().split(" ")[8].charAt(0) - '0';
                // The direction to move after writing
                instructions[i][1] = sc.nextLine().matches(".*right.") ? 1 : -1;
                // The new state to go to
                instructions[i][2] = sc.nextLine().split(" ")[8].charAt(0) - 'A';
            }
            states.add(instructions);
        }

        // The current x position of the cursor
        int x = 0;
        // The list of all written ones on the tape
        ArrayList<Integer> ones = new ArrayList<>();

        // Loop through all the steps
        for (int i=0; i<steps; ++i){
            // Get the relevant instructions based on state and value
            int[] instructions = states.get(state)[ones.contains(x) ? 1 : 0];
            // Write
            if (instructions[0] == 0){
                ones.remove(Integer.valueOf(x));
            }else if (!ones.contains(x)){
                ones.add(x);
            }
            // Move
            x += instructions[1];
            // Switch states
            state = instructions[2];
        }

        // Print the answer
        System.out.println(ones.size());
    }
}