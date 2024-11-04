import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.Scanner;
import java.util.PriorityQueue;

public class Main {
    final private static String name = "Day 9: Explosives in Cyberspace";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // Take in the entire input
        String file = sc.nextLine();
        // The length of the decompressed file
        long part1 = 0;
        long part2 = 0;
        // The list of all currently active multipliers
        PriorityQueue<ArrayState> multipliers = new PriorityQueue<>();
        int ignore = 0;
        // The current value of all multipliers
        long multiplier = 1;

        // Loop through every character in the input file
        for (int i=0; i<file.length(); ++i){
            // Remove expired multipliers
            while (!multipliers.isEmpty() && multipliers.peek().getArray()[0] < i){
                multiplier /= multipliers.remove().getArray()[1];
            }
            // If it's a marker
            if (file.charAt(i) == '('){
                // Skip the parenthesis
                ++i;
                // Used for collecting information from the marker
                String num = "";
                // Collect the first number
                while (file.charAt(i) != 'x'){
                    num += file.charAt(i);
                    ++i;
                }
                // The number of characters to repeat
                int numChars = Integer.parseInt(num);
                // Reset for the second number
                ++i;
                num = "";
                // Collect the second number
                while (file.charAt(i) != ')'){
                    num += file.charAt(i);
                    ++i;
                }
                // The number of repetitions
                int numReps = Integer.parseInt(num);

                if (ignore < i){
                    // Skip the repeated characters
                    ignore = i + numChars;
                    // Increase the length
                    part1 += numChars * numReps;
                }

                // Add the multiplier to the list
                multipliers.add(new ArrayState(new int[] {numChars + i, numReps}));
                // Increase the multiplier
                multiplier *= numReps;
            }else{
                // Increase the length
                if (ignore < i){
                    ++part1;
                }

                part2 += multiplier;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}