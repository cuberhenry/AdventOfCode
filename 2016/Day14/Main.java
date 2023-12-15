/*
Henry Anderson
Advent of Code 2016 Day 14 https://adventofcode.com/2016/day/14
Input: https://adventofcode.com/2016/day/14/input
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
        int index = 0;

        // The indeces of keys
        ArrayList<Integer> indices = new ArrayList<>();
        // Possible keys: [index, character]
        ArrayList<int[]> repetitions = new ArrayList<>();

        // Create the hasher
        MessageDigest md5;
        try{
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e){
            return;
        }

        // Until a break is found
        while (indices.size() < 64 || repetitions.size() > 0){
            // Remove past repetitions
            if (repetitions.size() > 0 && repetitions.get(0)[0] + 1000 < index){
                repetitions.remove(0);
            }
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
            while (hash.length() < 32){
                hash = 0 + hash;
            }

            // Part 1 finds the 64th time the hash meets the requirements
            // Part 2 does the same, but takes the hash of the hash 2016 times
            if (PART == 2){
                for (int i=0; i<2016; ++i){
                    // Find the hash of the string
                    md5.update(hash.getBytes(),0,hash.length());
                    hash = new BigInteger(1,md5.digest()).toString(16);
                    while (hash.length() < 32){
                        hash = 0 + hash;
                    }
                }
            }

            // The first sequence of three
            int[] sequence = null;
            // Loop through every triplet of characters
            for (int i=0; i<hash.length()-2; ++i){
                char c = hash.charAt(i);
                // If the three characters are the same
                if (c == hash.charAt(i+1) && c == hash.charAt(i+2)){
                    // If the hash contains a sequence of five
                    if (i < hash.length()-4 && c == hash.charAt(i+3) && c == hash.charAt(i+4)){
                        // Loop through every hash that has a triplet
                        for (int j=0; j<repetitions.size(); ++j){
                            // If the triplet matches the current five sequence
                            if (repetitions.get(j)[1] == c){
                                // Add it to the list of correct values
                                indices.add(repetitions.remove(j)[0]);
                                --j;
                            }
                        }
                    }
                    // Save the first triplet sequence
                    if (sequence == null && indices.size() < 64){
                        sequence = new int[] {index,c};
                    }
                }
            }
            // Add the sequence to repetitions
            if (sequence != null){
                repetitions.add(sequence);
            }

            // Increase the index
            ++index;
        }

        // Sort the list
        Collections.sort(indices);
        // Print the 64th key
        System.out.println(indices.get(63));
    }
}