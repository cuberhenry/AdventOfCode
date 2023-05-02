/*
Henry Anderson
Advent of Code 2022 Day 25 https://adventofcode.com/2022/day/25
Input: https://adventofcode.com/2022/day/25/input
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

        // Part 2 does not require any code to solve
        if (PART == 2){
            System.out.println("Start the Blender");
            return;
        }

        // The sum of all the numbers
        long total = 0;

        // Take in all the input
        while (sc.hasNext()){
            // Take in the next line
            String line = sc.nextLine();
            // The power of the digit
            long power = 1;
            // Work from least significant to most significant digit
            for (int j=line.length()-1; j>=0; --j){
                // The value at this digit
                int num = switch (line.charAt(j)) {
                    case '=' -> -2;
                    case '-' -> -1;
                    default -> Integer.parseInt(line.substring(j,j+1));
                };
                // Add the amount to the total
                total += power * num;
                // Increase the power for the next digit
                power *= 5;
            }
        }
        
        // The result to be printed
        String answer = "";
        // Until the entire number has been represented
        while (total != 0){
            // If the number requires a minus
            if (total % 5 == 4){
                answer = "-" + answer;
                // Increase the total for the integer division
                ++total;
            // If the number requires a double-minus
            }else if (total % 5 == 3){
                answer = "=" + answer;
                // Increase the total for the integer division
                total += 2;
            // Normal base10 digit
            }else{
                answer = (total % 5) + answer;
            }
            // Integer division
            total /= 5;
        }
        
        // Print the answer
        System.out.println(answer);
    }
}