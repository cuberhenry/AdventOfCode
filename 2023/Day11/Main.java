/*
Henry Anderson
Advent of Code 2023 Day 11 https://adventofcode.com/2023/day/11
Input: https://adventofcode.com/2023/day/11/input
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
        // The sum of the distances between every pair of galaxies
        long total = 0;

        // The given map of galaxies
        ArrayList<String> map = new ArrayList<>();
        // The list of empty rows and columns
        ArrayList<Integer> emptyRow = new ArrayList<>();
        ArrayList<Integer> emptyCol = new ArrayList<>();
        // The list of locations of galaxies
        ArrayList<String> galaxies = new ArrayList<>();

        // Take in the entire map
        while (sc.hasNext()){
            String line = sc.nextLine();
            // Add any found empty lines
            if (line.indexOf('#') == -1){
                emptyRow.add(map.size());
            }
            map.add(line);
        }

        // Search through every column
        for (int i=0; i<map.get(0).length(); ++i){
            boolean empty = true;
            for (int j=0; j<map.size(); ++j){
                if (map.get(j).charAt(i) == '#'){
                    galaxies.add(j + " " + i);
                    empty = false;
                }
            }
            // Add the column if it's empty
            if (empty){
                emptyCol.add(i);
            }
        }

        // Loop through every galaxy
        for (int i=0; i<galaxies.size(); ++i){
            // Collect its coordinates
            String[] galaxy = galaxies.get(i).split(" ");
            int x1 = Integer.parseInt(galaxy[0]);
            int y1 = Integer.parseInt(galaxy[1]);
            // Loop through every subsequent galaxy
            for (int j=i+1; j<galaxies.size(); ++j){
                // Collect its coordinates
                galaxy = galaxies.get(j).split(" ");
                int x2 = Integer.parseInt(galaxy[0]);
                int y2 = Integer.parseInt(galaxy[1]);

                // Add the base distance between the two galaxies
                total += Math.abs(x1-x2) + Math.abs(y1-y2);
                // Loop through every empty row
                for (Integer r : emptyRow){
                    // If the empty row is between the two galaxies
                    if (r < Math.max(x1,x2) && r > Math.min(x1,x2)){
                        // Part 1 expands empty rows to two times the size
                        if (PART == 1){
                            ++total;
                        }

                        // Part 2 expands empty rows to a million times the size
                        if (PART == 2){
                            total += 999999;
                        }
                    }
                }
                // Loop through every column
                for (Integer c : emptyCol){
                    // If the empty column is between the two galaxies
                    if (c < Math.max(y1,y2) && c > Math.min(y1,y2)){
                        // Part 1 expands empty columns to two times the size
                        if (PART == 1){
                            ++total;
                        }

                        // Part 2 expands empty columns to a million times the size
                        if (PART == 2){
                            total += 999999;
                        }
                    }
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}