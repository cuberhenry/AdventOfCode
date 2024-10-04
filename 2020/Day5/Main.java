/*
Henry Anderson
Advent of Code 2020 Day 5 https://adventofcode.com/2020/day/5
Input: https://adventofcode.com/2020/day/5/input
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
        // The maximum seat ID
        int max = 0;
        // The list of all seats
        ArrayList<Integer> seats = new ArrayList<>();
        
        // Loop through every boarding pass
        while (sc.hasNext()){
            // Take in the line
            String line = sc.nextLine();
            // The position of the given seat
            int row = 0;
            int col = 0;

            // Find the row
            for (int i=0; i<7; ++i){
                row <<= 1;
                if (line.charAt(i) == 'B'){
                    ++row;
                }
            }
            // Find the column
            for (int i=7; i<10; ++i){
                col <<= 1;
                if (line.charAt(i) == 'R'){
                    ++col;
                }
            }

            // Calculate the seat ID
            int seatID = row * 8 + col;
            // Save the ID
            seats.add(seatID);
            // Save the max
            max = Math.max(max,seatID);
        }

        // Part 1 finds the maximum seat ID
        if (PART == 1){
            System.out.println(max);
        }

        // Part 2 finds your seat ID
        if (PART == 2){
            // Starting with the max, decrease until yours is found
            while (seats.contains(max)){
                --max;
            }

            // Print the answer
            System.out.println(max);
        }
    }
}