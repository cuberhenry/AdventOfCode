import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 17: No Such Thing as Too Much";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // All of the containers
        ArrayList<Integer> containers = new ArrayList<>();
        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Add all containers to the list
        while (sc.hasNext()){
            containers.add(sc.nextInt());
        }

        // The current minimum number of containers
        int min = Integer.MAX_VALUE;

        // Loop through a number for every possible combination
        for (int i=0; i<(int)Math.pow(2,containers.size()); ++i){
            // The number of liters required filled with the given combination
            int liters = 0;
            // The number of containers used
            int numContainers = 0;
            int num = i;
            // Loop through every contiainer
            for (int j=0; num > 0; ++j){
                if (num % 2 == 1){
                    ++numContainers;
                    liters += containers.get(j);
                }
                num /= 2;
            }
            
            // If this combination perfectly contains 150 liters
            if (liters == 150){
                // Increase the total
                ++part1;

                // If it uses the minimum number of containers
                if (min == numContainers){
                    // Increase the total
                    ++part2;
                // If it uses a new minimum number
                }else if (min > numContainers){
                    // Reset the count
                    part2 = 1;
                    // Save the new min
                    min = numContainers;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}