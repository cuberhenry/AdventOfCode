/*
Henry Anderson
Advent of Code 2023 Day 6 https://adventofcode.com/2023/day/6
Input: https://adventofcode.com/2023/day/6/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // The desired problem to solve
    static int PART;
    static Scanner sc;
    // The file containing the puzzle input
    static String FILE_NAME = "input.txt";
    public static void main(String args[]) {
        if (args.length < 1 || args.length > 2){
            System.out.println("Wrong number of arguments");
            return;
        }
        // Take in the part and file name
        try {
            PART = Integer.parseInt(args[0]);
        } catch (Exception e){}
        if (!(PART == 1 || PART == 2)){
            System.out.println("Part can only be 1 or 2");
            return;
        }
        if (args.length == 2){
            FILE_NAME = args[1];
        }
        try {
            sc = new Scanner(new File(FILE_NAME));
        }catch (Exception e){
            System.out.println("File not found");
            return;
        }
        // The answer to the problem
        long total = 1;
        // Skip "Time:"
        sc.next();
        // Contains all times and distances for the races
        ArrayList<Long> times = new ArrayList<>();
        ArrayList<Long> dists = new ArrayList<>();

        // Part 1 finds all winning possibilities for each race
        if (PART == 1){
            // Take in all the times
            while (sc.hasNextLong()){
                times.add(sc.nextLong());
            }
            // Skip "Distance:"
            sc.next();
            // Take in all the distances
            while (sc.hasNext()){
                dists.add(sc.nextLong());
            }
        }

        // Part 2 combines all the times and distances into one race
        if (PART == 2){
            // Take in the first set of digits
            String number = sc.next();
            // Take in the next set of digits
            String helper = sc.next();
            // Add all digits until distance to the number
            while (!helper.equals("Distance:")){
                number += helper;
                helper = sc.next();
            }
            // Add the time
            times.add(Long.parseLong(number));
            // Take in and combine all of the digits
            number = sc.next();
            while (sc.hasNext()){
                number += sc.next();
            }
            // Add the distance
            dists.add(Long.parseLong(number));
        }

        // Loop through every time and distance combination
        for (int i=0; i<times.size(); ++i){
            long time = times.get(i);
            long dist = dists.get(i);
            // Bounds for the largest winning time possibility
            long upper = time;
            long middle = time/2;
            // Until the bounds meet
            while (upper != middle){
                // Find the middle
                long newUp = (upper-middle)/2+middle;
                // Decide whether it's a new lower or upper bound
                if ((time-newUp)*newUp <= dist){
                    upper = newUp;
                }else{
                    middle = newUp;
                }
                // Decide between the last two options
                if (upper - middle == 1){
                    if ((time-upper)*upper <= dist){
                        upper = middle;
                    }else{
                        middle = upper;
                    }
                }
            }

            // Bounds for the smallest winning time possibility
            long lower = 0L;
            middle = time/2;
            // Until the bounds meet
            while (lower != middle){
                // Find the middle
                long newDown = (lower-middle)/2+middle;
                // Decide whether it's a new lower or upper bound
                if ((time-newDown)*newDown <= dist){
                    lower = newDown;
                }else{
                    middle = newDown;
                }
                // Decide between the last two options
                if (middle - lower == 1){
                    if ((time-lower)*lower <= dist){
                        lower = middle;
                    }else{
                        middle = lower;
                    }
                }
            }

            // Include this race in the total
            total *= (upper-lower+1);
        }

        // Print the answer
        System.out.println(total);
    }
}