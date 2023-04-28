/*
Henry Anderson
Advent of Code 2022 Day 19 https://adventofcode.com/2022/day/19
Input: https://adventofcode.com/2022/day/19/input
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
        // The amount to be returned
        int total = -1;

        // Part 1 finds the sum of the quality levels of the blueprints
        if (PART == 1){
            total = 0;
        }

        // Part 2 finds the product of the number of geodes the first three can crack
        if (PART == 2){
            total = 1;
        }

        // For every blueprint
        while (sc.hasNext()){
            // Take in the blueprint
            String[] line = sc.nextLine().split(" ");
            ArrayList<int[]> list = new ArrayList<>();
            // Parse the blueprint
            int blueprintNum = Integer.parseInt(line[1].substring(0,line[1].length()-1));
            int oreCost = Integer.parseInt(line[6]);
            int clayCost = Integer.parseInt(line[12]);
            int obsOreCost = Integer.parseInt(line[18]);
            int obsClayCost = Integer.parseInt(line[21]);
            int geOreCost = Integer.parseInt(line[27]);
            int geObsCost = Integer.parseInt(line[30]);

            // The amount of time we get for the problem
            int time = 0;

            // Part 1 gets 24 minutes
            if (PART == 1){
                time = 24;
            }

            // Part 2 gets 32 minutes
            if (PART == 2){
                time = 32;
            }

            // 0    time left
            // 1    number of ore
            // 2    number of clay
            // 3    number of obsidian
            // 4    number of ore robots
            // 5    number of clay robots
            // 6    number of obsidian robots
            // 7    number of geodes
            // 8    number of geode robots
            list.add(new int[] {time,0,0,0,1,0,0,0});

            // The maximum amount of ore robots necessary per minute
            int maxCost = Math.max(clayCost,Math.max(obsOreCost,geOreCost));
            
            // The maximum amount of geodes collected
            int max = 0;
            
            // Search
            while (!list.isEmpty()){
                // Take the current state
                int[] currState = list.remove(0);

                // Optimization
                if (currState[7] + currState[0] * (currState[0]-1) / 2 <= max){
                    continue;
                }

                // If there aren't enough ore robots to instantly make any robot
                if (currState[4] < maxCost){
                    // Duplicate the current state
                    int[] newState = new int[currState.length];
                    for (int j=0; j<newState.length; ++j){
                        newState[j] = currState[j];
                    }
                    // Keep producing until an ore robot can be made
                    while (oreCost > newState[1] && newState[0]-2 > oreCost){
                        getResources(newState);
                    }
                    // If there's enough time for it to be useful
                    if (newState[0]-2 > oreCost){
                        // Pay for the robot
                        newState[1] -= oreCost;
                        // Produce
                        getResources(newState);
                        // Gain the new robot
                        ++newState[4];
                        // Add the new state to the queue
                        list.add(newState);
                    }
                }
                // If there aren't enough clay robots to make an obsidian robot
                if (currState[5] < obsClayCost){
                    // Duplicate the current state
                    int[] newState = new int[currState.length];
                    for (int j=0; j<newState.length; ++j){
                        newState[j] = currState[j];
                    }
                    // Keep producing until a clay robot can be made
                    while (clayCost > newState[1] && newState[0] > 5){
                        getResources(newState);
                    }
                    // If there's enough time for it to be useful
                    if (newState[0] > 5){
                        // Pay for the robot
                        newState[1] -= clayCost;
                        // Produce
                        getResources(newState);
                        // Gain the new robot
                        ++newState[5];
                        // Add the new state to the queue
                        list.add(newState);
                    }
                }
                // If there aren't enough obsidian robots to make a geode robot
                // and at least one clay robot has been made
                if (currState[6] < geObsCost && currState[5] > 0){
                    // Duplicate the current state
                    int[] newState = new int[currState.length];
                    for (int j=0; j<newState.length; ++j){
                        newState[j] = currState[j];
                    }
                    // Keep producing until an obsidian robot can be made
                    while ((obsOreCost > newState[1] || obsClayCost > newState[2]) && newState[0] > 3){
                        getResources(newState);
                    }
                    // If there's enough time for it to be useful
                    if (newState[0] > 3){
                        // Pay for the robot
                        newState[1] -= obsOreCost;
                        newState[2] -= obsClayCost;
                        // Produce
                        getResources(newState);
                        // Gain the new robot
                        ++newState[6];
                        // Add the new state to the queue
                        list.add(newState);
                    }
                }
                // If at least one obsidian robot has been made
                if (currState[6] > 0){
                    // Duplicate the current state
                    int[] newState = new int[currState.length];
                    for (int j=0; j<newState.length; ++j){
                        newState[j] = currState[j];
                    }
                    // Keep producing until a geode robot can be made
                    while ((geOreCost > newState[1] || geObsCost > newState[3]) && newState[0] > 1){
                        getResources(newState);
                    }
                    // If there's enough time for it to be useful
                    if (newState[0] > 1){
                        // Pay for the robot
                        newState[1] -= geOreCost;
                        newState[3] -= geObsCost;
                        // Produce
                        getResources(newState);
                        // Gain all the geodes the robot produces
                        newState[7] += newState[0];
                        // Set it to max if it's a new max
                        max = Math.max(newState[7],max);
                        // Add the new state to the queue
                        list.add(newState);
                    }
                }
            }
            
            // Part 1 finds the sum of blueprint quality levels
            if (PART == 1){
                total += blueprintNum * max;
            }

            // Part 2 finds the product of the maximum number of geodes cracked
            if (PART == 2){
                total *= max;
                if (blueprintNum == 3){
                    break;
                }
            }
        }
        
        // Print the answer
        System.out.println(total);
    }
    
    // A method that makes each robot produce a resource
    public static void getResources(int[] state){
        // Each ore robot produces one ore
        state[1] += state[4];
        // Each clay robot produces one clay
        state[2] += state[5];
        // Each obsidian robot produces one obsidian
        state[3] += state[6];
        // One minute passes
        --state[0];
    }
}