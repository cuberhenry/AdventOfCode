/*
Henry Anderson
Advent of Code 2019 Day 7 https://adventofcode.com/2019/day/7
Input: https://adventofcode.com/2019/day/7/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import com.aoc.mylibrary.IntCode;
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
        String line = sc.nextLine();

        // Find all permutations of the phase settings
        ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();
        permutations.add(new ArrayList<>());
        permutations.get(0).add(0);

        // Part 1 uses phase settings 0 - 4
        // Part 2 uses phase settings 5 - 9
        if (PART == 2){
            permutations.get(0).set(0,5);
        }

        for (int i=1; i<5; ++i){
            int setting = i;

            if (PART == 2){
                setting += 5;
            }

            ArrayList<ArrayList<Integer>> newPermutations = new ArrayList<>();
            for (int j=0; j<permutations.size(); ++j){
                for (int k=0; k<=permutations.get(j).size(); ++k){
                    ArrayList<Integer> list = new ArrayList<>(permutations.get(j));
                    list.add(k,setting);
                    newPermutations.add(list);
                }
            }
            permutations = newPermutations;
        }

        // The highest result
        long max = Integer.MIN_VALUE;
        // Loop through every permutation
        for (ArrayList<Integer> input : permutations){
            // The result of the amplification
            long output = 0;
            // The IntCode programs
            IntCode[] programs = new IntCode[5];
            for (int i=0; i<5; ++i){
                programs[i] = new IntCode(line);
                programs[i].addInput(input.get(i));
            }

            // Part 1 finds the highest amplification through one go
            if (PART == 1){
                for (int i=0; i<5; ++i){
                    programs[i].addInput(output);
                    output = programs[i].run().get(0);
                }
            }

            // Part 2 finds the highest amplification from a feedback loop
            if (PART == 2){
                while (!programs[0].isFinished()){
                    for (int i=0; i<5; ++i){
                        programs[i].addInput(output);
                        output = programs[i].run().get(0);
                    }
                }
            }

            // Save the highest output
            max = Math.max(max,output);
        }

        // Print the answer
        System.out.println(max);
    }
}