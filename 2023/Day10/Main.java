import com.aoc.mylibrary.Library;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 10: Pipe Maze";
    public static void main(String args[]) {
        // The map of pipes
        char[][] map = Library.getCharMatrix(args);
        // The coordinates of the beginning of the loop
        int startX = 0;
        int startY;
        for (startY=0; startY<map.length; ++startY){
            int index = Library.indexOf(map[startY],'S');
            if (index != -1){
                startX = index;
                break;
            }
        }

        // The list of pipes that are part of the loop
        HashSet<String> locations = new HashSet<>();
        locations.add(startY + " " + startX);

        // The starting position and orientation
        int x = startX;
        int y = startY;
        int dir;

        // Checking for connections in three directions
        boolean up = y > 0 && "F7|".contains(""+map[y-1][x]);
        boolean left = x > 0 && "FL-".contains(""+map[y][x-1]);
        boolean down = y < map.length-1 && "LJ|".contains(""+map[y+1][x]);

        // Check each direction
        if (up){
            // Start by going up
            --y;
            dir = 0;

            // Set the starting location to the correct pipe
            if (left){
                map[startY][startX] = 'J';
            }else if (down){
                map[startY][startX] = '|';
            }else{
                map[startY][startX] = 'L';
            }
        }else if (left){
            // Start by going left
            --x;
            dir = 3;
            
            // Set the starting location to the correct pipe
            if (down){
                map[startY][startX] = '7';
            }else{
                map[startY][startX] = '-';
            }
        }else{
            // Start by going down
            ++y;
            dir = 2;

            // Set the starting location to the correct pipe
            map[startY][startX] = 'F';
        }

        // Continue until the entire loop has been found
        while (y != startY || x != startX){
            // Add the new location
            locations.add(y + " " + x);
            // Turn
            switch (map[y][x]){
                case 'F' -> dir = dir == 0 ? 1 : 2;
                case 'L' -> dir = dir == 2 ? 1 : 0;
                case 'J' -> dir = dir == 1 ? 0 : 3;
                case '7' -> dir = dir == 0 ? 3 : 2;
            }
            // Move forward
            switch (dir){
                case 0 -> --y;
                case 1 -> ++x;
                case 2 -> ++y;
                case 3 -> --x;
            }
        }

        // The answer to the problem
        int part1 = locations.size() / 2;
        int part2 = 0;

        // Loop through only the inner rows
        for (int i=1; i<map.length-1; ++i){
            // Whether the current location is inside the loop
            boolean inside = false;
            // Loop through every character
            for (int j=0; j<map[i].length-1; ++j){
                // If the location is part of the loop
                if (locations.contains(i + " " + j)){
                    // If the pipe is vertically straight
                    if (map[i][j] == '|'){
                        // Guaranteed switch between inside and outside
                        inside = !inside;
                    }else{
                        // Save the pipe shape
                        char letter = map[i][j];
                        // Search for the end of the straight pipe connection
                        ++j;
                        while (map[i][j] == '-'){
                            ++j;
                        }
                        // The pipe shape that ends the straight pipe connection
                        char newL = map[i][j];
                        // If the pipe corners go opposite directions
                        if (letter == 'F' && newL == 'J'
                            || letter == 'L' && newL == '7'){
                            // Switch between inside and outside
                            inside = !inside;
                        }
                    }
                }else if (inside){
                    // An inner tile found
                    ++part2;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}