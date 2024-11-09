import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    final private static String name = "Day 13: Knights of the Dinner Table";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // All of the lines of input
        ArrayList<String[]> input = new ArrayList<>();
        // The names of all the people
        ArrayList<String> people = new ArrayList<>();

        // Take in all the input
        while (sc.hasNext()){
            // Split the next line
            String[] line = sc.nextLine().split(" ");
            // Add the person if they don't exist yet
            if (!people.contains(line[0])){
                people.add(line[0]);
            }
            // Add the line to input
            input.add(new String[] {line[0],
                                    line[10].substring(0,line[10].length()-1),
                                    (line[2].equals("lose") ? "-" : "") + line[3]});
        }
        
        // Create the happiness matrix
        int[][] happiness = Library.distMap(people,input,false);
        // Find the maximum happiness value
        int max = 0;
        for (int[] happy : happiness){
            for (int h : happy){
                max = Math.max(h,max);
            }
        }

        // Find the max happiness
        int part1 = solve(people,happiness,max);
        // Add yourself to the table
        people.add("you");
        // Recreate the dist map
        happiness = Library.distMap(people,input,false);
        // Fine the new max happiness
        int part2 = solve(people,happiness,max);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static int solve(ArrayList<String> people, int[][] happiness, int max){
        // The answer to the problem
        int best = 0;
        // The list of possible arrangements
        Stack<String> states = new Stack<>();
        // Because it's a circle, it arbitrarily starts with person 0
        states.add("0 "+people.get(0));
        // Continue until all arrangements have been checked
        while (!states.isEmpty()){
            // Grab the current line
            String line = states.pop();
            // Split it
            String[] currState = line.split(" ");
            // Grab the current happiness value
            int happy = Integer.parseInt(currState[0]);

            // Prune states whose happiness values can't ever beat the best
            if (happy + (people.size()-currState.length+2)*2*max <= best){
                continue;
            }

            // If all people have been added
            if (currState.length-1 == people.size()){
                // Add the final happiness values for the first and last person
                happy += happiness[people.indexOf(currState[1])][people.indexOf(currState[currState.length-1])];
                happy += happiness[people.indexOf(currState[currState.length-1])][people.indexOf(currState[1])];
                // If it's a new best, save it
                if (happy > best){
                    best = happy;
                }
                // Don't branch anymore
                continue;
            }

            // For every person except the first
            for (int i=1; i<people.size(); ++i){
                // If they don't have a spot at the table yet
                if (!line.contains(people.get(i))){
                    // The happiness value for the new state
                    int newHappy = happy;
                    // Add the bidirectional values
                    newHappy += happiness[people.indexOf(currState[1])][i];
                    newHappy += happiness[i][people.indexOf(currState[1])];
                    // Create a new state
                    String newState = newHappy + " " + people.get(i) + line.substring(line.indexOf(' '));
                    // Add the arrangement to states
                    states.push(newState);
                }
            }
        }

        return best;
    }
}