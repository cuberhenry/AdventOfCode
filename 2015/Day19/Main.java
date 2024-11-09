import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 19: Medicine for Rudolph";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The list of all replacements
        ArrayList<String[]> replacements = new ArrayList<>();

        // Take in the first replacement
        String line = sc.nextLine();
        // While there are replacements
        while (!line.equals("")){
            // Add the replacement
            replacements.add(line.split(" => "));
            // Take in the next line
            line = sc.nextLine();
        }

        // Take in the molecule
        String molecule = sc.nextLine();

        // Solve the problems
        int part1 = part1(molecule,replacements);
        int part2 = part2(molecule);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static int part1(String molecule, ArrayList<String[]> replacements){
        // The list of all molecules
        HashSet<String> molecules = new HashSet<>();
        // Loop through every character of the input
        for (int i=0; i<molecule.length(); ++i){
            // Loop through every possible replacement
            for (String[] replace : replacements){
                // If the current index matches the left side of the replacement
                if (replace[0].length()+i <= molecule.length()
                    && replace[0].equals(molecule.substring(i,i+replace[0].length()))){
                    // Create the new molecule
                    String newMol = molecule.substring(0,i) + replace[1] + molecule.substring(i+replace[0].length());
                    // If it's a unique molecule, add it
                    if (!molecules.contains(newMol)){
                        molecules.add(newMol);
                    }
                }
            }
        }

        // Print the answer
        return molecules.size();
    }

    private static int part2(String molecule){
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
        return num;
    }
}