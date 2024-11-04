import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 19: An Elephant Named Joseph";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The number of elves in the circle
        int number = sc.nextInt();
        
        // The answer to the problem
        // Josephus problem
        int part1 = ((number - (int)Math.pow(2,31-Integer.numberOfLeadingZeros(number))) << 1) + 1;
        int part2 = 0;

        // O(1) solution for stealing across
        int lastPow = (int)Math.pow(3,Math.floor(Math.log(number) / Math.log(3)));
        if (number == lastPow){
            part2 = number;
        }else if (number - lastPow <= lastPow){
            part2 = number - lastPow;
        }else{
            part2 = number * 2 - lastPow * 3;
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}