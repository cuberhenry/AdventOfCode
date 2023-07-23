/*
Henry Anderson
Advent of Code 2017 Day 5 https://adventofcode.com/2017/day/5
Input: https://adventofcode.com/2017/day/5/input
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
        // The list of jumps
        ArrayList<Integer> jumps = new ArrayList<>();
        // Take in and save all of the jump values
        while (sc.hasNext()){
            jumps.add(sc.nextInt());
        }

        // The number of steps required to escape
        int steps = 0;
        // The current jump instruction
        int index = 0;
        // While we are within the jumps
        while (index < jumps.size() && index >= 0){
            // Collect the current jump value
            int jump = jumps.get(index);

            // Part 1 increments every jump
            if (PART == 1){
                // Set the current jump to one more
                jumps.set(index,jumps.get(index)+1);
            }

            // Part 2 increments jumps less than 3 and decrements the others
            if (PART == 2){
                // If the current jump is more than 2
                if (jumps.get(index) >= 3){
                    // Decrement the jump
                    jumps.set(index,jumps.get(index)-1);
                }else{
                    // Increment the jump
                    jumps.set(index,jumps.get(index)+1);
                }
            }
            
            // Change the index by the offset
            index += jump;
            // Increment the number of steps
            ++steps;
        }

        // Print the answer
        System.out.println(steps);
    }
}