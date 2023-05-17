/*
Henry Anderson
Advent of Code 2016 Day 2 https://adventofcode.com/2016/day/2
Input: https://adventofcode.com/2016/day/2/input
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
        // The password
        String password = "";
        // The keypad
        char[][] keypad = {{}};

        // The current position of the button, starting at the 5
        int x = -1;
        int y = -1;

        // Part 1 uses a standard keypad
        if (PART == 1){
            keypad = new char[][] {{'1','2','3'},
                                   {'4','5','6'},
                                   {'7','8','9'}};
            x = 1;
            y = 1;
        }
        
        // Part 2 uses a strange keypad
        if (PART == 2){
            keypad = new char[][] {{' ',' ','1',' ',' '},
                                   {' ','2','3','4',' '},
                                   {'5','6','7','8','9'},
                                   {' ','A','B','C',' '},
                                   {' ',' ','D',' ',' '}};
            x = 0;
            y = 2;
        }

        // Loop through every instruction
        while (sc.hasNext()){
            // Grab the line
            String line = sc.next();
            // Loop through every character in the line
            for (int i=0; i<line.length(); ++i){
                // Move in the desired direction if possible
                switch (line.charAt(i)){
                    case 'U' -> {
                        if (y > 0 && keypad[y-1][x] != ' '){
                            --y;
                        }
                    }
                    case 'D' -> {
                        if (y < keypad.length-1 && keypad[y+1][x] != ' '){
                            ++y;
                        }
                    }
                    case 'L' -> {
                        if (x > 0 && keypad[y][x-1] != ' '){
                            --x;
                        }
                    }
                    case 'R' -> {
                        if (x < keypad.length-1 && keypad[y][x+1] != ' '){
                            ++x;
                        }
                    }
                }
            }

            // Add the button from the end of the line to the password
            password += keypad[y][x];
        }

        // Print the answer
        System.out.println(password);
    }
}