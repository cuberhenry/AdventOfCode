/*
Henry Anderson
Advent of Code 2022 Day 4 https://adventofcode.com/2022/day/4
Input: https://adventofcode.com/2022/day/4/input
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
        PART = Integer.parseInt(args[0]);
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
        // Total number of relevant elf pairs
        int total = 0;
        
        // Loop through all lines of input
        while (sc.hasNext()){
            // Take in the four numbers, ignoring the filler characters
            String line = sc.nextLine();
            int start1 = Integer.parseInt(line.substring(0,line.indexOf('-')));
            line = line.substring(line.indexOf('-')+1);
            int end1 = Integer.parseInt(line.substring(0,line.indexOf(',')));
            line = line.substring(line.indexOf(',')+1);
            int start2 = Integer.parseInt(line.substring(0,line.indexOf('-')));
            line = line.substring(line.indexOf('-')+1);
            int end2 = Integer.parseInt(line);
            
            // Part 1 finds the number of sections contained in other sections
            if (PART == 1){
                // If one is inside the other
                if (start1 <= start2 && end1 >= end2
                    || start2 <= start1 && end2 >= end1){
                    total++;
                }
            }
            
            // Part 2 finds the number of pairs of sections that overlap
            if (PART == 2){
                // If both starts are before both ends
                if (start1 <= end2 && start2 <= end1){
                    total++;
                }
            }
        }
        
        System.out.println(total);
    }
}