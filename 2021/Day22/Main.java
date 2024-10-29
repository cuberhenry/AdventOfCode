/*
Henry Anderson
Advent of Code 2021 Day 22 https://adventofcode.com/2021/day/22
Input: https://adventofcode.com/2021/day/22/input
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
        // Take in all the instructions
        ArrayList<int[]> instructions = new ArrayList<>();
        while (sc.hasNext()){
            int[] instruction = new int[7];
            // Split the input
            String[] split = sc.nextLine().split(" x=|,[yz]=|[.][.]");
            // The last int represents whether the instruction is on or off
            instruction[6] = split[0].equals("on") ? 1 : 0;
            // The rest are each dimension's bounds
            for (int i=0; i<6; ++i){
                instruction[i] = Integer.parseInt(split[i+1]);
            }
            instructions.add(instruction);
        }

        // The number of cubes that are on
        long count = 0;
        // Loop through every instruction
        for (int i=0; i<instructions.size(); ++i){
            // Skip off instructions
            if (instructions.get(i)[6] == 0){
                continue;
            }

            // Part 1 finds the number of on cubes between -50 and 50
            // Part 2 finds the total number of on cubes
            if (PART == 1){
                // If the instruction exceeds 50 in any direction, break
                int max = 0;
                for (int j=0; j<6; ++j){
                    max = Math.max(max,Math.abs(instructions.get(i)[j]));
                }
                if (max > 50){
                    break;
                }
            }

            // All of the sub-prisms that are turned on
            ArrayList<int[]> turnedOn = new ArrayList<>();
            // Add the intitial instruction
            turnedOn.add(instructions.get(i));
            // Loop through every future instruction
            for (int j=i+1; j<instructions.size(); ++j){
                // Create a new list
                ArrayList<int[]> stillOn = new ArrayList<>();
                // Get all of the cubes that are not affected by that instruction
                for (int[] prism : turnedOn){
                    stillOn.addAll(except(prism,instructions.get(j)));
                }
                turnedOn = stillOn;
                // If no cubes are still there, effectively no cubes are turned on
                if (turnedOn.isEmpty()){
                    break;
                }
            }

            // Add the number of cubes from each sub-prism
            for (int[] prism : turnedOn){
                count += (long)(prism[1]-prism[0]+1) * (prism[3]-prism[2]+1) * (prism[5]-prism[4]+1);
            }
        }

        // Print the answer
        System.out.println(count);
    }

    // Returns up to 26 prisms that exist in left but not right
    private static ArrayList<int[]> except(int[] left, int[] right){
        // All of the sub-prisms
        ArrayList<int[]> except = new ArrayList<>();
        // If they don't touch each other at all, just return left
        if (left[0] > right[1] || left[1] < right[0]
            || left[2] > right[3] || left[3] < right[2]
            || left[4] > right[5] || left[5] < right[4]){
            except.add(left);
            return except;
        }

        // Loop through each subprism
        for (int i=0; i<27; ++i){
            // 13 would represent right, which by definition should be discluded
            if (i == 13){
                continue;
            }
            // Create the new sub-prism
            int[] prism = new int[6];
            // Whether the prism actually contains volume
            boolean hasVolume = true;
            // Loop through each dimension
            for (int j=0; j<3; ++j){
                // Get the representation for this dimension
                switch(i/(int)Math.pow(3,j)%3){
                    // All left to the - of right
                    case 0 -> {
                        prism[j*2] = left[j*2];
                        prism[j*2+1] = right[j*2]-1;
                    }
                    // All left at right
                    case 1 -> {
                        prism[j*2] = Math.max(left[j*2],right[j*2]);
                        prism[j*2+1] = Math.min(left[j*2+1],right[j*2+1]);
                    }
                    // All left to the + of right
                    case 2 -> {
                        prism[j*2] = right[j*2+1]+1;
                        prism[j*2+1] = left[j*2+1];
                    }
                }
                // If the upper bound is less than the lower bound
                if (prism[j*2+1] < prism[j*2]){
                    // Irrelevant prism, break
                    hasVolume = false;
                    break;
                }
            }
            // Add relevant prisms
            if (hasVolume){
                except.add(prism);
            }
        }
        // Return all prisms
        return except;
    }
}