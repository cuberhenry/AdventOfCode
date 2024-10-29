/*
Henry Anderson
Advent of Code 2018 Day 14 https://adventofcode.com/2018/day/14
Input: https://adventofcode.com/2018/day/14/input
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
        // The ongoing list of recipes
        StringBuilder recipes = new StringBuilder("37");
        // The target
        int num = sc.nextInt();
        // The recipes the two elves are looking at
        int pointer1 = 0;
        int pointer2 = 1;

        // Continue until the end condition has been found
        while (true){
            // Get each score
            int score1 = recipes.charAt(pointer1) - '0';
            int score2 = recipes.charAt(pointer2) - '0';
            // Add the resulting score sum to recipes
            recipes.append(score1 + score2);
            // Move each elf forward its score amount
            pointer1 = (pointer1 + score1 + 1) % recipes.length();
            pointer2 = (pointer2 + score2 + 1) % recipes.length();

            // Part 1 finds the 10 scores starting at the input index
            if (PART == 1){
                if (recipes.length() >= num + 10){
                    System.out.println(recipes.substring(num,num+10));
                    break;
                }
            }

            // Part 2 finds the number of scores to the left of this string of scores
            if (PART == 2){
                if (recipes.substring(Math.max(0,recipes.length()-7)).indexOf("" + num) != -1){
                    System.out.println(recipes.indexOf("" + num));
                    break;
                }
            }
        }
    }
}