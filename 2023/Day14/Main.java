import com.aoc.mylibrary.Library;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 14: Parabolic Reflector Dish";
    public static void main(String args[]) {
        // The map of rocks
        char[][] map = Library.getCharMatrix(args);

        // The total load on the north support beams
        long part1 = 0;
        long part2 = 0;

        // The list of previous situations
        HashMap<String,Integer> history = new HashMap<>();

        // Whether the loop has been found
        boolean loopFound = false;
        // Perform 1 billion cycles
        for (int i=0; i<1000000000; ++i){
            // Tilt North
            // Loop through every column
            for (int j=0; j<map[0].length; ++j){
                // The first open space for a rock to roll into
                int topRock = 0;
                // Loop through every row
                for (int k=0; k<map.length; ++k){
                    // Depending on which rock
                    if (map[k][j] == '#'){
                        // Cube rocks stop round rocks
                        topRock = k+1;
                    }else if (map[k][j] == 'O'){
                        // If the rock isn't already in position
                        if (topRock != k){
                            // Move the rock up
                            map[topRock][j] = 'O';
                            map[k][j] = '.';
                        }
                        // The next available space is down one
                        ++topRock;
                    }
                }
            }
            if (i == 0){
                // Loop through every row
                for (int j=0; j<map.length; ++j){
                    // Loop through every column
                    for (int k=0; k<map[0].length; ++k){
                        // If the rock is round
                        if (map[j][k] == 'O'){
                            // Add to the total load
                            part1 += map.length-j;
                        }
                    }
                }
            }
            // Tilt West
            // Loop through every row
            for (int j=0; j<map.length; ++j){
                // The first open space for a rock to roll into
                int topRock = 0;
                // Loop through every column
                for (int k=0; k<map[0].length; ++k){
                    // Depending on which rock
                    if (map[j][k] == '#'){
                        // Cube rocks stop round rocks
                        topRock = k+1;
                    }else if (map[j][k] == 'O'){
                        // If the rock isn't already in position
                        if (topRock != k){
                            // Move the rock left
                            map[j][topRock] = 'O';
                            map[j][k] = '.';
                        }
                        // The next available space is right one
                        ++topRock;
                    }
                }
            }
            // Tilt South
            // Loop through every column
            for (int j=0; j<map[0].length; ++j){
                // The first open space for a rock to roll into
                int topRock = map.length-1;
                // Loop through every row
                for (int k=map.length-1; k>=0; --k){
                    // Depending on which rock
                    if (map[k][j] == '#'){
                        // Cube rocks stop round rocks
                        topRock = k-1;
                    }else if (map[k][j] == 'O'){
                        // If the rock isn't already in position
                        if (topRock != k){
                            // Move the rock down
                            map[topRock][j] = 'O';
                            map[k][j] = '.';
                        }
                        // The next available space is up one
                        --topRock;
                    }
                }
            }
            // Tilt East
            // Loop through every row
            for (int j=0; j<map.length; ++j){
                // The first open space for a rock to roll into
                int topRock = map.length-1;
                // Loop through every column
                for (int k=map.length-1; k>=0; --k){
                    // Depending on which rock
                    if (map[j][k] == '#'){
                        // Cube rocks stop round rocks
                        topRock = k-1;
                    }else if (map[j][k] == 'O'){
                        // If the rock isn't already in position
                        if (topRock != k){
                            // Move the rock right
                            map[j][topRock] = 'O';
                            map[j][k] = '.';
                        }
                        // The next available space is left one
                        --topRock;
                    }
                }
            }

            // As long as the loop hasn't been found
            if (!loopFound){
                // If there's a repeated situation
                if (history.containsKey(Library.toString(map))){
                    // Set i to the highest iteration of the loop that's still less than a billion
                    i = 999999999 - (999999999 - i) % (history.size() - history.get(Library.toString(map)));
                    // The loop has been found
                    loopFound = true;
                }else{
                    // Add the situation to the history
                    history.put(Library.toString(map),i);
                }
            }
        }

        // Loop through every row
        for (int i=0; i<map.length; ++i){
            // Loop through every column
            for (int j=0; j<map[0].length; ++j){
                // If the rock is round
                if (map[i][j] == 'O'){
                    // Add to the total load
                    part2 += map.length-i;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}