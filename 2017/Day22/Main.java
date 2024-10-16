/*
Henry Anderson
Advent of Code 2017 Day 22 https://adventofcode.com/2017/day/22
Input: https://adventofcode.com/2017/day/22/input
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
        // The infected nodes
        HashMap<String,Integer> infected = new HashMap<>();
        // The current position of the virus
        int y = 0;
        int x = 0;

        // Take in the input
        while (sc.hasNext()){
            String line = sc.nextLine();
            x = line.length() / 2;

            // If the node is infected, add it to the list
            for (int i=0; i<line.length(); ++i){
                if (line.charAt(i) == '#'){
                    infected.put(i + " " + y,1);
                }
            }
            ++y;
        }

        y /= 2;
        // The direction the virus is facing
        int dir = 0;

        // The number of infected nodes during a burst
        int numInfected = 0;

        // The number of times the virus acts
        int bursts = 10000;

        // Part 1 finds the number of infected after 10000 rounds
        // Part 2 does 10000000 rounds and adds two more node states
        if (PART == 2){
            bursts = 10000000;
        }

        // Loop through every round
        for (int i=0; i<bursts; ++i){
            // If the node isn't clean
            if (infected.containsKey(x + " " + y)){
                // Clean it and turn right
                if (PART == 1){
                    infected.remove(x + " " + y);
                    dir = (dir + 1) % 4;
                }

                if (PART == 2){
                    switch(infected.get(x + " " + y)){
                        // Is weakened, infect it and don't turn
                        case 0 -> {
                            infected.put(x + " " + y,1);
                            ++numInfected;
                        }
                        // Is infected, flag it and turn right
                        case 1 -> {
                            dir = (dir + 1) % 4;
                            infected.put(x + " " + y,2);
                        }
                        // Is flagged, clean it and reverse
                        case 2 -> {
                            dir = (dir + 2) % 4;
                            infected.remove(x + " " + y);
                        }
                    }
                }
            }else{
                // Turn left
                dir = (dir + 3) % 4;
                // Infect (Part 1) or weaken (Part 2)
                infected.put(x + " " + y,0);

                if (PART == 1){
                    ++numInfected;
                }
            }

            // Move in the given direction
            switch(dir){
                case 0 -> {--y;}
                case 1 -> {++x;}
                case 2 -> {++y;}
                case 3 -> {--x;}
            }
        }

        // Print the answer
        System.out.println(numInfected);
    }
}