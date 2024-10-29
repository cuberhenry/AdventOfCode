/*
Henry Anderson
Advent of Code 2019 Day 20 https://adventofcode.com/2019/day/20
Input: https://adventofcode.com/2019/day/20/input
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
        // Take in the whole map
        ArrayList<String> map = new ArrayList<>();
        while (sc.hasNext()){
            map.add(sc.nextLine());
        }

        // The source and destination for each portal
        HashMap<String,String> portals = new HashMap<>();
        // History to not repeat
        HashSet<String> visited = new HashSet<>();
        // The queue of locations to visit
        ArrayList<String> queue = new ArrayList<>();
        // Start and destination coordinates
        int startX = 0;
        int startY = 0;
        int destX = 0;
        int destY = 0;

        // Loop through the whole map looking for portals
        for (int i=0; i<map.size()-1; ++i){
            for (int j=0; j<map.get(0).length()-1; ++j){
                // If it's alphabetic
                if (Character.isAlphabetic(map.get(i).charAt(j))){
                    String name;
                    int x;
                    int y;
                    // Top-down name
                    if (Character.isAlphabetic(map.get(i+1).charAt(j))){
                        // Make the name
                        name = "" + map.get(i).charAt(j) + map.get(i+1).charAt(j);
                        x = j;
                        // Find the entrance to the portal
                        if (i > 0 && map.get(i-1).charAt(j) == '.'){
                            y = i-1;
                        }else{
                            y = i+2;
                        }
                    // Left-right name
                    }else if (Character.isAlphabetic(map.get(i).charAt(j+1))){
                        // Make the name
                        name = map.get(i).substring(j,j+2);
                        y = i;
                        // Find the entrance to the portal
                        if (j > 0 && map.get(i).charAt(j-1) == '.'){
                            x = j-1;
                        }else{
                            x = j+2;
                        }
                    }else{
                        // Character was the end of a name, skip
                        continue;
                    }
                    // Entrance
                    if (name.equals("AA")){
                        visited.add(x + " " + y + " 0");
                        queue.add(x + " " + y + " 0");
                        startX = x;
                        startY = y;
                    // Exit
                    }else if (name.equals("ZZ")){
                        destX = x;
                        destY = y;
                    // Found the other end
                    }else if (portals.containsKey(name)){
                        String other = portals.get(name);
                        portals.remove(name);
                        portals.put(x + " " + y,other);
                        portals.put(other,x + " " + y);
                    }else{
                    // Only found the first end
                        portals.put(name,x + " " + y);
                    }
                }
            }
        }

        // Fill in dead ends
        // Loop through each inner square
        for (int i=3; i<map.size()-3; ++i){
            for (int j=3; j<map.get(i).length()-3; ++j){
                // If it's open and has three walls, it's a dead end
                if (map.get(i).charAt(j) == '.'){
                    int count = 0;
                    if (map.get(i-1).charAt(j) == '#'){
                        ++count;
                    }
                    if (map.get(i+1).charAt(j) == '#'){
                        ++count;
                    }
                    if (map.get(i).charAt(j-1) == '#'){
                        ++count;
                    }
                    if (map.get(i).charAt(j+1) == '#'){
                        ++count;
                    }
                    if (count >= 3){
                        // Fill it in
                        map.set(i,map.get(i).substring(0,j) + '#' + map.get(i).substring(j+1));
                        // Back up to check for new dead ends
                        --i;
                        --j;
                    }
                }
            }
        }

        // The number of steps
        int steps = 0;
        // Continue until the exit is found
        while (true){
            // Increase steps
            ++steps;
            // Save all the locations you can get to in one more step
            ArrayList<String> newQueue = new ArrayList<>();
            // Continue for all the current locations
            while (!queue.isEmpty()){
                // Get the three values
                String[] state = queue.removeFirst().split(" ");
                int x = Integer.parseInt(state[0]);
                int y = Integer.parseInt(state[1]);
                int depth = Integer.parseInt(state[2]);

                // Loop in each direction and through portals
                for (int i=0; i<5; ++i){
                    int newX = x;
                    int newY = y;
                    int newDepth = depth;
                    switch(i){
                        case 0 -> {--newX;}
                        case 1 -> {--newY;}
                        case 2 -> {++newX;}
                        case 3 -> {++newY;}
                        case 4 -> {
                            // Skip if not a portal
                            if (portals.containsKey(x + " " + y)){
                                // Get the other end of the portal
                                String[] split = portals.get(x + " " + y).split(" ");
                                newX = Integer.parseInt(split[0]);
                                newY = Integer.parseInt(split[1]);

                                // Part 1 stays in a single plane
                                // Part 2 changes depths through portals
                                if (PART == 2){
                                    if (x == 2 || y == 2 || x == map.get(0).length()-3 || y == map.size()-3){
                                        --newDepth;
                                        if (newDepth < 0){
                                            continue;
                                        }
                                    }else{
                                        ++newDepth;
                                    }
                                }
                            }else{
                                continue;
                            }
                        }
                    }

                    // Can't access the start or end unless depth is 0
                    if (PART == 2){
                        if (newX == startX && newY == startY || newX == destX && newY == destY && depth != 0){
                            continue;
                        }
                    }

                    // Skip visited locations
                    if (!visited.contains(newX + " " + newY + " " + newDepth) && map.get(newY).charAt(newX) == '.'){
                        // Destination has been found
                        if (destX == newX && destY == newY){
                            // Print the answer
                            System.out.println(steps);
                            return;
                        }

                        // Add the new location
                        visited.add(newX + " " + newY + " " + newDepth);
                        newQueue.add(newX + " " + newY + " " + newDepth);
                    }
                }
            }
            queue = newQueue;
        }
    }
}