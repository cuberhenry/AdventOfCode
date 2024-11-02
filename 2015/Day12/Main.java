import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    final private static String name = "Day 12: JSAbacusFramework.io";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The sum of all numbers
        int part1 = 0;
        int part2 = 0;

        // Loop through every line of input
        while (sc.hasNext()){
            // Take in the next input line
            String line = sc.nextLine();
            // The depth
            Stack<Integer> stack = new Stack<>();
            int redDepth = Integer.MAX_VALUE;

            // Loop through every character in the input
            for (int i=0; i<line.length(); ++i){
                // Save the current index
                int start = i;
                // Continue until the end of the number is found
                while (Character.isDigit(line.charAt(i)) || line.charAt(i) == '-'){
                    ++i;
                }
                // If at least one digit has been found, add the number to the total
                if (start != i){
                    int num = Integer.parseInt(line.substring(start,i));
                    part1 += num;

                    if (redDepth > stack.size()){
                        part2 += num;
                    }

                    --i;
                }else if (redDepth > stack.size() && i < line.length() - 5 && line.substring(i,i+6).equals(":\"red\"")){
                    redDepth = stack.size();
                    part2 = 0;
                }else if (line.charAt(i) == '{' || line.charAt(i) == '['){
                    stack.push(part2);
                    part2 = 0;
                }else if (line.charAt(i) == '}' || line.charAt(i) == ']'){
                    part2 += stack.pop();
                    if (stack.size() < redDepth){
                        redDepth = Integer.MAX_VALUE;
                    }
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}