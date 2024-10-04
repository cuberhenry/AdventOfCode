/*
Henry Anderson
Advent of Code 2020 Day 7 https://adventofcode.com/2020/day/7
Input: https://adventofcode.com/2020/day/7/input
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
        // Each color bag
        ArrayList<String> outers = new ArrayList<>();
        // The bags within each color bag
        ArrayList<ArrayList<String>> inners = new ArrayList<>();

        // Take in every rule
        while (sc.hasNext()){
            // Split the rule
            String[] line = sc.nextLine().split(" ");
            // Add the bag color to the list
            outers.add(line[0] + " " + line[1]);
            ArrayList<String> inner = new ArrayList<>();
            inners.add(inner);
            // Skip bags that don't have any inner bags
            if (line[4].equals("no")){
                continue;
            }
            // Add each of the inner bag colors
            for (int i=4; i<line.length; i+= 4){
                inner.add(line[i] + " " + line[i+1] + " " + line[i+2]);
            }
        }

        // Part 1 finds the number of bags that can contain a shiny gold bag
        if (PART == 1){
            // The colors that can contain a shiny gold bag
            ArrayList<String> colors = new ArrayList<>();
            // Whether a new color has been added
            boolean repeat = true;

            // Keep going until all have been found
            while (repeat){
                repeat = false;
                // Loop through every color
                for (int i=0; i<outers.size(); ++i){
                    // Skip colors that have already been added
                    if (colors.contains(outers.get(i))){
                        continue;
                    }
                    // Loop through every inner color
                    for (String inner : inners.get(i)){
                        inner = inner.substring(inner.indexOf(' ')+1);
                        // If the color contains shiny gold or another color in the list
                        if (inner.equals("shiny gold") || colors.contains(inner)){
                            // Add it
                            colors.add(outers.get(i));
                            repeat = true;
                            break;
                        }
                    }
                }
            }

            // Print the answer
            System.out.println(colors.size());
        }

        // Part 2 finds the number of bags that one shiny gold bag contains
        if (PART == 2){
            // The number of bags
            int count = 0;
            // The inner bags that haven't been opened
            ArrayList<String> colors = new ArrayList<>();
            colors.add("shiny gold");
            // The number of that color bag
            ArrayList<Integer> counts = new ArrayList<>();
            counts.add(1);

            // Loop through every remaining bag
            while (colors.size() > 0){
                int index = outers.indexOf(colors.remove(0));
                int amount = counts.remove(0);
                // For every bag inside the current color
                for (String inner : inners.get(index)){
                    String[] split = inner.split(" ");
                    int num = Integer.parseInt(split[0]);
                    // Add the bags to the total
                    count += amount * num;
                    int innerIndex = colors.indexOf(split[1]+" "+split[2]);
                    // If this color isn't in the list
                    if (innerIndex == -1){
                        // Add it
                        colors.add(split[1] + " " + split[2]);
                        counts.add(amount * num);
                    }else{
                        // Add to it
                        counts.set(innerIndex,counts.get(innerIndex) + amount*num);
                    }
                }
            }

            // Print the answer
            System.out.println(count);
        }
    }
}