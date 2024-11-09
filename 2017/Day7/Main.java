import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 7: Recursive Circus";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // The input as an arraylist
        ArrayList<String[]> programs = new ArrayList<>();
        // The weight of each program
        HashMap<String,Integer> weights = new HashMap<>();
        // The weight of each sub-tower
        HashMap<String,Integer> towers = new HashMap<>();
        // Take in every line of input
        while (sc.hasNext()){
            // Get the program's info
            String[] split = sc.nextLine().split(" \\(|\\) -> |, |\\)");
            int weight = Integer.parseInt(split[1]);
            // Program has no children
            if (split.length == 2){
                // Its tower weight is known
                towers.put(split[0],weight);
            }else{
                // Save for later
                programs.add(split);
            }
            // Save its weight
            weights.put(split[0],weight);
        }

        // The answer to the problem
        String part1 = "";
        int part2 = 0;

        // Loop through every remaining program
        for (int i=0; !programs.isEmpty(); i=(i+1)%Math.max(1,programs.size())){
            String[] program = programs.get(i);

            // Whether all children have been calculated
            boolean containsAll = true;
            // Loop through every child
            for (int j=2; j<program.length; ++j){
                // If it's not been calculated
                if (!towers.containsKey(program[j])){
                    // Quit
                    containsAll = false;
                    break;
                }
            }
            if (!containsAll){
                continue;
            }

            // After the incorrect weight is found, weights don't matter
            if (part2 != 0){
                // Save the newest name
                part1 = program[0];
                // Put a filler value in
                towers.put(part1,0);
                programs.remove(i);
                continue;
            }

            // The size of the first child
            int size = towers.get(program[2]);
            // The index of the incorrect child
            int index = 3;
            // The size of the incorrect child
            int other = towers.get(program[3]);
            // If the two sizes are the same
            if (size == other){
                // Loop through every remaining child
                for (int j=4; j<program.length; ++j){
                    // If the size isn't the same
                    if (towers.get(program[j]) != size){
                        // It's the incorrect program
                        other = towers.get(program[j]);
                        // Save the index
                        index = j;
                        break;
                    }
                }
                // If all of the programs are the same weights
                if (size == other){
                    // Add it to the found
                    towers.put(program[0],weights.get(program[0]) + size * (program.length-2));
                    // Remove it from the unfound
                    programs.remove(i);
                    continue;
                }
            }else{
                // The weight of the third program
                int third = towers.get(program[4]);
                // If the first program is the odd one out
                if (third == other){
                    // Switch first and other
                    index = 2;
                    other = size;
                    size = third;
                }
            }
                
            // Update the desired size
            part2 = weights.get(program[index]) + size - other;
            towers.put(program[0],0);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}