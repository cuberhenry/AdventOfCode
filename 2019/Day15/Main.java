/*
Henry Anderson
Advent of Code 2019 Day 15 https://adventofcode.com/2019/day/15
Input: https://adventofcode.com/2019/day/15/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import com.aoc.mylibrary.IntCode;
import com.aoc.mylibrary.Library;
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
        // Create the program
        IntCode program = new IntCode(sc.nextLine());

        // The map of grid pieces found so far
        HashMap<String,Character> map = new HashMap<>();
        // The coordinates of the droid
        int x = 0;
        int y = 0;
        // Starts on an open spot
        map.put("0 0",'.');

        // All of the moves performed from the starting spot
        Stack<Integer> moves = new Stack<>();

        // The coordinates of the oxygen tank
        int oxygenX = 0;
        int oxygenY = 0;

        // Continue until every unknown spot has been identified
        while (true){
            // The direction in which to look
            int dir;
            if (!map.containsKey(x + " " + (y+1))){
                // Look north
                dir = 1;
            }else if (!map.containsKey(x + " " + (y-1))){
                // Look south
                dir = 2;
            }else if (!map.containsKey((x-1) + " " + y)){
                // Look west
                dir = 3;
            }else if (!map.containsKey((x+1) + " " + y)){
                // Look east
                dir = 4;
            }else{
                // If this happens, the entire map has been filled in
                if (moves.isEmpty()){
                    break;
                }
                // Back up one move
                dir = moves.pop();
                program.addInput(dir % 2 == 1 ? dir + 1 : dir - 1);
                switch(dir){
                    case 1 -> {--y;}
                    case 2 -> {++y;}
                    case 3 -> {++x;}
                    case 4 -> {--x;}
                }
                continue;
            }

            // Add the input
            program.addInput(dir);
            // Get the response
            long response = program.run().getLast();
            // The coordinates being examined
            int newX = x;
            int newY = y;
            // Move in the given direction
            switch(dir){
                case 1 -> {++newY;}
                case 2 -> {--newY;}
                case 3 -> {--newX;}
                case 4 -> {++newX;}
            }
            // Wall, don't move
            if (response == 0){
                // Add a wall there
                map.put(newX + " " + newY,'#');
            }else{
                // The move actually happened
                moves.push(dir);
                x = newX;
                y = newY;
                // Add an open spot
                map.put(newX + " " + newY,'.');
                // If it's the oxygen tank, save the coordinates
                if (response == 2){
                    oxygenX = x;
                    oxygenY = y;
                }
            }
        }
        
        // Part 1 finds the distance from start to the oxygen tank
        if (PART == 1){
            System.out.println(Library.BFS(map,oxygenX,oxygenY,0,0));
        }

        // Part 2 finds the number of minutes to fill the whole ship with oxygen
        if (PART == 2){
            System.out.println(Library.BFS(map,oxygenX,oxygenY));
        }
    }
}