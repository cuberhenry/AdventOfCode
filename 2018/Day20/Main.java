import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashMap;
import java.util.Stack;

public class Main {
    final private static String name = "Day 20: A Regular Map";
    public static void main(String args[]) {
        // Take in the regular expression
        String regex = Library.getString(args);
        // The distance to each of the crossroads
        Stack<ArrayState> branches = new Stack<>();
        // The distances to each room
        HashMap<ArrayState,Integer> dists = new HashMap<>();
        dists.put(new ArrayState(new int[] {0,0}),0);

        // Your current position
        int x = 0;
        int y = 0;

        // Loop through each character between the bookends
        for (char c : regex.substring(1,regex.length()-1).toCharArray()){
            switch(c){
                // Start a new branch
                case '(' -> {
                    // Save the start of the branch
                    branches.push(new ArrayState(new int[] {x,y}));
                }
                // Switching to a different branch
                case '|' -> {
                    // Return to the start of the branch
                    int[] pos = branches.peek().getArray();
                    x = pos[0];
                    y = pos[1];
                }
                // Ending a branch
                case ')' -> {
                    // Return to the start of the branch
                    int[] pos = branches.pop().getArray();
                    x = pos[0];
                    y = pos[1];
                }
                // Character representing a new room
                default -> {
                    // Look in the given direction
                    int newX = x;
                    int newY = y;
                    switch(c){
                        case 'N' -> {++newY;}
                        case 'S' -> {--newY;}
                        case 'W' -> {--newX;}
                        case 'E' -> {++newX;}
                    }

                    // Save the new move if it doesn't double back
                    ArrayState newState = new ArrayState(new int[] {newX,newY});
                    if (!dists.containsKey(newState)){
                        dists.put(newState,dists.get(new ArrayState(new int[] {x,y})) + 1);
                    }
                    
                    // Move
                    x = newX;
                    y = newY;
                }
            }
        }

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        for (ArrayState key : dists.keySet()){
            part1 = Math.max(part1,dists.get(key));
            if (dists.get(key) >= 1000){
                ++part2;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}