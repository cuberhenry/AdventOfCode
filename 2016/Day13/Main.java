import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 13: A Maze of Twisty Little Cubicles";
    public static void main(String args[]) {
        // The input
        int number = Library.getInt(args);
        // The breadth first search queue
        HashSet<ArrayState> history = new HashSet<>();
        LinkedList<int[]> queue = new LinkedList<>();
        // Add the starting position
        queue.add(new int[] {1,1,0});

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop through every position until a break point is found
        while (!queue.isEmpty()){
            // Grab the position and disect it
            int[] position = queue.remove();
            int a = position[0];
            int b = position[1];
            int dist = position[2];

            if (dist < 50){
                ++part2;
            }else if (part1 != 0){
                // If both problems have been solved, break
                break;
            }

            // Loop through every direction
            for (int j=0; j<4; ++j){
                // Copy the x and y
                int x = a;
                int y = b;
                // Change direction based on j
                switch (j){
                    case 0 -> --x;
                    case 1 -> --y;
                    case 2 -> ++x;
                    case 3 -> ++y;
                }

                // Non-negative rooms
                if (x < 0 || y < 0){
                    continue;
                }

                // Part 1's desired location
                if (x == 31 && y == 39){
                    part1 = dist + 1;
                }

                // The value to examine
                int val = x*x + 3*x + 2*x*y + y + y*y + number;
                // Whether the value contains an even number of 1's in binary
                boolean even = true;
                // Decipher how many 1's are in the number's binary representation
                while (val > 0){
                    // If there's a one, swap even
                    if (val % 2 == 1){
                        even = !even;
                    }
                    // Divide by 2
                    val >>= 1;
                }

                // If it's an open spot
                if (even){
                    // The new position
                    ArrayState state = new ArrayState(new int[] {x,y});
                    // If it's unvisited
                    if (!history.contains(state)){
                        // Look in that direction
                        history.add(state);
                        queue.add(new int[] {x,y,dist+1});
                    }
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}