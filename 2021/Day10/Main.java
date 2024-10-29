/*
Henry Anderson
Advent of Code 2021 Day 10 https://adventofcode.com/2021/day/10
Input: https://adventofcode.com/2021/day/10/input
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
        // The syntax error score
        long total = 0;
        // The completion string scores
        ArrayList<Long> scores = new ArrayList<>();
        // The point values for each character
        HashMap<Character,Integer> points = new HashMap<>();

        // Part 1 finds sum of scores for each corrupted line
        if (PART == 1){
            points.put(')',3);
            points.put(']',57);
            points.put('}',1197);
            points.put('>',25137);
        }

        // Part 2 finds the middle score of the incomplete lines
        if (PART == 2){
            points.put(')',1);
            points.put(']',2);
            points.put('}',3);
            points.put('>',4);
        }

        // Loop through every line
        while (sc.hasNext()){
            // Create a stack for the characters
            Stack<Character> stack = new Stack<>();
            String line = sc.nextLine();
            // Loop through every character
            for (int i=0; i<line.length(); ++i){
                switch(line.charAt(i)){
                    // If it's an open, push the close onto the stack
                    case '(' -> {stack.push(')');}
                    case '[' -> {stack.push(']');}
                    case '{' -> {stack.push('}');}
                    case '<' -> {stack.push('>');}
                    default -> {
                        // Otherwise check for a close
                        if (line.charAt(i) != stack.pop()){
                            // No close means corrupted, add the score and break
                            total += points.get(line.charAt(i));
                            line = "";
                        }
                    }
                }
            }

            if (PART == 2){
                // If the line isn't corrupted
                if (line.length() != 0){
                    // Calculate the completion score
                    long score = 0;
                    // Continue until each open has a close
                    while (!stack.isEmpty()){
                        score *= 5;
                        score += points.get(stack.pop());
                    }
                    scores.add(score);
                }
            }
        }

        if (PART == 1){
            // Print the answer
            System.out.println(total);
        }

        if (PART == 2){
            // Sort the scores
            Collections.sort(scores);
            // Print the middle score
            System.out.println(scores.get(scores.size()/2));
        }
    }
}