import com.aoc.mylibrary.Library;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 6: Tuning Trouble";
    public static void main(String args[]) {
        // The input as a string
        String input = Library.getString(args);
        // The characters being compared
        LinkedList<Character> chars = new LinkedList<>();

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Until there are 14 unique characters
        while (chars.size() < 14){
            // If there are four unique characters
            if (part1 == 0 && chars.size() == 4){
                part1 = part2;
            }
            // While there is a copy of the new character
            while (chars.contains(input.charAt(part2))){
                // Remove
                chars.remove();
            }
            // Add the character
            chars.add(input.charAt(part2));
            // Increase the index
            ++part2;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}