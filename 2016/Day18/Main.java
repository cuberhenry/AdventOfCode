import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 18: Like a Rogue";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // Take in the first row of tiles
        String input = sc.nextLine();
        // Whether each tile in the current row is a trap
        boolean[] row = new boolean[input.length()];

        // The number of safe tiles
        int part1 = 0;
        int part2 = 0;

        // Loop through every tile in the first row
        for (int i=0; i<row.length; ++i){
            // Record if it's a trap
            row[i] = input.charAt(i) == '^';
            // Add to numSafe if it's safe
            if (!row[i]){
                ++part2;
            }
        }

        // Loop through every consecutive row
        for (int i=1; i<400000; ++i){
            // Save after 40 rows
            if (i == 40){
                part1 = part2;
            }

            // The next row of tiles
            boolean[] newRow = new boolean[row.length];
            // Each tile is xored for it's previous left and right tiles
            newRow[0] = row[1];
            newRow[newRow.length-1] = row[row.length-2];
            for (int j=1; j<newRow.length-1; ++j){
                newRow[j] = row[j-1] ^ row[j+1];
            }

            // Set the current row to the next row
            row = newRow;

            // Count the number of safe tiles in this new row
            for (int j=0; j<row.length; ++j){
                if (!row[j]){
                    ++part2;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}