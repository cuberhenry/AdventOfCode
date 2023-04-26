/*
Henry Anderson
Advent of Code 2022 Day 13 https://adventofcode.com/2022/day/13
Input: https://adventofcode.com/2022/day/13/input
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
        // The packets that will be sorted
        ArrayList<String> packets = new ArrayList<>();
        // The index of the current pair of packets
        int index = 1;
        // The value to be returned
        int total = 0;

        // In part two, add two divider packets into the list of packets
        if (PART == 2){
            packets.add("[[2]]");
            packets.add("[[6]]");
        }

        // While there are more packets
        while (sc.hasNextLine()){
            // Read in two packets, ignoring whitespace
            String one = sc.nextLine();
            if (one.equals("")){
                one = sc.nextLine();
            }
            String two = sc.nextLine();

            // Part 1 returns the sum of the indeces of pairs that are correctly ordered
            if (PART == 1){
                if (compare(one,two) == 1){
                    total += index;
                }
            }

            // Part 2 returns the product of the indeces of the two divider packets
            // in an ordered list of all packets
            if (PART == 2){
                // Figure out which of the two packets are smaller
                String min, max;
                if (compare(one,two) == 1){
                    min = one;
                    max = two;
                }else{
                    min = two;
                    max = one;
                }

                // Find the location at which to put the smaller packet
                int i = 0;
                while (i < packets.size() && compare(min,packets.get(i)) == -1){
                    ++i;
                }
                packets.add(i,min);
                ++i;
                // Find the location at which to put the bigger packet
                while (i < packets.size() && compare(max,packets.get(i)) == -1){
                    ++i;
                }
                packets.add(i,max);
            }

            // Increase the pair index
            ++index;
        }

        // Set the total to the product of the indeces
        if (PART == 2){
            // The indeces
            int num1 = 0;
            int num2 = 0;
            
            // Go over all of the packets
            for (int i=0; i<packets.size(); ++i){
                // Find the first packet
                if (packets.get(i).equals("[[2]]")){
                    num1 = i + 1;
                // Second packet found, exit the loop
                }else if (packets.get(i).equals("[[6]]")){
                    num2 = i + 1;
                    break;
                }
            }
            
            total = num1 * num2;
        }

        // Print the result
        System.out.println(total);
    }

    // This function takes in two strings and returns 0 if they're equal,
    // 1 if the first is smaller than the second, and -1 if the first is bigger
    private static int compare(String s1, String s2){
        // Turn the strings into string lists
        String[] a1 = makeList(s1);
        String[] a2 = makeList(s2);
        // If the two inputs are integers
        if (a1.length == 1 && a2.length == 1){
            // Try to compare the integers
            try {
                int one = Integer.parseInt(a1[0]);
                int two = Integer.parseInt(a2[0]);
                if (one == two){
                    return 0;
                }
                if (one < two){
                    return 1;
                }
                return -1;
            } catch (Exception e){}
        }

        // Compare every element of the list
        for (int i=0; i<a1.length && i<a2.length; ++i){
            int compare = compare(a1[i],a2[i]);
            if (compare != 0){
                return compare;
            }
        }

        // If one of them runs out of elements, return the comparison based on length
        if (a2.length < a1.length){
            return -1;
        }
        if (a1.length < a2.length){
            return 1;
        }
        return 0;
    }

    // This turns a string input into the highest level of String list
    private static String[] makeList(String line){
        // If the String is empty, the array should be too
        if (line.length() == 0){
            return new String[0];
        }
        // Turning it into a list removes the need for the outermost brackets
        if (line.charAt(0) == '['){
            line = line.substring(1,line.length()-1);
        }
        
        // This block turns the outermost layer of commas into semicolons
        int numLayers = 0;
        for (int i=0; i<line.length(); ++i){
            if (line.charAt(i) == '['){
                numLayers++;
            }else if (line.charAt(i) == ']'){
                numLayers--;
            }else if (line.charAt(i) == ',' && numLayers == 0){
                line = line.substring(0,i)+";"+line.substring(i+1);
            }
        }
        // Create the outermost list
        return line.split(";");
    }
}