import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.DisjointSet;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 12: Digital Plumber";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // All of the house numbers with their corresponding pipes
        DisjointSet<Integer> pipes = new DisjointSet<>();
        while (sc.hasNext()){
            // Take in the house line
            String[] house = sc.nextLine().split(" <-> ");
            // Save the house number
            int number = Integer.parseInt(house[0]);
            // Add all the related pipes
            for (int pipe : Library.intSplit(house[1],", ")){
                pipes.union(number,pipe);
            }
        }

        // The answer to the problem
        int part1 = pipes.setSize(0);
        int part2 = pipes.size();

        // Print the answer
        Library.print(part1,part2,name);
    }
}