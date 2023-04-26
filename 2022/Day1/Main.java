/*
Henry Anderson
Advent of Code 2022 Day 1 https://adventofcode.com/2022/day/1
Input: https://adventofcode.com/2022/day/1/input
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
        // Used to save amount of calories for each elf
        ArrayList<Integer> array = new ArrayList<>();
        // Temporary total of each elf
        int total = 0;

        // Loop through all lines of input
        while (sc.hasNext()){
            // Take in the line so as not to skip empty lines
            String line = sc.nextLine();
            // If the line is empty, move on to the next elf
            if (line.equals("")){
                array.add(total);
                total = 0;
            }else{
                total += Integer.parseInt(line);
            }
        }
        // Check to see if last elf hasn't been checked
        if (total != 0){
            array.add(total);
        }
        
        // Sort and reverse so the biggest numbers are most easily available
        Collections.sort(array);
        Collections.reverse(array);
        
        // Part 1 finds the elf with the most calories
        if (PART == 1){
            System.out.println(array.get(0));
        }
        
        // Part 2 finds the sum of the three elves with the most calories
        if (PART == 2){
            System.out.println(array.get(0) + array.get(1) + array.get(2));
        }
    }
}
