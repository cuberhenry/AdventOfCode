import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 2: I Was Told There Would Be No Math";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        // For every present
        while (sc.hasNext()){
            // Take in and split the next present
            int[] line = Library.intSplit(sc.nextLine(),"x");
            
            // Surface area
            part1 += 2 * (line[0]*line[1] + line[1]*line[2] + line[2]*line[0]);
            // Extra wrapping paper the size of the smallest size
            part1 += Math.min(Math.min(line[0]*line[1],line[1]*line[2]),line[2]*line[0]);

            // The bow
            part2 += line[0]*line[1]*line[2];
            // Add two times the sum of the smallest dimensions
            part2 += 2 * (Library.sum(line) - line[Library.maxIndex(line)]);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}