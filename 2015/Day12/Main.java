/*
Henry Anderson
Advent of Code 2015 Day 12 https://adventofcode.com/2015/day/12
Input: https://adventofcode.com/2015/day/12/input
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
        // The sum of all numbers
        int total = 0;

        // Loop through every line of input
        while (sc.hasNext()){
            // Take in the next input line
            String line = sc.nextLine();

            // Part 1 finds the sum of all numbers in the input
            // Part 2 ignores all numbers within an object that the attribute red
            if (PART == 2){
                // Take in the index of the first instance of the attribute red
                int index = line.indexOf(":\"red\"");

                // As long as the attribute exists in the input
                while (index != -1){
                    // The index of the open bracket for the object
                    int start = index-1;
                    // How many other objects this is in
                    int deep = 0;
                    // Until the bracket that directly contains red is found
                    while (line.charAt(start) != '{' || deep != 0){
                        // Change deep if a bracket is found
                        if (line.charAt(start) == '}'){
                            ++deep;
                        }else if (line.charAt(start) == '{'){
                            --deep;
                        }
                        // Decrease the pointer
                        --start;
                    }
                    // The index of the close bracket for the object
                    int end = index + 6;
                    // Until the bracket that directly contains red is found
                    while (line.charAt(end) != '}' || deep != 0){
                        // Change deep if a bracket is found
                        if (line.charAt(end) == '{'){
                            ++deep;
                        }else if (line.charAt(end) == '}'){
                            --deep;
                        }
                        // Increase the pointer
                        ++end;
                    }
                    // Remove the object
                    line = line.substring(0,start) + line.substring(end+1);
                    // See if there's another red attribute in the input
                    index = line.indexOf(":\"red\"");
                }
            }

            // Loop through every character in the input
            for (int i=0; i<line.length(); ++i){
                // Save the current index
                int start = i;
                // Continue until the end of the number is found
                while (Character.isDigit(line.charAt(i)) || line.charAt(i) == '-'){
                    ++i;
                }
                // If at least one digit has been found, add the number to the total
                if (start != i){
                    total += Integer.parseInt(line.substring(start,i));
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}