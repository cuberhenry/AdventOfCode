import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 2: Rock Paper Scissors";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // Total number of points
        int part1 = 0;
        int part2 = 0;

        // Loop through all lines of input
        while (sc.hasNext()){
            // Take in the first number as its point value
            int one = sc.next().charAt(0) - 'A' + 1;
            // Take in second value numbed 1-3
            int two = sc.next().charAt(0) - 'X' + 1;
            
            // Points for which shape selected
            part1 += two;
            // Points for who won
            part1 += (two - one + 4) % 3 * 3;
            
            // Points for which shape selected
            part2 += (one + two) % 3 + 1;
            // Points for who won
            part2 += (two - 1) * 3;
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}