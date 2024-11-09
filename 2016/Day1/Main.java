import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 1: No Time for a Taxicab";
    public static void main(String args[]) {
        // The list of instructions
        String[] input = Library.getStringArray(args,", ");
        // Current position
        int[] position = new int[2];
        // Current direction
        int direction = 0;

        // All visited positions
        HashSet<ArrayState> positions = new HashSet<>();
        // Add the starting position
        positions.add(new ArrayState(position.clone()));

        // The answer to the problem
        int part1;
        int part2 = -1;

        // Loop through every instruction
        for (String instruction : input){
            // Turn in the desired direction
            if (instruction.charAt(0) == 'L'){
                direction = (direction+3)%4;
            }else{
                direction = (direction+1)%4;
            }

            // Grab the distance to be travelled
            int distance = Integer.parseInt(instruction.substring(1));

            // Move distance in direction but keeping track of every position
            for (int i=0; i<distance; ++i){
                switch (direction){
                    case 0 -> {++position[1];}
                    case 1 -> {++position[0];}
                    case 2 -> {--position[1];}
                    case 3 -> {--position[0];}
                }

                // If the first intersection hasn't been found yet
                if (part2 == -1){
                    // If visited before
                    if (positions.contains(new ArrayState(position))){
                        // Save the distance
                        part2 = Math.abs(position[0]) + Math.abs(position[1]);
                    }else{
                        // Add the current position
                        positions.add(new ArrayState(position.clone()));
                    }
                }
            }
        }

        // Find the ending distance
        part1 = Math.abs(position[0]) + Math.abs(position[1]);

        // Print the distance
        Library.print(part1,part2,name);
    }
}