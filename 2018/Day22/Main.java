/*
Henry Anderson
Advent of Code 2018 Day 22 https://adventofcode.com/2018/day/22
Input: https://adventofcode.com/2018/day/22/input
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
        // The depth of the cave, from input
        int depth = Integer.parseInt(sc.nextLine().split(" ")[1]);
        // The coordinates of the man's friend
        String[] coordinates = sc.nextLine().split(" ")[1].split(",");
        int targetX = Integer.parseInt(coordinates[0]);
        int targetY = Integer.parseInt(coordinates[1]);
        // The list of all recorded erosions by coordinates
        HashMap<String,Long> erosions = new HashMap<>();

        // The total amount of risk
        int risk = 0;
        // Loop through every square between the mouth and the target
        for (int i=0; i<=targetY; ++i){
            for (int j=0; j<=targetX; ++j){
                // Calculate erosion
                if (i == 0 && j == 0 || i == targetY && j == targetX){
                    // Erosion is 0 at target and mouth
                    erosions.put(j + " " + i,depth % 20183L);
                }else if (i == 0){
                    // X is 0
                    erosions.put(j + " " + i,(j * 16807L + depth) % 20183);
                }else if (j == 0){
                    // Y is 0
                    erosions.put(j + " " + i,(i * 48271L + depth) % 20183);
                }else{
                    // Based on previous two erosion values
                    erosions.put(j + " " + i,(erosions.get((j-1)+" "+i) * erosions.get(j+" "+(i-1)) + depth) % 20183);
                }
                // Add the risk, which is the same as the type
                risk += erosions.get(j + " " + i) % 3;
            }
        }

        // Part 1 finds the total risk of the cave between you and the target
        if (PART == 1){
            System.out.println(risk);
        }

        // Part 2 finds the fastest way to the target
        if (PART == 2){
            // The distances to each square
            HashMap<String,Integer> dists = new HashMap<>();
            // Organize states based on Djikstra's
            PriorityQueue<String> states = new PriorityQueue<>((a,b) -> {
                String[] aSplit = ((String)a).split(" ");
                String[] bSplit = ((String)b).split(" ");
                int aDist = Integer.parseInt(aSplit[0]) + Integer.parseInt(aSplit[1]);
                int bDist = Integer.parseInt(bSplit[0]) + Integer.parseInt(bSplit[1]);
                if (aDist < bDist){
                    return -1;
                }
                if (aDist > bDist){
                    return 1;
                }
                if (dists.containsKey(a) && dists.containsKey(b)){
                    int distA = dists.get(a);
                    int distB = dists.get(b);
                    if (distA < distB){
                        return -1;
                    }
                    if (distA > distB){
                        return 1;
                    }
                }
                return 0;
            });
            // Add the initial state
            states.add("0 0 1");
            dists.put("0 0 1",0);

            // The current minimum distance, starting with the maximum
            // possible distance, assuming a tool change every step
            int minDist = (targetX + targetY) * 8 + 7;
            // The limits of the calculated erosions
            int maxX = targetX;
            int maxY = targetY;
            // Continue until all states have been searched
            while (!states.isEmpty()){
                // Take the state
                String state = states.remove();
                String[] split = state.split(" ");
                // Get the relevant values for the current state
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                int terrain = (int)(erosions.get(x + " " + y) % 3);
                int tool = Integer.parseInt(split[2]);
                int dist = dists.get(state);
                // Trim states that can't beat minDist even with no tool changes
                if (dist + Math.abs(targetX-x) + Math.abs(targetY-y) >= minDist){
                    continue;
                }

                // Loop in each direction
                for (int i=0; i<4; ++i){
                    // Perform a step
                    int newX = x;
                    int newY = y;
                    switch(i){
                        case 0 -> {--newX;}
                        case 1 -> {--newY;}
                        case 2 -> {++newX;}
                        case 3 -> {++newY;}
                    }
                    // Stay within the boundaries of the cave
                    if (newX < 0 || newY < 0){
                        continue;
                    }
                    // Expand the inner boundaries if necessary
                    if (newX > maxX){
                        // Add the base case
                        erosions.put(newX + " 0",(newX * 16807L + depth) % 20183);
                        // Expand from there
                        for (int j=1; j<=maxY; ++j){
                            erosions.put(newX + " " + j,(erosions.get((newX-1)+" "+j) * erosions.get(newX+" "+(j-1)) + depth) % 20183);
                        }
                        ++maxX;
                    }
                    if (newY > maxY){
                        // Add the base case
                        erosions.put("0 " + newY,(newY * 48271L + depth) % 20183);
                        // Expand from there
                        for (int j=1; j<=maxX; ++j){
                            erosions.put(j + " " + newY,(erosions.get((j-1)+" "+newY) * erosions.get(j+" "+(newY-1)) + depth) % 20183);
                        }
                        ++maxY;
                    }

                    // If the new location is the target
                    if (newX == targetX && newY == targetY){
                        // Get the new values
                        int newTerrain = 0;
                        int newDist = dist + 1;
                        int newTool = tool;
                        // Equip a tool if you have none equipped
                        if (newTerrain == newTool){
                            // Increase time spent
                            newDist += 7;
                            newTool = 3 - terrain - newTerrain;
                        }
                        // Must end with the torch
                        if (newTool != 1){
                            newDist += 7;
                        }
                        // Save the new minimum value
                        minDist = Math.min(newDist,minDist);
                        continue;
                    }

                    // Get the new values
                    int newTerrain = (int)(erosions.get(newX + " " + newY) % 3);
                    int newDist = dist + 1;
                    int newTool = tool;
                    // Change tools if it's not compatible with the new terrain
                    if (newTerrain == newTool){
                        newDist += 7;
                        newTool = 3 - terrain - newTerrain;
                    }
                    // The new location and tool
                    String newState = newX + " " + newY + " " + newTool;

                    // Trim
                    if (dists.containsKey(newState) && newDist >= dists.get(newState)){
                        continue;
                    }
                    if (newDist + Math.abs(targetX-newX) + Math.abs(targetY-newY) >= minDist){
                        continue;
                    }

                    // Add the new state
                    dists.put(newState,newDist);
                    if (!states.contains(newState)){
                        states.add(newState);
                    }
                }
            }

            // Print the answer
            System.out.println(minDist);
        }
    }
}