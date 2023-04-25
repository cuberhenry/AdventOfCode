/*
Henry Anderson
Advent of Code 2022 Day 7 https://adventofcode.com/2022/day/7
Input: https://adventofcode.com/2022/day/7/input
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
        // The number being searched for
        int total = 0;
        // The total size of all files
        int sum = 0;
        // Stack representing the current directory hierarchy
        Stack<Integer> numStack = new Stack<>();
        // List of all directory sizes
        ArrayList<Integer> array = new ArrayList<>();

        // Loop through all lines of input
        for (int i=0; i<979; ++i){
            // Current command or display
            String line = sc.nextLine();
            
            // Going one directory up
            if (line.equals("$ cd ..")){
                // The size of the current directory
                int num = numStack.pop();
                
                // Part 1 finds the total size of all directories that have a
                // size of at most 100000
                if (PART == 1){
                    if (num < 100000){
                        total += num;
                    }
                }
                
                // Part 2 finds the smallest directory that could be deleted to
                // preserve a maximum size of 40000000
                if (PART == 2){
                    array.add(num);
                }
                
                // Add current directory size to the parent directory
                if (!numStack.isEmpty()){
                    numStack.push(num+numStack.pop());
                }
            // File display
            }else if (Character.isDigit(line.charAt(0))){
                // Take in file size, ignoring the name
                int num = Integer.parseInt(line.substring(0,line.indexOf(' ')));
                
                // Add to the sum of all files
                if (PART == 2){
                    sum += num;
                }
                
                // Add file size to the parent directory
                if (!numStack.isEmpty()){
                    numStack.push(num+numStack.pop());
                }
            // Changing directory
            }else if (line.substring(0,3).equals("$ c")){
                numStack.push(0);
            }
        }
        
        // Empty the stack, same as moving up one but in a while loop
        while (!numStack.isEmpty()){
            int num = numStack.pop();
            
            if (PART == 1){
                if (num < 100000){
                    total += num;
                }
            }
            
            if (PART == 2){
                array.add(num);
            }
                        
            if (!numStack.isEmpty()){
                numStack.push(num+numStack.pop());
            }
        }
        
        if (PART == 2){
            total = sum;
            // Loop through all directory sizes
            for (int i=0; i<array.size(); ++i){
                // If this directory is a better size than total, replace it
                if (array.get(i) < total && array.get(i) > sum - 40000000){
                    total = array.get(i);
                }
            }
        }

        System.out.println(total);
    }
}