import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.Scanner;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 9: Rope Bridge";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // List to keep track of visited positions
        HashSet<ArrayState> part1 = new HashSet<>();
        HashSet<ArrayState> part2 = new HashSet<>();
        // The array to keep track of the locations of each knot
        int[][] array = new int[10][2];
        // Add the current location as a visited one
        part1.add(new ArrayState(new int[] {0,0}));
        part2.add(new ArrayState(new int[] {0,0}));

        // Loop through all lines of input
        while (sc.hasNext()){
            // Take in the direction
            char dir = sc.next().charAt(0);
            // Take in the number of times moved
            int num = sc.nextInt();

            // Repeat num times
            for (int j=0; j<num; ++j){
                // Move the head in the indicated direction
                switch(dir){
                    case 'U' -> ++array[0][1];
                    case 'R' -> ++array[0][0];
                    case 'D' -> --array[0][1];
                    case 'L' -> --array[0][0];
                }
                
                // Loop through every knot
                for (int k=1; k<array.length; ++k){
                    // If x or y of the previous knot is two spaces away
                    if (Math.abs(array[k][0]-array[k-1][0]) > 1
                     || Math.abs(array[k][1]-array[k-1][1]) > 1){
                        // Move both directions towards the previous knot
                        array[k][0] += (int)Math.signum(array[k-1][0]
                                                      - array[k][0]);
                        array[k][1] += (int)Math.signum(array[k-1][1]
                                                      - array[k][1]);
                    }
                }

                // Add the two positions
                part1.add(new ArrayState(array[1].clone()));
                part2.add(new ArrayState(array[9].clone()));
            }
        }

        // Print the answer
        Library.print(part1.size(),part2.size(),name);
    }
}