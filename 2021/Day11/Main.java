/*
Henry Anderson
Advent of Code 2021 Day 11 https://adventofcode.com/2021/day/11
Input: https://adventofcode.com/2021/day/11/input
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
        // Take in the starting energy levels
        int[][] octopi = new int[10][10];
        for (int i=0; i<10; ++i){
            String line = sc.nextLine();
            for (int j=0; j<10; ++j){
                octopi[i][j] = line.charAt(j) - '0';
            }
        }

        // The answer to the problem
        int answer = 0;
        // Loop until the end conditions, counting the rounds
        for (int i=1; true; ++i){
            // All energy levels increase by 1
            for (int j=0; j<10; ++j){
                for (int k=0; k<10; ++k){
                    ++octopi[j][k];
                }
            }

            // Cause flashes, resettings ones that flashed to 0
            int numFlashes = 0;
            // Loop through every octopus
            for (int j=0; j<10; ++j){
                for (int k=0; k<10; ++k){
                    // If it's ready to flash
                    if (octopi[j][k] >= 10){
                        ++numFlashes;
                        // Set it to 0 energy
                        octopi[j][k] = 0;
                        // Search in every direction
                        for (int l=0; l<8; ++l){
                            int newJ = j;
                            int newK = k;
                            switch (l){
                                case 0 -> {--newJ;}
                                case 1 -> {--newJ; ++newK;}
                                case 2 -> {++newK;}
                                case 3 -> {++newJ; ++newK;}
                                case 4 -> {++newJ;}
                                case 5 -> {++newJ; --newK;}
                                case 6 -> {--newK;}
                                case 7 -> {--newJ; --newK;}
                            }

                            // If the octopus didn't already flash, increase its energy
                            if (newJ >= 0 && newJ < 10 && newK >= 0 && newK < 10 && octopi[newJ][newK] != 0){
                                ++octopi[newJ][newK];
                            }
                        }
                        
                        // Back up to check for caused flashes
                        if (k == 0){
                            if (j != 0){
                                --k;
                            }
                        }else{
                            k -= 2;
                        }
                        if (j != 0){
                            --j;
                        }
                    }
                }
            }

            // Part 1 finds the number of flashes during 100 rounds
            if (PART == 1){
                // Add flashes
                answer += numFlashes;
                // Break after 100 rounds
                if (i == 100){
                    break;
                }
            }

            // Part 2 finds the first synchronized flash round
            if (PART == 2){
                // If all octopi flashed
                if (numFlashes == 100){
                    // The answer is the round number
                    answer = i;
                    break;
                }
            }
        }

        // Print the answer
        System.out.println(answer);
    }
}