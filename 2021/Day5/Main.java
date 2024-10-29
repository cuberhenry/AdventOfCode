/*
Henry Anderson
Advent of Code 2021 Day 5 https://adventofcode.com/2021/day/5
Input: https://adventofcode.com/2021/day/5/input
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
        // All points that are on one line
        ArrayList<String> unique = new ArrayList<>();
        // All points that are on at least two lines
        ArrayList<String> duplicates = new ArrayList<>();

        // Take in every line
        while (sc.hasNext()){
            // Get the values
            String[] split = sc.nextLine().split(",| -> ");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);

            int a = Integer.parseInt(split[2]);
            int b = Integer.parseInt(split[3]);

            if (x == a){
                // Vertical line
                for (int i=Math.min(y,b); i<=Math.max(y,b); ++i){
                    String point = x + " " + i;
                    if (unique.contains(point)){
                        unique.remove(point);
                        duplicates.add(point);
                    }else if (!duplicates.contains(point)){
                        unique.add(point);
                    }
                }
            }else if (y == b){
                // Horizontal line
                for (int i=Math.min(x,a); i<=Math.max(x,a); ++i){
                    String point = i + " " + y;
                    if (unique.contains(point)){
                        unique.remove(point);
                        duplicates.add(point);
                    }else if (!duplicates.contains(point)){
                        unique.add(point);
                    }
                }
            }

            // Part 1 finds the number of duplicates with non-diagonal lines
            // Part 2 finds the same with all lines
            if (PART == 2){
                // Diagonal line
                if (x != a && y != b){
                    for (int i=0; i<=Math.max(x,a)-Math.min(x,a); ++i){
                        String point = Math.min(x,a) + i + " " +
                            ((a > x) ^ (b > y) ? (Math.max(y,b) - i) : (Math.min(y,b) + i));
                        if (unique.contains(point)){
                            unique.remove(point);
                            duplicates.add(point);
                        }else if (!duplicates.contains(point)){
                            unique.add(point);
                        }
                    }
                }
            }
        }

        // Print the answer
        System.out.println(duplicates.size());
    }
}