/*
Henry Anderson
Advent of Code 2019 Day 24 https://adventofcode.com/2019/day/24
Input: https://adventofcode.com/2019/day/24/input
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
        // Part 1 finds the first repeated biodiversity rating
        if (PART == 1){
            // Static sized 5x5 grid
            boolean[][] grid = new boolean[5][5];
            // The starting state
            String state = "";
            // Loop through each row
            for (int i=0; i<5; ++i){
                String line = sc.nextLine();
                // Loop through each column
                for (int j=0; j<5; ++j){
                    // Add whether there's a bug
                    grid[i][j] = line.charAt(j) == '#';
                }
                state += Arrays.toString(grid[i]);
            }

            // History to find the first duplicate
            ArrayList<String> history = new ArrayList<>();

            // Continue until duplicated
            while (!history.contains(state)){
                history.add(state);
                // Record the next state
                state = "";
                // The next grid
                boolean[][] newGrid = new boolean[5][5];
                // Loop through each spot
                for (int i=0; i<5; ++i){
                    for (int j=0; j<5; ++j){
                        // The number of adjacent bugs
                        int count = 0;
                        // Loop in each direction
                        for (int k=0; k<4; ++k){
                            int x = j;
                            int y = i;
                            switch(k){
                                case 0 -> {--x;}
                                case 1 -> {--y;}
                                case 2 -> {++x;}
                                case 3 -> {++y;}
                            }
                            // If it's a bug, add
                            if (x >= 0 && y >= 0 && x < 5 && y < 5 && grid[y][x]){
                                ++count;
                            }
                        }
                        // Whether or not a bug stays or is created at this location
                        newGrid[i][j] = count == 1 || count == 2 && !grid[i][j];
                    }
                    state += Arrays.toString(newGrid[i]);
                }
                grid = newGrid;
            }

            // The rating is an integer representation from least to most significant bit
            int rating = 0;
            // Loop through each spot backwards
            for (int i=4; i>=0; --i){
                for (int j=4; j>=0; --j){
                    // Multiply by 2
                    rating <<= 1;
                    // Add if there's a bug
                    if (grid[i][j]){
                        ++rating;
                    }
                }
            }

            // Print the answer
            System.out.println(rating);
        }

        // Part 2 finds the number of bugs after 200 minutes with recursion
        if (PART == 2){
            // An abstracted representation of bugs existing at "x y depth"
            HashSet<String> bugs = new HashSet<>();
            // Loop through each spot
            for (int i=0; i<5; ++i){
                String line = sc.nextLine();
                for (int j=0; j<5; ++j){
                    // Add if there's a bug
                    if (line.charAt(j) == '#'){
                        bugs.add(j + " " + i + " 0");
                    }
                }
            }

            // Loop through each minute
            for (int i=0; i<200; ++i){
                HashSet<String> newBugs = new HashSet<>();

                // Loop through each possible depth
                for (int depth=-1-i/2; depth<=i/2+1; ++depth){
                    // Loop through each spot
                    for (int y=0; y<5; ++y){
                        for (int x=0; x<5; ++x){
                            // Middles can't have bugs
                            if (y == 2 && x == 2){
                                continue;
                            }
                            // The number of adjacent bugs
                            int count = 0;
                            // Look in each direction
                            for (int m=0; m<4; ++m){
                                switch(m){
                                    // Up
                                    case 0 -> {
                                        if (y == 0){
                                            // Back out one depth
                                            if (bugs.contains("2 1 " + (depth-1))){
                                                ++count;
                                            }
                                        }else if (y == 3 && x == 2){
                                            // Go in one depth
                                            for (int n=0; n<5; ++n){
                                                if (bugs.contains(n + " 4 " + (depth+1))){
                                                    ++count;
                                                }
                                            }
                                        }else if (bugs.contains(x + " " + (y-1) + " " + depth)){
                                            // Just look up
                                            ++count;
                                        }
                                    }
                                    // Right
                                    case 1 -> {
                                        if (x == 4){
                                            // Back out one depth
                                            if (bugs.contains("3 2 " + (depth-1))){
                                                ++count;
                                            }
                                        }else if (y == 2 && x == 1){
                                            // Go in one depth
                                            for (int n=0; n<5; ++n){
                                                if (bugs.contains("0 " + n + " " + (depth+1))){
                                                    ++count;
                                                }
                                            }
                                        }else if (bugs.contains(x+1 + " " + y + " " + depth)){
                                            // Just look right
                                            ++count;
                                        }
                                    }
                                    // Down
                                    case 2 -> {
                                        if (y == 4){
                                            // Back out one depth
                                            if (bugs.contains("2 3 " + (depth-1))){
                                                ++count;
                                            }
                                        }else if (y == 1 && x == 2){
                                            // Go in one depth
                                            for (int n=0; n<5; ++n){
                                                if (bugs.contains(n + " 0 " + (depth+1))){
                                                    ++count;
                                                }
                                            }
                                        }else if (bugs.contains(x + " " + (y+1) + " " + depth)){
                                            // Just look down
                                            ++count;
                                        }
                                    }
                                    // Left
                                    case 3 -> {
                                        if (x == 0){
                                            // Back out one depth
                                            if (bugs.contains("1 2 " + (depth-1))){
                                                ++count;
                                            }
                                        }else if (y == 2 && x == 3){
                                            // Go in one depth
                                            for (int n=0; n<5; ++n){
                                                if (bugs.contains("4 " + n + " " + (depth+1))){
                                                    ++count;
                                                }
                                            }
                                        }else if (bugs.contains(x-1 + " " + y + " " + depth)){
                                            // Just look left
                                            ++count;
                                        }
                                    }
                                }
                            }
                            // Add a bug if it survives or is created
                            if (count == 1 || count == 2 && !bugs.contains(x + " " + y + " " + depth)){
                                newBugs.add(x + " " + y + " " + depth);
                            }
                        }
                    }
                }

                bugs = newBugs;
            }

            // Print the answer
            System.out.println(bugs.size());
        }
    }
}