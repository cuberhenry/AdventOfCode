import com.aoc.mylibrary.Library;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 16: Permutation Promenade";
    public static void main(String args[]) {
        // The list of all moves
        String[] moves = Library.getStringArray(args,",");
        // The positions of the programs
        char[] programs = new char[16];
        // Initialize the programs
        for (int i=0; i<programs.length; ++i){
            programs[i] = (char)(i + 'a');
        }

        // The positions visited before
        HashMap<String,Integer> visited = new HashMap<>();
        // Whether a loop has been found
        boolean loop = false;

        // The result after the first dance
        String part1 = "";

        for (int z=0; z<1000000000; ++z){
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
                            char help = programs[15];
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
                        char help = programs[one];
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
                            if (swaps[0].charAt(0) == programs[i]){
                                one = i;
                            }
                            if (swaps[1].charAt(0) == programs[i]){
                                two = i;
                            }
                        }
                        // Switch the programs
                        char help = programs[one];
                        programs[one] = programs[two];
                        programs[two] = help;
                    }
                }
            }

            // Save result after one dance
            if (z == 0){
                part1 = String.valueOf(programs);
            }

            // If a loop hasn't been found
            if (!loop){
                // If a repeated orientation has been found
                if (!loop && visited.containsKey(String.valueOf(programs))){
                    // Save the index
                    int loopStart = visited.get(String.valueOf(programs));
                    int delta = z - loopStart;

                    // Skip ahead
                    z = (1000000000-loopStart) / delta * delta + loopStart;
                    loop = true;
                }
                // Add the position
                visited.put(String.valueOf(programs),z);
            }
        }

        // The result after 1 billion dances
        String part2 = String.valueOf(programs);

        // Print the answer
        Library.print(part1,part2,name);
    }
}