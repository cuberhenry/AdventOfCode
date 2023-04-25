/*
Henry Anderson
Advent of Code 2022 Day 5 https://adventofcode.com/2022/day/5
Input: https://adventofcode.com/2022/day/5/input
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
        // The rows of crates
        Stack<String> crates = new Stack<>();
        String line = sc.nextLine();
        // The number of stacks of crates
        int numStacks = 0;
        // Put the rows of crates into a stack
        while (line.charAt(1) != '1'){
            if ((line.length()+1) / 4 > numStacks){
                numStacks = (line.length()+1) / 4;
            }
            crates.push(line);
            line = sc.nextLine();
        }
        // Filler line
        sc.nextLine();
        // A list of all of the stacks of crates
        ArrayList<Stack<Character>> stacks = new ArrayList<>();
        for (int i=0; i<numStacks; ++i){
            stacks.add(new Stack<>());
        }
        
        // For every line of input
        while (!crates.isEmpty()){
            line = crates.pop();
            
            // Add the crates to the stack if it exists
            for (int j=0; j<numStacks; ++j){
                if (line.charAt(j*4+1) != ' '){
                    stacks.get(j).push(line.charAt(j*4+1));
                }
            }
        }
        
        // Loop through all remaining lines of input
        while (sc.hasNext()){
            // Take in the three numbers, ignoring the filler words
            sc.next();
            int num = sc.nextInt();
            sc.next();
            int first = sc.nextInt();
            sc.next();
            int sec = sc.nextInt();
            
            // Part 1 moves the crates one at a time
            if (PART == 1){
                // Move num crates from stack first to stack sec
                for (int j=0; j<num; ++j){
                    stacks.get(sec-1).push(stacks.get(first-1).pop());
                }
            }
            
            // Part 2 moves the crates in stacks
            if (PART == 2){
                // Stack to reverse the reversal of the crates
                Stack<Character> tempStack = new Stack<>();
                // Move num crates from stack first to tempStack
                for (int j=0; j<num; ++j){
                    tempStack.push(stacks.get(first-1).pop());
                }
                // Move num crates from tempStack to stack sec
                for (int j=0; j<num; ++j){
                    stacks.get(sec-1).push(tempStack.pop());
                }
            }
        }
        
        // Take the list of all the top crates
        String answer = "";
        for (int i=0; i<numStacks; ++i){
            answer += stacks.get(i).peek();
        }
        System.out.println(answer);
    }
}