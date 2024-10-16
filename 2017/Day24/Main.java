/*
Henry Anderson
Advent of Code 2017 Day 24 https://adventofcode.com/2017/day/24
Input: https://adventofcode.com/2017/day/24/input
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
        // All of the available parts
        ArrayList<int[]> parts = new ArrayList<>();
        while (sc.hasNext()){
            String[] split = sc.nextLine().split("/");
            parts.add(new int[] {Integer.parseInt(split[0]),Integer.parseInt(split[1])});
        }

        // The pieces currently making up the bridge
        Stack<int[]> bridge = new Stack<>();
        // The strongest bridge
        int strongest = 0;
        // The longest bridge
        int longest = 0;

        // The current port of the end of the current bridge
        int port = 0;
        // The strength of the current bridge
        int size = 0;
        // The part currently being examined
        int index = 0;
        // Continue until every possible combination has been tried
        while (true){
            // Loop through every part
            while (index < parts.size()){
                // Can't add the same piece twice
                if (bridge.contains(parts.get(index))){
                    ++index;
                    continue;
                }

                // If the piece has a matching port, attach it,
                // starting over with the new open port
                if (parts.get(index)[0] == port){
                    bridge.push(parts.get(index));
                    port = parts.get(index)[1];
                    size += parts.get(index)[0] + parts.get(index)[1];
                    index = 0;
                }else if (parts.get(index)[1] == port){
                    bridge.push(parts.get(index));
                    port = parts.get(index)[0];
                    size += parts.get(index)[0] + parts.get(index)[1];
                    index = 0;
                }else{
                    // Otherwise, look at the next piece
                    ++index;
                }
            }
            // If all starting pieces have been examined, quit
            if (bridge.size() == 0){
                break;
            }

            // Part 1 finds the strength of the strongest bridge
            if (PART == 1){
                strongest = Math.max(strongest,size);
            }

            // Part 2 finds the strength of the strongest longest bridge
            if (PART == 2){
                // If the bridge is longer
                if (bridge.size() > longest){
                    // Override the strength
                    longest = bridge.size();
                    strongest = size;
                }else if (bridge.size() == longest){
                    // If they're the same, choose the strongest
                    strongest = Math.max(strongest,size);
                }
            }

            // All pieces have been examined, back out one piece
            index = parts.indexOf(bridge.pop());
            // Go back to looking for the previous port
            if (port == parts.get(index)[0]){
                port = parts.get(index)[1];
            }else{
                port = parts.get(index)[0];
            }
            // Decrease by the strength of the last piece
            size -= parts.get(index)[0] + parts.get(index)[1];
            // Start looking for the next piece after the one you just tried
            ++index;
        }

        // Print the answer
        System.out.println(strongest);
    }
}