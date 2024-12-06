import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 6: Guard Gallivant";
    public static void main(String[] args){
        // Take in the starting map
        char[][] map = Library.getCharMatrix(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // The starting coordinates of the guard
        int startX = 0;
        int startY = 0;
        for (int i=0; i<map.length; ++i){
            int index = Library.indexOf(map[i],'^');
            if (index > -1){
                startY = i;
                startX = index;
                break;
            }
        }

        // Every position the guard visits
        HashSet<ArrayState> positions = new HashSet<>();

        {
            // Track the guards position
            int x = startX;
            int y = startY;
            int dir = 0;

            // Continue until broken by the guard leaving the area
            while (true){
                // Look in the given direction
                int newX = x;
                int newY = y;
                switch(dir){
                    case 0 -> --newY;
                    case 1 -> ++newX;
                    case 2 -> ++newY;
                    case 3 -> --newX;
                }

                // Break if the guard would exit the area
                if (newX < 0 || newY < 0 || newX == map[0].length || newY == map.length){
                    break;
                }

                // Turn if necessary
                if (map[newY][newX] == '#'){
                    dir = (dir + 1) % 4;
                    continue;
                }

                // Move to the new spot
                x = newX;
                y = newY;

                // Add the new position
                positions.add(new ArrayState(new int[] {x,y}));
            }
        }

        // Save the number of unique positions visited
        part1 = positions.size() + 1;

        // Loop through each position
        for (ArrayState position : positions){
            // Get the initial position
            int[] pos = position.getArray();
            // Put an obstacle there
            map[pos[1]][pos[0]] = '#';

            // The list of visited positions to find a loop
            HashSet<ArrayState> history = new HashSet<>();

            // Get the starting position
            int x = startX;
            int y = startY;
            int dir = 0;

            // Continue until the position has already been visited
            while (!history.contains(new ArrayState(new int[] {x,y,dir}))){
                // Add the current position
                history.add(new ArrayState(new int[] {x,y,dir}));
                // Look in the given direction
                int newX = x;
                int newY = y;
                switch(dir){
                    case 0 -> --newY;
                    case 1 -> ++newX;
                    case 2 -> ++newY;
                    case 3 -> --newX;
                }

                // Break if exiting the area
                if (newX < 0 || newY < 0 || newX == map[0].length || newY == map.length){
                    x = newX;
                    y = newY;
                    break;
                }

                // Turn if necessary
                if (map[newY][newX] == '#'){
                    dir = (dir + 1) % 4;
                    continue;
                }

                // Move to the new spot
                x = newX;
                y = newY;
            }

            // If a cycle was found, count it
            if (history.contains(new ArrayState(new int[] {x,y,dir}))){
                ++part2;
            }

            // Revert the obstacle to an empty spot
            map[pos[1]][pos[0]] = '.';
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}