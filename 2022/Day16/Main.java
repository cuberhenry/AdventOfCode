import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    final private static String name = "Day 16: Proboscidea Volcanium";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

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
        
        // The answer to the problem
        int part1 = maxFlow(30,numWithFlow,valves,shortDist,flowValues,new ArrayList<>());
        ArrayList<String> eStates = new ArrayList<>();
        int part2 = maxFlow(26,numWithFlow,valves,shortDist,flowValues,eStates);

        // Reverse the list to improve the clipping
        Collections.reverse(eStates);
        // The single best path for 26 minutes
        int maxSingle = part2;
        // Go through every state
        while (!eStates.isEmpty()){
            String[] state1 = eStates.remove(0).split(" ");
            // Go through every other state
            for (int i=0; i<eStates.size(); ++i){
                // Get the state
                String state2 = eStates.get(i);
                // If there is no state this state can be combined with to beat the solution
                if (maxSingle + Integer.parseInt(state2.split(" ")[1]) < part2){
                    // Remove it
                    eStates.remove(i);
                    --i;
                    continue;
                }

                // Checks whether the two paths open the same valve
                boolean duplicate = false;
                // Go through every opened valve
                for (int j=1; j<state1.length-1; ++j){
                    duplicate = duplicate || state2.contains(state1[j]);
                }
                // If it's a duplicate, skip
                if (duplicate) continue;
                // If there's a new max, save it
                part2 = Math.max(part2,Integer.parseInt(state1[1]) + Integer.parseInt(state2.split(" ")[1]));
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static int maxFlow(int minLeft, int numWithFlow, ArrayList<String> valves, int[][] shortDist, int[] flowValues, ArrayList<String> states){
        // The queue of states in the breadth first search
        // States have the following form:
        // minLeft currPressureReleased currLocation valves opened ...
        ArrayList<String> queue = new ArrayList<>();
        // The maximum flow
        int maxFlow = 0;
        // Add the initial state
        queue.add(minLeft+" 0 AA");
        
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
                    maxFlow = Math.max(maxFlow,flow);

                    // Create the new state
                    String newState = newMinLeft + " " + flow + " " + valves.get(i);
                    for (int j=2; j<state.length; ++j){
                        newState += " " + state[j];
                    }
                    // Add the new state to the queue
                    queue.add(newState);
                }
            }

            // Add the state to examine later
            states.add(line);
        }

        return maxFlow;
    }
}