import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 25: The Halting Problem";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);
        
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

        // The number of 1s
        int part1 = ones.size();

        // Part 2 doesn't require code
        String part2 = "Reboot the Printer";

        // Print the answer
        Library.print(part1,part2,name);
    }
}