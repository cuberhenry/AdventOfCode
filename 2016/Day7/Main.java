/*
Henry Anderson
Advent of Code 2016 Day 7 https://adventofcode.com/2016/day/7
Input: https://adventofcode.com/2016/day/7/input
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
        // The number of IPs that fit the requirements
        int total = 0;

        // Look through every IP
        while (sc.hasNext()){
            // Grab the IP
            String ip = sc.nextLine();
            // Whether the current index is within square brackets
            boolean inside = false;
            // Used for the first condition
            boolean one = false;
            // Used for the second condition
            boolean two = false;
            // Used for sandwiches in part 2
            ArrayList<String> in = new ArrayList<>();
            ArrayList<String> out = new ArrayList<>();

            // Loop through every character
            for (int i=0; i<ip.length(); ++i){
                // First check for square brackets
                if (ip.charAt(i) == '['){
                    inside = true;
                }else if (ip.charAt(i) == ']'){
                    inside = false;
                }else{
                    // Part 1 finds a sequence abba in the outside but not the inside
                    if (PART == 1){
                        // If the current index starts a sequence abba
                        if (i < ip.length()-3 && ip.charAt(i) == ip.charAt(i+3) && ip.charAt(i+1) == ip.charAt(i+2) && ip.charAt(i) != ip.charAt(i+1) && ip.charAt(i+1) != '[' && ip.charAt(i+1) != ']'){
                            if (inside){
                                // This sequence inside automatically disqualifies this IP
                                two = true;
                                break;
                            }else{
                                // This sequence outside qualifies this IP
                                one = true;
                            }
                        }
                    }

                    // Part 2 finds a sequence aba in the outside and bab in the inside
                    if (PART == 2){
                        // If the current index starts a sequence aba
                        if (i < ip.length()-2 && ip.charAt(i) == ip.charAt(i+2) && ip.charAt(i) != ip.charAt(i+1) && ip.charAt(i+1) != '[' && ip.charAt(i+1) != ']'){
                            // Save the sequence
                            String string = ip.substring(i,i+3);
                            if (inside){
                                // If a reverse sequence exists outside
                                if (out.contains(string.substring(1) + string.charAt(1))){
                                    // Success
                                    one = true;
                                    break;
                                }else{
                                    // Add for looking at later
                                    in.add(string);
                                }
                            }else{
                                // If a reverse sequence exists inside
                                if (in.contains(string.substring(1) + string.charAt(1))){
                                    // Success
                                    one = true;
                                    break;
                                }else{
                                    // Add for looking at later
                                    out.add(string);
                                }
                            }
                        }
                    }
                }
            }

            // If the conditions are met, increase the total
            if (one && !two){
                ++total;
            }
        }

        // Print the answer
        System.out.println(total);
    }
}