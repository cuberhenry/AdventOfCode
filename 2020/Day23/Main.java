/*
Henry Anderson
Advent of Code 2020 Day 23 https://adventofcode.com/2020/day/23
Input: https://adventofcode.com/2020/day/23/input
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
        // Take in the input
        int[] input = new int[9];
        String line = sc.nextLine();
        for (int i=0; i<line.length(); ++i){
            input[i] = line.charAt(i)-'0';
        }
        // The number of times to move cups
        int numTurns = 100;
        // nextCup[i] is the cup after i
        int[] nextCup = new int[10];
        // Loop through the input
        for (int i=0; i<input.length; ++i){
            // Add each cup's next cup
            nextCup[input[i]] = input[(i+1)%9];
        }

        // Part 1 finds the order of the 9 cups after 100 suffles
        // Part 2 finds the next 2 cups after 1 after ten million shuffles
        if (PART == 2){
            // One million cups
            int[] cups = new int[1000001];
            // Copy over the original 9 cups
            for (int i=1; i<10; ++i){
                cups[i] = nextCup[i];
            }
            // Update the last cup to not circle back
            cups[input[input.length-1]] = 10;
            // Set all the rest of the cups to their next cup
            for (int i=10; i<1000000; ++i){
                cups[i] = i+1;
            }
            // Update the last cup to circle back to the first
            cups[1000000] = input[0];
            // Save the new nextCups
            nextCup = cups;

            // Perform ten million shuffles
            numTurns = 10000000;
        }

        // Start with the first cup
        int currentCup = input[0];
        // Loop through each suffle
        for (int i=0; i<numTurns; ++i){
            // The first of the three removed cups
            int firstCup = nextCup[currentCup];
            // The last of the three removed cups
            int thirdCup = nextCup[nextCup[firstCup]];
            // The dest cup is one less than the current cup
            int destCup = (currentCup - 3 + nextCup.length) % (nextCup.length-1) + 1;
            // Keep subtracting one if the cup is one of the removed cups
            while (destCup == firstCup || destCup == nextCup[firstCup] || destCup == thirdCup){
                destCup = (destCup - 3 + nextCup.length) % (nextCup.length-1) + 1;
            }
            // Finish removing the three cups
            nextCup[currentCup] = nextCup[thirdCup];
            // Connect the three cups to their new spot
            nextCup[thirdCup] = nextCup[destCup];
            nextCup[destCup] = firstCup;

            // Move to the next clockwise cup
            currentCup = nextCup[currentCup];
        }
        
        if (PART == 1){
            // Get the 8 cups values after 1
            int currCup = 1;
            // Print each cup number
            for (int i=0; i<8; ++i){
                currCup = nextCup[currCup];
                System.out.print(currCup);
            }
            System.out.println();
        }

        if (PART == 2){
            // Get the product of the two cup values after 1
            System.out.println((long)nextCup[1] * nextCup[nextCup[1]]);
        }
    }
}