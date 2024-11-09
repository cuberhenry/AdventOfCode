import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 6: Memory Reallocation";
    public static void main(String args[]) {
        // Take in the input and split
        int[] blocks = Library.getIntArray(args,"\t");
        // The list of all previous states
        HashMap<ArrayState,Integer> history = new HashMap<>();

        // The current state
        ArrayState state = new ArrayState(blocks.clone());
        // Continue until a duplicate is found
        while (!history.containsKey(state)){
            // Add the current state
            history.put(state,history.size());

            // The index of the largest block
            int index = Library.maxIndex(blocks);
            // Reset the largest block
            int max = blocks[index];
            blocks[index] = 0;

            // Redistribute that maximum value starting at the next block
            for (int i=1; i<=blocks.length; ++i){
                blocks[(index + i) % blocks.length] += max / blocks.length + (max % blocks.length >= i ? 1 : 0);
            }

            // New state
            state = new ArrayState(blocks.clone());
        }

        // The answer to the problem
        int part1 = history.size();
        int part2 = part1 - history.get(state);

        // Print the answer
        Library.print(part1,part2,name);
    }
}