/*
Henry Anderson
Advent of Code 2018 Day 8 https://adventofcode.com/2018/day/8
Input: https://adventofcode.com/2018/day/8/input
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

        // Part 1 finds the sum of all metadata
        if (PART == 1){
            System.out.println(part1());
        }

        // Part 2 finds the value of the root node
        if (PART == 2){
            System.out.println(part2());
        }
    }

    private static int part1(){
        // The number of children
        int children = sc.nextInt();
        // The number of metadata entries
        int metadata = sc.nextInt();
        // The value of the node
        int total = 0;

        // Loop through every child
        for (int i=0; i<children; ++i){
            // Add the child's value
            total += part1();
        }

        // Loop through every metadata item
        for (int i=0; i<metadata; ++i){
            // Add to the value
            total += sc.nextInt();
        }

        // Return the answer
        return total;
    }

    private static int part2(){
        // The node's children
        int[] children = new int[sc.nextInt()];
        // The number of metadata entries
        int metadata = sc.nextInt();
        // The value of the node
        int total = 0;

        // Loop through every child
        for (int i=0; i<children.length; ++i){
            // Save the child's value
            children[i] = part2();
        }

        if (children.length == 0){
            // Nodes with no children returns the sum of its metadata
            for (int i=0; i<metadata; ++i){
                // Add to the value
                total += sc.nextInt();
            }
        }else{
            // Nodes with children returns the value of each indicated child
            for (int i=0; i<metadata; ++i){
                // Save the index
                int index = sc.nextInt();
                // If it's a valid child
                if (index <= children.length){
                    // Add to the value
                    total += children[index-1];
                }
            }
        }

        // Return the answer
        return total;
    }
}