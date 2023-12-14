/*
Henry Anderson
Advent of Code 2023 Day 10 https://adventofcode.com/2023/day/10
Input: https://adventofcode.com/2023/day/10/input
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
        // The map of pipes
        ArrayList<String> map = new ArrayList<>();
        // The coordinates of the beginning of the loop
        int startX = 0;
        int startY = 0;

        // Pipes that connect to the pipe in the given direction
        String up = "LJ|";
        String down = "F7|";
        String right = "LF-";
        String left = "J7-";

        // Take in every line of input
        while (sc.hasNext()){
            // Take in the next line
            String line = sc.nextLine();
            // Save the coordinates of the start
            if (line.contains("S")){
                startY = map.size();
                startX = line.indexOf('S');
            }
            // Add the line
            map.add(line);
        }

        // The list of pipes that are part of the loop
        ArrayList<String> locations = new ArrayList<>();
        locations.add(startY + " " + startX);

        // Whether the starting pipe connects to these two directions
        boolean d,r;
        String line = map.get(startY);
        // Check if the pipe below connects up
        d = startY < map.size() && up.contains(""+map.get(startY+1).charAt(startX));
        // Check if the pipe to the right connects left
        r = startX < map.get(0).length() && left.contains(""+line.charAt(startX+1));

        // Figure out the first pipe that the starting pipe is connected to and
        // set the starting pipe to its correct configuration
        // Check if the pipe above connects down
        if (startY > 0 && down.contains(""+map.get(startY-1).charAt(startX))){
            locations.add(0,startY-1 + " " + startX);
            if (d){
                map.set(startY,line.substring(0,startX) + "|" + line.substring(startX+1));
            }else if (r){
                map.set(startY,line.substring(0,startX) + "L" + line.substring(startX+1));
            }else{
                map.set(startY,line.substring(0,startX) + "J" + line.substring(startX+1));
            }
        }else if (d){
            locations.add(0,startY+1 + " " + startX);
            if (r){
                map.set(startY,line.substring(0,startX) + "F" + line.substring(startX+1));
            }else{
                map.set(startY,line.substring(0,startX) + "7" + line.substring(startX+1));
            }
        }else{
            locations.add(0,startY + " " + (startX+1));
            map.set(startY,line.substring(0,startX) + "-" + line.substring(startX+1));
        }

        // Continue until the entire loop has been found
        for (boolean added = true; added;){
            // Collect and dissect the previous location
            String[] state = locations.get(0).split(" ");
            int y = Integer.parseInt(state[0]);
            int x = Integer.parseInt(state[1]);
            // Find the pipe shape
            String pos = map.get(y).substring(x,x+1);

            // Look in each direction for whether the pipe connects to it and it hasn't been found
            if (up.contains(pos) && !locations.contains(y-1 + " " + x)){
                locations.add(0,y-1 + " " + x);
            }else if (left.contains(pos) && !locations.contains(y + " " + (x-1))){
                locations.add(0,y + " " + (x-1));
            }else if (down.contains(pos) && !locations.contains(y+1 + " " + x)){
                locations.add(0,y+1 + " " + x);
            }else if (right.contains(pos) && !locations.contains(y + " " + (x+1))){
                locations.add(0,y + " " + (x+1));
            }else{
                // If none of the four directions succeed, the loop has been completed
                added = false;
            }
        }

        // Part 1 finds the furthest distance in the loop from S
        if (PART == 1){
            // Print the answer
            System.out.println(locations.size()/2);
            return;
        }

        // Part 2 finds the number of tiles enclosed within the loop
        int total = 0;

        // Loop through only the inner rows
        for (int i=1; i<map.size()-1; ++i){
            // Whether the current location is inside the loop
            boolean inside = false;
            // Loop through every character
            for (int j=0; j<map.get(i).length()-1; ++j){
                // If the location is part of the loop
                if (locations.contains(i + " " + j)){
                    // The current pipe
                    char pos = map.get(i).charAt(j);
                    // If the pipe is vertically straight
                    if (pos == '|'){
                        // Guaranteed switch between inside and outside
                        inside = !inside;
                    }else if (pos == 'L' || pos == 'F'){
                        // Save the pipe shape
                        char letter = map.get(i).charAt(j);
                        // Search for the end of the straight pipe connection
                        ++j;
                        while (map.get(i).charAt(j) == '-'){
                            ++j;
                        }
                        // The pipe shape that ends the straight pipe connection
                        char newL = map.get(i).charAt(j);
                        // If the pipe corners go opposite directions
                        if (letter == 'F' && newL == 'J'
                            || letter == 'L' && newL == '7'){
                            // Switch between inside and outside
                            inside = !inside;
                        }
                    }
                }else if (inside){
                    // An inner tile found
                    ++total;
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}