import com.aoc.mylibrary.Library;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 17: Spinlock";
    public static void main(String args[]) {
        // The number of steps to move forward each time
        int steps = Library.getInt(args);
        // The current pointer
        int position = 0;

        // Create a data structure
        ArrayList<Integer> spinlock = new ArrayList<>();
        // Add the initial value
        spinlock.add(0);

        // Loop through every value to add
        for (int i=1; i<=2017; ++i){
            // Move forward step number of values
            position = (position + steps) % spinlock.size() + 1;
            // Insert the new value
            spinlock.add(position,i);
        }

        // The answer to the problem
        int part1 = spinlock.get((spinlock.indexOf(2017) + 1) % spinlock.size());
        int part2 = spinlock.get(1);

        // The size of the structure
        int size = 2018;

        // Loop through every value to add
        for (int i=2018; i<=50_000_000; ++i){
            // Move forward step number of values
            position = (position + steps) % size + 1;
            // Increase the size
            ++size;
            // Update the second value if applicable
            if (position == 1){
                part2 = i;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}