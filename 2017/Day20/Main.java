import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    final private static String name = "Day 20: Particle Swarm";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        // The list of particles
        ArrayList<int[][]> particles = new ArrayList<>();

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        
        // Take in every particle
        while (sc.hasNext()){
            // The particle's stats
            int[][] particle = new int[3][3];
            // Dissect the data
            int[] line = Library.intSplit(sc.nextLine().substring(3),">, v=<|>, a=<|>|,");
            for (int i=0; i<9; ++i){
                particle[i/3][i%3] = line[i];
            }

            // Add the particle
            particles.add(particle);

            // Compare to find the minimum acceleration total
            if (Library.absSum(particle[2]) < Library.absSum(particles.get(part1)[2])){
                part1 = particles.size() - 1;
            }
        }

        // Continue until all particles have either collided or permanently escaped collision
        while (!particles.isEmpty()){
            // Loop through every particle
            for (int i=0; i<particles.size(); ++i){
                // Get the current particle
                int[][] particle = particles.get(i);
                // Simulate its change in velocity and then position
                for (int j=1; j>=0; --j){
                    for (int k=2; k>=0; --k){
                        particle[j][k] += particle[j+1][k];
                    }
                }
            }

            // Loop through every particle
            for (int i=0; i<particles.size(); ++i){
                // Whether the current particle has collided with any others
                boolean collided = false;
                // Loop through every unchecked particle
                for (int j=i+1; j<particles.size(); ++j){
                    // If the particles have the same position, remove the second one
                    if (Arrays.equals(particles.get(i)[0],particles.get(j)[0])){
                        collided = true;
                        particles.remove(j);
                        --j;
                    }
                }
                // Remove the current particle if it collided with at least one other particle
                if (collided){
                    particles.remove(i);
                    --i;
                }
            }

            // The list of all of each stat among all particles
            ArrayList<ArrayList<Integer>> allValues = new ArrayList<>();
            // Loop through each value type
            for (int i=0; i<9; ++i){
                // Create a new list
                ArrayList<Integer> values = new ArrayList<>();
                // Loop through every particle
                for (int[][] particle : particles){
                    // Add the value
                    values.add(particle[i/3][i%3]);
                }
                // Sort the data
                Collections.sort(values);
                // Add the data
                allValues.add(values);
            }
            // Loop through every particle
            for (int i=0; i<particles.size(); ++i){
                // Get the current particle
                int[][] particle = particles.get(i);
                // Loop through each direction
                for (int j=0; j<3; ++j){
                    // If the current particle has the greatest position, velocity,
                    // and acceleration in any given direction
                    if ((particle[0][j] == allValues.get(j).get(0) &&
                        particle[1][j] == allValues.get(j+3).get(0) &&
                        particle[2][j] == allValues.get(j+6).get(0)) ||
                        (particle[0][j] == allValues.get(j).get(allValues.get(j).size()-1) &&
                        particle[1][j] == allValues.get(j+3).get(allValues.get(j+3).size()-1) &&
                        particle[2][j] == allValues.get(j+6).get(allValues.get(j+6).size()-1))){

                        // It cannot collide with any other particle, so remove it
                        particles.remove(i);
                        --i;
                        // Increase the count of uncollided particles
                        ++part2;
                        break;
                    }
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}