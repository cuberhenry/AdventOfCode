import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 19: Not Enough Minerals";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 1;

        // For every blueprint
        while (sc.hasNext()){
            // Take in the blueprint
            String[] blueprint = sc.nextLine().split(" ");
            int blueprintNum = Integer.parseInt(blueprint[1].substring(0,blueprint[1].length()-1));
            
            // Add the result to the total
            part1 += blueprintNum * simulate(blueprint,24);
            if (blueprintNum <= 3){
                part2 *= simulate(blueprint,32);
            }
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }

    // A method that simulates the robot production
    public static int simulate(String[] blueprint, int time){
        LinkedList<int[]> queue = new LinkedList<>();
        // Parse the blueprint
        int oreCost = Integer.parseInt(blueprint[6]);
        int clayCost = Integer.parseInt(blueprint[12]);
        int obsOreCost = Integer.parseInt(blueprint[18]);
        int obsClayCost = Integer.parseInt(blueprint[21]);
        int geOreCost = Integer.parseInt(blueprint[27]);
        int geObsCost = Integer.parseInt(blueprint[30]);

        // 0    time left
        // 1    number of ore
        // 2    number of clay
        // 3    number of obsidian
        // 4    number of ore robots
        // 5    number of clay robots
        // 6    number of obsidian robots
        // 7    number of geodes
        // 8    number of geode robots
        queue.add(new int[] {time,0,0,0,1,0,0,0});

        // The maximum amount of ore robots necessary per minute
        int maxCost = Math.max(clayCost,Math.max(obsOreCost,geOreCost));
        
        // The maximum amount of geodes collected
        int max = 0;
        
        // Search
        while (!queue.isEmpty()){
            // Take the current state
            int[] currState = queue.remove();

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
                    queue.add(newState);
                }
            }
            // If there aren't enough clay robots to make an obsidian robot
            if (currState[5] < obsClayCost){
                // Duplicate the current state
                int[] newState = currState.clone();
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
                    queue.add(newState);
                }
            }
            // If there aren't enough obsidian robots to make a geode robot
            // and at least one clay robot has been made
            if (currState[6] < geObsCost && currState[5] > 0){
                // Duplicate the current state
                int[] newState = currState.clone();
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
                    queue.add(newState);
                }
            }
            // If at least one obsidian robot has been made
            if (currState[6] > 0){
                // Duplicate the current state
                int[] newState = currState.clone();
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
                    queue.add(newState);
                }
            }
        }

        return max;
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