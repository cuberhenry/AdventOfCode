/*
Henry Anderson
Advent of Code 2019 Day 12 https://adventofcode.com/2019/day/12
Input: https://adventofcode.com/2019/day/12/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import com.aoc.mylibrary.Library;
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
        // The positions and velocities of each moon
        int[][][] moons = new int[4][2][3];
        // Take in each moon's initial position
        for (int i=0; i<4; ++i){
            String[] line = sc.nextLine().split("<|, |=|>");
            moons[i][0][0] = Integer.parseInt(line[2]);
            moons[i][0][1] = Integer.parseInt(line[4]);
            moons[i][0][2] = Integer.parseInt(line[6]);
        }

        // Get a history for each of the three dimensions
        ArrayList<HashSet<String>> states = new ArrayList<>();
        for (int i=0; i<3; ++i){
            states.add(new HashSet<>());
        }
        // The number of steps to repeat for each dimension
        long[] completed = new long[3];

        // Continue until the break condition
        for (long i=0; true; ++i){
            // Part 1 finds the total energy after 1000 steps
            if (PART == 1){
                if (i == 1000){
                    break;
                }
            }

            // Part 2 finds the number of steps before the system restarts
            if (PART == 2){
                // Whether the answer has been found
                boolean finished = true;
                // Loop through each dimension
                for (int j=0; j<3; ++j){
                    // If completed, don't recheck
                    if (completed[j] != 0){
                        continue;
                    }
                    // Not completed
                    finished = false;
                    // Create the new state
                    String state = "";
                    // Loop through each moon
                    for (int k=0; k<4; ++k){
                        // Add this dimension's info to the state
                        state += "[" + moons[k][0][j] + "," + moons[k][1][j] + "]";
                    }
                    // If the state has been repeated
                    if (states.get(j).contains(state)){
                        // This dimension is complete
                        completed[j] = i;
                    }else{
                        // Save history
                        states.get(j).add(state);
                    }
                }

                // Finished, ending simulation
                if (finished){
                    break;
                }
            }

            // Loop through each moon
            for (int j=0; j<4; ++j){
                // Loop through each other moon
                for (int k=j+1; k<4; ++k){
                    // Loop through each dimension
                    for (int l=0; l<3; ++l){
                        // Adjust the velocity based on the other moon
                        if (moons[j][0][l] < moons[k][0][l]){
                            ++moons[j][1][l];
                            --moons[k][1][l];
                        }else if (moons[j][0][l] > moons[k][0][l]){
                            --moons[j][1][l];
                            ++moons[k][1][l];
                        }
                    }
                }
                // Loop through each dimension
                for (int k=0; k<3; ++k){
                    // Adjust the moon's position
                    moons[j][0][k] += moons[j][1][k];
                }
            }
        }

        // Calculate the energy
        if (PART == 1){
            // Total energy of the system
            int energy = 0;
            // Loop through each moon
            for (int i=0; i<4; ++i){
                // Potential energy (position)
                int pot = 0;
                // Kinetic energy (velocity)
                int kin = 0;
                // Loop through each dimension
                for (int j=0; j<3; ++j){
                    // Add each energy
                    pot += Math.abs(moons[i][0][j]);
                    kin += Math.abs(moons[i][1][j]);
                }
                // Add the moon's energy to the total energy
                energy += pot * kin;
            }

            // Print the answer
            System.out.println(energy);
        }

        if (PART == 2){
            // Find the first step that's a multiple of each
            // Print the answer
            System.out.println(Library.LCM(Library.LCM(completed[0],completed[1]),completed[2]));
        }
    }
}