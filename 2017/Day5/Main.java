import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 5: A Maze of Twisty Trampolines, All Alike";
    public static void main(String args[]) {
        // The list of jumps
        int[] jumps = Library.getIntArray(args,"\n");
        
        // The answer to the problem
        int part1 = jumps(jumps.clone(),false);
        int part2 = jumps(jumps,true);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static int jumps(int[] jumps, boolean stranger){
        // The number of steps required to escape
        int steps = 0;
        // The current jump instruction
        int index = 0;
        // While we are within the jumps
        while (index < jumps.length && index >= 0){
            // Update the index and the used jump instruction
            index += stranger && jumps[index] >= 3 ? jumps[index]-- : jumps[index]++;
            // Increment steps
            ++steps;
        }

        // Print the answer
        return steps;
    }
}