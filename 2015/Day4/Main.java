import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 4: The Ideal Stocking Stuffer";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);
        // Take in the input
        String input = sc.next();
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
                break;
            }
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}