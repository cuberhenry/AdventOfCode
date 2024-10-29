/*
Henry Anderson
Advent of Code 2021 Day 21 https://adventofcode.com/2021/day/21
Input: https://adventofcode.com/2021/day/21/input
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
        // Get each player's starting position
        int p1Pos = Integer.parseInt(sc.nextLine().split(" ")[4])-1;
        int p2Pos = Integer.parseInt(sc.nextLine().split(" ")[4])-1;

        // Part 1 finds the winner using a preset die of 1-100
        if (PART == 1){
            // Each player's score
            int p1Score = 0;
            int p2Score = 0;

            // The number of rolls so far
            int numRolls = 0;
            // Continue until a player has won
            while (true){
                // The total roll for the player
                int roll = 0;
                for (int i=0; i<3; ++i){
                    // Perform three rolls and add their value
                    roll += numRolls%100 + 1;
                    ++numRolls;
                }
                // Change the player's position
                p1Pos = (p1Pos + roll) % 10;
                // Add to the score
                p1Score += p1Pos + 1;
                // If they've won, print and break
                if (p1Score >= 1000){
                    System.out.println(p2Score * numRolls);
                    break;
                }

                // The total roll for the player
                roll = 0;
                for (int i=0; i<3; ++i){
                    // Perform three rolls and add their value
                    roll += numRolls%100 + 1;
                    ++numRolls;
                }
                // Change the player's position
                p2Pos = (p2Pos + roll) % 10;
                // Add to the score
                p2Score += p2Pos + 1;
                // If they've won, print and break
                if (p2Score >= 1000){
                    System.out.println(p1Score * numRolls);
                    break;
                }
            }
        }

        // Part 2 finds the player that wins in the most universes
        if (PART == 2){
            // The number of universes each player has won in
            long p1Wins = 0;
            long p2Wins = 0;
            // This solution uses a finite state machine, as each game
            // can only be in one of 44100 states: each position between
            // 1 and 10 and each score between 0 and 20

            // i = p1Pos + p2Pos*10 + p1Score*100 + p2Score*2100
            // p1Pos = i % 10
            // p2Pos = i / 10 % 10
            // p1Score = i / 100 % 21
            // p2Score = i / 2100
            long[] universes = new long[44100];
            long[] empty = new long[44100];
            // Add the starting state (both scores start at 0)
            ++universes[p1Pos+p2Pos*10];

            // Every turn, a player's possible roll set are the same
            int[] rolls = {0,0,0,1,3,6,7,6,3,1};

            // Continue until all universes have been decided
            while (!Arrays.equals(universes,empty)){
                // Create a new set of universes
                long[] newUniverses = new long[44100];
                // Loop through every active universe
                for (int i=0; i<universes.length; ++i){
                    if (universes[i] == 0){
                        continue;
                    }
                    // Loop through every roll total
                    for (int j=3; j<rolls.length; ++j){
                        // Get player 1's new position and score
                        int newPos = (i+j) % 10;
                        int newScore = i/100%21 + newPos + 1;

                        // If player 1 wins
                        if (newScore >= 21){
                            // Add all active universes in this state (times the new ones created)
                            p1Wins += universes[i] * rolls[j];
                        }else{
                            // Otherwise, move all those universes to the new state
                            newUniverses[newPos + i/10%10*10 + newScore*100 + i/2100*2100] += universes[i] * rolls[j];
                        }
                    }
                }
                universes = newUniverses;

                // Create a new set of universes
                newUniverses = new long[44100];
                // Loop through every active universe
                for (int i=0; i<universes.length; ++i){
                    if (universes[i] == 0){
                        continue;
                    }
                    // Loop through every roll total
                    for (int j=3; j<rolls.length; ++j){
                        // Get player 2's new position and score
                        int newPos = (i/10+j) % 10;
                        int newScore = i/2100 + newPos + 1;

                        // If player 2 wins
                        if (newScore >= 21){
                            // Add all active universes in this state (times the new ones created)
                            p2Wins += universes[i] * rolls[j];
                        }else{
                            // Otherwise, move all those universes to the new state
                            newUniverses[i%10 + newPos*10 + i/100%21*100 + newScore*2100] += universes[i] * rolls[j];
                        }
                    }
                }
                universes = newUniverses;
            }

            // Print the answer
            System.out.println(Math.max(p1Wins,p2Wins));
        }
    }
}