/*
Henry Anderson
Advent of Code 2016 Day 24 https://adventofcode.com/2016/day/24
Input: https://adventofcode.com/2016/day/24/input
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
        // The map of whether each point is accessible
        ArrayList<boolean[]> map = new ArrayList<>();
        // The list of destinations
        ArrayList<String> dests = new ArrayList<>();
        // The robot's starting point
        int startX = 0;
        int startY = 0;

        // Loop through the whole map
        while (sc.hasNext()){
            // Take in the line
            String line = sc.nextLine();
            boolean[] row = new boolean[line.length()];

            // Loop through every value
            for (int i=0; i<line.length(); ++i){
                if (line.charAt(i) == '.'){
                    // Passable
                    row[i] = true;
                }else if (Character.isDigit(line.charAt(i))){
                    // Passable and destination
                    row[i] = true;
                    if (line.charAt(i) == '0'){
                        // startX = i;
                        // startY = map.size();
                        dests.add(0,i + " " + map.size());
                    }else{
                        dests.add(i + " " + map.size());
                    }
                }
            }
            map.add(row);
        }

        // Fill in dead ends
        // Loop through every square
        for (int i=1; i<map.get(0).length-1; ++i){
            for (int j=1; j<map.size()-1; ++j){
                // Skip walls, destinations, and your starting point
                if (!map.get(j)[i] || dests.contains(i + " " + j)
                                   || (i == startX && j == startY)){
                    continue;
                }
                // The number of adjacent walls
                int count = 0;
                if (!map.get(j)[i-1]){
                    ++count;
                }
                if (!map.get(j)[i+1]){
                    ++count;
                }
                if (!map.get(j-1)[i]){
                    ++count;
                }
                if (!map.get(j+1)[i]){
                    ++count;
                }

                // If there are three adjacent walls, this is a dead end
                if (count >= 3){
                    // Count it as a wall
                    map.get(j)[i] = false;
                    // Back up enough to check the surrounding area
                    --i;
                    j -= 2;
                }
            }
        }

        // The distance between every two destinations and the start
        int[][] dists = new int[dests.size()][dests.size()];
        // Loop through every start and destination
        for (int i=0; i<dists.length-1; ++i){
            // All positions visited so far
            ArrayList<String> visited = new ArrayList<>();
            // Current positions at j steps
            ArrayList<String> queue = new ArrayList<>();
            visited.add(dests.get(i));
            queue.add(dests.get(i));
            // The number of destinations found, ignoring previous ones
            int found = dests.size()-i-1;

            // Loop through steps until all destinations have been found
            for (int j=1; found > 0; ++j){
                // The next distance's steps
                ArrayList<String> newQueue = new ArrayList<>();
                // Continue until all dests are found or through every position
                while (!queue.isEmpty() && found > 0){
                    // Get the position
                    String[] split = queue.remove(0).split(" ");
                    int x = Integer.parseInt(split[0]);
                    int y = Integer.parseInt(split[1]);

                    // Loop in every direction
                    for (int k=0; k<4; ++k){
                        int newX = x;
                        int newY = y;
                        // Move that direction
                        switch(k){
                            case 0 -> {++newX;}
                            case 1 -> {--newX;}
                            case 2 -> {++newY;}
                            case 3 -> {--newY;}
                        }
                        // If it's an unvisited and non-wall spot
                        if (!visited.contains(newX + " " + newY) && map.get(newY)[newX]){
                            // If it's an unfound destination
                            if (dests.indexOf(newX + " " + newY) > i){
                                // Declare the mininum distance bidirectionally
                                dists[i][dests.indexOf(newX + " " + newY)] = j;
                                dists[dests.indexOf(newX + " " + newY)][i] = j;
                                --found;
                            }
                            // Add the new position
                            visited.add(newX + " " + newY);
                            newQueue.add(newX + " " + newY);
                        }
                    }
                }

                // Start over with the next distance
                queue = newQueue;
            }
        }

        // Every possible order of destinatinos
        ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();
        permutations.add(new ArrayList<>());
        permutations.get(0).add(0);

        // Loop through every destination
        for (int i=1; i<dests.size(); ++i){
            // The new permutations created, deleting non-complete ones
            ArrayList<ArrayList<Integer>> newPerms = new ArrayList<>();

            // Loop through every current permutation
            for (int j=permutations.size()-1; j>=0; --j){
                // Loop through every post-0 index
                for (int k=permutations.get(j).size(); k>0; --k){
                    // Create a new permutation by adding destination i to permutation j at position k
                    ArrayList<Integer> newPerm = new ArrayList<>(permutations.get(j));
                    newPerm.add(k,i);
                    newPerms.add(newPerm);
                }
            }

            permutations = newPerms;
        }

        // The answer to the problem
        int minDist = Integer.MAX_VALUE;
        // Loop through every permutation
        for (ArrayList<Integer> perm : permutations){
            // Add the distance for each leg of the journey
            int dist = 0;
            for (int i=1; i<perm.size(); ++i){
                dist += dists[perm.get(i-1)][perm.get(i)];
            }
            
            // Part 1 finds the minimum distance to arrive at each destination
            // Part 2 finds the same, but returning to the starting position
            if (PART == 2){
                // Add the distance from the last destination back to the start
                dist += dists[perm.get(perm.size()-1)][0];
            }

            // Save the minimum
            minDist = Math.min(dist,minDist);
        }

        // Print the answer
        System.out.println(minDist);
    }
}