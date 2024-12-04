import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 4: Ceres Search";
    public static void main(String[] args){
        // Take in the word search
        char[][] grid = Library.getCharMatrix(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop through each character in the word search
        for (int i=0; i<grid.length; ++i){
            for (int j=0; j<grid[0].length; ++j){
                // If it's the beginning of XMAS
                if (grid[i][j] == 'X'){
                    // Look in each direction
                    for (int k=0; k<8; ++k){
                        int x = j;
                        int y = i;
                        // Whether it spells XMAS
                        boolean good = true;
                        // Loop through each letter
                        for (int l=0; l<3; ++l){
                            // Move one character in that direction
                            switch(k){
                                case 0 -> ++x;
                                case 1 -> {++x; ++y;}
                                case 2 -> ++y;
                                case 3 -> {--x; ++y;}
                                case 4 -> --x;
                                case 5 -> {--x; --y;}
                                case 6 -> --y;
                                case 7 -> {++x; --y;}
                            }

                            // Quit if it falls off the edge
                            if (x < 0 || y < 0 || x == grid[0].length || y == grid.length){
                                good = false;
                                break;
                            }
                            // Quit if it doesn't match the correct letter
                            if (grid[y][x] != switch(l) {
                                case 0 -> 'M';
                                case 1 -> 'A';
                                default -> 'S';
                            }){
                                good = false;
                                break;
                            }
                        }

                        // Count if it spells the word
                        if (good){
                            ++part1;
                        }
                    }
                // Count if it's an A not on the corner and it's the center of to MAS crosses
                }else if (grid[i][j] == 'A' && i > 0 && j > 0 && i < grid.length-1 && j < grid[0].length-1
                && (grid[i-1][j-1] == 'M' && grid[i+1][j+1] == 'S' || grid[i-1][j-1] == 'S' && grid[i+1][j+1] == 'M')
                && (grid[i-1][j+1] == 'M' && grid[i+1][j-1] == 'S' || grid[i-1][j+1] == 'S' && grid[i+1][j-1] == 'M')){
                    ++part2;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}