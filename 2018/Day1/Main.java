import com.aoc.mylibrary.Library;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 1: Chronal Calibration";
    public static void main(String args[]) {
        // The frequence changes
        int[] input = Library.getIntArray(args,"\n");
        // The answer to the problem
        int part1 = Library.sum(input);
        int part2 = 0;

        // The history of the frequency
        HashSet<Integer> reached = new HashSet<>();
        
        // Repeat until a repeat is found
        while (!reached.contains(part2)){
            for (int change : input){
                reached.add(part2);

                // Change the frequency
                part2 += change;

                // Check for repeats
                if (reached.contains(part2)){
                    break;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}