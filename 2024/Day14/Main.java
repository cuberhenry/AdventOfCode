import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 14: Restroom Redoubt";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        // Get the robots' information
        ArrayList<int[]> robots = new ArrayList<>();
        while (sc.hasNext()){
            robots.add(Library.intSplit(sc.nextLine().substring(2),",| v="));
        }

        // The answer to the problem
        long part1 = 1;
        long part2 = 0;
        
        // Continue until the tree is found
        for (int i=0;; ++i){
            // Find the safety factor after 100 seconds
            if (i == 100){
                int[] quadrants = new int[4];
                // Loop through each robot
                for (int[] robot : robots){
                    // Add it to its quadrant
                    if (robot[0] < 50){
                        if (robot[1] < 51){
                            ++quadrants[0];
                        }else if (robot[1] > 51){
                            ++quadrants[1];
                        }
                    }else if (robot[0] > 50){
                        if (robot[1] < 51){
                            ++quadrants[2];
                        }else if (robot[1] > 51){
                            ++quadrants[3];
                        }
                    }
                }
    
                // Find the product of the four quadrants
                for (int quad : quadrants){
                    part1 *= quad;
                }
            }
            
            // The positions of each robot
            HashSet<ArrayState> positions = new HashSet<>();
            // Loop through each robot
            for (int[] robot : robots){
                // Move the robot
                robot[0] = (robot[0] + robot[2] + 101) % 101;
                robot[1] = (robot[1] + robot[3] + 103) % 103;
                // Add the robot's position
                positions.add(new ArrayState(new int[] {robot[0],robot[1]}));
            }

            // The number of robots next to another robot
            int count = 0;
            // Loop through each robot position
            for (ArrayState state : positions){
                // Look in each direction
                for (int j=0; j<8; ++j){
                    int x = state.getArray()[0];
                    int y = state.getArray()[1];
                    switch (j) {
                        case 0 -> --x;
                        case 1 -> {--x; --y;}
                        case 2 -> --y;
                        case 3 -> {++x; --y;}
                        case 4 -> ++x;
                        case 5 -> {++x; ++y;}
                        case 6 -> ++y;
                        case 7 -> {--x; ++y;}
                    }
                    // If touching, add
                    if (positions.contains(new ArrayState(new int[] {x,y}))){
                        ++count;
                        break;
                    }
                }
            }

            // If an arbitrarily high number of robots are touching, that's the tree
            if (count > 350){
                part2 = i+1;
                break;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}