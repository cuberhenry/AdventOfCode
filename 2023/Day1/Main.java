/*
Henry Anderson
Advent of Code 2023 Day 1 https://adventofcode.com/2023/day/1
Input: https://adventofcode.com/2023/day/1/input
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
        // The sum of the calibration values
        int total = 0;
        // Numbers spelled out
        String[] numbers = {"one","two","three","four","five",
                            "six","seven","eight","nine"};

        // Loop through every line of input
        while (sc.hasNext()){
            // The calibration value for the current line
            int number = 0;
            // Take in the input
            String line = sc.nextLine();

            // Loop through every character starting at the front
            // until the first digit is found
            for (int i=0; i<line.length() && number == 0; ++i){
                // If a numeral is found
                if (Character.isDigit(line.charAt(i))){
                    // Set the digit to it
                    number += 10 * (line.charAt(i)-'0');
                    break;
                }
                
                // Part 2 allows for spelled out digits
                if (PART == 2){
                    // Loop through every possible spelled out digit
                    for (int j=0; j<numbers.length; ++j){
                        // If the current character begins a spelled out digit
                        if (line.length()-i >= numbers[j].length()
                            && line.substring(i,i+numbers[j].length()).equals(numbers[j])){
                            // Set the digit to it
                            number += 10 * (j+1);
                            break;
                        }
                    }
                }
            }

            // Loop through every character starting at the end
            // until the second digit is found
            for (int i=line.length()-1; i>=0 && number%10==0; --i){
                // If a numeral is found
                if (Character.isDigit(line.charAt(i))){
                    // Set the digit to it
                    number += line.charAt(i)-'0';
                    break;
                }

                // Check for spelled out digits
                if (PART == 2){
                    // Loop through every possible spelled out digit
                    for (int j=0; j<numbers.length; ++j){
                        // If the current character begins a spelled out digit
                        if (line.length()-i >= numbers[j].length()
                            && line.substring(i,i+numbers[j].length()).equals(numbers[j])){
                            // Set the digit to it
                            number += j+1;
                            break;
                        }
                    }
                }
            }

            // Add the calibration value to the total
            total += number;
        }
        
        // Print the answer
        System.out.println(total);
    }
}