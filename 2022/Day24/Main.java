import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Main {
    final private static String name = "Day 24: Blizzard Basin";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The dimensions of the grid
        int width = 0;
        int height = 0;
        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // The list of all the blizzards
        ArrayList<int[]> blizzards = new ArrayList<>();

        // Take in all the input
        while (sc.hasNext()){
            String line = sc.nextLine();
            width = line.length();
            // Find any blizzards
            for (int i=0; i<line.length(); ++i){
                // Add the blizzard with the correct direction to the list
                switch (line.charAt(i)) {
                    case '^' -> blizzards.add(new int[] {i,height,0});
                    case '>' -> blizzards.add(new int[] {i,height,1});
                    case 'v' -> blizzards.add(new int[] {i,height,2});
                    case '<' -> blizzards.add(new int[] {i,height,3});
                }
            }
            // Increase the height
            ++height;
        }

        // For Part 2, which leg of the journey we're on
        int trip = 0;
        
        // The list of states in this time
        LinkedHashSet<ArrayState> queue = new LinkedHashSet<>();
        // Add the first state
        queue.add(new ArrayState(new int[] {1,0}));
        // The amount of time spent so far
        int time = 0;
        // Loop until a break is triggered
        while (true){
            // The list of states for the next time
            LinkedHashSet<ArrayState> newQueue = new LinkedHashSet<>();
            // Increase the time
            ++time;
            // Move all of the blizzards
            for (int[] blizzard : blizzards){
                switch (blizzard[2]) {
                    case 0 -> {
                        --blizzard[1];
                        if (blizzard[1] == 0){
                            blizzard[1] = height-2;
                        }
                    }
                    case 1 -> {
                        ++blizzard[0];
                        if (blizzard[0] == width-1){
                            blizzard[0] = 1;
                        }
                    }
                    case 2 -> {
                        ++blizzard[1];
                        if (blizzard[1] == height-1){
                            blizzard[1] = 1;
                        }
                    }
                    case 3 -> {
                        --blizzard[0];
                        if (blizzard[0] == 0){
                            blizzard[0] = width-2;
                        }
                    }
                }
            }
            // Go through all the states
            while (!queue.isEmpty()){
                // Remove and dissect the state
                int[] currState = queue.removeFirst().getArray();
                int x = currState[0];
                int y = currState[1];
                // If the final trip has been completed
                if (trip == 2 && x == width-2 && y == height-2){
                    part2 = time;
                    break;
                }
                // If the first trip has been completed
                if (trip == 0 && x == width-2 && y == height-2){
                    part1 = time;

                    // Clear the queue and add only one state
                    newQueue.clear();
                    newQueue.add(new ArrayState(new int[] {width-2,(height-1)}));
                    ++trip;
                    break;
                }
                // If the trip back has been completed
                if (trip == 1 && x == 1 && y == 1){
                    // Clear the queue and add only one state
                    newQueue.clear();
                    newQueue.add(new ArrayState(new int[] {1,0}));
                    ++trip;
                    break;
                }

                // The five options from any given state
                boolean good = true;
                boolean upGood = y > 1;
                boolean downGood = y < height-2;
                boolean rightGood = x != width-2 && y != 0;
                boolean leftGood = x != 1 && y != height-1;

                // Loop through all blizzards
                for (int[] blizzard : blizzards){
                    // If no directions exist, break
                    if (!(good || upGood || downGood || rightGood || leftGood)){
                        break;
                    }
                    // If the blizzard is in the row
                    if (blizzard[0] == x){
                        // If the blizzard is in the same column
                        if (blizzard[1] == y){
                            // Can't stay still
                            good = false;
                        // If the blizzard is to the north
                        }else if (blizzard[1] + 1 == y){
                            // Can't go up
                            upGood = false;
                        // If the blizzard is to the south
                        }else if (blizzard[1] - 1 == y){
                            // Can't go down
                            downGood = false;
                        }
                    // If the blizzard is in the column
                    }else if (blizzard[1] == y){
                        // If the blizzard is to the west
                        if (blizzard[0] + 1 == x){
                            // Can't go left
                            leftGood = false;
                        // If the blizzard is to the east
                        }else if (blizzard[0] - 1 == x){
                            // Can't go right
                            rightGood = false;
                        }
                    }
                }

                // Add possible and newly found states to the new queue
                if (good){
                    newQueue.add(new ArrayState(new int[] {x,y}));
                }
                if (upGood){
                    newQueue.add(new ArrayState(new int[] {x,y-1}));
                }
                if (downGood){
                    newQueue.add(new ArrayState(new int[] {x,y+1}));
                }
                if (rightGood){
                    newQueue.add(new ArrayState(new int[] {x+1,y}));
                }
                if (leftGood){
                    newQueue.add(new ArrayState(new int[] {x-1,y}));
                }
            }
            // If an answer has been found, stop looping
            if (part2 != 0){
                break;
            }

            // Set the queue to newQueue
            queue = newQueue;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}