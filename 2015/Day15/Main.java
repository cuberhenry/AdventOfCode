import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 15: Science for Hungry People";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);
        
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

        int part1 = best;
        int part2 = best;

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
            part1 = Math.max(part1,score);

            // The number of calories in this combination
            int numCalories = 0;
            // Loop through every ingredient
            for (int i=0; i<numIngredients; ++i){
                // Add the number of calories contributed by that ingredient
                numCalories += ingredients.get(i)[4]*amounts[i];
            }
            // Only count combinations that are 500 calories
            if (numCalories == 500){
                part2 = Math.max(part2,score);
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}