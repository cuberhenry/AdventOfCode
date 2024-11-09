import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import com.aoc.mylibrary.PermutationList;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Main {
    final private static String name = "Day 24: Air Duct Spelunking";
    public static void main(String args[]) {
        // The map of whether each point is accessible
        char[][] map = Library.getCharMatrix(args);
        // The list of destinations
        ArrayList<ArrayState> dests = new ArrayList<>();

        // Loop through the whole map
        for (int i=0; i<map.length; ++i){
            for (int j=0; j<map[i].length; ++j){
                if (Character.isDigit(map[i][j])){
                    if (map[i][j] == '0'){
                        dests.add(0,new ArrayState(new int[] {j,i}));
                    }else{
                        dests.add(new ArrayState(new int[] {j,i}));
                    }
                }
            }
        }

        // Fill in dead ends
        // Loop through every square
        for (int i=1; i<map.length-1; ++i){
            for (int j=1; j<map[0].length-1; ++j){
                // Skip walls, destinations, and your starting point
                if (map[i][j] != '.'){
                    continue;
                }
                // The number of adjacent walls
                int count = 0;
                if (map[i][j-1] == '#'){
                    ++count;
                }
                if (map[i][j+1] == '#'){
                    ++count;
                }
                if (map[i-1][j] == '#'){
                    ++count;
                }
                if (map[i+1][j] == '#'){
                    ++count;
                }

                // If there are three adjacent walls, this is a dead end
                if (count >= 3){
                    // Count it as a wall
                    map[i][j] = '#';
                    // Back up enough to check the surrounding area
                    --i;
                    --j;
                }
            }
        }

        // The distance between every two destinations and the start
        int[][] dists = new int[dests.size()][dests.size()];
        // Loop through every start and destination
        for (int i=0; i<dists.length-1; ++i){
            // All positions visited so far
            HashSet<ArrayState> visited = new HashSet<>();
            // Current positions at j steps
            LinkedList<ArrayState> queue = new LinkedList<>();
            visited.add(dests.get(i));
            queue.add(dests.get(i));
            // The number of destinations found, ignoring previous ones
            int found = dests.size()-i-1;

            // Loop through steps until all destinations have been found
            for (int j=1; found > 0; ++j){
                // The next distance's steps
                LinkedList<ArrayState> newQueue = new LinkedList<>();
                // Continue until all dests are found or through every position
                while (!queue.isEmpty() && found > 0){
                    // Get the position
                    int[] pos = queue.remove().getArray();
                    int x = pos[0];
                    int y = pos[1];

                    // Loop in every direction
                    for (int k=0; k<4; ++k){
                        int newX = x;
                        int newY = y;
                        // Move that direction
                        switch(k){
                            case 0 -> {++newX;}
                            case 1 -> {--newX;}
                            case 2 -> {++newY;}
                            case 3 -> {--newY;}
                        }
                        ArrayState newPos = new ArrayState(new int[] {newX,newY});
                        // If it's an unvisited and non-wall spot
                        if (!visited.contains(newPos) && map[newY][newX] != '#'){
                            // If it's an unfound destination
                            if (dests.indexOf(newPos) > i){
                                // Declare the mininum distance bidirectionally
                                dists[i][dests.indexOf(newPos)] = j;
                                dists[dests.indexOf(newPos)][i] = j;
                                --found;
                            }
                            // Add the new position
                            visited.add(newPos);
                            newQueue.add(newPos);
                        }
                    }
                }

                // Start over with the next distance
                queue = newQueue;
            }
        }

        // Get all permutations 
        PermutationList<Integer> permutations = new PermutationList<>();
        for (int i=1; i<dests.size(); ++i){
            permutations.add(i);
        }

        // The answer to the problem
        int part1 = Integer.MAX_VALUE;
        int part2 = Integer.MAX_VALUE;
        // Loop through every permutation
        for (List<Integer> perm : permutations){
            // Put zero at the front
            perm.add(0,0);
            // Add the distance for each leg of the journey
            int dist = 0;
            for (int i=1; i<perm.size(); ++i){
                dist += dists[perm.get(i-1)][perm.get(i)];
            }

            part1 = Math.min(dist,part1);
            dist += dists[perm.get(perm.size()-1)][0];
            part2 = Math.min(dist,part2);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}