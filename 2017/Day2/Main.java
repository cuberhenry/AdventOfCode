import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 2: Corruption Checksum";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // The checksum of the file
        int part1 = 0;
        int part2 = 0;

        // Loop through every row of input
        while (sc.hasNext()){
            // Take in and split the row
            int[] line = Library.intSplit(sc.nextLine(),"\t");

            // Collect the first value
            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;
            int quotient = 0;

            // Loop through every following number
            for (int i : line){
                // Decide whether it's a new max or min
                if (i > max){
                    max = i;
                }else if (i < min){
                    min = i;
                }

                if (quotient == 0){
                    for (int j : line){
                        if (i <= j){
                            continue;
                        }

                        if (i % j == 0){
                            quotient = i / j;
                            break;
                        }
                    }
                }
            }

            // Add the difference for the row into the checksum
            part1 += max - min;
            part2 += quotient;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}