/*
Henry Anderson
Advent of Code 2017 Day 19 https://adventofcode.com/2017/day/19
Input: https://adventofcode.com/2017/day/19/input
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
        // Take in the entire input
        ArrayList<String> map = new ArrayList<>();
        while (sc.hasNext()){
            map.add(sc.nextLine());
        }

        // The starting position
        int y = 0;
        int x = map.get(0).indexOf('|');
        // The starting direction (down)
        int dir = 2;
        // The answers to Parts 1 and 2 respectively
        String answer = "";
        int steps = 0;

        // Continue travelling until the end of the path is reached
        while (y >= 0 && x >= 0 && y < map.get(0).length() && x < map.get(0).length() && map.get(y).charAt(x) != ' '){
            // If the current position is a letter, add it to answer
            if (Character.isLetter(map.get(y).charAt(x))){
                answer += map.get(y).charAt(x);
            }

            // If the current position is a crossroads
            if (map.get(y).charAt(x) == '+'){
                // Determine the new direction based on adjacent characters and the current direction
                if (y > 0 && map.get(y-1).charAt(x) != ' ' && dir != 2){
                    // Start going up
                    dir = 0;
                }else if (x < map.get(y).length()-1 && map.get(y).charAt(x+1) != ' ' && dir != 3){
                    // Start going right
                    dir = 1;
                }else if (y < map.size()-1 && map.get(y+1).charAt(x) != ' ' && dir != 0){
                    // Start going down
                    dir = 2;
                }else if (x > 0 && map.get(y).charAt(x-1) != ' ' && dir != 1){
                    // Start going left
                    dir = 3;
                }
            }

            // Move in the current direction
            switch (dir){
                case 0 -> {--y;}
                case 1 -> {++x;}
                case 2 -> {++y;}
                case 3 -> {--x;}
            }
            // Increment the number of steps taken
            ++steps;
        }

        // Part 1 finds the order of characters passed through
        if (PART == 1){
            System.out.println(answer);
        }

        // Part 2 finds the number of steps taken to complete the path
        if (PART == 2){
            System.out.println(steps);
        }
    }
}