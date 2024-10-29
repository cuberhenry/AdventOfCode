/*
Henry Anderson
Advent of Code 2021 Day 14 https://adventofcode.com/2021/day/14
Input: https://adventofcode.com/2021/day/14/input
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
        // Take in the initial polymer
        String polymer = sc.nextLine();
        sc.nextLine();

        // The rules given
        HashMap<String,String> insertions = new HashMap<>();
        // Each possible pair
        ArrayList<String> pairs = new ArrayList<>();
        while (sc.hasNext()){
            String[] split = sc.nextLine().split(" -> ");
            insertions.put(split[0],split[1]);
            pairs.add(split[0]);
        }

        // The number of instances of each pair
        long[] counts = new long[pairs.size()];
        // Add the initial pairs to counts
        for (int i=0; i<polymer.length()-1; ++i){
            ++counts[pairs.indexOf(polymer.substring(i,i+2))];
        }

        // Part 1 finds the difference after 10 rounds
        int numRounds = 10;

        // Part 2 finds the difference after 40 rounds
        if (PART == 2){
            numRounds = 40;
        }

        // Loop through each round
        for (int i=0; i<numRounds; ++i){
            long[] newCounts = new long[counts.length];
            // Replace the pairs with the new pairs given the insertions
            for (int j=0; j<counts.length; ++j){
                newCounts[pairs.indexOf(pairs.get(j).charAt(0) + insertions.get(pairs.get(j)))] += counts[j];
                newCounts[pairs.indexOf(insertions.get(pairs.get(j)) + pairs.get(j).charAt(1))] += counts[j];
            }
            counts = newCounts;
        }

        // Count the number of each character doubled
        long[] common = new long[26];
        // Start with the ends
        ++common[polymer.charAt(0) - 'A'];
        ++common[polymer.charAt(polymer.length()-1) - 'A'];
        for (int i=0; i<counts.length; ++i){
            // Add each character from each pair
            common[pairs.get(i).charAt(0) - 'A'] += counts[i];
            common[pairs.get(i).charAt(1) - 'A'] += counts[i];
        }

        // Get the minimum and maximum
        long max = 0;
        long min = Long.MAX_VALUE;
        for (long c : common){
            if (c != 0){
                max = Math.max(c,max);
                min = Math.min(c,min);
            }
        }

        // Print the answer
        System.out.println((max - min) / 2);
    }
}