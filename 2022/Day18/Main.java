import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.Scanner;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 18: Boiling Boulders";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The input as parsed integers
        HashSet<ArrayState> droplets = new HashSet<>();
        // Empty locations outside the droplet formation
        HashSet<ArrayState> outside = new HashSet<>();
        // The maximum coordinate value
        int max = 0;

        // Take in all lines of input
        while (sc.hasNext()){
            // Parse the line of input
            int[] line = Library.intSplit(sc.nextLine(),",");
            // Update max
            max = Math.max(Math.max(line[0],line[1]),Math.max(line[2],max));
            droplets.add(new ArrayState(line));
        }

        ++max;

        // Find all spots that are outside
        ArrayState initialState = new ArrayState(new int[] {-1,-1,-1});
        outside.add(initialState);
        // Breadth First Search
        LinkedList<ArrayState> queue = new LinkedList<>();
        queue.add(initialState);
        while (!queue.isEmpty()){
            // Get the coordinates
            int[] coords = queue.remove().getArray();
                                    
            // Look in each direction
            for (int i=0; i<6; ++i){
                int x = coords[0];
                int y = coords[1];
                int z = coords[2];
                switch(i){
                    case 0 -> ++x;
                    case 1 -> --x;
                    case 2 -> --y;
                    case 3 -> ++y;
                    case 4 -> ++z;
                    case 5 -> --z;
                }
                
                // Don't go unnecessarily far out
                if (x < -1 || y < -1 || z < -1 || x > max || y > max || z > max){
                    continue;
                }
                ArrayState newState = new ArrayState(new int[] {x,y,z});
                // Ensure this state hasn't already been found
                if (droplets.contains(newState) || outside.contains(newState)){
                    continue;
                }
                // Search it
                queue.add(newState);
                outside.add(newState);
            }
        }

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        
        // Check every square
        for (int i=-1; i<=max; ++i){
            for (int j=0; j<=max; ++j){
                for (int k=(i+j)%2; k<=max; k+=2){
                    ArrayState state = new ArrayState(new int[] {i,j,k});
                    // Look in each direction
                    for (int l=0; l<6; ++l){
                        int x = i;
                        int y = j;
                        int z = k;
                        switch(l){
                            case 0 -> --x;
                            case 1 -> ++x;
                            case 2 -> --y;
                            case 3 -> ++y;
                            case 4 -> --z;
                            case 5 -> ++z;
                        }
                        ArrayState other = new ArrayState(new int[] {x,y,z});
                        // If one is a droplet and the other isn't
                        if (droplets.contains(state) ^ droplets.contains(other)){
                            ++part1;
                            // If the non-droplet is outside
                            if (outside.contains(state) || outside.contains(other)){
                                ++part2;
                            }
                        }
                    }
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}