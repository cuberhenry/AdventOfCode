/*
Henry Anderson
Advent of Code 2020 Day 21 https://adventofcode.com/2020/day/21
Input: https://adventofcode.com/2020/day/21/input
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
        // The list of all allergens
        ArrayList<String> allergens = new ArrayList<>();
        // The possible sources for each allergen
        ArrayList<ArrayList<String>> possibilities = new ArrayList<>();
        // The amount of times each ingredient appears
        HashMap<String,Integer> ingredients = new HashMap<>();

        // Continue through every item
        while (sc.hasNext()){
            // Take in the next item
            String line = sc.nextLine();
            // Split up the two parts to the item
            String[] ingredientList = line.substring(0,line.indexOf('(')-1).split(" ");
            String[] allergenInfo = line.substring(line.indexOf('(')+10,line.length()-1).split(", ");

            // Copy the Array into an ArrayList
            ArrayList<String> ings = new ArrayList<String>();
            for (String ing : ingredientList){
                ings.add(ing);
                // Add to the ingredient counts
                if (ingredients.containsKey(ing)){
                    ingredients.put(ing,ingredients.get(ing)+1);
                }else{
                    ingredients.put(ing,1);
                }
            }

            // Loop through every allergen
            for (String a : allergenInfo){
                // If it's already been added
                if (allergens.contains(a)){
                    // Remove all the ingredients that aren't in this item
                    possibilities.get(allergens.indexOf(a)).retainAll(ings);
                }else{
                    // Add the new allergen in to the list alphabetically
                    int index = 0;
                    while (index < allergens.size() && allergens.get(index).compareTo(a) < 0){
                        ++index;
                    }
                    allergens.add(index,a);
                    
                    // Add a copy of the ingredients as the potential list for this allergen
                    ArrayList<String> possibility = new ArrayList<>();
                    Object clone = ings.clone();
                    if (clone instanceof ArrayList<?>){
                        ArrayList<?> al = (ArrayList<?>) clone;
                        for (Object ob : al){
                            if (ob instanceof String){
                                possibility.add((String)ob);
                            }
                        }
                    }
                    possibilities.add(index,possibility);
                }
            }
        }

        // Part 1 finds the count of each ingredient that doesn't have an allergen
        if (PART == 1){
            // Remove ingredients that have allergens
            for (int i=0; i<allergens.size(); ++i){
                for (String ing : possibilities.get(i)){
                    ingredients.remove(ing);
                }
            }

            // Sum up the counts of each ingredient's appearances
            long num = 0;
            for (String key : ingredients.keySet()){
                num += ingredients.get(key);
            }

            // Print the answer
            System.out.println(num);
        }

        // Part 2 finds the ingredient containing each allergen
        if (PART == 2){
            // The final answer on which ingredients contain each allergen
            String[] answers = new String[allergens.size()];

            // Loop through every allergen's possibilities
            for (int i=0; i<possibilities.size(); ++i){
                // If there's only one
                if (possibilities.get(i).size() == 1){
                    // Declare it
                    answers[i] = possibilities.get(i).get(0);
                    // It can only have one allergen, so remove it from the others
                    for (ArrayList<String> list : possibilities){
                        list.remove(answers[i]);
                    }
                    // Start over
                    i = -1;
                }
            }

            // Print the answer
            System.out.print(answers[0]);
            for (int i=1; i<answers.length; ++i){
                System.out.print(',' + answers[i]);
            }
            System.out.println();
        }
    }
}