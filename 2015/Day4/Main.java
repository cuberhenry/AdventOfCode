/*
Henry Anderson
Advent of Code 2015 Day 4 https://adventofcode.com/2015/day/4
Input: https://adventofcode.com/2015/day/4/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import java.security.*;
import java.math.*;
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
        // Take in the input
        String input = sc.next();
        // The index at which to start
        int index = 1;
        // Until a break is found
        while (true){
            // Create the hasher
            MessageDigest md;
            try{
                md = MessageDigest.getInstance("MD5");
            } catch (Exception e){
                return;
            }
            // The input string
            String string = input+index;
            // Find the hash of the string
            md.update(string.getBytes(),0,string.length());
            String hash = new BigInteger(1,md.digest()).toString(16);

            // Part 1 finds the number at which the hash starts with five zeroes
            if (PART == 1){
                if (hash.length() < 28){
                    break;
                }
            }

            // Part 2 finds the number at which the hash starts with six zeroes
            if (PART == 2){
                if (hash.length() < 27){
                    break;
                }
            }

            // Increase the index
            ++index;
        }
        
        // Print the answer
        System.out.println(index);
    }
}