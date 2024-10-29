/*
Henry Anderson
Advent of Code 2018 Day 20 https://adventofcode.com/2018/day/20
Input: https://adventofcode.com/2018/day/20/input
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
        // Take in the regular expression
        String regex = sc.nextLine();
        // The distance to each of the crossroads
        Stack<String> branches = new Stack<>();
        // The distances to each room
        HashMap<String,Integer> dists = new HashMap<>();
        dists.put("0 0",0);

        // Your current position
        int x = 0;
        int y = 0;

        // Loop through each character between the bookends
        for (char c : regex.substring(1,regex.length()-1).toCharArray()){
            switch(c){
                // Start a new branch
                case '(' -> {
                    // Save the start of the branch
                    branches.push(x + " " + y);
                }
                // Switching to a different branch
                case '|' -> {
                    // Return to the start of the branch
                    String[] split = branches.peek().split(" ");
                    x = Integer.parseInt(split[0]);
                    y = Integer.parseInt(split[1]);
                }
                // Ending a branch
                case ')' -> {
                    // Return to the start of the branch
                    String[] split = branches.pop().split(" ");
                    x = Integer.parseInt(split[0]);
                    y = Integer.parseInt(split[1]);
                }
                // Character representing a new room
                default -> {
                    // Look in the given direction
                    int newX = x;
                    int newY = y;
                    switch(c){
                        case 'N' -> {++newY;}
                        case 'S' -> {--newY;}
                        case 'W' -> {--newX;}
                        case 'E' -> {++newX;}
                    }

                    // Save the new move if it doesn't double back
                    if (!dists.containsKey(newX + " " + newY)){
                        dists.put(newX + " " + newY,dists.get(x + " " + y) + 1);
                    }
                    
                    // Move
                    x = newX;
                    y = newY;
                }
            }
        }

        // Part 1 finds the maximum distance from the start
        if (PART == 1){
            int max = 0;
            for (String key : dists.keySet()){
                max = Math.max(max,dists.get(key));
            }
            System.out.println(max);
        }

        // Part 2 finds the number of rooms at least 1000 doors away
        if (PART == 2){
            int count = 0;
            for (String key : dists.keySet()){
                if (dists.get(key) >= 1000){
                    ++count;
                }
            }
            System.out.println(count);
        }
    }
}