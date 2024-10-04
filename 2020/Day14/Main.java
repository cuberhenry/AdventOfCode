/*
Henry Anderson
Advent of Code 2020 Day 14 https://adventofcode.com/2020/day/14
Input: https://adventofcode.com/2020/day/14/input
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
        // The two parts to the mask
        long andMask = 0;
        long orMask = 0;
        // The memory being written to
        HashMap<Long,Long> memory = new HashMap<>();

        // Loop through every input
        while (sc.hasNextLine()){
            // Split the line
            String[] line = sc.nextLine().split(" = ");
            // Update the mask
            if (line[0].equals("mask")){
                // Reset the masks
                andMask = 0;
                orMask = 0;

                // Loop through every input
                for (int i=0; i<line[1].length(); ++i){
                    // Update both to 1, force set to 1
                    if (line[1].charAt(line[1].length()-1-i) == '1'){
                        andMask += Math.pow(2,i);
                        orMask += Math.pow(2,i);
                    // Update only and to 1, allow either value
                    }else if (line[1].charAt(line[1].length()-1-i) == 'X'){
                        andMask += Math.pow(2,i);
                    }
                }
            }else{
                // The address being written to
                long address = Integer.parseInt(line[0].substring(4,line[0].length()-1));
                // The value being written
                long value = Long.parseLong(line[1]);

                // Part 1 masks the value by forcing 1s and 0s and leaving Xs
                if (PART == 1){
                    // Force the 0s
                    value &= andMask;
                    // Force the 1s
                    value |= orMask;
                    // Store the memory
                    memory.put(address,value);
                }

                // Part 2 masks the address by forcing 1s and fluctuating Xs
                if (PART == 2){
                    // Force the 1s
                    address |= orMask;
                    // The addresses being written to
                    ArrayList<Long> addresses = new ArrayList<>();
                    // The default address will be written to
                    addresses.add(address);
                    // Copy the masks
                    long andCopy = andMask;
                    long orCopy = orMask;
                    // The power of 2 being compared
                    long power = 1;

                    // Continue until the masks don't differ
                    while (andCopy != orCopy){
                        // If they differ
                        if (andCopy % 2 != orCopy % 2){
                            // Duplicate each address, fluctuating the current bit
                            for (int i=addresses.size()-1; i>=0; --i){
                                addresses.add(addresses.get(i) ^ power);
                            }
                        }
                        // Proceed to the next bit
                        power <<= 1;
                        andCopy >>= 1;
                        orCopy >>= 1;
                    }

                    // Write to all memory addresses
                    for (long add : addresses){
                        memory.put(add,value);
                    }
                }
            }
        }

        // The sum of all numbers in memory
        long sum = 0;
        // Loop through every address that stores a value
        for (long address : memory.keySet()){
            sum += memory.get(address);
        }
        // Print the answer
        System.out.println(sum);
    }
}