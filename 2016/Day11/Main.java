import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 11: Radioisotope Thermoelectric Generators";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        int items = 0;

        // Loop through each floor that needs to be emptied
        for (int i=0; i<3; ++i){
            // Get the floor
            String line = sc.nextLine();
            // Count the number of items
            for (int j=line.indexOf(" a "); j>-1; j=line.indexOf(" a ",j+1)){
                ++items;
            }
            // Add the required moves to move all items up one floor
            part1 += items * 2 - 3;
        }
        
        // Add 24 moves for four items three floors
        int part2 = part1 + 24;

        // Print the answer
        Library.print(part1,part2,name);
    }
}