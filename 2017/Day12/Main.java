/*
Henry Anderson
Advent of Code 2017 Day 12 https://adventofcode.com/2017/day/12
Input: https://adventofcode.com/2017/day/12/input
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
        // All of the house numbers with their corresponding pipes
        HashMap<Integer,int[]> houses = new HashMap<>();
        while (sc.hasNext()){
            // Take in the house line
            String house = sc.nextLine();
            // Save the house number
            int number = Integer.parseInt(house.substring(0,house.indexOf(' ')));
            // Get the list of connected houses
            String[] split = house.substring(house.indexOf('>')+2).split(", ");
            int[] pipes = new int[split.length];
            for (int i=0; i<split.length; ++i){
                pipes[i] = Integer.parseInt(split[i]);
            }
            // Attach the house number to the pipes
            houses.put(number,pipes);
        }

        // Part 1 finds the number of houses connected to house 0
        if (PART == 1){
            // The houses connected to house 0
            ArrayList<Integer> connections = new ArrayList<>();
            // House 0 is connected to itself
            connections.add(0);

            // Visit every connected house
            for (int i=0; i<connections.size(); ++i){
                // Check all of the connections
                int[] house = houses.get(connections.get(i));
                for (int pipe : house){
                    // If it's a new house
                    if (!connections.contains(pipe)){
                        // Add it
                        connections.add(pipe);
                    }
                }
            }

            // Print the answer
            System.out.println(connections.size());
        }
        
        // Part 2 finds the number of separated groups
        if (PART == 2){
            // The list of groups
            ArrayList<ArrayList<Integer>> groups = new ArrayList<>();

            // Continue until every house has been assigned a group
            while (!houses.isEmpty()){
                // Create a new group
                ArrayList<Integer> group = new ArrayList<>();
                // The lowest house number without a group
                int start = 0;
                // Loop through every existing group
                for (int i=0; i<groups.size(); ++i){
                    int prestart = start;
                    // Change the index as long as the group contains it
                    while (groups.get(i).contains(start)){
                        ++start;
                    }
                    // If the index changed, start over at the first group
                    if (prestart != start){
                        i = -1;
                    }
                }
                // Add the first house to the group
                group.add(start);

                // Visit every house in the group
                for (int i=0; i<group.size(); ++i){
                    // Check all of the connections
                    int[] house = houses.remove(group.get(i));
                    for (int pipe : house){
                        // If it's a new house
                        if (!group.contains(pipe)){
                            // Add it
                            group.add(pipe);
                        }
                    }
                }
                // Add the group to the list of groups
                groups.add(group);
            }

            // Print the answer
            System.out.println(groups.size());
        }
    }
}