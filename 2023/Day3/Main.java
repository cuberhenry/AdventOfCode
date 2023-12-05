/*
Henry Anderson
Advent of Code 2023 Day 3 https://adventofcode.com/2023/day/3
Input: https://adventofcode.com/2023/day/3/input
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
        // The schematics
        ArrayList<char[]> grid = new ArrayList<>();
        // A list of the positions and numbers of all gears
        ArrayList<String> gears = new ArrayList<>();
        // The answer to the problem
        int total = 0;

        // Take in all lines of input
        while (sc.hasNext()){
            // Take in a line
            String line = sc.nextLine();
            // Copy it over into a char array
            char[] array = new char[line.length()];
            for (int i=0; i<line.length(); ++i){
                array[i] = line.charAt(i);
            }
            // Add the array to the schematics
            grid.add(array);
        }

        // Loop through every character in the grid
        for (int i=0; i<grid.size(); ++i){
            for (int j=0; j<grid.size(); ++j){
                // Only look for numbers
                if (!Character.isDigit(grid.get(i)[j])){
                    continue;
                }
                // Used for Part 1, whether the number is next to a symbol
                boolean nextToSymbol = false;
                // Used for Part 2, a list of all adjacent gears
                ArrayList<String> adjGears = new ArrayList<>();
                // The number as a string
                String number = "";

                // Check the position to the left
                // Part 1 adds up all the numbers adjacent to at least one symbol 
                if (PART == 1){
                    if (j > 0 && !Character.isDigit(grid.get(i)[j-1]) && grid.get(i)[j-1] != '.'){
                        nextToSymbol = true;
                    }
                }

                // Part 2 adds up the gear ratios of all gears
                if (PART == 2){
                    if (j > 0 && grid.get(i)[j-1] == '*'){
                        adjGears.add(i + " " + (j-1));
                    }
                }

                // Find the entire number
                while (j < grid.get(i).length && Character.isDigit(grid.get(i)[j])){
                    // Add the digit
                    number += grid.get(i)[j];
                    // Check to the left for symbols or gears
                    if (j > 0){
                        if (PART == 1){
                            // Check up and to the left
                            if (i > 0 && !Character.isDigit(grid.get(i-1)[j-1]) && grid.get(i-1)[j-1] != '.'){
                                nextToSymbol = true;
                            // Check down and to the left
                            }else if (i+1 < grid.size() && !Character.isDigit(grid.get(i+1)[j-1]) && grid.get(i+1)[j-1] != '.'){
                                nextToSymbol = true;
                            }
                        }

                        if (PART == 2){                            
                            // Check up and to the left
                            if (i > 0 && grid.get(i-1)[j-1] == '*'){
                                adjGears.add(i-1 + " " + (j-1));
                            }
                            // Check down and to the left
                            if (i+1 < grid.size() && grid.get(i+1)[j-1] == '*'){
                                adjGears.add(i+1 + " " + (j-1));
                            }
                        }
                    }
                    // See if the number continues
                    ++j;
                }

                // Check the surrounding spots for symbols from the spot after the final digit
                if (PART == 1){
                    if (!nextToSymbol){
                        // Check above the final digit
                        if (i > 0 && !Character.isDigit(grid.get(i-1)[j-1]) && grid.get(i-1)[j-1] != '.'){
                            nextToSymbol = true;
                        // Check below the final digit
                        }else if (i+1 < grid.size() && !Character.isDigit(grid.get(i+1)[j-1]) && grid.get(i+1)[j-1] != '.'){
                            nextToSymbol = true;
                        // Check after the final digit
                        }else if (j < grid.get(i).length){
                            // Check the next position
                            if (!Character.isDigit(grid.get(i)[j]) && grid.get(i)[j] != '.'){
                                nextToSymbol = true;
                            // Check up
                            }else if (i > 0 && !Character.isDigit(grid.get(i-1)[j]) && grid.get(i-1)[j] != '.'){
                                nextToSymbol = true;
                            // Check down
                            }else if (i+1 < grid.size() && !Character.isDigit(grid.get(i+1)[j]) && grid.get(i+1)[j] != '.'){
                                nextToSymbol = true;
                            }
                        }
                    }

                    // Add the number if it is a part
                    if (nextToSymbol){
                        total += Integer.parseInt(number);
                    }
                }

                // Check the surrounding spots for symbols from the spot after the final digit
                if (PART == 2){
                    // Check above the final digit
                    if (i > 0 && grid.get(i-1)[j-1] == '*'){
                        adjGears.add(i-1 + " " + (j-1));
                    }
                    // Check below the final digit
                    if (i+1 < grid.size() && grid.get(i+1)[j-1] == '*'){
                        adjGears.add(i+1 + " " + (j-1));
                    }
                    // Check after the final digit
                    if (j < grid.get(i).length){
                        // Check the next position
                        if (grid.get(i)[j] == '*'){
                            adjGears.add(i + " " + j);
                        }
                        // Check up
                        if (i > 0 && grid.get(i-1)[j] == '*'){
                            adjGears.add(i-1 + " " + j);
                        }
                        // Check down
                        if (i+1 < grid.size() && grid.get(i+1)[j] == '*'){
                            adjGears.add(i+1 + " " + j);
                        }
                    }

                    // Loop through every gear adjacent to the number
                    for (String current : adjGears){
                        // Whether the gear has already been found
                        boolean found = false;
                        // Loop through all previously existing gears
                        for (int k=0; k<gears.size(); ++k){
                            // The current gear
                            String gear = gears.get(k);
                            // If the adjacent gear is at the same position as the existing gear
                            if (current.split(" ")[0].equals(gear.split(" ")[0]) && current.split(" ")[1].equals(gear.split(" ")[1])){
                                // Add the current number as adjacent to the gear
                                gear += " " + number;
                                gears.set(k,gear);
                                found = true;
                                // Stop searching
                                break;
                            }
                        }
                        // If the gear had not been found
                        if (!found){
                            // Add the new gear
                            gears.add(current + " " + number);
                        }
                    }
                }
            }
        }

        // Add up the gear ratios
        if (PART == 2){
            // Loop through every gear
            for (String gear : gears){
                // Gears are only adjacent to exactly two numbers
                if (gear.split(" ").length != 4){
                    continue;
                }
                String[] split = gear.split(" ");
                // Add the gear ratio
                total += Integer.parseInt(split[2]) * Integer.parseInt(split[3]);
            }
        }

        // Print the answer
        System.out.println(total);
    }
}