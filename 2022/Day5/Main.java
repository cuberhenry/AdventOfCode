import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    final private static String name = "Day 5: Supply Stacks";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        // The rows of crates
        Stack<String> crates = new Stack<>();
        String line = sc.nextLine();
        // The number of stacks of crates
        int numStacks = 0;
        // Put the rows of crates into a stack
        while (line.charAt(1) != '1'){
            numStacks = Math.max(numStacks, (line.length()+1) / 4);
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

        // Make a copy
        ArrayList<Stack<Character>> copy = new ArrayList<>();
        for (Stack<Character> stack : stacks){
            Stack<Character> duplicate = new Stack<>();
            duplicate.addAll(stack);
            copy.add(duplicate);
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
            
            // Move num crates from stack first to stack sec
            for (int j=0; j<num; ++j){
                stacks.get(sec-1).push(stacks.get(first-1).pop());
            }
            
            // Stack to reverse the reversal of the crates
            Stack<Character> tempStack = new Stack<>();
            // Move num crates from stack first to tempStack
            for (int j=0; j<num; ++j){
                tempStack.push(copy.get(first-1).pop());
            }
            // Move num crates from tempStack to stack sec
            for (int j=0; j<num; ++j){
                copy.get(sec-1).push(tempStack.pop());
            }
        }
        
        // Take the list of all the top crates
        String part1 = "";
        String part2 = "";
        for (int i=0; i<numStacks; ++i){
            part1 += stacks.get(i).peek();
            part2 += copy.get(i).peek();
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}