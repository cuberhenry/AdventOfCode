/*
Henry Anderson
Advent of Code 2018 Day 9 https://adventofcode.com/2018/day/9
Input: https://adventofcode.com/2018/day/9/input
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
        // Dissect the input
        String[] input = sc.nextLine().split(" ");
        int players = Integer.parseInt(input[0]);
        int marbles = Integer.parseInt(input[6]) + 1;

        // Part 1 finds the winning elf's score
        // Part 2 uses 100 times as many marbles
        if (PART == 2){
            marbles = (marbles-1) * 100 + 1;
        }

        // The placed marbles
        LinkedList<Integer> circle = new LinkedList<>();
        circle.add(0);
        // The scores (ordering is irrelevant)
        long[] scores = new long[players];

        // Loop through every marble after the first
        for (int i=1; i<marbles; ++i){
            // Decide what to do
            if (i % 23 == 0){
                // Loop back 6 times
                circle.addFirst(circle.removeLast());
                circle.addFirst(circle.removeLast());
                circle.addFirst(circle.removeLast());
                circle.addFirst(circle.removeLast());
                circle.addFirst(circle.removeLast());
                circle.addFirst(circle.removeLast());
                // Add the score to the current elf's score
                scores[i%scores.length] += i + circle.removeLast();
            }else{
                // Loop forward twice
                circle.addLast(circle.removeFirst());
                circle.addLast(circle.removeFirst());
                // Add the new marble
                circle.addFirst(i);
            }
        }

        // Find the max score
        long max = 0;
        for (int i=0; i<scores.length; ++i){
            max = Math.max(scores[i],max);
        }
        // Print the answer
        System.out.println(max);
    }
}