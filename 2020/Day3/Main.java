/*
Henry Anderson
Advent of Code 2020 Day 3 https://adventofcode.com/2020/day/3
Input: https://adventofcode.com/2020/day/3/input
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
        // The grid of trees
        ArrayList<String> grid = new ArrayList<>();
        // Take in every line of trees
        while (sc.hasNext()){
            grid.add(sc.nextLine());
        }

        // The number of trees encountered
        int[] numTrees = new int[1];
        // Your current x position
        int[] xPos = new int[1];
        // The change in x every row
        int[] change = {3};

        // Part 1 finds the number of trees found when going down 1, right 3
        // Part 2 finds the product of the number of trees passed in five different routes
        if (PART == 2){
            numTrees = new int[5];
            xPos = new int[5];
            change = new int[] {1,3,5,7};
        }

        // Loop through every row in the grid
        for (int i=1; i<grid.size(); ++i){
            // Loop through every path (except the down 2)
            for (int j=0; j<change.length; ++j){
                // Move to the right
                xPos[j] = (xPos[j] + change[j]) % grid.get(i).length();
                // If you encounter a tree, increase numTrees
                if (grid.get(i).charAt(xPos[j]) == '#'){
                    ++numTrees[j];
                }
            }

            if (PART == 2){
                // Check the right 1 down 2 path
                if (i % 2 == 0){
                    // Move to the right
                    xPos[4] = (xPos[4] + 1) % grid.get(i).length();
                    // If you encounter a tree, increase numTrees
                    if (grid.get(i).charAt(xPos[4]) == '#'){
                        ++numTrees[4];
                    }
                }
            }
        }

        // The answer to the problem
        long answer = 1;

        for (int i=0; i<numTrees.length; ++i){
            answer *= numTrees[i];
        }

        // Print the answer
        System.out.println(answer);
    }
}