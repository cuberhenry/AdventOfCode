import com.aoc.mylibrary.*;
import java.util.*;

public class Main {
    final private static String name = "Day 7: ";
    public static void main(String[] args){
        // Get all the lines of input
        String[][] input = Library.getStringMatrix(args,": ");

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // Loop through each line of input
        for (String[] line : input){
            // Parse numbers from the string input
            long result = Long.parseLong(line[0]);
            int[] nums = Library.intSplit(line[1]," ");

            // The list of all possible numbers using the operators
            HashSet<Long> numbers = new HashSet<>();
            numbers.add((long)nums[0]);

            for (int i=1; i<nums.length; ++i){
                // Create the next result set after including the next number
                HashSet<Long> newNums = new HashSet<>();
                // Add both results of the operation for all partial results
                for (long num : numbers){
                    newNums.add(num + nums[i]);
                    newNums.add(num * nums[i]);
                }
                numbers = newNums;
            }

            // Identify if the result was met using any combination of operations
            if (numbers.contains(result)){
                part1 += result;
            }else{
                // Otherwise, start over and check including the concatenation operator
                numbers.clear();
                numbers.add((long)nums[0]);

                for (int i=1; i<nums.length; ++i){
                    HashSet<Long> newNums = new HashSet<>();
                    // Add the results for each operation on each partial sum
                    for (long num : numbers){
                        newNums.add(num + nums[i]);
                        newNums.add(num * nums[i]);
                        newNums.add(Long.parseLong(num + "" + nums[i]));
                    }
                    numbers = newNums;
                }

                // Identify if the result was met using any combination of operations
                if (numbers.contains(result)){
                    part2 += result;
                }
            }
        }

        // All numbers that meet the first requirement also meet the second
        part2 += part1;

        // Print the answer
        Library.print(part1,part2,name);
    }
}