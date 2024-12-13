import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 13: Claw Contraption";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // Loop through each input
        while (sc.hasNext()){
            // Take in the three pairs
            int[] a = Library.intSplit(sc.nextLine().substring(12),", Y\\+");
            int[] b = Library.intSplit(sc.nextLine().substring(12),", Y\\+");
            long[] prize = Library.longSplit(sc.nextLine().substring(9),", Y=");

            // Calculate the intersection of the system of linear equations
            long i = prize[0] * a[1] - a[0] * prize[1];
            long j = b[0] * a[1] - a[0] * b[1];
            if (i % j == 0){
                j = i / j;
                i = prize[1] - b[1] * j;
                if (i % a[1] == 0){
                    i /= a[1];
                    // If they intersect, save the token cost
                    part1 += i * 3 + j;
                }
            }

            // Shift the prize by ten trillion in each direction
            prize[0] += 10000000000000L;
            prize[1] += 10000000000000L;

            // Calculate the intersection of the system of linear equations
            i = prize[0] * a[1] - a[0] * prize[1];
            j = b[0] * a[1] - a[0] * b[1];
            if (i % j == 0){
                j = i / j;
                i = prize[1] - b[1] * j;
                if (i % a[1] == 0){
                    i /= a[1];
                    // If they intersect, save the token cost
                    part2 += i * 3 + j;
                }
            }

            // Skip empty lines
            if (sc.hasNext()){
                sc.nextLine();
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}