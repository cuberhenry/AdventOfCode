/*
Henry Anderson
Advent of Code 2022 Day 24 https://adventofcode.com/2022/day/24
Input: https://adventofcode.com/2022/day/24/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // The desired problem to solve
    static int PART;
    static Scanner sc;
    // The file containing the puzzle input
    static String FILE_NAME = "input.txt";

    public static void main(String args[]) {
        if (args.length < 1 || args.length > 2){
            System.out.println("Wrong number of arguments");
            return;
        }
        // Take in the part and file name
        try {
            PART = Integer.parseInt(args[0]);
        } catch (Exception e){}
        if (!(PART == 1 || PART == 2)){
            System.out.println("Part can only be 1 or 2");
            return;
        }
        if (args.length == 2){
            FILE_NAME = args[1];
        }
        try {
            sc = new Scanner(new File(FILE_NAME));
        }catch (Exception e){
            System.out.println("File not found");
            return;
        }
        // The dimensions of the grid
        int width = 0;
        int height = 0;
        // The answer to the problem
        int total = 0;

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
                    case '^' -> {
                        blizzards.add(new int[] {i,height,0});
                    }
                    case '>' -> {
                        blizzards.add(new int[] {i,height,1});
                    }
                    case 'v' -> {
                        blizzards.add(new int[] {i,height,2});
                    }
                    case '<' -> {
                        blizzards.add(new int[] {i,height,3});
                    }
                    default -> {}
                }
            }
            // Increase the height
            ++height;
        }

        // For Part 2, which leg of the journey we're on
        int trip = 0;
        
        // The list of states in this time
        ArrayList<String> queue = new ArrayList<>();
        // Add the first state
        queue.add("1 0 0");
        // The amount of time spent so far
        int time = 0;
        // Loop until a break is triggered
        while (true){
            // The list of states for the next time
            ArrayList<String> newQueue = new ArrayList<>();
            // Increase the time
            ++time;
            // Move all of the blizzards
            for (int[] blizzard : blizzards){
                switch (blizzard[2]) {
                    case 0:
                        --blizzard[1];
                        if (blizzard[1] == 0){
                            blizzard[1] = height-2;
                        }   break;
                    case 1:
                        ++blizzard[0];
                        if (blizzard[0] == width-1){
                            blizzard[0] = 1;
                        }   break;
                    case 2:
                        ++blizzard[1];
                        if (blizzard[1] == height-1){
                            blizzard[1] = 1;
                        }   break;
                    case 3:
                        --blizzard[0];
                        if (blizzard[0] == 0){
                            blizzard[0] = width-2;
                        }   break;
                    default:
                        break;
                }
            }
            // Go through all the states
            while (!queue.isEmpty()){
                // Remove and dissect the state
                String[] currState = queue.remove(0).split(" ");
                int x = Integer.parseInt(currState[0]);
                int y = Integer.parseInt(currState[1]);
                // If the final trip has been completed
                if (trip == 2 && x == width-2 && y == height-2){
                    total = time;
                    break;
                }
                // If the first trip has been completed
                if (trip == 0 && x == width-2 && y == height-2){
                    // In Part 1, this is the only trip
                    if (PART == 1){
                        total = time;
                        break;
                    }

                    // Clear the queue and add only one state
                    newQueue.clear();
                    newQueue.add(width-2+" "+(height-1));
                    ++trip;
                    break;
                }
                // If the trip back has been completed
                if (trip == 1 && x == 1 && y == 1){
                    // Clear the queue and add only one state
                    newQueue.clear();
                    newQueue.add("1 0");
                    ++trip;
                    break;
                }

                // The five options from any given state
                boolean good = true;
                boolean upGood = true;
                boolean downGood = true;
                boolean rightGood = true;
                boolean leftGood = true;

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

                // Check for impassable walls to restrict movement
                if (x == 1 || y == height-1){
                    leftGood = false;
                }
                if (x == width-2 || y == 0){
                    rightGood = false;
                }
                if (y == 1 || y == 0){
                    upGood = false;
                }
                if (y == height-2 || y == height-1){
                    downGood = false;
                }

                // Add possible and newly found states to the new queue
                if (good){
                    if (!newQueue.contains(x+" "+y))
                        newQueue.add(x+" "+y);
                }
                if (upGood){
                    if (!newQueue.contains(x+" "+(y-1)))
                        newQueue.add(x+" "+(y-1));
                }
                if (downGood){
                    if (!newQueue.contains(x+" "+(y+1)))
                        newQueue.add(x+" "+(y+1));
                }
                if (rightGood){
                    if (!newQueue.contains(x+1+" "+y))
                        newQueue.add(x+1+" "+y);
                }
                if (leftGood){
                    if (!newQueue.contains(x-1+" "+y))
                        newQueue.add(x-1+" "+y);
                }
            }
            // If an answer has been found, stop looping
            if (total != 0){
                break;
            }

            // Set the queue to newQueue
            queue = newQueue;
        }

        // Print the answer
        System.out.println(total);
    }
}