/*
Henry Anderson
Advent of Code 2019 Day 14 https://adventofcode.com/2019/day/14
Input: https://adventofcode.com/2019/day/14/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // A class to simplify reaction access
    private static class Reaction {
        // The amount of the product
        private int product;
        // The number and type of each reactant
        private HashMap<String,Integer> reactants = new HashMap<>();

        // Parse the reaction out of a line
        public Reaction(String line){
            // Split
            String[] split = line.split(" => |, | ");
            // Get the result
            product = Integer.parseInt(split[split.length-2]);
            // Loop through each reactant
            for (int i=0; i<split.length-2; i+=2){
                reactants.put(split[i+1],Integer.parseInt(split[i]));
            }
        }

        // Getter for product
        public int getProduct(){
            return product;
        }

        // Getter for reactants
        public HashMap<String,Integer> getReactants(){
            return reactants;
        }
    }
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
        // Take in all compounds and their reactions in no order
        ArrayList<String> unsortedC = new ArrayList<>();
        ArrayList<Reaction> unsortedR = new ArrayList<>();
        while (sc.hasNext()){
            String line = sc.nextLine();
            String[] split = line.split(" ");
            // Get the last item, the created compound
            unsortedC.add(split[split.length-1]);
            // Add the Reaction
            unsortedR.add(new Reaction(line));
        }

        // Sorted lists based on the order in which reactions have to occur
        ArrayList<String> compounds = new ArrayList<>();
        compounds.add("ORE");
        ArrayList<Reaction> reactions = new ArrayList<>();
        // Loop until all reactions have been sorted
        while (unsortedC.size() > 0){
            // Loop through each reaction
            for (int i=unsortedC.size()-1; i>=0; --i){
                // Whether it can be added
                boolean ready = true;
                // Loop through each reactant
                for (String key : unsortedR.get(i).getReactants().keySet()){
                    // If the reactant hasn't been sorted, neither can this
                    if (unsortedC.contains(key)){
                        ready = false;
                        break;
                    }
                }
                // Add the compound
                if (ready){
                    compounds.add(unsortedC.remove(i));
                    reactions.add(unsortedR.remove(i));
                }
            }
        }
        // Remove the ORE placeholder as it has no reaction
        compounds.remove(0);

        // The number of ore required to make one fuel
        long ore = getOreForFuel(compounds,reactions,1);

        // Part 1 finds the number of ore required to make one fuel
        if (PART == 1){
            System.out.println(ore);
        }

        // Part 2 finds the number of fuel that can be made from 1 trillion ore
        if (PART == 2){
            // The lower limit disregards excess
            long fuel = (1000000000000L / ore + 1);
            // Upper limit assumption
            long max = fuel * 2;

            // Continue until they meet
            while (max - 1 > fuel){
                // Binary search
                long middle = (max+fuel) / 2;
                if (getOreForFuel(compounds,reactions,middle) > 1000000000000L){
                    max = middle;
                }else{
                    fuel = middle;
                }
            }

            // Print the answer
            System.out.println(fuel);
        }
    }

    // Finds the number of ore required to make a certain amount of fuel
    private static long getOreForFuel(ArrayList<String> compounds, ArrayList<Reaction> reactions, long fuel){
        // The list of chemicals needed and their quantities
        HashMap<String,Long> chemicals = new HashMap<>();
        // Require the input amount of fuel
        chemicals.put("FUEL",fuel);

        // Loop through each reaction in reverse order
        for (int i=compounds.size()-1; i>=0; --i){
            // If the chemical is not relevant or is in excess
            if (!chemicals.containsKey(compounds.get(i)) || chemicals.get(compounds.get(i)) < 0){
                continue;
            }
            // Get the chemical and the amount needed
            String chemical = compounds.get(i);
            long amount = chemicals.get(chemical);

            // Get the reaction
            Reaction reaction = reactions.get(i);
            // The amount produced by this reaction
            long result = reaction.getProduct();
            // Ensure that the amount produced is the minimum amount to reach the amount required
            long multiplier = amount / result;
            while (result * multiplier < amount){
                ++multiplier;
            }

            // Get the reactants from the reaction
            HashMap<String,Integer> reactants = reaction.getReactants();
            // Loop through each compound
            for (String compound : reactants.keySet()){
                // The amount of this reactant used
                long quantity = reactants.get(compound) * multiplier;
                
                // Increase the amount of this reactant required
                if (chemicals.containsKey(compound)){
                    chemicals.put(compound,chemicals.get(compound) + quantity);
                }else{
                    chemicals.put(compound,quantity);
                }
            }
        }

        // Return the amount of ore required
        return chemicals.get("ORE");
    }
}