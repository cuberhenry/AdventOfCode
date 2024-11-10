import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 12: Subterranean Sustainability";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // Take in the initial state
        String plants = sc.nextLine().split(" ")[2];
        sc.nextLine();
        // The index of the leftmost plant
        long left = 0;

        // The rules deciding whether plants grow
        HashMap<String,String> rules = new HashMap<>();
        // Take in all the rules
        while (sc.hasNext()){
            String[] line = sc.nextLine().split(" ");
            rules.put(line[0],line[2]);
        }
        
        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // Repeat for every generation
        for (long i=0; i<50000000000L; ++i){
            if (i == 20){
                for (int j=0; j<plants.length(); ++j){
                    if (plants.charAt(j) == '#'){
                        // Add the plant's index
                        part1 += j + left;
                    }
                }
            }

            // Add buffers
            plants = "...." + plants + "....";
            // The next generation
            String newPlants = "";
            long newLeft = left-2;

            // Repeat for every possible plant pot
            for (int j=2; j<plants.length()-2; ++j){
                // Add the pot's new state
                if (rules.containsKey(plants.substring(j-2,j+3))){
                    newPlants += rules.get(plants.substring(j-2,j+3));
                }else{
                    newPlants += ".";
                }
            }

            // Trim unnecessary buffers
            while (newPlants.charAt(0) == '.'){
                newPlants = newPlants.substring(1);
                ++newLeft;
            }
            while (newPlants.charAt(newPlants.length()-1) == '.'){
                newPlants = newPlants.substring(0,newPlants.length()-1);
            }

            // If it repeats
            if (plants.equals("...."+newPlants+"....")){
                // Add the difference until you get to the desired generation
                left = 50000000000L-(i+1) + newLeft;
                plants = newPlants;
                break;
            }

            // Move on
            plants = newPlants;
            left = newLeft;
        }

        // The sum of the indexes of the plants
        for (int i=0; i<plants.length(); ++i){
            if (plants.charAt(i) == '#'){
                // Add the plant's index
                part2 += i + left;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}