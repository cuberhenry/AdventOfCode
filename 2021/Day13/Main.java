/*
Henry Anderson
Advent of Code 2021 Day 13 https://adventofcode.com/2021/day/13
Input: https://adventofcode.com/2021/day/13/input
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
        // The list of all dots
        ArrayList<String> dots = new ArrayList<>();
        // Take in the initial dots
        String line = sc.nextLine();
        while (!line.equals("")){
            dots.add(line);
            line = sc.nextLine();
        }

        // The bounds of the result
        int maxX = 0;
        int maxY = 0;

        // Take in all the folds
        while (sc.hasNext()){
            // Get the fold specs
            String fold = sc.nextLine().split(" ")[2];
            boolean vertical = fold.charAt(0) == 'y';
            int marker = Integer.parseInt(fold.substring(2));
            // Save the new bounds
            if (vertical){
                maxY = marker-1;
            }else{
                maxX = marker-1;
            }

            // Loop through every dot
            for (int i=dots.size()-1; i>=0; --i){
                if (vertical){
                    int value = Integer.parseInt(dots.get(i).split(",")[1]);
                    // If it's beyond the new bounds
                    if (value > marker){
                        // Create the new dot by folding it over
                        String newDot = dots.get(i).split(",")[0] + "," + (marker - (value-marker));
                        // Merge dots that are now the same
                        if (dots.contains(newDot)){
                            dots.remove(i);
                        }else{
                            dots.set(i,newDot);
                        }
                    }
                }else{
                    int value = Integer.parseInt(dots.get(i).split(",")[0]);
                    // If it's beyond the new bounds
                    if (value > marker){
                        // Create the new dot by folding it over
                        String newDot = marker - (value-marker) + "," + dots.get(i).split(",")[1];
                        // Merge dots that are now the same
                        if (dots.contains(newDot)){
                            dots.remove(i);
                        }else{
                            dots.set(i,newDot);
                        }
                    }
                }
            }

            // Part 1 finds the number of dots left after the first fold
            if (PART == 1){
                System.out.println(dots.size());
                return;
            }
        }
        
        // Part 2 finds the result after finishing folding the paper
        for (int i=0; i<=maxY; ++i){
            for (int j=0; j<=maxX; ++j){
                if (dots.contains(j + "," + i)){
                    System.out.print("#");
                }else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}