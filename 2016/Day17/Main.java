/*
Henry Anderson
Advent of Code 2016 Day 17 https://adventofcode.com/2016/day/17
Input: https://adventofcode.com/2016/day/17/input
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
        // The password for the hashes
        String password = sc.next();
        // Breadth first search
        ArrayList<String> queue = new ArrayList<>();
        // Add the first state
        queue.add("0 0 " + password);
        // The current longest path
        int longest = 0;

        // Continue until every path has been searched
        while (queue.size() > 0){
            // Grab the current state
            String[] state = queue.remove(0).split(" ");
            // Identify the current location
            int x = Integer.parseInt(state[0]);
            int y = Integer.parseInt(state[1]);

            // If the vault has been reached
            if (x == 3 && y == 3){
                // Part 1 finds the shortest path
                if (PART == 1){
                    // Print the path and quit
                    System.out.println(state[2].substring(password.length()));
                    return;
                }

                // Part 2 finds the longest path
                if (PART == 2){
                    // Update the longest path value
                    longest = state[2].length();
                    continue;
                }
            }

            // Take the current hash string
            String hash = state[2];
            // Create the hasher
            MessageDigest md;
            try{
                md = MessageDigest.getInstance("MD5");
            } catch (Exception e){
                return;
            }
            // Find the hash of the string
            md.update(hash.getBytes(),0,hash.length());
            hash = new BigInteger(1,md.digest()).toString(16);
            // Add leading zeroes
            while (hash.length() < 32){
                hash = "0" + hash;
            }

            // If there is a door up and it's unlocked
            if (hash.charAt(0) > 'a' && y > 0){
                queue.add(x + " " + (y-1) + " " + state[2] + "U");
            }

            // If there is a door down and it's unlocked
            if (hash.charAt(1) > 'a' && y < 3){
                queue.add(x + " " + (y+1) + " " + state[2] + "D");
            }

            // If there is a door left and it's unlocked
            if (hash.charAt(2) > 'a' && x > 0){
                queue.add(x-1 + " " + y + " " + state[2] + "L");
            }

            // If there is a door right and it's unlocked
            if (hash.charAt(3) > 'a' && x < 3){
                queue.add(x+1 + " " + y + " " + state[2] + "R");
            }
        }

        // Print the length of the longest path
        System.out.println(longest-password.length());
    }
}