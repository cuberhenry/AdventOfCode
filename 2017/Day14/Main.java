/*
Henry Anderson
Advent of Code 2017 Day 14 https://adventofcode.com/2017/day/14
Input: https://adventofcode.com/2017/day/14/input
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
        // The string used to create the hash
        String key = sc.next();
        // The answer to the problem
        int total = 0;
        // The grid representing the disk
        boolean[][] grid = new boolean[128][128];

        // Loop through every row
        for (int i=0; i<128; ++i){
            // Collect the input as a string
            String input = key + "-" + i;
            // Create the list of lengths based on the ascii characters
            int[] lengths = new int[input.length()+5];
            for (int j=0; j<input.length(); ++j){
                lengths[j] = (int)input.charAt(j);
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
            for (int j=0; j<list.length; ++j){
                list[j] = j;
            }

            // Perform the twists 64 times
            for (int j=0; j<64; ++j){
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
            for (int j=0; j<16; ++j){
                // Create the dense value by XORing each one
                int value = list[j*16];
                for (int k=1; k<16; ++k){
                    value ^= list[j*16+k];
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

            // Loop through every character in the hash
            for (int j=0; j<hash.length(); ++j){
                // Convert the hexadecimal to binary
                String bin = Integer.toBinaryString(Integer.parseInt(hash.substring(j,j+1),16));
                
                if (PART == 2){
                    // Add leading 0s
                    while (bin.length() < 4){
                        bin = "0" + bin;
                    }
                }

                // Loop through each bit
                for (int k=0; k<bin.length(); ++k){
                    // If the bit is active
                    if (bin.charAt(k) == '1'){
                        // Part 1 counts the active bits
                        if (PART == 1){
                            ++total;
                        }
                        
                        // Part 2 counts the 2d groups of active bits
                        if (PART == 2){
                            // Set the corresponding grid position to true
                            grid[i][j*4+k] = true;
                        }
                    }
                }
            }
        }

        // Count the number of groups within the grid
        if (PART == 2){
            // Loop through every bit
            for (int i=0; i<128; ++i){
                for (int j=0; j<128; ++j){
                    // If the bit is active
                    if (grid[i][j]){
                        // Increase the number of groups
                        ++total;
                        // Breadth first search to find every bit in the group
                        ArrayList<String> queue = new ArrayList<>();
                        // Add the initial position
                        queue.add(i + " " + j);

                        // Perform the BFS
                        while (!queue.isEmpty()){
                            // Grab the x and y of the position
                            String[] position = queue.remove(0).split(" ");
                            int x = Integer.parseInt(position[0]);
                            int y = Integer.parseInt(position[1]);
                            // Deactivate the bit
                            grid[x][y] = false;

                            // Look in each direction and add active bits
                            if (x > 0 && grid[x-1][y]){
                                queue.add(x-1 + " " + y);
                            }
                            if (x < 127 && grid[x+1][y]){
                                queue.add(x+1 + " " + y);
                            }
                            if (y > 0 && grid[x][y-1]){
                                queue.add(x + " " + (y-1));
                            }
                            if (y < 127 && grid[x][y+1]){
                                queue.add(x + " " + (y+1));
                            }
                        }
                    }
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}