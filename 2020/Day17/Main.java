/*
Henry Anderson
Advent of Code 2020 Day 17 https://adventofcode.com/2020/day/17
Input: https://adventofcode.com/2020/day/17/input
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
        // The list of all active cubes
        ArrayList<String> active = new ArrayList<>();
        // The initial bounds of the square
        int height = 0;
        int width = 0;

        // Take in the starting square
        while (sc.hasNext()){
            String line = sc.nextLine();
            width = line.length();
            // Loop through each cube (or hypercube)
            for (int i=0; i<line.length(); ++i){
                // If it's active
                if (line.charAt(i) == '#'){
                    // Part 1 deals in 3 dimensions
                    if (PART == 1){
                        active.add(i + " " + height + " 0");
                    }

                    // Part 2 deals in 4 dimensions
                    if (PART == 2){
                        active.add(i + " " + height + " 0 0");
                    }
                }
            }
            ++height;
        }

        // Perform 6 cycles
        for (int i=1; i<=6; ++i){
            // All cubes act simultaneously
            ArrayList<String> nextStep = new ArrayList<>();

            if (PART == 1){
                // Loop through every cube within the previous bounds +1
                for (int x=0-i; x<width+i; ++x){
                    for (int y=0-i; y<height+i; ++y){
                        for (int z=0-i; z<=i; ++z){
                            // The number of adjacent active cubes
                            int neighbors = 0;
                            // Loop through each adjacent cube
                            for (int a=-1; a<=1; ++a){
                                for (int b=-1; b<=1; ++b){
                                    for (int c=-1; c<=1; ++c){
                                        // Skip the current cube
                                        if (a == 0 && b == 0 && c == 0){
                                            continue;
                                        }
                                        // If it's active, add
                                        if (active.contains((x+a) + " " + (y+b) + " " + (z+c))){
                                            ++neighbors;
                                        }
                                    }
                                }
                            }

                            // If the cube is already active
                            if (active.contains(x + " " + y + " " + z)){
                                // It must have 2 or 3 neighbors to remain active
                                if (neighbors == 2 || neighbors == 3){
                                    nextStep.add(x + " " + y + " " + z);
                                }
                            }else{
                                // It must have 3 neighbors to activate
                                if (neighbors == 3){
                                    nextStep.add(x + " " + y + " " + z);
                                }
                            }
                        }
                    }
                }
            }

            if (PART == 2){
                // Loop through every hypercube within the previous bounds +1
                for (int x=0-i; x<width+i; ++x){
                    for (int y=0-i; y<height+i; ++y){
                        for (int z=0-i; z<=i; ++z){
                            for (int w=0-i; w<=i; ++w){
                                // The number of adjacent active hypercubes
                                int neighbors = 0;
                                // Loop through each adjacent hypercube
                                for (int a=-1; a<=1; ++a){
                                    for (int b=-1; b<=1; ++b){
                                        for (int c=-1; c<=1; ++c){
                                            for (int d=-1; d<=1; ++d){
                                                // Skip the current hypercube
                                                if (a == 0 && b == 0 && c == 0 && d == 0){
                                                    continue;
                                                }
                                                // If it's active, add
                                                if (active.contains((x+a) + " " + (y+b) + " " + (z+c) + " " + (w+d))){
                                                    ++neighbors;
                                                }
                                            }
                                        }
                                    }
                                }

                                // If the hypercube is already active
                                if (active.contains(x + " " + y + " " + z + " " + w)){
                                    // It must have 2 or 3 neighbors to remain active
                                    if (neighbors == 2 || neighbors == 3){
                                        nextStep.add(x + " " + y + " " + z + " " + w);
                                    }
                                }else{
                                    // It must have 3 neighbors to activate
                                    if (neighbors == 3){
                                        nextStep.add(x + " " + y + " " + z + " " + w);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Move on to the next step
            active = nextStep;
        }

        // Print the answer
        System.out.println(active.size());
    }
}