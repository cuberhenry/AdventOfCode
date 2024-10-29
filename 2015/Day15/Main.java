/*
Henry Anderson
Advent of Code 2015 Day 15 https://adventofcode.com/2015/day/15
Input: https://adventofcode.com/2015/day/15/input
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
        // The list of all ingredients
        ArrayList<int[]> ingredients = new ArrayList<>();

        // Loop through all input
        while (sc.hasNext()){
            // Get and split the line
            String[] line = sc.nextLine().split(", | ");
            // Create a new ingredient
            int[] ingredient = new int[5];
            // Grab the ingredient's attributes
            ingredient[0] = Integer.parseInt(line[2]);
            ingredient[1] = Integer.parseInt(line[4]);
            ingredient[2] = Integer.parseInt(line[6]);
            ingredient[3] = Integer.parseInt(line[8]);
            ingredient[4] = Integer.parseInt(line[10]);
            // Add the ingredient to the list of ingredients
            ingredients.add(ingredient);
        }

        // Save this variable for many uses
        int numIngredients = ingredients.size();
        // The current amount of each ingredient
        int[] amounts = new int[numIngredients];
        // Set the last ingredient to a total of 100
        amounts[numIngredients-1] = 100;
        // The current best score
        int best = 1;
        // Loop through every attribute
        for (int i=0; i<4; ++i){
            // Get the attribute from the last ingredient
            int attribute = ingredients.get(numIngredients-1)[i];
            // Automatic score of 0
            if (attribute <= 0){
                best = 0;
                break;
            }
            // Include this attribute in the score
            best *= attribute * 100;
        }

        // Loop until every possibility has been found
        while (amounts[0] != 100){
            // Increment
            // Get the number of the first ingredient
            int sum = amounts[0];
            // Whether all 100 ingredients are accounted for
            boolean found = false;
            // Loop through every subsequent ingredient
            for (int i=1; i<numIngredients; ++i){
                // If this is the first ingredient at which all 100 are accounted for
                if (!found && 100 - sum == amounts[i]){
                    // Increase the number of the previous ingredient
                    ++amounts[i-1];
                    // Increase the sum due to the previous amount
                    ++sum;
                    // All have been accounted for
                    found = true;
                }
                // Perform the carry
                if (found){
                    amounts[i] = 0;
                }
                // Increase the total ingredients found
                sum += amounts[i];
            }
            // Make sure the number of ingredients always equals 100
            amounts[numIngredients-1] = 100 - sum;

            // Part 1 finds the ideal cookie score using 100 ingredients
            // Part 2 stipulates that the cookie must have 500 calories
            if (PART == 2){
                // The number of calories in this combination
                int numCalories = 0;
                // Loop through every ingredient
                for (int i=0; i<numIngredients; ++i){
                    // Add the number of calories contributed by that ingredient
                    numCalories += ingredients.get(i)[4]*amounts[i];
                }
                // Only count combinations that are 500 calories
                if (numCalories != 500){
                    continue;
                }
            }

            // Calculate score
            // The score of the current combination
            int score = 1;
            // Loop through every attribute
            for (int i=0; i<4; ++i){
                // The score for the attribute
                int total = 0;
                // Loop through every ingredient
                for (int j=0; j<numIngredients; ++j){
                    // Add the attribute's score contributed by this ingredient
                    total += ingredients.get(j)[i] * amounts[j];
                }
                // If the total is 0, immediately disqualify this combination
                if (total <= 0){
                    score = 0;
                    break;
                }
                // Contribute the attribute to the total score
                score *= total;
            }
            // If a new best is found, save it
            if (score > best){
                best = score;
            }
        }

        // Print the answer
        System.out.println(best);
    }
}