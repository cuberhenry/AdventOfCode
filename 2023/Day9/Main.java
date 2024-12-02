import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    final private static String name = "Day 9: Mirage Maintenance";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // For every list of numbers
        while (sc.hasNext()){
            // The stack of numbers
            Stack<Integer> front = new Stack<>();
            Stack<Integer> back = new Stack<>();
            // Turn the string of numbers into an int array
            int[] numbers = Library.intSplit(sc.nextLine()," ");

            // Whether enough information was found to extrapolate
            boolean finished = false;
            // Continue until finished
            while (!finished){
                // Create an array representing changes between the numbers
                int[] next = new int[numbers.length-1];
                
                finished = true;
                // For each new number
                for (int i=0; i<next.length; ++i){
                    // Set it to the difference between the two numbers in the old array
                    next[i] = numbers[i+1] - numbers[i];
                    // The old array does not have a constant slope
                    if (next[i] != next[0]){
                        finished = false;
                    }
                }

                // Put the relevant numbers on the stack
                back.push(numbers[numbers.length-1]);
                front.push(numbers[0]);

                numbers = next;
            }

            // Get the change between the edge numbers and the extrapolated numbers
            int forwards = numbers[0];
            int backwards = numbers[0];

            // Continue until the target is found
            while (!front.isEmpty()){
                // Find the number after the saved number
                backwards = back.pop() + backwards;
                // Find the number before the saved number
                forwards = front.pop() - forwards;
            }

            // Add the number to the total
            part1 += backwards;
            part2 += forwards;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}