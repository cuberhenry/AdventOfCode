/*
Henry Anderson
Advent of Code 2015 Day 24 https://adventofcode.com/2015/day/24
Input: https://adventofcode.com/2015/day/24/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // The desired problem to solve
    static int PART;
    static Scanner sc;
    // The file containing the puzzle input
    static String FILE_NAME = "input.txt";
    public static void main(String args[]) {
        if (args.length < 1 || args.length > 2){
            System.out.println("Wrong number of arguments");
            return;
        }
        // Take in the part and file name
        try {
            PART = Integer.parseInt(args[0]);
        } catch (Exception e){}
        if (!(PART == 1 || PART == 2)){
            System.out.println("Part can only be 1 or 2");
            return;
        }
        if (args.length == 2){
            FILE_NAME = args[1];
        }
        try {
            sc = new Scanner(new File(FILE_NAME));
        }catch (Exception e){
            System.out.println("File not found");
            return;
        }
        // The list of all packages
        ArrayList<Integer> packages = new ArrayList<>();
        // The desired weight for the passenger seat
        int weight = 0;
        // Loop through every package in the input
        while (sc.hasNext()){
            // Add the weight to the total weight and the packages
            int w = sc.nextInt();
            packages.add(w);
            weight += w;
        }

        // Part 1 splits the packages into 3 groups
        if (PART == 1){
            weight /= 3;
        }

        // Part 2 splits the packages into 4 groups
        if (PART == 2){
            weight /= 4;
        }

        // The minimum quantum entanglement
        long QE = Long.MAX_VALUE;

        // Loop until a valid combination is found
        for (int i=1; QE == Long.MAX_VALUE; ++i){
            // The indices of the packages being looked at
            int[] indices = new int[i];

            // Continue until every combination of i packages has been examined
            while (indices[0] <= packages.size() - i){
                // The total weight of the current packages
                int total = 0;
                // The quantum entanglement of this combination
                long qe = 1;
                // Loop through every package
                for (int j=0; j<i; ++j){
                    // Calculate the values
                    total += packages.get(indices[j]);
                    qe *= packages.get(indices[j]);
                }

                // If it's a new best quantum entanglement
                if (total == weight && qe < QE){
                    QE = qe;
                }

                // Change the combination starting at the end
                int j = i-1;
                // If it's already reached the end, go back one index
                while (j >= 0 && indices[j] == packages.size()-i+j){
                    --j;
                }

                // If all of the indices are maxed
                if (j < 0){
                    break;
                }

                // Increase the index
                ++indices[j];
                // Reset all following indices
                for (++j; j<i; ++j){
                    indices[j] = indices[j-1] + 1;
                }
            }
        }

        // Print the answer
        System.out.println(QE);
    }
}