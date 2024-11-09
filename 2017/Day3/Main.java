import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 3: Spiral Memory";
    public static void main(String args[]) {
        // Get the input
        int input = Library.getInt(args);
        
        // The answer to the problem
        int part1 = part1(input);
        int part2 = part2(input);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static int part1(int input){
        // The highest root of a square lower than input
        int lower = (int)Math.sqrt(input);
        // The radius of the square
        int steps = lower / 2;
        // The power of the 
        int pow = lower * lower;
        if (lower % 2 == 1){
            // Bottom right corner
            if (pow == input){
                return steps * 2;
            }
            // Move it out one square
            ++steps;
        }else{
            // One right from the top left corner
            if (pow == input){
                return steps * 2 - 1;
            }
            // Move to the top left corner
            ++pow;
        }
        int sideLength = steps * 2;
        // Calculate the closest distance from a corner to the input
        int dist = Math.min(input - pow,Math.min(Math.abs(sideLength+pow-input),pow+sideLength*2-input));
        // Return the distance from input to 1
        return steps + sideLength / 2 - dist;
    }

    private static int part2(int input){
        // The starting position
        int x = 0;
        int y = 0;
        // The starting value
        int i = 1;
        // The radius of the current square
        int side = 1;
        // The hashmap of location values, used for Part 2
        HashMap<ArrayState,Integer> hash = new HashMap<>();
        // Add the starting position's value
        hash.put(new ArrayState(new int[] {0,0}),1);

        // Continue until the value is found
        while (i <= input){
            // Go right
            for (int j=0; j<side && i<=input; ++j){
                ++x;
                i = sumNeighbors(x,y,hash);
            }
            // Go up
            for (int j=0; j<side && i<=input; ++j){
                ++y;
                i = sumNeighbors(x,y,hash);
            }
            ++side;
            // Go left
            for (int j=0; j<side && i<=input; ++j){
                --x;
                i = sumNeighbors(x,y,hash);
            }
            // Go down
            for (int j=0; j<side && i<=input; ++j){
                --y;
                i = sumNeighbors(x,y,hash);
            }
            ++side;
        }

        return i;
    }

    private static int sumNeighbors(int x, int y, HashMap<ArrayState,Integer> hash){
        // The sum of all neighbors
        int sum = 0;
        // Look in each direction
        for (int i=0; i<9; ++i){
            // Skip itself
            if (i == 4){
                continue;
            }
            // Get the coordinates for this neighbor
            int newX = x + i % 3 - 1;
            int newY = y + i / 3 % 3 - 1;
            // Add the value if it exists
            ArrayState newState = new ArrayState(new int[] {newX,newY});
            if (hash.containsKey(newState)){
                sum += hash.get(newState);
            }
        }
        // Save the value
        hash.put(new ArrayState(new int[] {x,y}),sum);
        // Return the sum
        return sum;
    }
}