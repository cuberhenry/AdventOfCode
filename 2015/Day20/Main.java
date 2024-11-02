import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 20: Infinite Elves and Infinite Houses";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The number to find
        int number = sc.nextInt();

        // The answer to the problem
        int part1 = solve(number,10,number/10);
        int part2 = solve(number,11,50);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static int solve(int number, int perHouse, int numDeliveries){
        // The number of presents delivered to each house
        int[] presents = new int[number/10];
        // The answer to the problem
        int index = presents.length;

        // Loop through every relevant elf
        for (int i=1; i<index; ++i){
            // Loop through every relevant house to be delivered to
            for (int j=i; j<index; j+=i){
                // Deliver only to 50 houses
                if ((j-i)/i == numDeliveries){
                    break;
                }

                // Deliver presents
                presents[j] += i;

                // If it passes the minimum and is earlier than the current answer
                if (presents[j] >= number/perHouse && j < index){
                    // Save the index
                    index = j;
                }
            }
        }

        return index;
    }
}