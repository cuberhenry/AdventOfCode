/*
Henry Anderson
Advent of Code 2020 Day 24 https://adventofcode.com/2020/day/24
Input: https://adventofcode.com/2020/day/24/input
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
        // The tiles that are black
        ArrayList<String> black = new ArrayList<>();
        // The boundaries of the black tiles
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        // Loop through every input
        while (sc.hasNext()){
            // The coordinates of the new black tile
            int x = 0;
            int y = 0;
            // Take in the next input
            String line = sc.nextLine();
            
            // Loop through every character
            for (int i=0; i<line.length(); ++i){
                // Decide a direction based on the character
                switch(line.charAt(i)){
                    // East
                    case 'e' -> {++x;}
                    // West
                    case 'w' -> {--x;}
                    // Northwest or northeast
                    case 'n' -> {
                        ++y;
                        if (line.charAt(++i) == 'w'){
                            --x;
                        }
                    }
                    // Southwest or southeast
                    case 's' -> {
                        --y;
                        if (line.charAt(++i) == 'e'){
                            ++x;
                        }
                    }
                }
            }

            // Save the new mins and maxes
            minX = Math.min(minX,x);
            maxX = Math.max(maxX,x);
            minY = Math.min(minY,y);
            maxY = Math.max(maxY,y);
            // Toggle the tile
            String coords = x + " " + y;
            if (black.contains(coords)){
                black.remove(coords);
            }else{
                black.add(coords);
            }
        }

        // Part 1 finds the number of black tiles after all the toggles
        // Part 2 finds the number of black tiles after 100 days
        if (PART == 2){
            // Loop through 100 days
            for (int a=0; a<100; ++a){
                // Expand the borders by one
                --minX;
                ++maxX;
                --minY;
                ++maxY;
                // All updates happen at the same time
                ArrayList<String> newBlack = new ArrayList<String>();
                int newMinX = Integer.MAX_VALUE;
                int newMaxX = Integer.MIN_VALUE;
                int newMinY = Integer.MAX_VALUE;
                int newMaxY = Integer.MIN_VALUE;

                // Loop through each tile within the bounds
                for (int i=minX; i<=maxX; ++i){
                    for (int j=minY; j<=maxY; ++j){
                        // The number of black neighbors
                        int count = 0;
                        // East
                        if (black.contains((i+1) + " " + j)){
                            ++count;
                        }
                        // West
                        if (black.contains((i-1) + " " + j)){
                            ++count;
                        }
                        // Northeast
                        if (black.contains(i + " " + (j+1))){
                            ++count;
                        }
                        // Northwest
                        if (black.contains(i + " " + (j-1))){
                            ++count;
                        }
                        // Southwest
                        if (black.contains((i+1) + " " + (j-1))){
                            ++count;
                        }
                        // Southeast
                        if (black.contains((i-1) + " " + (j+1))){
                            ++count;
                        }

                        // If the tile is black next time, save it
                        boolean isBlack = black.contains(i + " " + j);
                        if (isBlack && count > 0 && count < 3 || !isBlack && count == 2){
                            newBlack.add(i + " " + j);
                            newMinX = Math.min(newMinX,i);
                            newMaxX = Math.max(newMaxX,i);
                            newMinY = Math.min(newMinY,j);
                            newMaxY = Math.max(newMaxY,j);
                        }
                    }
                }

                // Move on to the next day
                black = newBlack;
                minX = newMinX;
                maxX = newMaxX;
                minY = newMinY;
                maxY = newMaxY;
            }
        }

        // Print the answer
        System.out.println(black.size());
    }
}