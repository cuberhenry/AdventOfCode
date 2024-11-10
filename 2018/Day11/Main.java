import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 11: Chronal Charge";
    public static void main(String args[]) {
        // Take in the input
        int serial = Library.getInt(args);
        // Create the grid of power cells
        int[][] grid = new int[300][300];
        for (int i=0; i<300; ++i){
            for (int j=0; j<300; ++j){
                // Initialize each value to its power level
                grid[i][j] = ((j+11)*(i+1)+serial)*(j+11)/100%10-5;
            }
        }

        // Record the maximum amount and location
        int max = 0;
        int x = 0;
        int y = 0;
        int size = 0;

        // Loop through every available 3x3
        for (int i=0; i<297; ++i){
            for (int j=0; j<297; ++j){
                // Get the power level of the 3x3
                int total = grid[i][j] + grid[i][j+1] + grid[i][j+2]
                        + grid[i+1][j] + grid[i+1][j+1] + grid[i+1][j+2]
                        + grid[i+2][j] + grid[i+2][j+1] + grid[i+2][j+2];
                // If it's the new greatest, save it
                if (total > max){
                    max = total;
                    x = j;
                    y = i;
                }
            }
        }

        // Get the answer
        String part1 = (x+1) + "," + (y+1);

        // Loop through every cell as the top left of the square
        for (int i=0; i<300; ++i){
            for (int j=0; j<300; ++j){
                // Save the progressive total for this cell
                int total = 0;
                // Loop through every possible size
                for (int k=0; k<300-Math.max(i,j); ++k){
                    // Add the corner
                    total += grid[i+k][j+k];
                    // Add each edge
                    for (int l=0; l<k; ++l){
                        total += grid[i+l][j+k] + grid[i+k][j+l];
                    }
                    // If it's the new greatest, save it
                    if (total > max){
                        max = total;
                        x = j;
                        y = i;
                        size = k;
                    }
                }
            }
        }

        // Get the answer
        String part2 = (x+1) + "," + (y+1) + "," + (size+1);

        // Print the answer
        Library.print(part1,part2,name);
    }
}