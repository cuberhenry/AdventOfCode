/*
Henry Anderson
Advent of Code 2017 Day 10 https://adventofcode.com/2017/day/10
Input: https://adventofcode.com/2017/day/10/input
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
        // Part 1 finds the resulting list after performing knot twists
        if (PART == 1){
            // Collect the list of lengths
            String[] input = sc.nextLine().split(",");
            int[] lengths = new int[input.length];
            for (int i=0; i<input.length; ++i){
                lengths[i] = Integer.parseInt(input[i]);
            }

            // The values to be carried over between twists
            int skip = 0;
            int position = 0;
            // The list of numbers
            int[] list = new int[256];
            for (int i=0; i<list.length; ++i){
                list[i] = i;
            }

            // Loop through every length
            for (int length : lengths){
                // Skip small lengths
                if (length < 2){
                    position = (position + length + skip) % list.length;
                    ++skip;
                    continue;
                }
                // Get the start and end positions of the reversed section
                int start = position;
                int end = (position + length - 1) % list.length;

                // Reverse the section
                while (true){
                    // Switch the two numbers
                    int help = list[start];
                    list[start] = list[end];
                    list[end] = help;

                    // Move the start forward
                    start = (start + 1) % list.length;
                    // Check for loop break
                    if (start == end){
                        break;
                    }
                    // Move the end backward
                    end = (end + list.length - 1) % list.length;
                    // Check for loop break
                    if (start == end){
                        break;
                    }
                }

                // Change the current position
                position = (position + length + skip) % list.length;
                // Increment the skip
                ++skip;
            }

            // Print the product of the first two values in the list
            System.out.println(list[0] * list[1]);
        }

        // Part 2 finds the Knot Hash
        if (PART == 2){
            // Collect the input as a string
            String input = sc.nextLine();
            // Create the list of lengths based on the ascii characters
            int[] lengths = new int[input.length()+5];
            for (int i=0; i<input.length(); ++i){
                lengths[i] = (int)input.charAt(i);
            }
            // Add the five required lengths
            lengths[lengths.length-5] = 17;
            lengths[lengths.length-4] = 31;
            lengths[lengths.length-3] = 73;
            lengths[lengths.length-2] = 47;
            lengths[lengths.length-1] = 23;

            // The values to be carried over between twists
            int skip = 0;
            int position = 0;
            // The list of numbers
            int[] list = new int[256];
            for (int i=0; i<list.length; ++i){
                list[i] = i;
            }

            // Perform the twists 64 times
            for (int i=0; i<64; ++i){
                // Loop through every length
                for (int length : lengths){
                    // Skip small numbers
                    if (length < 2){
                        position = (position + length + skip) % list.length;
                        ++skip;
                        continue;
                    }
                    // Get the start and end positions of the reversed section
                    int start = position;
                    int end = (position + length - 1) % list.length;

                    // Reverse the section
                    while (true){
                        // Switch the two numbers
                        int help = list[start];
                        list[start] = list[end];
                        list[end] = help;

                        // Move the start forward
                        start = (start + 1) % list.length;
                        // Check for loop break
                        if (start == end){
                            break;
                        }
                        // Move the end backward
                        end = (end + list.length - 1) % list.length;
                        // Check for loop break
                        if (start == end){
                            break;
                        }
                    }

                    // Change the current position
                    position = (position + length + skip) % list.length;
                    // Increment the skip
                    ++skip;
                }
            }
            
            // Create the hash
            String hash = "";
            // Loop through every dense value
            for (int i=0; i<16; ++i){
                // Create the dense value by XORing each one
                int value = list[i*16];
                for (int j=1; j<16; ++j){
                    value ^= list[i*16+j];
                }
                
                // The first character of the hexadecimal value
                int sixteens = value / 16;
                if (sixteens > 9){
                    // Alphabetic character
                    hash += (char)(sixteens-10+'a');
                }else{
                    // Numerical character
                    hash += sixteens;
                }
                // The second character of the hexadecimal value
                int ones = value % 16;
                if (ones > 9){
                    // Alphabetic character
                    hash += (char)(ones-10+'a');
                }else{
                    // Numerical character
                    hash += ones;
                }
            }

            // Print the hash
            System.out.println(hash);
        }
    }
}