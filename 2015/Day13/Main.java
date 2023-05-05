/*
Henry Anderson
Advent of Code 2015 Day 13 https://adventofcode.com/2015/day/13
Input: https://adventofcode.com/2015/day/13/input
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
            input.add(line);
        }
        
        // Part 1 finds the change of happiness for the optimal seating arrangement
        // Part 2 adds you to the seating with happiness changes of 0
        if (PART == 2){
            people.add("you");
        }

        // Create the happiness matrix
        int[][] happiness = new int[people.size()][people.size()];

        // The maximum happiness value
        int max = 0;
        // Loop through every line of input
        for (String[] line : input){
            // Get the happiness value
            int happy = Integer.parseInt(line[3]);
            // Adjust to negative if they lose happiness
            if (line[2].equals("lose")){
                happy *= -1;
            }
            // If it's a new max, save it
            if (happy > max){
                max = happy;
            }
            // Get the index of the first person
            int first = people.indexOf(line[0]);
            // Get the index of the second person
            String withPeriod = line[10].substring(0,line[10].length()-1);
            int second = people.indexOf(withPeriod);
            // Add the value to the matrix
            happiness[first][second] = happy;
        }

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

        // Print the answer
        System.out.println(best);
    }
}