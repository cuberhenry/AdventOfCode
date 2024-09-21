/*
Henry Anderson
Advent of Code 2018 Day 3 https://adventofcode.com/2018/day/3
Input: https://adventofcode.com/2018/day/3/input
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
        // The x, y, width, and height of each claim
        ArrayList<int[]> claims = new ArrayList<>();
        // The number of claims for each square of fabric
        int[][] fabric = new int[1000][1000];

        // Repeat for each claim
        while (sc.hasNext()){
            // Collect the information from the line of input
            String[] line = sc.nextLine().split(" ");
            String[] offset = line[2].substring(0,line[2].length()-1).split(",");
            int x = Integer.parseInt(offset[0]);
            int y = Integer.parseInt(offset[1]);
            String[] size = line[3].split("x");
            int width = Integer.parseInt(size[0]);
            int height = Integer.parseInt(size[1]);

            // Increase the number of claims for each square
            for (int i=x; i<x+width; ++i){
                for (int j=y; j<y+height; ++j){
                    ++fabric[i][j];
                }
            }

            if (PART == 2){
                // Add the claim's info
                claims.add(new int[] {x,y,width,height});
            }
        }

        // Part 1 finds the number of overlapping squares
        if (PART == 1){
            int total = 0;
            // Loop through every square
            for (int i=0; i<1000; ++i){
                for (int j=0; j<1000; ++j){
                    // If claimed more than once, increase the total
                    if (fabric[i][j] > 1){
                        ++total;
                    }
                }
            }

            // Print the answer
            System.out.println(total);
        }

        // Part 2 finds the claim that doesn't overlap
        if (PART == 2){
            // Loop through every claim
            for (int i=0; i<claims.size(); ++i){
                int[] claim = claims.get(i);
                boolean found = true;
                // Loop through every square in the claim
                for (int j=claim[0]; j<claim[0]+claim[2] && found; ++j){
                    for (int k=claim[1]; k<claim[1]+claim[3]; ++k){
                        // If claimed more than once, break out
                        if (fabric[j][k] > 1){
                            found = false;
                            break;
                        }
                    }
                }

                // If this is the claim, print the ID
                if (found){
                    System.out.println(i+1);
                    break;
                }
            }
        }
    }
}