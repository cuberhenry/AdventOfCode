/*
Henry Anderson
Advent of Code 2017 Day 7 https://adventofcode.com/2017/day/7
Input: https://adventofcode.com/2017/day/7/input
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
        // The input as an arraylist
        ArrayList<String> programs = new ArrayList<>();
        // Take in every line of input
        while (sc.hasNext()){
            programs.add(sc.nextLine());
        }

        // Part 1 finds the bottom program
        if (PART == 1){
            // The index of the current program
            int index = 0;
            // Look through every program
            for (int i=0; i<programs.size(); ++i){
                // Skip the current program and any leaf programs
                if (index == i || programs.get(i).indexOf('>') == -1){
                    continue;
                }

                // If the current program is on a disc of this program
                if (programs.get(i).contains(programs.get(index).substring(0,programs.get(index).indexOf(' ')))){
                    // Updated this program to the current program
                    index = i;
                    // Start over
                    i = -1;
                }
            }

            // Print the name of the current program
            System.out.println(programs.get(index).split(" ")[0]);
        }

        // Part 2 finds the corrected size of the imbalanced program
        if (PART == 2){
            // The original sizes of all of the programs
            HashMap<String,Integer> original = new HashMap<>();
            // The adjusted weights of the programs including the programs they're holding
            HashMap<String,Integer> updated = new HashMap<>();
            // Loop through every program
            for (int i=0; i<programs.size(); ++i){
                // Collect the program and its relevant information
                String program = programs.get(i);
                String name = program.substring(0,program.indexOf(' '));
                int weight = Integer.parseInt(program.substring(program.indexOf('(')+1,program.indexOf(')')));
                // Add it to the original list
                original.put(name,weight);
                // If the program doesn't have any children
                if (program.indexOf('-') == -1){
                    // Add it to the updated list
                    updated.put(name,weight);
                    // Remove the program from the unknown list
                    programs.remove(i);
                    --i;
                }
            }
            
            // Loop through all programs until they have all been placed
            for (int i=0; programs.size()>0; i=(i+1)%programs.size()){
                // Collect the program and its relevant information
                String program = programs.get(i);
                String name = program.substring(0,program.indexOf(' '));
                String[] subprograms = program.substring(program.indexOf('>')+2).split(", ");
                // Whether or not all of its children have been accounted for
                boolean containsAll = true;

                // Loop through every child
                for (String sub : subprograms){
                    // If it's not been updated
                    if (!updated.containsKey(sub)){
                        // Quit
                        containsAll = false;
                        break;
                    }
                }
                if (!containsAll){
                    continue;
                }

                // The size of the first child
                int size = updated.get(subprograms[0]);
                // The index of the incorrect child
                int index = 1;
                // The size of the incorrect child
                int other = updated.get(subprograms[1]);
                // If the two sizes are the same
                if (size == other){
                    // Loop through every remaining child
                    for (int j=2; j<subprograms.length; ++j){
                        // If the size isn't the same
                        if (updated.get(subprograms[j]) != size){
                            // It's the incorrect program
                            other = updated.get(subprograms[j]);
                            // Save the index
                            index = j;
                            break;
                        }
                    }
                    // If all of the programs are the same weights
                    if (size == other){
                        // Add it to the found
                        updated.put(name,original.get(name) + size * subprograms.length);
                        // Remove it from the unfound
                        programs.remove(i);
                        continue;
                    }
                }else{
                    // The weight of the third program
                    int third = updated.get(subprograms[2]);
                    // If the first program is the odd one out
                    if (third == other){
                        // Switch first and other
                        index = 0;
                        other = size;
                        size = third;
                    }
                }
                // Print the original weight of the program adjusted by the size difference of its siblings
                System.out.println(original.get(subprograms[index]) + size - other);
                return;
            }
        }
    }
}