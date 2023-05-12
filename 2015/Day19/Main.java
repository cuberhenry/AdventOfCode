/*
Henry Anderson
Advent of Code 2015 Day 19 https://adventofcode.com/2015/day/19
Input: https://adventofcode.com/2015/day/19/input
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
        // The list of all replacements
        ArrayList<String[]> replacements = new ArrayList<>();

        // Take in the first replacement
        String line = sc.nextLine();
        // While there are replacements
        while (!line.equals("")){
            // Add the replacement
            replacements.add(line.split(" "));
            // Take in the next line
            line = sc.nextLine();
        }

        // Take in the molecule
        String molecule = sc.nextLine();

        // Part 1 finds the number of molecules that can be made with one replacement
        if (PART == 1){
            // The list of all molecules
            ArrayList<String> molecules = new ArrayList<>();
            // Loop through every character of the input
            for (int i=0; i<molecule.length(); ++i){
                // Loop through every possible replacement
                for (String[] replace : replacements){
                    // If the current index matches the left side of the replacement
                    if (replace[0].length()+i <= molecule.length()
                        && replace[0].equals(molecule.substring(i,i+replace[0].length()))){
                        // Create the new molecule
                        String newMol = molecule.substring(0,i) + replace[2] + molecule.substring(i+replace[0].length());
                        // If it's a unique molecule, add it
                        if (!molecules.contains(newMol)){
                            molecules.add(newMol);
                        }
                    }
                }
            }

            // Print the answer
            System.out.println(molecules.size());
        }

        // Part 2 finds the number of replacements necessary to make the molecule
        if (PART == 2){
            // Whether the next character should be counted
            boolean count = false;
            // The number of replacements necessary
            int num = 0;
            // Loop through every character of the input
            for (int i=0; i<molecule.length(); ++i){
                // The atom to be looked at
                String atom = "" + molecule.charAt(i);
                // If the atom includes a lower-case character
                if (molecule.length() > i+1 && Character.isLowerCase(molecule.charAt(i+1))){
                    // Add the next character
                    ++i;
                    atom += molecule.charAt(i);
                }
                // Decide based on the atom
                switch (atom){
                    // Part of another replacement
                    case "Rn","Y" -> {
                        count = false;
                    }
                    // Count one for nesting
                    case "Ar" -> {
                        ++num;
                        count = true;
                    }
                    // Count one for concatenation
                    default -> {
                        // Only count if it's not the first one
                        if (count){
                            ++num;
                        }else{
                            count = true;
                        }
                    }
                }
            }

            // Print the answer
            System.out.println(num);
        }
    }
}