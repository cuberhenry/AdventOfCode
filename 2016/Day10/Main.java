/*
Henry Anderson
Advent of Code 2016 Day 10 https://adventofcode.com/2016/day/10
Input: https://adventofcode.com/2016/day/10/input
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
        // The list of all bots of the form
        // {low, high, val1, val2}
        ArrayList<int[]> bots = new ArrayList<>();
        // The list of all outputs
        ArrayList<ArrayList<Integer>> outputs = new ArrayList<>();
        // The queue of robots to determine giving
        ArrayList<Integer> queue = new ArrayList<>();
        // Loop through every line of input
        while (sc.hasNext()){
            // Split the line
            String[] line = sc.nextLine().split(" ");
            // Add a value to a bot
            if (line[0].equals("value")){
                // Get the bot index
                int bot = Integer.parseInt(line[5]);
                // If the bot doesn't exist, add bots until it does
                while (bots.size() <= bot){
                    bots.add(new int[4]);
                }
                // If it already is holding something
                if (bots.get(bot)[2] != 0){
                    // Add the item to the bot's second slot
                    bots.get(bot)[3] = Integer.parseInt(line[1]);
                    // Bot has two items, so add it to the queue
                    queue.add(bot);
                }else{
                    // Add the item to the bot's first slot
                    bots.get(bot)[2] = Integer.parseInt(line[1]);
                }
            // Add a bot's instructions
            }else{
                // Get the bot index
                int bot = Integer.parseInt(line[1]);
                // Get the location to give the lower value
                int low = Integer.parseInt(line[6]);
                // Get the location to give the higher value
                int high = Integer.parseInt(line[11]);
                // Use negative values for output to differentiate
                if (line[5].equals("output")){
                    low *= -1;
                    --low;
                }
                if (line[10].equals("output")){
                    high *= -1;
                    --high;
                }
                // Make sure all the bots exist
                while (bots.size() <= Math.max(Math.max(low,high),bot)){
                    bots.add(new int[4]);
                }
                // Make sure all the outputs exist
                while (outputs.size() <= (Math.min(low,high)+1)*-1){
                    outputs.add(new ArrayList<>());
                }
                // Set the values
                bots.get(bot)[0] = low;
                bots.get(bot)[1] = high;
            }
        }

        // Continue until all bots have finished deciding
        while (!queue.isEmpty()){
            // Get the index of the bot
            int index = queue.remove(0);
            // Get the bot
            int[] bot = bots.get(index);
            // Get the chip values
            int high = Math.max(bot[2],bot[3]);
            int low = Math.min(bot[2],bot[3]);
            // Reset the values
            bot[2] = 0;
            bot[3] = 0;

            // Part 1 finds the bot that compares values 61 and 17
            if (PART == 1){
                if (high == 61 && low == 17){
                    System.out.println(index);
                    return;
                }
            }

            // Give the lower item away
            if (bot[0] < 0){
                // Add the value to output
                outputs.get(bot[0]*-1-1).add(low);
                // If the relevant outputs are full, end early
                if (!outputs.get(0).isEmpty() && !outputs.get(1).isEmpty() && !outputs.get(2).isEmpty()){
                    break;
                }
            }else if (bots.get(bot[0])[2] == 0){
                // Add the value to the bot's first slot
                bots.get(bot[0])[2] = low;
            }else{
                // Add the value to the bot's second slot
                bots.get(bot[0])[3] = low;
                // Add the bot to the queue
                queue.add(bot[0]);
            }

            // Give the higher item away
            if (bot[1] < 0){
                // Add the value to output
                outputs.get(bot[1]*-1-1).add(high);
                // If the relevant outputs are full, end early
                if (!outputs.get(0).isEmpty() && !outputs.get(1).isEmpty() && !outputs.get(2).isEmpty()){
                    break;
                }
            }else if (bots.get(bot[1])[2] == 0){
                // Add the value to the bot's first slot
                bots.get(bot[1])[2] = high;
            }else{
                // Add the value to the bot's second slot
                bots.get(bot[1])[3] = high;
                // Add the bot to the queue
                queue.add(bot[1]);
            }
        }

        // Part 2 finds the product of the values of chips in outputs 0, 1, and 2
        System.out.println(outputs.get(0).get(0)*outputs.get(1).get(0)*outputs.get(2).get(0));
    }
}