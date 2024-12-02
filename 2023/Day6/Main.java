import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 6: Wait For It";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // The answer to the problem
        long part1 = 1;
        long part2 = 0;
        // Skip "Time:"
        sc.next();
        // Contains all times and distances for the races
        ArrayList<Long> times = new ArrayList<>();
        ArrayList<Long> dists = new ArrayList<>();

        // The combined amount
        String number = "";

        // Take in all the times
        while (sc.hasNextLong()){
            times.add(sc.nextLong());
            number += times.getLast();
        }
        times.addFirst(Long.parseLong(number));

        sc.next();
        number = "";
        // Take in all the distances
        while (sc.hasNext()){
            dists.add(sc.nextLong());
            number += dists.getLast();
        }
        dists.addFirst(Long.parseLong(number));

        // Loop through every time and distance combination
        for (int i=0; i<times.size(); ++i){
            long time = times.get(i);
            long dist = dists.get(i);
            // Bounds for the lowest winning time possibility
            long lower = 0;
            long upper = time/2;
            // Until the bounds meet
            while (lower < upper-1){
                // Find the middle
                long middle = (upper+lower)/2;
                // Decide whether it's a new lower or upper bound
                if ((time-middle)*middle <= dist){
                    lower = middle;
                }else{
                    upper = middle;
                }
            }

            // Include this race in the total
            if (i == 0){
                part2 = time - upper * 2 + 1;
            }else{
                part1 *= time - upper * 2 + 1;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}