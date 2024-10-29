/*
Henry Anderson
Advent of Code 2021 Day 4 https://adventofcode.com/2021/day/4
Input: https://adventofcode.com/2021/day/4/input
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
        // Get the numbers that are called
        String[] split = sc.nextLine().split(",");
        int[] numbers = new int[split.length];
        for (int i=0; i<split.length; ++i){
            numbers[i] = Integer.parseInt(split[i]);
        }

        // Take in all the boards
        ArrayList<int[][]> boards = new ArrayList<>();
        while (sc.hasNextLine()){
            int[][] board = new int[5][5];
            // Skip the newline
            sc.nextLine();
            // Loop through each row
            for (int i=0; i<5; ++i){
                // Clean up the input
                split = sc.nextLine().trim().replace("  "," ").split(" ");
                // Loop through each number
                for (int j=0; j<5; ++j){
                    board[i][j] = Integer.parseInt(split[j]);
                }
            }
            boards.add(board);
        }

        // Loop through each called number
        for (int number : numbers){
            // Loop through every board
            for (int i=0; i<boards.size(); ++i){
                // Loop through every square in the board
                for (int j=0; j<5; ++j){
                    for (int k=0; k<5; ++k){
                        // If there's a match
                        if (boards.get(i)[j][k] == number){
                            // Mark it
                            boards.get(i)[j][k] = -1;
                            // Look for a horizontal win
                            boolean win = true;
                            for (int l=0; l<5; ++l){
                                if (boards.get(i)[j][l] != -1){
                                    win = false;
                                    break;
                                }
                            }
                            // Look for a vertical win
                            if (!win){
                                win = true;
                                for (int l=0; l<5; ++l){
                                    if (boards.get(i)[l][k] != -1){
                                        win = false;
                                        break;
                                    }
                                }
                            }
                            
                            // If it was a win
                            if (win){
                                // Part 1 finds the score of the first winning board
                                if (PART == 1){
                                    // Add up all the unmarked values
                                    int sum = 0;
                                    for (int l=0; l<5; ++l){
                                        for (int m=0; m<5; ++m){
                                            if (boards.get(i)[l][m] != -1){
                                                sum += boards.get(i)[l][m];
                                            }
                                        }
                                    }

                                    // Print the answer
                                    System.out.println(sum * number);
                                    return;
                                }

                                // Part 2 finds the score of the last winning board
                                if (PART == 2){
                                    // Only if it's the last board
                                    if (boards.size() == 1){
                                        // Add up all the unmarked values
                                        int sum = 0;
                                        for (int l=0; l<5; ++l){
                                            for (int m=0; m<5; ++m){
                                                if (boards.get(i)[l][m] != -1){
                                                    sum += boards.get(i)[l][m];
                                                }
                                            }
                                        }

                                        // Print the answer
                                        System.out.println(sum * number);
                                        return;
                                    }
                                }

                                // Remove the winning board
                                boards.remove(i);

                                // Do looping cleanup
                                if (i == boards.size()){
                                    j = 5;
                                    break;
                                }
                                j = 0;
                                k = -1;
                            }
                        }
                    }
                }
            }
        }
    }
}