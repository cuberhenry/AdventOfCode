import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    final private static String name = "Day 9: All in a Single Night";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

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
        int part1 = Integer.MAX_VALUE;
        int part2 = 0;

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

            // Prunes states
            if (distance >= part1 && distance + (cities.size()-currState.length+1)*maxDist <= part2){
                continue;
            }

            // If it survived the pruning and it's a finished path, it's the new best
            if (currState.length-1 == cities.size()){
                part1 = Math.min(part1,distance);
                part2 = Math.max(part2,distance);
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
        Library.print(part1,part2,name);
    }
}