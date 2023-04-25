/*
Henry Anderson
Advent of Code 2022 Day 12 https://adventofcode.com/2022/day/12
Input: https://adventofcode.com/2022/day/12/input
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
        // The queue of positions to update
        ArrayList<String> queue = new ArrayList<>();
        // The height of the land
        ArrayList<int[]> grid = new ArrayList<>();
        // The distance from the start
        ArrayList<int[]> dist = new ArrayList<>();
        
        // The position of the goal
        int finX = 0;
        int finY = 0;

        // Take in the first line
        String line = sc.next();
        // The width of the grid
        final int WIDTH = line.length();
        // The default value for distance
        int max = Integer.MAX_VALUE;

        // Take in the grid
        while (true){
            // To be added to grid
            int[] row = new int[WIDTH];
            // To be added to dist
            int[] temp = new int[WIDTH];

            // Take in the next row and initialize values
            for (int i=0; i<WIDTH; ++i){
                // Set altitude of position
                row[i] = line.charAt(i)-'a';
                // Set distance to unknown
                temp[i] = max;

                // Starting position for Part 1
                if (line.charAt(i) == 'S'){
                    finX = i;
                    finY = grid.size();
                    row[i] = 0;
                }

                // Target position
                if (line.charAt(i) == 'E'){
                    queue.add(grid.size()+" "+i);
                    temp[i] = 0;
                    row[i] = 25;
                }
            }

            // Add the new arrays
            grid.add(row);
            dist.add(temp);

            // If there's no more grid, stop taking it in
            if (!sc.hasNext()){
                break;
            }

            // Take in the next row
            line = sc.next();
        }

        // The height of the grid
        final int HEIGHT = grid.size();
        
        // Breadth First Search
        while (!queue.isEmpty()){
            // Take the position values out of the queue
            String[] pos = queue.remove(0).split(" ");
            int i = Integer.parseInt(pos[0]);
            int j = Integer.parseInt(pos[1]);
            
            // Part 1 finds the distance from 'S' to 'F'
            if (PART == 1){
                if (finX == j && finY == i){
                    System.out.println(dist.get(i)[j]);
                    return;
                }
            }
            
            // Part 2 finds the distance from any 'a' to 'F'
            if (PART == 2){
                if (grid.get(i)[j] == 0){
                    System.out.println(dist.get(i)[j]);
                    return;
                }
            }
            // Distance update north
            if (i > 0 && grid.get(i)[j] - grid.get(i-1)[j] <= 1 && dist.get(i-1)[j] == max){
                dist.get(i-1)[j] = dist.get(i)[j] + 1;
                queue.add(i-1+" "+j);
            }
            // Distance update south
            if (i < HEIGHT-1 && grid.get(i)[j] - grid.get(i+1)[j] <= 1 && dist.get(i+1)[j] == max){
                dist.get(i+1)[j] = dist.get(i)[j] + 1;
                queue.add(i+1+" "+j);
            }
            // Distance update west
            if (j > 0 && grid.get(i)[j] - grid.get(i)[j-1] <= 1 && dist.get(i)[j-1] == max){
                dist.get(i)[j-1] = dist.get(i)[j] + 1;
                queue.add(i+" "+(j-1));
            }
            // Distance update east
            if (j < WIDTH-1 && grid.get(i)[j] - grid.get(i)[j+1] <= 1 && dist.get(i)[j+1] == max){
                dist.get(i)[j+1] = dist.get(i)[j] + 1;
                queue.add(i+" "+(j+1));
            }
        }
    }
}