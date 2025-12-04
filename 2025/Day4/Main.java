import com.aoc.mylibrary.Library;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 4: Printing Department";
    public static void main(String[] args){
        char[][] input = Library.getCharMatrix(args);

        // Create a set of all paper for better boundary handling
        HashSet<String> set = new HashSet<>();

        // Add every roll of paper to the set
        for (int i=0; i<input.length; ++i){
            for (int j=0; j<input[i].length; ++j){
                if (input[i][j] == '@'){
                    set.add(i + " " + j);
                }
            }
        }

        // The rolls removable in the first pass
        HashSet<String> firstPass = new HashSet<>();
        // Loop through each valid location
        for (int i=0; i<input.length; ++i){
            for (int j=0; j<input[i].length; ++j){
                // If it's a paper roll
                if (input[i][j] == '@'){
                    // The number of adjacent rolls, -1 to discount itself
                    int count = -1;
                    // Check each location in the 3x3 around this roll for another roll
                    for (int k=i-1; k<i+2; ++k){
                        for (int l=j-1; l<j+2; ++l){
                            if (set.contains(k + " " + l)){
                                ++count;
                            }
                        }
                    }

                    // Rolls must be adjacent to less than 4 other rolls to be accessible
                    if (count < 4){
                        firstPass.add(i + " " + j);
                    }
                }
            }
        }

        // The answer to the problem
        // Part 1 finds the number of removable rolls before removing any
        int part1 = firstPass.size();
        // Part 2 finds the total number of removable rolls while removing rolls
        int part2 = set.size();

        // The number of rolls before the current pass
        int prevSize = part2;
        set.removeAll(firstPass);

        // Continue until no rolls have been removed
        while (set.size() < prevSize){
            prevSize = set.size();
            // Loop through each valid location
            for (int i=0; i<input.length; ++i){
                for (int j=0; j<input[i].length; ++j){
                    // If it's a paper roll that hasn't been removed
                    if (set.contains(i + " " + j)){
                        // The number of adjacent rolls, -1 to discount itself
                        int count = -1;
                        // Check each location in the 3x3 around this roll for another roll
                        for (int k=i-1; k<i+2; ++k){
                            for (int l=j-1; l<j+2; ++l){
                                if (set.contains(k + " " + l)){
                                    ++count;
                                }
                            }
                        }

                        // Rolls must be adjacent to less than 4 other rolls to be accessible
                        if (count < 4){
                            // Remove immediately for maximum efficiency
                            set.remove(i + " " + j);
                        }
                    }
                }
            }
        }

        // Calculate the number of rolls removed since the start
        part2 -= set.size();
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}