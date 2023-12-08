/*
Henry Anderson
Advent of Code 2017 Day 20 https://adventofcode.com/2017/day/20
Input: https://adventofcode.com/2017/day/20/input
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
        // The list of particles
        ArrayList<int[][]> particles = new ArrayList<>();
        // The answer to the problem
        int answer = 0;
        // Used for Part 1, the minimum total acceleration among all the particles
        int min = Integer.MAX_VALUE;
        
        // Take in every particle
        while (sc.hasNext()){
            // The particle's stats
            int[][] particle = new int[3][3];
            // Dissect the data
            String[] line = sc.nextLine().split(", ");

            // Part 1 finds the particle that ends up closest to 0,0,0
            if (PART == 1){
                // The acceleration of the current particle
                int acceleration = 0;
                // Take in the acceleration vector and trim useless characters
                String vector = line[2].substring(3,line[2].length()-1);
                // Add x acceleration
                acceleration += Math.abs(Integer.parseInt(vector.substring(0,vector.indexOf(','))));
                vector = vector.substring(vector.indexOf(',')+1);
                // Add y acceleration
                acceleration += Math.abs(Integer.parseInt(vector.substring(0,vector.indexOf(','))));
                vector = vector.substring(vector.indexOf(',')+1);
                // Add z acceleration
                acceleration += Math.abs(Integer.parseInt(vector));

                // Save this particle if it's the new min
                if (acceleration < min){
                    min = acceleration;
                    answer = particles.size();
                }
            }

            // Part 2 finds the number of remaining particles after removing those that collide
            if (PART == 2){
                // Loop through each vector
                for (int i=0; i<3; ++i){
                    // Take in the vector and trim useless characters
                    String vector = line[i].substring(line[i].indexOf('<')+1);
                    // Save the x direction
                    particle[i][0] = Integer.parseInt(vector.substring(0,vector.indexOf(',')));
                    vector = vector.substring(vector.indexOf(',')+1);
                    // Save the y direction
                    particle[i][1] = Integer.parseInt(vector.substring(0,vector.indexOf(',')));
                    vector = vector.substring(vector.indexOf(',')+1);
                    // Save the z direction
                    particle[i][2] = Integer.parseInt(vector.substring(0,vector.indexOf('>')));
                }
            }

            // Add the particle to the list of particles
            particles.add(particle);
        }

        // Simulate collisions
        if (PART == 2){
            // Continue until all particles have either collided or permanently escaped collision
            while (particles.size() > 0){
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
                            ++answer;
                            break;
                        }
                    }
                }
            }
        }

        // Print the answer
        System.out.println(answer);
    }
}