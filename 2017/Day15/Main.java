import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 15: Dueling Generators";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // Take in the two initial values
        long A = Integer.parseInt(sc.nextLine().split(" ")[4]);
        long B = Integer.parseInt(sc.nextLine().split(" ")[4]);
        // The number of matches
        int part1 = 0;
        int part2 = 0;
        
        // Duplicate the initial values
        long a = A;
        long b = B;
        
        // Compare loop matches
        for (int i=0; i<40000000; ++i){
            // Generate the next number
            a = a*16807 % 2147483647;
            b = b*48271 % 2147483647;

            // If the last 16 digits match, increment the total
            if (a % 65536 == b % 65536){
                ++part1;
            }
        }

        // Reset the values
        a = A;
        b = B;

        // Compare with criteria
        for (int i=0; i<5000000; ++i){
            // Generate the next number
            a = a*16807 % 2147483647;
            b = b*48271 % 2147483647;

            // Ensure the values match the criteria
            while (a % 4 != 0){
                a = a*16807 % 2147483647;
            }
            while (b % 8 != 0){
                b = b*48271 % 2147483647;
            }

            // If the last 16 digits match, increment the total
            if (a % 65536 == b % 65536){
                ++part2;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}