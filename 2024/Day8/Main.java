import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 8: Resonant Collinearity";
    public static void main(String[] args){
        // Take in the map of frequencies
        char[][] map = Library.getCharMatrix(args);

        // All unique positions with antinodes
        HashSet<ArrayState> part1 = new HashSet<>();
        HashSet<ArrayState> part2 = new HashSet<>();

        // Loop through each position
        for (int i=0; i<map.length * map[0].length; ++i){
            // Get the coordinates
            int x1 = i % map[0].length;
            int y1 = i / map[0].length;
            // Only pay attention to positions with frequencies
            if (map[y1][x1] == '.'){
                continue;
            }
            // Loop through each subsequent position
            for (int j=i+1; j<map.length * map[0].length; ++j){
                // Get the coordinates
                int x2 = j % map[0].length;
                int y2 = j / map[0].length;
                // Only pay attention to matching frequencies
                if (map[y1][x1] != map[y2][x2]){
                    continue;
                }

                // Get the difference between the coordinates
                int dx = x2 - x1;
                int dy = y2 - y1;

                // Check the location that distance beyond each antenna
                if (x1 - dx >= 0 && x1 - dx < map[0].length
                && y1 - dy >= 0 && y1 - dy < map.length){
                    part1.add(new ArrayState(new int[] {x1-dx,y1-dy}));
                }
                if (x2 + dx >= 0 && x2 + dx < map[0].length
                && y2 + dy >= 0 && y2 + dy < map.length){
                    part1.add(new ArrayState(new int[] {x2+dx,y2+dy}));
                }

                // Decrease the distance to check every in-line position
                long gcd = Library.GCD(dx,dy);
                dx /= gcd;
                dy /= gcd;
                
                // Find the first position in the line
                int x = x1;
                int y = y1;
                while (x - dx >= 0 && x - dx < map[0].length
                && y - dy >= 0 && y - dy < map.length){
                    x -= dx;
                    y -= dy;
                }

                // Add every position on the map that's in that line
                while (x >= 0 && x < map[0].length
                && y >= 0 && y < map.length){
                    part2.add(new ArrayState(new int[] {x,y}));
                    x += dx;
                    y += dy;
                }
            }
        }

        // Print the answer
        Library.print(part1.size(),part2.size(),name);
    }
}