/*
Henry Anderson
Advent of Code 2021 Day 9 https://adventofcode.com/2021/day/9
Input: https://adventofcode.com/2021/day/9/input
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
        ArrayList<String> heightmap = new ArrayList<>();
        while (sc.hasNext()){
            heightmap.add(sc.nextLine());
        }

        // Part 1 finds the local minima
        if (PART == 1){
            // The total risk
            int risk = 0;
            // Loop through every spot
            for (int i=0; i<heightmap.size(); ++i){
                for (int j=0; j<heightmap.getFirst().length(); ++j){
                    char current = heightmap.get(i).charAt(j);
                    // If there's an adjacent lower spot, skip
                    if (i > 0 && heightmap.get(i-1).charAt(j) <= current){
                        continue;
                    }
                    if (j > 0 && heightmap.get(i).charAt(j-1) <= current){
                        continue;
                    }
                    if (i < heightmap.size()-1 && heightmap.get(i+1).charAt(j) <= current){
                        continue;
                    }
                    if (j < heightmap.getFirst().length()-1 && heightmap.get(i).charAt(j+1) <= current){
                        continue;
                    }
                    // Add the risk level
                    risk += current - '0' + 1;
                }
            }

            // Print the answer
            System.out.println(risk);
        }

        // Part 2 finds the three largest basins
        if (PART == 2){
            // Convert the map to a boolean grid
            boolean[][] grid = new boolean[heightmap.size()][heightmap.getFirst().length()];
            for (int i=0; i<grid.length; ++i){
                for (int j=0; j<grid[i].length; ++j){
                    grid[i][j] = heightmap.get(i).charAt(j) == '9';
                }
            }

            // The three largest basins
            int[] largest = new int[3];
            // Loop through every spot
            for (int i=0; i<grid.length; ++i){
                for (int j=0; j<grid[i].length; ++j){
                    // If it's not a 9 and hasn't been found
                    if (!grid[i][j]){
                        // Breadth first search for the basin
                        ArrayList<String> queue = new ArrayList<>();
                        queue.add(i + " " + j);
                        int size = 0;

                        while (!queue.isEmpty()){
                            String[] split = queue.removeFirst().split(" ");
                            int y = Integer.parseInt(split[0]);
                            int x = Integer.parseInt(split[1]);

                            // Already added
                            if (grid[y][x]){
                                continue;
                            }
                            // Add the new spot
                            grid[y][x] = true;
                            ++size;

                            // Look in each direction
                            if (x > 0 && !grid[y][x-1]){
                                queue.add(y + " " + (x-1));
                            }
                            if (y > 0 && !grid[y-1][x]){
                                queue.add(y-1 + " " + x);
                            }
                            if (x < grid[0].length-1 && !grid[y][x+1]){
                                queue.add(y + " " + (x+1));
                            }
                            if (y < grid.length-1 && !grid[y+1][x]){
                                queue.add(y+1 + " " + x);
                            }
                        }
                        
                        // Insert the size into largest if it fits
                        if (size > largest[2]){
                            if (size > largest[1]){
                                largest[2] = largest[1];
                                if (size > largest[0]){
                                    largest[1] = largest[0];
                                    largest[0] = size;
                                }else{
                                    largest[1] = size;
                                }
                            }else{
                                largest[2] = size;
                            }
                        }

                    }
                }
            }

            // Print the answer
            System.out.println(largest[0] * largest[1] * largest[2]);
        }
    }
}