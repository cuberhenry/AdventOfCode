/*
Henry Anderson
Advent of Code 2022 Day 1 https://adventofcode.com/2022/day/1
Input: https://adventofcode.com/2022/day/1/input
*/
import java.util.*;
import java.io.*;
public class Main {
    // Select the desired problem to solve
    static int PART = 1;
    static Scanner sc;
    
    static String FILE_NAME = "input.txt";
    public static void main(String args[]) {
        // Take in the part
        if (args.length > 0){
            PART = Integer.parseInt(args[0]);
            if (args.length == 2){
                FILE_NAME = args[1];
            }
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
