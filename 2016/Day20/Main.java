/*
Henry Anderson
Advent of Code 2016 Day 20 https://adventofcode.com/2016/day/20
Input: https://adventofcode.com/2016/day/20/input
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
        // The list of all ranges
        ArrayList<String> ranges = new ArrayList<>();
        // Loop through every line of input
        while (sc.hasNext()){
            // Take in the next line
            String[] split = sc.nextLine().split("-");
            // Save the two values
            long low = Long.parseLong(split[0]);
            long high = Long.parseLong(split[1]);

            // Loop through every existing range
            for (int i=0; i<ranges.size(); ++i){
                // Split and save the other range
                String[] split2 = ranges.get(i).split("-");
                long low2 = Long.parseLong(split2[0]);
                long high2 = Long.parseLong(split2[1]);

                // If the two ranges intersect
                if (low-1 <= high2 && low2-1 <= high){
                    // Merge the ranges
                    ranges.remove(i);
                    low = Math.min(low,low2);
                    high = Math.max(high,high2);
                }
            }

            // Add the final range
            ranges.add(low + "-" + high);
        }

        // Part 1 finds the lowest unblocked IP
        if (PART == 1){
            // Loop through every range
            for (String range : ranges){
                // When the range that starts at 0 is found
                if (range.substring(0,range.indexOf('-')).equals("0")){
                    // Print the next value outside the range
                    System.out.println(Long.parseLong(range.substring(range.indexOf('-')+1))+1);
                    return;
                }
            }
            // Otherwise 0 is the answer
            System.out.println(0);
        }

        // Part 2 finds the number of unblocked IPs
        if (PART == 2){
            // Loop through every range after the first
            for (int i=1; i<ranges.size(); ++i){
                // Whether the current range has been sorted
                boolean swapped = false;
                // Loop through every previous range
                for (int j=i-1; j>=0; --j){
                    // If the range belongs after the current j range
                    if (Long.parseLong(ranges.get(j).split("-")[0])
                        < Long.parseLong(ranges.get(i).split("-")[0])){
                        // Swap them
                        ranges.add(j+1,ranges.remove(i));
                        swapped = true;
                        break;
                    }
                }
                // Otherwise the range belongs at the beginning
                if (!swapped){
                    ranges.add(0,ranges.remove(i));
                }
            }

            // The number of unblocked IPs
            long total = 4294967295L;
            // Calculate the number of unblocked IPs at the end of the possible range
            total -= Long.parseLong(ranges.get(ranges.size()-1).split("-")[1]);
            
            // Loop through every range
            for (int i=0; i<ranges.size()-1; ++i){
                // Add the space between the two ranges
                total += Long.parseLong(ranges.get(i+1).split("-")[0])
                         - Long.parseLong(ranges.get(i).split("-")[1]) - 1;
            }

            // Print the answer
            System.out.println(total);
        }
    }
}