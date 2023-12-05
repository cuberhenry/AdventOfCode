/*
Henry Anderson
Advent of Code 2023 Day 2 https://adventofcode.com/2023/day/2
Input: https://adventofcode.com/2023/day/2/input
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
        // The answer to the problem
        int total = 0;
        // Used for Part 1, the suggested amounts of cubes
        int red = 12;
        int green = 13; 
        int blue = 14;

        // Loop through every game
        while (sc.hasNext()){
            // Used for Part 1, whether the game is possible
            boolean success = true;
            // Used for Part 2, the minimum amount of each cube required
            int thisRed = 0;
            int thisGreen = 0;
            int thisBlue = 0;
            // Take in the game
            String[] line = sc.nextLine().split(" ");

            // Loop through every colored group of cubes
            for (int i=2; i<line.length; i += 2){
                // The number of cubes of a specific type pulled
                int amount = Integer.parseInt(line[i]);

                // Part 1 finds the number of games that are possible with a given number of cubes
                if (PART == 1){
                    // Check the color and compare the amount to the given amount
                    // On failure, move on to the next game
                    if (line[i+1].charAt(0) == 'r' && amount > red){
                        success = false;
                        break;
                    }else if (line[i+1].charAt(0) == 'b' && amount > blue){
                        success = false;
                        break;
                    }else if (line[i+1].charAt(0) == 'g' && amount > green){
                        success = false;
                        break;
                    }
                }

                // Part 2 finds the minimum number of cubes required for any given game to be true
                if (PART == 2){
                    // Check the color and save the new minimum
                    if (line[i+1].charAt(0) == 'r'){
                        thisRed = Math.max(thisRed,amount);
                    }else if (line[i+1].charAt(0) == 'b'){
                        thisBlue = Math.max(thisBlue,amount);
                    }else{
                        thisGreen = Math.max(thisGreen,amount);
                    }
                }
            }

            // Add the game ID if the game was possible
            if (PART == 1){
                if (success){
                    total += Integer.parseInt(line[1].substring(0,line[1].length()-1));
                }
            }

            // Add the power of the minimum set of cubes
            if (PART == 2){
                total += thisRed * thisGreen * thisBlue;
            }
        }

        // Print the answer
        System.out.println(total);
    }
}