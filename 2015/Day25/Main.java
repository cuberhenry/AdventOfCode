import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 25: Let It Snow";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // Get the input
        String[] line = sc.nextLine().split("[.]  |[.]|, | ");
        // Grab the row
        int row = Integer.parseInt(line[15]);
        // Grab the column
        int column = Integer.parseInt(line[17]);
        // Change it so each row has row number of values
        row += column - 1;

        // The first number
        long part1 = 20151125;

        // Loop through every following position up to row column
        for (int i=2; i<=row; ++i){
            for (int j=1; j<=i; ++j){
                // Calculate next number
                part1 *= 252533;
                part1 %= 33554393;
                // If this is the number we're looking for, stop
                if (i == row && j == column){
                    break;
                }
            }
        }

        // Part 2 does not require any code to solve
        String part2 = "Start the Weather Machine";

        // Print the answer
        Library.print(part1,part2,name);
    }
}