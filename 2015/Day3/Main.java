/*
Henry Anderson
Advent of Code 2015 Day 3 https://adventofcode.com/2015/day/3
Input: https://adventofcode.com/2015/day/3/input
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
        // All of the houses that received at least one present
        HashSet<String> delivered = new HashSet<>();
        // Santa's position
        int x = 0;
        int y = 0;
        // Robot Santa's position
        int robX = 0;
        int robY = 0;
        // The current house gets a present
        delivered.add("0 0");
        // Take in the input
        String input = sc.nextLine();

        // For every instruction
        for (int i=0; i<input.length(); ++i){
            // Move Santa
            switch (input.charAt(i)){
                case '^' -> {--y;}
                case 'v' -> {++y;}
                case '<' -> {--x;}
                case '>' -> {++x;}
            }

            // Deliver a present and add if it's a new house
            if (!delivered.contains(x+" "+y)){
                delivered.add(x+" "+y);
            }

            // Part 1 finds how many houses Santa delivers to
            // Part 2 adds a Robot Santa
            if (PART == 2){
                // Increase the index
                ++i;
                // Move Robot Santa
                switch (input.charAt(i)){
                    case '^' -> {--robY;}
                    case 'v' -> {++robY;}
                    case '<' -> {--robX;}
                    case '>' -> {++robX;}
                }
    
                // Deliver a present and add if it's a new house
                if (!delivered.contains(robX+" "+robY)){
                    delivered.add(robX+" "+robY);
                }
            }
        }

        // Print the answer
        System.out.println(delivered.size());
    }
}