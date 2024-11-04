import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 15: Timing is Everything";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        // The change to ensure previous discs aren't lost
        int step = 1;
        // The time it takes to fall to this disc
        int disc = 1;
        // Take in every line of input
        while (sc.hasNext()){
            // Split the line on spaces
            String[] line = sc.nextLine().split(" |[.]");
            // The number of positions on this disc
            int size = Integer.parseInt(line[3]);
            // The starting position of this disc
            int pos = Integer.parseInt(line[11]);
            
            // Find the time that it falls through this disc
            while ((part1 + disc + pos - size) % size != 0){
                part1 += step;
            }

            // Account for this disc later
            step *= size;
            ++disc;
        }

        // Add a size 11 disc
        int part2 = part1;
        while ((part2 + disc) % 11 != 0){
            part2 += step;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}