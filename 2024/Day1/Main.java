import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    final private static String name = "Day 1: Historian Hysteria";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        // The two lists of numbers
        ArrayList<Integer> left = new ArrayList<>();
        ArrayList<Integer> right = new ArrayList<>();
        // Loop through each row
        while (sc.hasNext()){
            // Add the numbers to their respective lists
            left.add(sc.nextInt());
            right.add(sc.nextInt());
        }
        // Sort both lists
        Collections.sort(left);
        Collections.sort(right);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // The current index of the right list
        int index = 0;

        // Loop through each number in the left list
        for (int i=0; i<left.size(); ++i){
            // Add the difference between it and its similarity in the right list
            part1 += Math.abs(left.get(i)-right.get(i));

            // The number of times this number appears in the right list
            int count = 0;
            // Skip numbers smaller in the right list
            while (index < right.size() && right.get(index) < left.get(i)){
                ++index;
            }
            // Find the count
            while (index < right.size() && right.get(index).equals(left.get(i))){
                ++count;
                ++index;
            }
            // Add the count times the number
            part2 += count * left.get(i);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}