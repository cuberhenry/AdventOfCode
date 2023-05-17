/*
Henry Anderson
Advent of Code 2016 Day 8 https://adventofcode.com/2016/day/8
Input: https://adventofcode.com/2016/day/8/input
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
        // The screen of pixels
        boolean[][] screen = new boolean[50][6];

        // Loop through every instruction
        while (sc.hasNext()){
            // Get the instruction
            String line = sc.nextLine();
            // Light up a rectangle
            if (line.substring(0,4).equals("rect")){
                // Get the dimensions of the rectangle
                int width = Integer.parseInt(line.substring(line.indexOf(' ')+1,line.indexOf('x')));
                int height = Integer.parseInt(line.substring(line.indexOf('x')+1));
                // Light the rectangle
                for (int i=0; i<width; ++i){
                    for (int j=0; j<height; ++j){
                        screen[i][j] = true;
                    }
                }
            // Spin a row to the right
            }else if (line.substring(7,10).equals("row")){
                // Get the numbers from the instruction
                int row = Integer.parseInt(line.split(" ")[2].split("=")[1]);
                int num = Integer.parseInt(line.split(" ")[4]);
                // Repeat num times
                for (int i=0; i<num % 50; ++i){
                    // Save the edge
                    boolean save = screen[49][row];
                    // Move the row
                    for (int j=49; j>0; --j){
                        screen[j][row] = screen[j-1][row];
                    }
                    // Put the edge back
                    screen[0][row] = save;
                }
            // Spin a column down
            }else{
                // Get the numbers from the instruction
                int col = Integer.parseInt(line.split(" ")[2].split("=")[1]);
                int num = Integer.parseInt(line.split(" ")[4]);
                // Repeat num times
                for (int i=0; i<num % 6; ++i){
                    // Save the edge
                    boolean save = screen[col][5];
                    // Move the column
                    for (int j=5; j>0; --j){
                        screen[col][j] = screen[col][j-1];
                    }
                    // Put the edge back
                    screen[col][0] = save;
                }
            }
        }

        // Part 1 finds the number of lights that are on
        if (PART == 1){
            // The number of active lights
            int total = 0;
            // Loop through every pixel
            for (int i=0; i<screen.length; ++i){
                for (int j=0; j<screen[0].length; ++j){
                    // Add if the pixel is on
                    if (screen[i][j]){
                        ++total;
                    }
                }
            }
            // Print the answer
            System.out.println(total);
        }

        // Part 2 finds the code it should display
        if (PART == 2){
            // Loop through every pixel
            for (int i=0; i<screen[0].length; ++i){
                for (int j=0; j<screen.length; ++j){
                    // Print the pixel
                    if (screen[j][i]){
                        System.out.print("#");
                    }else{
                        System.out.print(" ");
                    }
                }
                // Print a newline
                System.out.println();
            }
        }
    }
}