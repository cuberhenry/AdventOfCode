/*
Henry Anderson
Advent of Code 2019 Day 8 https://adventofcode.com/2019/day/8
Input: https://adventofcode.com/2019/day/8/input
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
        // The minimum number of 0s in a layer
        int minZeros = Integer.MAX_VALUE;
        // The number of 1s times the number of 2s
        int answer = 0;
        // The grid so far, starting all transparent
        int[][] grid = new int[6][25];
        for (int i=0; i<6; ++i){
            for (int j=0; j<25; ++j){
                grid[i][j] = 2;
            }
        }
        
        // Take in the input
        String input = sc.nextLine();
        int pointer = 0;
        // Loop through every layer
        while (pointer < input.length()){
            // The current layer's info
            int[] counts = new int[3];
            // Loop through every pixel
            for (int i=0; i<6; ++i){
                for (int j=0; j<25; ++j){
                    // If the grid is transparent, show this layer's pixel
                    if (grid[i][j] == 2){
                        grid[i][j] = input.charAt(pointer) - '0';
                    }
                    // Add the values
                    ++counts[input.charAt(pointer)-'0'];
                    ++pointer;
                }
            }
            // If it's a new min, save the new answer
            if (counts[0] < minZeros){
                minZeros = counts[0];
                answer = counts[1] * counts[2];
            }
        }

        // Part 1 finds #1 * #2 where #0 is the least in a layer
        if (PART == 1){
            System.out.println(answer);
        }

        // Part 2 finds the resulting picture
        if (PART == 2){
            for (int i=0; i<6; ++i){
                for (int j=0; j<25; ++j){
                    if (grid[i][j] == 1){
                        System.out.print('#');
                    }else{
                        System.out.print(' ');
                    }
                }
                System.out.println();
            }
        }
    }
}