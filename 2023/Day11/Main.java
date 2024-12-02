import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 11: Cosmic Expansion";
    public static void main(String args[]) {
        // The given map of galaxies
        char[][] map = Library.getCharMatrix(args);
        // The sum of the distances between every pair of galaxies
        long part1 = 0;
        long part2 = 0;

        // The list of empty rows and columns
        HashSet<Integer> emptyRow = new HashSet<>();
        HashSet<Integer> emptyCol = new HashSet<>();
        // The list of locations of galaxies
        ArrayList<int[]> galaxies = new ArrayList<>();

        // Take in the entire map
        for (int i=0; i<map.length; ++i){
            // Add any found empty lines
            if (Library.indexOf(map[i],'#') == -1){
                emptyRow.add(i);
            }
        }

        // Search through every column
        for (int i=0; i<map[0].length; ++i){
            boolean empty = true;
            for (int j=0; j<map.length; ++j){
                if (map[j][i] == '#'){
                    galaxies.add(new int[] {i,j});
                    empty = false;
                }
            }
            // Add the column if it's empty
            if (empty){
                emptyCol.add(i);
            }
        }

        // Loop through every galaxy
        for (int i=0; i<galaxies.size(); ++i){
            // Collect its coordinates
            int[] galaxy = galaxies.get(i);
            int x1 = galaxy[0];
            int y1 = galaxy[1];
            // Loop through every subsequent galaxy
            for (int j=i+1; j<galaxies.size(); ++j){
                // Collect its coordinates
                galaxy = galaxies.get(j);
                int x2 = galaxy[0];
                int y2 = galaxy[1];

                // Add the base distance between the two galaxies
                part1 += Math.abs(x1-x2) + Math.abs(y1-y2);
                part2 += Math.abs(x1-x2) + Math.abs(y1-y2);
                // Loop through every column
                for (int k=Math.min(x1,x2)+1; k<Math.max(x1,x2); ++k){
                    if (emptyCol.contains(k)){
                        ++part1;
                        part2 += 999999;
                    }
                }
                // Loop through every row
                for (int k=Math.min(y1,y2)+1; k<Math.max(y1,y2); ++k){
                    if (emptyRow.contains(k)){
                        ++part1;
                        part2 += 999999;
                    }
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}