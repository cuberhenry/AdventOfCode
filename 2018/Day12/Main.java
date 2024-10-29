/*
Henry Anderson
Advent of Code 2018 Day 12 https://adventofcode.com/2018/day/12
Input: https://adventofcode.com/2018/day/12/input
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

        // The number of generations to track
        long generations = 20;

        // Part 1 finds the plants after 20 generations
        // Part 2 finds the plants after 50 billion generations
        if (PART == 2){
            generations = 50000000000L;
        }

        // Repeat for every generation
        for (long i=0; i<generations; ++i){
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
                left = generations-(i+1) + newLeft;
                plants = newPlants;
                break;
            }

            // Move on
            plants = newPlants;
            left = newLeft;
        }

        // The sum of the indexes of the plants
        long total = 0;
        for (int i=0; i<plants.length(); ++i){
            if (plants.charAt(i) == '#'){
                // Add the plant's index
                total += i + left;
            }
        }

        // Print the answer
        System.out.println(total);
    }
}