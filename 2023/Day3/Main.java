import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 3: Gear Ratios";
    public static void main(String args[]) {
        // Get the schematics
        char[][] grid = Library.getCharMatrix(args);
        // A list of the positions and numbers of all gears
        ArrayList<String> gears = new ArrayList<>();
        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop through every character in the grid
        for (int i=0; i<grid.length; ++i){
            for (int j=0; j<grid[i].length; ++j){
                // Only look for numbers
                if (!Character.isDigit(grid[i][j])){
                    continue;
                }
                // Used for Part 1, whether the number is next to a symbol
                boolean nextToSymbol = false;
                // Used for Part 2, a list of all adjacent gears
                HashSet<String> adjGears = new HashSet<>();
                // The number as a string
                String number = "";

                // Check the position to the left
                if (j > 0 && !Character.isDigit(grid[i][j-1]) && grid[i][j-1] != '.'){
                    nextToSymbol = true;
                }

                // Add up the gear ratios of all gears
                if (j > 0 && grid[i][j-1] == '*'){
                    adjGears.add(i + " " + (j-1));
                }

                // Find the entire number
                while (j < grid[i].length && Character.isDigit(grid[i][j])){
                    // Add the digit
                    number += grid[i][j];
                    // Check to the left for symbols or gears
                    if (j > 0){
                        // Check up and to the left
                        if (i > 0 && !Character.isDigit(grid[i-1][j-1]) && grid[i-1][j-1] != '.'){
                            nextToSymbol = true;
                        // Check down and to the left
                        }else if (i+1 < grid.length && !Character.isDigit(grid[i+1][j-1]) && grid[i+1][j-1] != '.'){
                            nextToSymbol = true;
                        }

                        // Check up and to the left
                        if (i > 0 && grid[i-1][j-1] == '*'){
                            adjGears.add(i-1 + " " + (j-1));
                        }
                        // Check down and to the left
                        if (i+1 < grid.length && grid[i+1][j-1] == '*'){
                            adjGears.add(i+1 + " " + (j-1));
                        }
                    }
                    // See if the number continues
                    ++j;
                }

                // Check the surrounding spots for symbols from the spot after the final digit
                if (!nextToSymbol){
                    // Check above the final digit
                    if (i > 0 && !Character.isDigit(grid[i-1][j-1]) && grid[i-1][j-1] != '.'){
                        nextToSymbol = true;
                    // Check below the final digit
                    }else if (i+1 < grid.length && !Character.isDigit(grid[i+1][j-1]) && grid[i+1][j-1] != '.'){
                        nextToSymbol = true;
                    // Check after the final digit
                    }else if (j < grid[i].length){
                        // Check the next position
                        if (!Character.isDigit(grid[i][j]) && grid[i][j] != '.'){
                            nextToSymbol = true;
                        // Check up
                        }else if (i > 0 && !Character.isDigit(grid[i-1][j]) && grid[i-1][j] != '.'){
                            nextToSymbol = true;
                        // Check down
                        }else if (i+1 < grid.length && !Character.isDigit(grid[i+1][j]) && grid[i+1][j] != '.'){
                            nextToSymbol = true;
                        }
                    }
                }

                // Add the number if it is a part
                if (nextToSymbol){
                    part1 += Integer.parseInt(number);
                }

                // Check the surrounding spots for symbols from the spot after the final digit
                // Check above the final digit
                if (i > 0 && grid[i-1][j-1] == '*'){
                    adjGears.add(i-1 + " " + (j-1));
                }
                // Check below the final digit
                if (i+1 < grid.length && grid[i+1][j-1] == '*'){
                    adjGears.add(i+1 + " " + (j-1));
                }
                // Check after the final digit
                if (j < grid[i].length){
                    // Check the next position
                    if (grid[i][j] == '*'){
                        adjGears.add(i + " " + j);
                    }
                    // Check up
                    if (i > 0 && grid[i-1][j] == '*'){
                        adjGears.add(i-1 + " " + j);
                    }
                    // Check down
                    if (i+1 < grid.length && grid[i+1][j] == '*'){
                        adjGears.add(i+1 + " " + j);
                    }
                }

                // Loop through every gear adjacent to the number
                for (String current : adjGears){
                    // Whether the gear has already been found
                    boolean found = false;
                    // Loop through all previously existing gears
                    for (int k=0; k<gears.size(); ++k){
                        // The current gear
                        String gear = gears.get(k);
                        // If the adjacent gear is at the same position as the existing gear
                        if (current.split(" ")[0].equals(gear.split(" ")[0]) && current.split(" ")[1].equals(gear.split(" ")[1])){
                            // Add the current number as adjacent to the gear
                            gear += " " + number;
                            gears.set(k,gear);
                            found = true;
                            // Stop searching
                            break;
                        }
                    }
                    // If the gear had not been found
                    if (!found){
                        // Add the new gear
                        gears.add(current + " " + number);
                    }
                }
            }
        }

        // Add up the gear ratios
        for (String gear : gears){
            // Gears are only adjacent to exactly two numbers
            if (gear.split(" ").length != 4){
                continue;
            }
            String[] split = gear.split(" ");
            // Add the gear ratio
            part2 += Integer.parseInt(split[2]) * Integer.parseInt(split[3]);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}