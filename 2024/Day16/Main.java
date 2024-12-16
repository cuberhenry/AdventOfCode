import com.aoc.mylibrary.*;
import java.util.*;

public class Main {
    final private static String name = "Day 16: Reindeer Maze";
    public static void main(String[] args){
        // Take in the map
        char[][] map = Library.getCharMatrix(args);

        // Find the starting coordinates
        int startX = 0;
        int startY = 0;
        for (int i=0; i<map.length; ++i){
            int index = Library.indexOf(map[i],'S');
            if (index != -1){
                startX = index;
                startY = i;
                break;
            }
        }

        // The answer to the problem
        long part1 = Long.MAX_VALUE;
        long part2 = 0;

        // The best score for each position and orientation
        HashMap<ArrayState,Integer> scores = new HashMap<>();
        // The list of positions to search
        LinkedList<ArrayState> queue = new LinkedList<>();

        // The previous states that resulted in this state's best score
        HashMap<ArrayState,ArrayList<ArrayState>> previous = new HashMap<>();

        // Start at S facing right
        ArrayState start = new ArrayState(new int[] {startX,startY,1});
        queue.add(start);
        scores.put(start,0);
        previous.put(start,new ArrayList<>());

        // The list of best states at E
        LinkedList<ArrayState> end = new LinkedList<>();

        // Continue until all best solutions have been solved
        while (!queue.isEmpty()){
            // Get the state
            ArrayState state = queue.remove();
            int score = scores.get(state);

            // Copy the state
            int[] straight = state.getArray();
            // Move forward
            switch(straight[2]){
                case 0 -> --straight[1];
                case 1 -> ++straight[0];
                case 2 -> ++straight[1];
                case 3 -> --straight[0];
            }

            // Copy the state and turn left and right
            int[] right = state.getArray();
            right[2] = (right[2]+1)%4;
            int[] left = state.getArray();
            left[2] = (left[2]+3)%4;

            ArrayState forward = new ArrayState(straight);
            // Make sure you can move forward and it is better than previous attempts if any
            if (map[straight[1]][straight[0]] != '#' && (!scores.containsKey(forward) || scores.get(forward) > score + 1)){
                // Add it as a new best path into the new state
                previous.put(forward,new ArrayList<>());
                previous.get(forward).add(state);
                // If it's the end
                if (map[straight[1]][straight[0]] == 'E'){
                    // If it's the new best score
                    if (part1 > score + 1){
                        // Start over
                        end.clear();
                        end.add(forward);
                    }else if (part1 == score + 1){
                        // Add as another best path
                        end.add(forward);
                    }
                    // Save the best score
                    part1 = Math.min(part1,score + 1);
                }else{
                    // Add the score 
                    scores.put(forward,score+1);
                    queue.add(forward);
                }
            }else if (scores.containsKey(forward) && scores.get(forward) == score + 1){
                // It's a tie, add the previous state
                previous.get(forward).add(state);
            }
            // Turn right
            ArrayState turn = new ArrayState(right);
            if (!scores.containsKey(turn) || scores.get(turn) > score + 1000){
                // Save the new best score for this state
                scores.put(turn,score + 1000);
                queue.add(turn);
                previous.put(turn,new ArrayList<>());
                previous.get(turn).add(state);
            }else if (scores.containsKey(turn) && scores.get(turn) == score + 1000){
                // Add another best path into this state
                previous.get(turn).add(state);
            }
            // Turn left
            turn = new ArrayState(left);
            if (!scores.containsKey(turn) || scores.get(turn) > score + 1000){
                // Save the new best score for this state
                scores.put(turn,score + 1000);
                queue.add(turn);
                previous.put(turn,new ArrayList<>());
                previous.get(turn).add(state);
            }else if (scores.containsKey(turn) && scores.get(turn) == score + 1000){
                // Add another best path into this state
                previous.get(turn).add(state);
            }
        }

        // The list of positions on at least one best path
        HashSet<ArrayState> best = new HashSet<>();

        // Continue backwards until all best paths have been checked
        while (!end.isEmpty()){
            // Get the state
            ArrayState state = end.remove();
            int[] pos = state.getArray();
            // Add the position if it's not already present
            best.add(new ArrayState(new int[] {pos[0],pos[1]}));
            // Add all the states leading into this one
            end.addAll(previous.get(state));
        }

        // The number of positions on a best path
        part2 = best.size();

        // Print the answer
        Library.print(part1,part2,name);
    }
}