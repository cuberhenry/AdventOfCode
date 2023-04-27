/*
Henry Anderson
Advent of Code 2022 Day 16 https://adventofcode.com/2022/day/16
Input: https://adventofcode.com/2022/day/16/input
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
        // Every line of input as a string
        ArrayList<String> input = new ArrayList<>();
        // The name of each valve pulled from the lines of input
        ArrayList<String> valves = new ArrayList<>();
        // The number of valves that don't have flow rate=0
        int numWithFlow = 0;

        // Take in every line of input
        while (sc.hasNext()){
            String line = sc.nextLine();

            // If it has a flow rate of 0, put it at the end
            if (Integer.parseInt(line.substring(23,line.indexOf(';'))) == 0){
                input.add(line);
                valves.add(line.substring(6,8));
            // Otherwise, put it at the beginning and increase numWithFlow
            }else{
                input.add(0,line);
                valves.add(0,line.substring(6,8));
                ++numWithFlow;
            }
        }

        // The flow of the valves that aren't 0
        int[] flowValues = new int[numWithFlow];
        // Fill in flowValues
        for (int i=0; i<flowValues.length; ++i){
            flowValues[i] = Integer.parseInt(input.get(i).substring(23,input.get(i).indexOf(';')));
        }

        // The number of valves
        int numValves = valves.size();
        // Cost matrix
        int[][] shortDist = new int[numValves][numValves];
        
        // Fill in the cost matrix with:
        for (int i=0; i<numValves; ++i){
            for (int j=0; j<numValves; ++j){
                if (input.get(i).contains(valves.get(j))){
                    // 1 if there's a direct path
                    shortDist[i][j] = 1;
                }else{
                    // Infinity if there's not
                    shortDist[i][j] = Integer.MAX_VALUE/2;
                }
            }
            // and 0 to itself
            shortDist[i][i] = 0;
        }
        
        // Floyd-Warshall
        for (int k=0; k<numValves; ++k){
            for (int i=0; i<numValves; ++i){
                if (k == i) continue;
                for (int j=0; j<numValves; ++j){
                    if (k == j || i == j) continue;
                    shortDist[i][j] = Math.min(shortDist[i][j],shortDist[i][k] + shortDist[k][j]);
                }
            }
        }
        
        // The maximum amount of pressure released
        int maxSolution = 0;
        // The number of minutes left
        int minLeft = 0;

        // Part 1 finds the maximum pressure released in 30 minutes
        if (PART == 1){
            minLeft = 30;
        }

        // Part 2 finds the maximum pressure released by two beings in 26 minutes
        if (PART == 2){
            minLeft = 26;
        }

        // The queue of states in the breadth first search
        // States have the following form:
        // minLeft currPressureReleased currLocation valves opened ...
        ArrayList<String> queue = new ArrayList<>();
        // Add the initial state
        queue.add(minLeft+" 0 AA");
        // All of the states to be examined in part 2
        ArrayList<String> eStates = new ArrayList<>();
        
        // Breadth first search
        while (!queue.isEmpty()){
            // Remove the first from the queue
            String line = queue.remove(0);
            String[] state = line.split(" ");
            // The valve we are currently at
            int currValve = valves.indexOf(state[2]);
            
            // Search through all valves that are useful
            for (int i=0; i<numWithFlow; ++i){
                // If the valve hasn't already been opened and we can open it
                // with more than 0 minutes left
                if (!line.contains(valves.get(i)) && 
                        shortDist[currValve][i] + 1 < Integer.parseInt(state[0])){
                    // Calculate the new number of minutes remaining
                    int newMinLeft = Integer.parseInt(state[0]) - 1;
                    newMinLeft -= shortDist[currValve][i];

                    // Calculate the new total flow
                    int flow = Integer.parseInt(state[1]);
                    flow += flowValues[i] * newMinLeft;
                    // If we found a better solution, update it
                    if (flow > maxSolution){
                        maxSolution = flow;
                    }

                    // Create the new state
                    String newState = newMinLeft + " " + flow + " " + valves.get(i);
                    for (int j=2; j<state.length; ++j){
                        newState += " " + state[j];
                    }
                    // Add the new state to the queue
                    queue.add(newState);
                }
            }

            // Add the state to examine in Part 2
            if (PART == 2){
                eStates.add(line);
            }
        }

        // Inspect all of the paths and see which two are the best solution
        if (PART == 2){
            // Reverse the list to improve the clipping
            Collections.reverse(eStates);
            // The single best path for 26 minutes
            int maxSingle = maxSolution;
            // Go through every state
            while (!eStates.isEmpty()){
                String[] state1 = eStates.remove(0).split(" ");
                // Go through every other state
                for (int i=0; i<eStates.size(); ++i){
                    // Get the state
                    String state2 = eStates.get(i);
                    // If there is no state this state can be combined with to beat the solution
                    if (maxSingle + Integer.parseInt(state2.split(" ")[1]) < maxSolution){
                        // Remove it
                        eStates.remove(i);
                        --i;
                        continue;
                    }

                    // Checks whether the two paths open the same valve
                    boolean duplicate = false;
                    // Go through every opened valve
                    for (int j=1; j<state1.length-1; ++j){
                        if (state2.contains(state1[j])) duplicate = true;
                    }
                    // If it's a duplicate, skip
                    if (duplicate) continue;
                    // If there's a new max, save it
                    if (Integer.parseInt(state1[1]) + Integer.parseInt(state2.split(" ")[1]) > maxSolution){
                        maxSolution = Integer.parseInt(state1[1]) + Integer.parseInt(state2.split(" ")[1]);
                    }
                }
            }
        }

        // Print the solution
        System.out.println(maxSolution);
    }
}