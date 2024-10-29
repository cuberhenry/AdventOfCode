/*
Henry Anderson
Advent of Code 2015 Day 9 https://adventofcode.com/2015/day/9
Input: https://adventofcode.com/2015/day/9/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import com.aoc.mylibrary.Library;
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
        // All lines of input
        ArrayList<String[]> input = new ArrayList<>();
        // The names of all of the cities
        ArrayList<String> cities = new ArrayList<>();

        // Take in all of the lines of input
        while (sc.hasNext()){
            // Split them
            String[] line = sc.nextLine().split(" ");
            // If a city isn't in the list of cities, add it
            if (!cities.contains(line[0])){
                cities.add(line[0]);
            }
            if (!cities.contains(line[2])){
                cities.add(line[2]);
            }
            // Add the line to the input
            input.add(new String[] {line[0],line[2],line[4]});
        }

        // The array of distances between cities
        int[][] dist = Library.distMap(cities,input,true);
        // The maximum distance
        int maxDist = 0;
        for (int[] distance : dist){
            for (int d : distance){
                maxDist = Math.max(d,maxDist);
            }
        }

        // The answer to the problem
        int best = Integer.MAX_VALUE;

        // Part 1 finds the shortest distance required to visit all cities
        // Part 2 finds the longest distance to do the same
        if (PART == 2){
            best = 0;
        }

        // The sets of possible paths
        Stack<String> states = new Stack<>();
        // Add each city as a possible starting city
        for (String city : cities){
            states.push("0 "+city);
        }

        // Continue until all paths have been searched
        while (!states.isEmpty()){
            // Take the current state
            String line = states.pop();
            String[] currState = line.split(" ");
            // Grab the distance
            int distance = Integer.parseInt(currState[0]);

            // Prunes states whose distances are already longer than the best solution
            if (PART == 1){
                if (distance >= best){
                    continue;
                }
            }

            // Prune states whose distances can't beat the current max no matter what
            if (PART == 2){
                if (distance + (cities.size()-currState.length+1)*maxDist <= best){
                    continue;
                }
            }

            // If it survived the pruning and it's a finished path, it's the new best
            if (currState.length-1 == cities.size()){
                best = distance;
                continue;
            }

            // Loop through every city
            for (int i=0; i<cities.size(); ++i){
                // If it's unvisited
                if (!line.contains(cities.get(i))){
                    // Create a new distance
                    String newState = ""+(distance+dist[cities.indexOf(currState[1])][i]);
                    // Add all of the visited cities including this new one
                    newState += " " + cities.get(i) + line.substring(line.indexOf(' '));
                    // Add the path to states
                    states.push(newState);
                }
            }
        }

        // Print the answer
        System.out.println(best);
    }
}