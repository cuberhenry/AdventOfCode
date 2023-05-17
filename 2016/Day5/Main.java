/*
Henry Anderson
Advent of Code 2016 Day 5 https://adventofcode.com/2016/day/5
Input: https://adventofcode.com/2016/day/5/input
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
        // The password
        String password = "";
        
        // Part 1 fills the password with the 6th digit of the hash
        // Part 2 fills the password at the 6th digit with the 7th digit
        if (PART == 2){
            password = "        ";
        }
        // Until the password has been discovered
        while (password.length() < 8 || password.indexOf(' ') != -1){
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

            // Add leading zeroes up to where relevant
            while (hash.length() < 27){
                hash = "0" + hash;
            }

            // If there are 5 leading zeroes
            if (hash.length() < 28){
                if (PART == 1){
                    // Add the character to the password
                    password += hash.charAt(0);
                }

                if (PART == 2){
                    // The index in the password
                    int first = hash.charAt(0)-'0';
                    // If the index is within the password's range and is not yet found
                    if (first >= 0 && first <= 7 && password.charAt(first) == ' '){
                        // Add the character to the password
                        password = password.substring(0,first) + hash.charAt(1) + password.substring(first+1);
                    }
                }
            }
            
            // Increase the index
            ++index;
        }
        
        // Print the answer
        System.out.println(password);
    }
}