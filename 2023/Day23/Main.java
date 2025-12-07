import com.aoc.mylibrary.ArrayState;
import com.aoc.mylibrary.Library;
import java.util.HashMap;
import java.util.Stack;

public class Main {
    final private static String name = "Day 23: A Long Walk";
    public static void main(String args[]) {
        // Get the map of hiking trails
        char[][] map = Library.getCharMatrix(args);

        // The distance between adjacent intersections
        HashMap<ArrayState,HashMap<ArrayState,Integer>> dists = new HashMap<>();
        // Find the two most important positions
        ArrayState startingPos = null;
        ArrayState endingPos = null;
        for (int i=1; i<map[0].length-1; ++i){
            if (map[0][i] == '.'){
                startingPos = new ArrayState(new int[] {i,0});
                dists.put(startingPos,new HashMap<ArrayState,Integer>());
            }
            if (map[map.length-1][i] == '.'){
                endingPos = new ArrayState(new int[] {i,map.length-1});
                dists.put(endingPos,new HashMap<ArrayState,Integer>());
            }
        }
        // Add all of the other intersections
        for (int i=1; i<map.length-1; ++i){
            for (int j=1; j<map[i].length-1; ++j){
                if (map[i][j] == '.' && map[i-1][j] != '.'
                 && map[i+1][j] != '.' && map[i][j+1] != '.' && map[i][j-1] != '.'){
                    dists.put(new ArrayState(new int[] {j,i}),new HashMap<ArrayState,Integer>());
                }
            }
        }

        // Loop through each intersection to find adjacent intersections
        for (ArrayState intersection : dists.keySet()){
            // End doesn't have adjacent intersections
            if (intersection == endingPos){
                continue;
            }
            int[] pos = intersection.getArray();
            // Look in each direction
            for (int i=0; i<4; ++i){
                int prevX = pos[0];
                int prevY = pos[1];

                int x = prevX;
                int y = prevY;

                switch(i){
                    case 0 -> ++x;
                    case 1 -> --x;
                    case 2 -> ++y;
                    case 3 -> --y;
                }

                // Don't go backwards from the starting position
                if (y < 0){
                    continue;
                }

                // Don't go up steep slopes
                if (map[y][x] == switch(i){
                    case 0 -> '>';
                    case 1 -> '<';
                    case 2 -> 'v';
                    default -> '^';
                } || intersection == startingPos && map[y][x] == '.'){
                    // The distance between the two intersections
                    int dist = 1;

                    // Continue until hitting an intersection
                    while (!dists.containsKey(new ArrayState(new int[] {x,y}))){
                        for (int j=0; j<4; ++j){
                            int nextX = x;
                            int nextY = y;

                            // Look in each direction
                            switch(j){
                                case 0 -> ++nextX;
                                case 1 -> --nextX;
                                case 2 -> ++nextY;
                                case 3 -> --nextY;
                            }

                            // Don't go backwards or into a wall
                            if ((nextX == prevX && nextY == prevY) || map[nextY][nextX] == '#'){
                                continue;
                            }

                            // Move
                            ++dist;
                            prevX = x;
                            prevY = y;
                            x = nextX;
                            y = nextY;

                            break;
                        }
                    }

                    // Add the distance
                    dists.get(intersection).put(new ArrayState(new int[] {x,y}),dist);
                }
            }
        }

        // The intersections taken in order
        Stack<ArrayState> stack = new Stack<>();
        stack.push(startingPos);
        // Part 1 finds the longest path not going up steep slopes
        int part1 = search(stack,endingPos,dists,0);

        // Make each distance bidirectional
        for (ArrayState intersection : dists.keySet()){
            for (ArrayState other : dists.get(intersection).keySet()){
                dists.get(other).put(intersection,dists.get(intersection).get(other));
            }
        }

        // Start over at the beginning
        stack.clear();
        stack.push(startingPos);
        // Part 2 finds the longest path with going up steep slopes
        int part2 = search(stack,endingPos,dists,0);

        // Print the answer
        Library.print(part1,part2,name);
    }

    public static int search(Stack<ArrayState> stack, ArrayState end, HashMap<ArrayState,HashMap<ArrayState,Integer>> dists, int dist){
        // The maximum distance from this path so far
        int max = 0;
        ArrayState currPos = stack.peek();

        // Loop through each adjacent intersection
        for (ArrayState intersection : dists.get(currPos).keySet()){
            // If this is the end, return the path distance
            if (intersection.equals(end)){
                return Math.max(max,dist + dists.get(currPos).get(intersection));
            // Don't repeat intersections
            }else if (stack.contains(intersection)){
                continue;
            // If this is the intersection adjacent to the end, don't do anything other than go to the end
            }else if (!dists.get(end).isEmpty() && currPos.equals(dists.get(end).keySet().toArray()[0])){
                break;
            }else{
                // Add this intersection to the path
                stack.push(intersection);
                // Continue the search from the new location
                max = Math.max(max,search(stack,end,dists,dist + dists.get(currPos).get(intersection)));
                stack.pop();
            }
        }
        // Return the maximum distance
        return max;
    }
}