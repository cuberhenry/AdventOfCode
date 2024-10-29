/*
Henry Anderson
Advent of Code 2015 Day 16 https://adventofcode.com/2015/day/16
Input: https://adventofcode.com/2015/day/16/input
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
        // Used to find the value of the desired Aunt Sue
        HashMap<String,Integer> hashmap = new HashMap<>();
        // Input the values from the MFCSAM
        hashmap.put("children",3);
        hashmap.put("cats",7);
        hashmap.put("samoyeds",2);
        hashmap.put("pomeranians",3);
        hashmap.put("akitas",0);
        hashmap.put("vizslas",0);
        hashmap.put("goldfish",5);
        hashmap.put("trees",3);
        hashmap.put("cars",2);
        hashmap.put("perfumes",1);

        // The index of the Aunt Sue
        int index = 1;
        // Loop through every Aunt Sue
        while (sc.hasNext()){
            // Take in and split the line
            String[] sue = sc.nextLine().split(", |: | ");
            // Whether the correct Aunt Sue has been found
            boolean found = true;
            // Loop through every attribute of the candidate Aunt Sue
            for (int i=2; i<sue.length; i+=2){
                // Part 1 finds the Aunt Sue that exactly matches the description
                if (PART == 1){
                    if (hashmap.get(sue[i]) != Integer.parseInt(sue[i+1])){
                        found = false;
                        break;
                    }
                }

                // Part 2 makes adjustments for comparisons rather than exact numbers
                if (PART == 2){
                    // Must be more than the given number of cats or trees
                    if (sue[i].equals("cats") || sue[i].equals("trees")){
                        if (hashmap.get(sue[i]) >= Integer.parseInt(sue[i+1])){
                            found = false;
                            break;
                        }
                    // Must be less than the given number of pomeranians or goldfish
                    }else if (sue[i].equals("pomeranians") || sue[i].equals("goldfish")){
                        if (hashmap.get(sue[i]) <= Integer.parseInt(sue[i+1])){
                            found = false;
                            break;
                        }
                    // Must be equal to everything else
                    }else{
                        if (hashmap.get(sue[i]) != Integer.parseInt(sue[i+1])){
                            found = false;
                            break;
                        }
                    }
                }
            }
            // Stop looping once found
            if (found){
                break;
            }

            // Increase the index
            ++index;
        }

        // Print the answer
        System.out.println(index);
    }
}