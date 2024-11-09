import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 4: The Ideal Stocking Stuffer";
    public static void main(String args[]) {
        // Take in the input
        String input = Library.getString(args);
        // The answer to the problem
        int part1 = -1;
        int part2 = -1;
        // Until a break is found
        for (int i=1; part2 == -1; ++i){
            String hash = Library.md5(input + i);

            // Check for five leading zeroes
            if (hash.length() < 28 && part1 == -1){
                part1 = i;
            }

            // Check for six leading zeroes
            if (hash.length() < 27){
                part2 = i;
            }
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}