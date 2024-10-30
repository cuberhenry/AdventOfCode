/*
Henry Anderson
Advent of Code 2021 Day 15 https://adventofcode.com/2021/day/15
Input: https://adventofcode.com/2021/day/15/input
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
        // Take in the map
        ArrayList<String> scan = new ArrayList<>();
        while (sc.hasNext()){
            scan.add(sc.nextLine());
        }
        int size = scan.size();

        int realSize = size;

        // Part 2 multiplies the square size by 25
        if (PART == 2){
            realSize *= 5;
        }

        // Create a grid representing the smallest risk
        final int[][] risk = new int[realSize][realSize];

        // Initialize all to infinity
        for (int i=0; i<risk.length; ++i){
            for (int j=0; j<risk.length; ++j){
                risk[i][j] = Integer.MAX_VALUE;
            }
        }

        // Start at 0
        risk[0][0] = 0;
        PriorityQueue<int[]> queue = new PriorityQueue<>((a,b) -> {
            return Integer.compare(risk[a[0]][a[1]],risk[b[0]][b[1]]);
        });
        queue.add(new int[] {0,0});

        // Continue until all shortest paths have been found
        while (!queue.isEmpty()){
            // Get the coordinates
            int[] state = queue.remove();
            int x = state[0];
            int y = state[1];

            // Trim branches that can't possibly improve the final answer
            if (risk[y][x] + risk.length-x + risk.length-y >= risk[risk.length-1][risk.length-1]){
                continue;
            }

            // Loop through each direction
            for (int i=0; i<4; ++i){
                // Move in that direction
                int newX = x;
                int newY = y;
                switch(i){
                    case 0 -> {--newX;}
                    case 1 -> {--newY;}
                    case 2 -> {++newX;}
                    case 3 -> {++newY;}
                }

                // If it's within the bounds of risk
                if (newY >= 0 && newX >= 0 && newX < risk.length && newY < risk.length){
                    // Calculate the risk path level after moving to this spot
                    int newRisk = scan.get(newY%size).charAt(newX%size)-'0'+newY/size+newX/size;
                    if (newRisk > 9){
                        newRisk -= 9;
                    }
                    newRisk += risk[y][x];

                    // If it's better than the risk level currently there
                    if (newRisk < risk[newY][newX]){
                        // Set it
                        risk[newY][newX] = newRisk;
                        // Add this new spot if it can improve the final answer
                        if (newRisk + risk.length-newX + risk.length-newY < risk[risk.length-1][risk.length-1]){
                            queue.add(new int[] {newX,newY});
                        }
                    }
                }
            }
        }

        // Print the answer
        System.out.println(risk[risk.length-1][risk.length-1]);
    }
}