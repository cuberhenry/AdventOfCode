/*
Henry Anderson
Advent of Code 2021 Day 25 https://adventofcode.com/2021/day/25
Input: https://adventofcode.com/2021/day/25/input
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
        // Part 2 doesn't require code
        if (PART == 2){
            System.out.println("Remotely Start The Sleigh");
        }

        // The map of sea cucumbers
        ArrayList<char[]> map = new ArrayList<>();
        while (sc.hasNext()){
            map.add(sc.nextLine().toCharArray());
        }

        // Save the dimensions
        int height = map.size();
        int width = map.get(0).length;
        
        // Whether a cucumber moved this round
        boolean moved = true;
        // The number of steps taken
        int step;
        for (step = 0; moved; ++step){
            // The sea cucumbers that can move
            ArrayList<int[]> toMove = new ArrayList<>();
            // Check all coordinates
            for (int i=0; i<height; ++i){
                for (int j=0; j<width; ++j){
                    // If the east-facing cucumber can move
                    if (map.get(i)[j] == '>' && map.get(i)[(j+1)%width] == '.'){
                        // Schedule it to move
                        toMove.add(new int[] {j,i});
                    }
                }
            }
            // Assign moved
            moved = !toMove.isEmpty();
            // Move each scheduled cucumber at the same time
            for (int[] move : toMove){
                map.get(move[1])[move[0]] = '.';
                map.get(move[1])[(move[0]+1)%width] = '>';
            }

            // Start looking for south-facing cucumbers
            toMove.clear();
            // Check all coordinates
            for (int i=0; i<height; ++i){
                for (int j=0; j<width; ++j){
                    // If the south-facing cucumber can move
                    if (map.get(i)[j] == 'v' && map.get((i+1)%height)[j] == '.'){
                        // Schedule it to move
                        toMove.add(new int[] {j,i});
                    }
                }
            }
            // Assign moved
            moved = moved || !toMove.isEmpty();
            // Move each scheduled cucumber at the same time
            for (int[] move : toMove){
                map.get(move[1])[move[0]] = '.';
                map.get((move[1]+1)%height)[move[0]] = 'v';
            }
        }

        // Print the answer
        System.out.println(step);
    }
}