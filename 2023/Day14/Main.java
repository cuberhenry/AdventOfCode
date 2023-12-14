/*
Henry Anderson
Advent of Code 2023 Day 14 https://adventofcode.com/2023/day/14
Input: https://adventofcode.com/2023/day/14/input
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
        // The map of rocks
        ArrayList<String> map = new ArrayList<>();
        // Take in the entire input
        while (sc.hasNext()){
            map.add(sc.nextLine());
        }

        // The total load on the north support beams
        long total = 0;

        // Part 1 finds the load after tilting north once
        if (PART == 1){
            // Loop through every column
            for (int i=0; i<map.get(0).length(); ++i){
                // The first open space for a rock to roll into
                int topRock = 0;
                // Loop through every row
                for (int j=0; j<map.size(); ++j){
                    // Depending on which rock
                    if (map.get(j).charAt(i) == '#'){
                        // Cube rocks stop round rocks
                        topRock = j+1;
                    }else if (map.get(j).charAt(i) == 'O'){
                        // It rolls up and fills the open space
                        total += map.size() - topRock;
                        ++topRock;
                    }
                }
            }
        }

        // Part 2 finds the load after tilting in every direction a billion times
        if (PART == 2){
            // The list of previous situations
            ArrayList<String> history = new ArrayList<>();

            // Whether the loop has been found
            boolean loopFound = false;
            // Perform 1 billion cycles
            for (long i=0; i<1000000000; ++i){
                // Tilt North
                // Loop through every column
                for (int j=0; j<map.get(0).length(); ++j){
                    // The first open space for a rock to roll into
                    int topRock = 0;
                    // Loop through every row
                    for (int k=0; k<map.size(); ++k){
                        // Depending on which rock
                        if (map.get(k).charAt(j) == '#'){
                            // Cube rocks stop round rocks
                            topRock = k+1;
                        }else if (map.get(k).charAt(j) == 'O'){
                            // If the rock isn't already in position
                            if (topRock != k){
                                // Move the rock up
                                String upper = map.get(topRock);
                                String lower = map.get(k);
                                map.set(topRock,upper.substring(0,j) + "O" + upper.substring(j+1));
                                map.set(k,lower.substring(0,j) + "." + lower.substring(j+1));
                            }
                            // The next available space is down one
                            ++topRock;
                        }
                    }
                }
                // Tilt West
                // Loop through every row
                for (int j=0; j<map.size(); ++j){
                    // The first open space for a rock to roll into
                    int topRock = 0;
                    // Loop through every column
                    for (int k=0; k<map.get(0).length(); ++k){
                        // Depending on which rock
                        if (map.get(j).charAt(k) == '#'){
                            // Cube rocks stop round rocks
                            topRock = k+1;
                        }else if (map.get(j).charAt(k) == 'O'){
                            // If the rock isn't already in position
                            if (topRock != k){
                                // Move the rock left
                                String s = map.get(j);
                                s = s.substring(0,topRock) + "O" + s.substring(topRock+1,k) + "." + s.substring(k+1);
                                map.set(j,s);
                            }
                            // The next available space is right one
                            ++topRock;
                        }
                    }
                }
                // Tilt South
                // Loop through every column
                for (int j=0; j<map.get(0).length(); ++j){
                    // The first open space for a rock to roll into
                    int topRock = map.size()-1;
                    // Loop through every row
                    for (int k=map.size()-1; k>=0; --k){
                        // Depending on which rock
                        if (map.get(k).charAt(j) == '#'){
                            // Cube rocks stop round rocks
                            topRock = k-1;
                        }else if (map.get(k).charAt(j) == 'O'){
                            // If the rock isn't already in position
                            if (topRock != k){
                                // Move the rock down
                                String upper = map.get(k);
                                String lower = map.get(topRock);
                                map.set(topRock,lower.substring(0,j) + "O" + lower.substring(j+1));
                                map.set(k,upper.substring(0,j) + "." + upper.substring(j+1));
                            }
                            // The next available space is up one
                            --topRock;
                        }
                    }
                }
                // Tilt East
                // Loop through every row
                for (int j=0; j<map.size(); ++j){
                    // The first open space for a rock to roll into
                    int topRock = map.size()-1;
                    // Loop through every column
                    for (int k=map.size()-1; k>=0; --k){
                        // Depending on which rock
                        if (map.get(j).charAt(k) == '#'){
                            // Cube rocks stop round rocks
                            topRock = k-1;
                        }else if (map.get(j).charAt(k) == 'O'){
                            // If the rock isn't already in position
                            if (topRock != k){
                                // Move the rock right
                                String s = map.get(j);
                                s = s.substring(0,k) + "." + s.substring(k+1,topRock) + "O" + s.substring(topRock+1);
                                map.set(j,s);
                            }
                            // The next available space is left one
                            --topRock;
                        }
                    }
                }

                // As long as the loop hasn't been found
                if (!loopFound){
                    // If there's a repeated situation
                    if (history.contains(map.toString())){
                        // Set i to the highest iteration of the loop that's still less than a billion
                        i = 999999999 - (999999999 - i) % (history.size() - history.indexOf(map.toString()));
                        // The loop has been found
                        loopFound = true;
                    }else{
                        // Add the situation to the history
                        history.add(map.toString());
                    }
                }
            }

            // Loop through every row
            for (int i=0; i<map.size(); ++i){
                // Loop through every column
                for (int j=0; j<map.get(0).length(); ++j){
                    // If the rock is round
                    if (map.get(i).charAt(j) == 'O'){
                        // Add to the total load
                        total += map.size()-i;
                    }
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}