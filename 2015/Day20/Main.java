/*
Henry Anderson
Advent of Code 2015 Day 20 https://adventofcode.com/2015/day/20
Input: https://adventofcode.com/2015/day/20/input
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
        // The number to find
        int number = sc.nextInt();
        // The number of presents delivered to each house
        int[] presents = new int[number/10];
        // The answer to the problem
        int index = presents.length;

        // The number of presents delivered to each house
        int perHouse = 0;

        // Part 1 delivers 10 presents to infintely many houses
        if (PART == 1){
            perHouse = 10;
        }

        // Part 2 delivers 11 presents to 50 houses
        if (PART == 2){
            perHouse = 11;
        }

        // Loop through every relevant elf
        for (int i=1; i<index; ++i){
            // Loop through every relevant house to be delivered to
            for (int j=i; j<index; j+=i){
                // Deliver presents
                presents[j] += i;

                // If it passes the minimum and is earlier than the current answer
                if (presents[j] >= number/perHouse && j < index){
                    // Save the index
                    index = j;
                }

                // Deliver only to 50 houses
                if (PART == 2){
                    if ((j-i)/i == 50){
                        break;
                    }
                }
            }
        }

        // Print the answer
        System.out.println(index);
    }
}