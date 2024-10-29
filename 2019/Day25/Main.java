/*
Henry Anderson
Advent of Code 2019 Day 25 https://adventofcode.com/2019/day/25
Input: https://adventofcode.com/2019/day/25/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import com.aoc.mylibrary.IntCode;
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
        // Part 2 doesn't require code
        if (PART == 2){
            System.out.println("Align the Warp Drive");
            return;
        }

        // Create the program
        IntCode program = new IntCode(sc.nextLine());

        // The list of items not to pick up
        ArrayList<String> forbiddenItems = new ArrayList<>();
        forbiddenItems.add("escape pod");
        forbiddenItems.add("giant electromagnet");
        forbiddenItems.add("infinite loop");
        forbiddenItems.add("molten lava");
        forbiddenItems.add("photons");

        // The moves that get you to the sensor room
        String movesToDest = "";
        // The door that moves you to the pressure sensor room
        String nextDoor = "";
        // All of the moves from start that it takes to get to the current position
        Stack<String> moves = new Stack<>();
        // A list of states of moves
        ArrayList<String> explored = new ArrayList<>();
        // The starting position has been explored
        explored.add("[]");

        // All of the items held by the robot
        ArrayList<String> heldItems = new ArrayList<>();

        // Continue until all rooms have been searched
        while (true){
            // Get output from the program
            String outString = "";
            for (long out : program.run()){
                char outChar = (char)out;
                outString += outChar;
            }

            // Parse the output
            Scanner stringSc = new Scanner(outString);
            String line = stringSc.nextLine();
            // Find the name of the current room
            while (!line.matches("== .* ==")){
                line = stringSc.nextLine();
            }
            String roomName = line.substring(3,line.length()-3);
            // Find the start of the list of doors
            while (!line.equals("Doors here lead:")){
                line = stringSc.nextLine();
            }
            line = stringSc.nextLine();
            // The list of doors in this room
            ArrayList<String> doors = new ArrayList<>();
            while (!line.equals("")){
                doors.add(line.substring(2));
                line = stringSc.nextLine();
            }
            line = stringSc.nextLine();
            ArrayList<String> items = new ArrayList<>();
            // Check if there are any items here
            if (line.equals("Items here:")){
                line = stringSc.nextLine();
                // Get the names of the items
                while (!line.equals("")){
                    items.add(line.substring(2));
                    line = stringSc.nextLine();
                }
            }
            stringSc.close();
            
            // Loop through each item
            for (String item : items){
                // If it won't break the program
                if (!forbiddenItems.contains(item)){
                    // Take it
                    program.addInput("take " + item);
                    heldItems.add(item);
                }
            }

            // The next direction to move in
            String direction = "";
            // Whether a new room is being entered
            boolean exploring = true;
            // A string representing the current moves
            String movesSoFar = moves.toString();
            // Choose the mvoe
            if (roomName.equals("Security Checkpoint")){
                // Save the door to the pressure sensor
                nextDoor = doors.get(1 - doors.indexOf(switch(moves.peek()){
                    case "north" -> "south";
                    case "south" -> "north";
                    case "east" -> "west";
                    default -> "east";
                }));
                // Save the steps to return to this room
                movesToDest = moves.toString();
                // Not exploring a new room
                exploring = false;
            // Check each direction if there's a door, you haven't just come from that direction, and you haven't been to that room
            }else if (doors.contains("north") && (moves.size() == 0 || !moves.peek().equals("south")) && !explored.contains(movesSoFar.substring(0,movesSoFar.length()-1) + (movesSoFar.length() > 2 ? ", " : "") + "north]")){
                direction = "north";
            }else if (doors.contains("south") && (moves.size() == 0 || !moves.peek().equals("north")) && !explored.contains(movesSoFar.substring(0,movesSoFar.length()-1) + (movesSoFar.length() > 2 ? ", " : "") + "south]")){
                direction = "south";
            }else if (doors.contains("east") && (moves.size() == 0 || !moves.peek().equals("west")) && !explored.contains(movesSoFar.substring(0,movesSoFar.length()-1) + (movesSoFar.length() > 2 ? ", " : "") + "east]")){
                direction = "east";
            }else if (doors.contains("west") && (moves.size() == 0 || !moves.peek().equals("east")) && !explored.contains(movesSoFar.substring(0,movesSoFar.length()-1) + (movesSoFar.length() > 2 ? ", " : "") + "west]")){
                direction = "west";
            }else if (moves.size() == 0){
                // All rooms have been explored
                // Return to the security checkpoint
                for (String dir : movesToDest.substring(1,movesToDest.length()-1).split(", ")){
                    program.addInput(dir);
                }
                program.run();
                break;
            }else{
                // Go back one room
                exploring = false;
            }

            if (exploring){
                // Save relevant values for the new move
                moves.add(direction);
                explored.add(moves.toString());
            }else{
                // Go back one room
                direction = switch(moves.pop()){
                    case "north" -> "south";
                    case "south" -> "north";
                    case "east" -> "west";
                    default -> "east";
                };
            }

            // Move in the indicated direction
            program.addInput(direction);
        }

        // Loop through each combination of items
        for (int i=0; i<Math.pow(2,heldItems.size()); ++i){
            // Drop all items that aren't in this combination
            for (int j=0; j<heldItems.size(); ++j){
                if (i / (j+1) % 2 == 1){
                    program.addInput("drop " + heldItems.get(j));
                }
            }
            // Move to the sensor
            program.addInput(nextDoor);

            // Get the program's output
            String outString = "";
            for (long out : program.run()){
                outString += (char)out;
            }

            // If you found the exit
            if (program.isFinished()){
                // Parse the output
                Scanner stringSc = new Scanner(outString);
                // Find the line that includes the answer
                String line = stringSc.nextLine();
                while (!line.matches("\"Oh, hello! You should be able to get in by typing \\d* on the keypad at the main airlock.\"")){
                    line = stringSc.nextLine();
                }
                stringSc.close();

                // Print the answer
                System.out.println(line.split(" ")[11]);
                break;
            }

            // Pick up all dropped items
            for (int j=0; j<heldItems.size(); ++j){
                if (i / (j+1) % 2 == 1){
                    program.addInput("take " + heldItems.get(j));
                }
            }
        }
    }
}