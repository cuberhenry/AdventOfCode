/*
Henry Anderson
Advent of Code 2020 Day 25 https://adventofcode.com/2020/day/25
Input: https://adventofcode.com/2020/day/25/input
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
        // Part 2 doesn't require code
        if (PART == 2){
            System.out.println("Pay The Deposit");
            return;
        }
        
        // Get the two public keys
        long cardPublicKey = sc.nextLong();
        long doorPublicKey = sc.nextLong();
        // Subject is always 7
        long subject = 7;
        // The first transform
        long value = 1;
        // The number of loops required
        long cardLoop = 0;
        // Get the number of loops to create the card's public key
        while (value != cardPublicKey){
            value = value * subject % 20201227;
            ++cardLoop;
        }

        // Use that to get the encryption key
        value = 1;
        for (long i=0; i<cardLoop; ++i){
            value = value * doorPublicKey % 20201227;
        }

        // Print the answer
        System.out.println(value);
    }
}