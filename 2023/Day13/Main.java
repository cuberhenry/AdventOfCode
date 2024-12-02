import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 13: Point of Incidence";
    public static void main(String args[]) {
        char[][][] grids = Library.getCharTensor(args,"\n\n");

        // The number of rows above the mirror lines
        int rows1 = 0;
        int rows2 = 0;
        // The number of columns left of the mirror lines
        int cols1 = 0;
        int cols2 = 0;

        // Loop through each grid
        for (char[][] grid : grids){
            // Whether the mirror line has been found
            boolean found1 = false;
            boolean found2 = false;
            // Mirror lines imply that the first or last will match something else
            // Look for a horizontal mirror line
            for (int i=0; i<grid.length && !(found1 && found2); i+= grid.length-1){
                // Search through every other row
                for (int j=0; j<grid.length && !(found1 && found2); ++j){
                    // Mirror lines are only found between two rows
                    if (i == j || Math.abs(i-j)%2 != 1){
                        continue;
                    }
                    // The number of differences between the rows
                    int count = 0;
                    // Loop through every pair of rows between i and j
                    for (int k=0; k<=Math.abs(j-i)/2 && count <= 1; ++k){
                        // Loop through every pair of characters in the rows
                        for (int l=0; l<grid[0].length && count <= 1; ++l){
                            // If the two characters don't match
                            if (grid[Math.max(i,j)-k][l] != grid[Math.min(i,j)+k][l]){
                                // Increase the number of mistakes
                                ++count;
                            }
                        }
                    }
                    // If the mirror line has been found
                    if (!found1 && count == 0){
                        // Mark it as found
                        found1 = true;
                        // Increase the number of rows
                        rows1 += (Math.abs(j-i)/2+Math.min(i,j)+1);
                    }else if (!found2 && count == 1){
                        // Mark it as found
                        found2 = true;
                        // Increase the number of rows
                        rows2 += (Math.abs(j-i)/2+Math.min(i,j)+1);
                    }
                }
            }
            // Look for a vertical mirror line
            for (int i=0; i<grid[0].length && !(found1 && found2); i+=grid[0].length-1){
                // Search through every column
                for (int j=0; j<grid[0].length && !(found1 && found2); ++j){
                    // Mirror lines are only found between two columns
                    if (i == j || Math.abs(i-j)%2 != 1){
                        continue;
                    }
                    // The number of differences between the rows
                    int count = 0;
                    // Loop through every pair of columns between i and j
                    for (int k=0; k<=Math.abs(j-i)/2 && count <= 1; ++k){
                        // Loop through every pair of characters in the columns
                        for (int l=0; l<grid.length && count <= 1; ++l){
                            // If the two characters don't match
                            if (grid[l][Math.max(i,j)-k] != grid[l][Math.min(i,j)+k]){
                                // Increase the number of mistakes
                                ++count;
                            }
                        }
                    }
                    // If the mirror line has been found
                    if (!found1 && count == 0){
                        // Mark it as found
                        found1 = true;
                        // Increase the number of columns
                        cols1 += (Math.abs(j-i)/2+Math.min(i,j)+1);
                    }else if (!found2 && count == 1){
                        // Mark it as found
                        found2 = true;
                        // Increase the number of columns
                        cols2 += (Math.abs(j-i)/2+Math.min(i,j)+1);
                    }
                }
            }
        }

        // The answer to the problem
        int part1 = 100 * rows1 + cols1;
        int part2 = 100 * rows2 + cols2;

        // Print the answer
        Library.print(part1,part2,name);
    }
}