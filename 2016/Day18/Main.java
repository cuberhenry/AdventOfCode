import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 18: Like a Rogue";
    public static void main(String args[]) {
        // Whether each tile in the current row is a trap
        boolean[] row = Library.getBooleanArray(args,'^');

        // The number of safe tiles
        int part1 = 0;
        int part2 = row.length - Library.count(row);

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