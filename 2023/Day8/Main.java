/*
Henry Anderson
Advent of Code 2023 Day 8 https://adventofcode.com/2023/day/8
Input: https://adventofcode.com/2023/day/8/input
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
        // The list of lefts and rights
        String directions = sc.next();
        // Skip newline characters
        sc.nextLine();
        sc.nextLine();
        
        // The next location based on current location and direction
        HashMap<String,String> hash = new HashMap<>();
        // The current positions
        String currPos1 = "";
        ArrayList<String> currPos2 = new ArrayList<>();

        // Take in every line of input
        while (sc.hasNext()){
            // Dissect the input
            String[] split = sc.nextLine().split(" ");
            String[] line = new String[3];
            line[0] = split[0];
            line[1] = split[2].substring(1,4);
            line[2] = split[3].substring(0,3);
            // Add each direction into the map
            hash.put(line[0]+"L",line[1]);
            hash.put(line[0]+"R",line[2]);

            // Part 1 finds the distance from AAA to ZZZ
            if (PART == 1){
                if (line[0].equals("AAA")){
                    currPos1 = line[0];
                }
            }
            
            // Part 2 starts at all locations that end in A and simultaneously
            // ends up at all locations that end in Z
            if (PART == 2){
                if (line[0].charAt(2) == 'A'){
                    currPos2.add(line[0]);
                }
            }
        }

        // The answer to the problem
        long total = 0;
        
        if (PART == 1){
            // Continue traversing until the destination is found
            while (!currPos1.equals("ZZZ")){
                // Move forward one location
                currPos1 = hash.get(currPos1 + (directions.charAt((int)total%directions.length())));
                // Increase the number of steps taken
                ++total;
            }
        }

        // Part 2 uses large enough numbers that require finding loops
        if (PART == 2){
            // All previous locations visited for each simultaneous position
            ArrayList<ArrayList<String>> history = new ArrayList<>();
            // Add the starting position to each history
            for (String pos : currPos2){
                ArrayList<String> hist = new ArrayList<>();
                hist.add("0" + pos);
                history.add(hist);
            }

            // The number of steps taken for the loops
            int steps = 0;
            // Increase total for muliplicative purposes
            ++total;
            // Continue until each loop has been found
            while (!currPos2.isEmpty()){
                // Loop through each position
                for (int i=0; i<currPos2.size(); ++i){
                    // Move forward one location
                    currPos2.set(i,hash.get(currPos2.get(i) + (directions.charAt(steps%directions.length()))));
                    // If a loop has been found
                    if (history.get(i).contains(steps%directions.length() + currPos2.get(i)) && currPos2.get(i).charAt(2) == 'Z'){
                        // The loop length is half of steps taken
                        // Find the least common multiple
                        long larger = Math.max(total,steps/2+1);
                        long smaller = Math.min(total,steps/2+1);
                        total = larger;
                        while (total % smaller != 0){
                            total += larger;
                        }
                        // Remove the finished loop
                        currPos2.remove(i);
                        history.remove(i);
                        --i;
                    }else{
                        // Add the location to the position's history
                        history.get(i).add(steps%directions.length() + currPos2.get(i));
                    }
                }

                // Increment the number of steps taken
                ++steps;
            }
        }

        // Print the answer
        System.out.println(total);
    }
}