/*
Henry Anderson
Advent of Code 2020 Day 11 https://adventofcode.com/2020/day/11
Input: https://adventofcode.com/2020/day/11/input
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
        // Whether each seat is taken
        HashMap<String,Boolean> seats = new HashMap<>();
        // The number of rows so far
        int i = 0;
        // The horizontal bounds
        int maxX = 0;
        // Take in every row of input
        while (sc.hasNext()){
            // Take in the line
            String line = sc.nextLine();
            maxX = line.length();
            // Add each seat to the hashmap
            for (int j=0; j<line.length(); ++j){
                if (line.charAt(j) == 'L'){
                    seats.put(j + " " + i,false);
                }
            }
            ++i;
        }
        // The vertical bounds
        int maxY = i;

        // Whether a change has been made since the last iteration
        boolean changed = true;
        // Continue until equilibrium is found
        while (changed){
            // No change yet
            changed = false;
            // All changes happen at the same time
            HashMap<String,Boolean> nextSeats = new HashMap<>();
            // Loop through every seat
            for (String key : seats.keySet()){
                // Get the coordinates
                String[] split = key.split(" ");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                // The number of occupied visible seats
                int count = 0;
                // Loop through every direction
                for (i=0; i<8; ++i){
                    int nextX = x;
                    int nextY = y;
                    // Move once in that direction
                    switch(i){
                        case 0 -> {--nextX; --nextY;}
                        case 1 -> {--nextX;}
                        case 2 -> {--nextX; ++nextY;}
                        case 3 -> {++nextY;}
                        case 4 -> {++nextX; ++nextY;}
                        case 5 -> {++nextX;}
                        case 6 -> {++nextX; --nextY;}
                        case 7 -> {--nextY;}
                    }

                    // Part 1 finds equilibrium when each seat looks at only adjacent seats
                    // Part 2 finds equilibrium when each seat looks at each visible seat
                    if (PART == 2){
                        // Continue until beyond the bounds or finding another seat
                        while (!seats.containsKey(nextX + " " + nextY) && nextX >= 0 && nextY >= 0
                                && nextX < maxX && nextY < maxY){
                            switch(i){
                                case 0 -> {--nextX; --nextY;}
                                case 1 -> {--nextX;}
                                case 2 -> {--nextX; ++nextY;}
                                case 3 -> {++nextY;}
                                case 4 -> {++nextX; ++nextY;}
                                case 5 -> {++nextX;}
                                case 6 -> {++nextX; --nextY;}
                                case 7 -> {--nextY;}
                            }
                        }
                    }

                    // If it's occupied, increase count
                    if (seats.containsKey(nextX + " " + nextY) && seats.get(nextX + " " + nextY)){
                        ++count;
                    }
                }

                // If the seat is currently occupied
                if (seats.get(key)){
                    // The number of seats required to empty this seat
                    int tolerance = 4;

                    // Part 2 has a tolerance of 5
                    if (PART == 2){
                        tolerance = 5;
                    }
                    
                    // If it's beyond the tolerance
                    if (count >= tolerance){
                        // The seat empties
                        changed = true;
                        nextSeats.put(key,false);
                    }else{
                        // The seat remains occupied
                        nextSeats.put(key,true);
                    }
                // No other visible occupied seats
                }else if (count == 0){
                    // The seat fills
                    changed = true;
                    nextSeats.put(key,true);
                }else{
                    // The seat remains empty
                    nextSeats.put(key,false);
                }
            }
            // Move on to the next state
            seats = nextSeats;
        }

        // The number of occupied seats at equilibrium
        int count = 0;
        // Search every seat
        for (String key : seats.keySet()){
            if (seats.get(key)){
                ++count;
            }
        }
        // Print the answer
        System.out.println(count);
    }
}