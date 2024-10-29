/*
Henry Anderson
Advent of Code 2021 Day 8 https://adventofcode.com/2021/day/8
Input: https://adventofcode.com/2021/day/8/input
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
        // The answer to the problem
        int total = 0;

        // Loop through every set of displays
        while (sc.hasNext()){
            // Take in the next line
            String[] split = sc.nextLine().split(" \\| ");

            // This set's total
            int subtotal = 0;
            // Loop through each displayed digit
            for (String digit : split[1].split(" ")){
                // Part 1 finds the number of 1s, 4s, 7s, and 8s
                if (PART == 1){
                    // If the length matches one of those numbers, add one
                    if ("2347".contains("" + digit.length())){
                        ++subtotal;
                    }
                }

                // Part 2 finds the sum of all displays
                if (PART == 2){
                    // Shift left
                    subtotal *= 10;

                    if (digit.length() == 2){
                        // Digit is 1
                        ++subtotal;
                    }else if (digit.length() == 3){
                        // Digit is 7
                        subtotal += 7;
                    }else if (digit.length() == 4){
                        // Digit is 4
                        subtotal += 4;
                    }else if (digit.length() == 7){
                        // Digit is 8
                        subtotal += 8;
                    }else if (digit.length() == 5){
                        // Collect the 1 and 4 from the left side
                        String one = "";
                        String four = "";
                        for (String dig : split[0].split(" ")){
                            if (dig.length() == 2){
                                one = dig;
                            }else if (dig.length() == 4){
                                four = dig;
                            }
                        }
                        // If it contains both segments from 1, it must be 3
                        if (digit.indexOf(one.charAt(0)) != -1 && digit.indexOf(one.charAt(1)) != -1){
                            subtotal += 3;
                        }else{
                            // Count the number of matches against 4
                            int count = 0;
                            for (int i=0; i<4; ++i){
                                if (digit.contains("" + four.charAt(i))){
                                    ++count;
                                }
                            }
                            // If it matches 2, it must be 2, otherwise 5
                            if (count == 2){
                                subtotal += 2;
                            }else{
                                subtotal += 5;
                            }
                        }
                    }else{
                        // Collect the segments from 4 and 7
                        String fourSeven = "";
                        for (String dig : split[0].split(" ")){
                            if (dig.length() == 4){
                                fourSeven += dig;
                                if (fourSeven.length() == 7){
                                    break;
                                }
                            }else if (dig.length() == 3){
                                fourSeven += dig;
                                if (fourSeven.length() == 7){
                                    break;
                                }
                            }
                        }
                        // Count the number of matches against both
                        int count = 0;
                        for (int i=0; i<7; ++i){
                            if (digit.contains("" + fourSeven.charAt(i))){
                                ++count;
                            }
                        }
                        // If it matches all 7, it must be 9
                        // If it matches 5, it must be 6
                        // 6 matches means 0, but that can be ignored
                        if (count == 7){
                            subtotal += 9;
                        }else if (count == 5){
                            subtotal += 6;
                        }
                    }
                }
            }

            // Add the subtotal
            total += subtotal;
        }

        // Print the answer
        System.out.println(total);
    }
}