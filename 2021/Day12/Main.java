/*
Henry Anderson
Advent of Code 2021 Day 12 https://adventofcode.com/2021/day/12
Input: https://adventofcode.com/2021/day/12/input
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
        // All the caves each cave is connected to
        HashMap<String,ArrayList<String>> edges = new HashMap<>();
        // Take in each path between caves
        while (sc.hasNext()){
            String[] line = sc.nextLine().split("-");
            // If not leaving end or arriving at start, add each cave to the other
            if (!(line[0].equals("end") || line[1].equals("start"))){
                if (edges.containsKey(line[0])){
                    edges.get(line[0]).add(line[1]);
                }else{
                    ArrayList<String> edge = new ArrayList<>();
                    edge.add(line[1]);
                    edges.put(line[0],edge);
                }
            }
            if (!(line[0].equals("start") || line[1].equals("end"))){
                if (edges.containsKey(line[1])){
                    edges.get(line[1]).add(line[0]);
                }else{
                    ArrayList<String> edge = new ArrayList<>();
                    edge.add(line[0]);
                    edges.put(line[1],edge);
                }
            }
        }

        // All of the current BFS paths
        ArrayList<String> openPaths = new ArrayList<>();
        // All paths start at start
        openPaths.add("start");
        // The number of unique completed paths
        int paths = 0;
        // Continue until all paths have been completed
        while (!openPaths.isEmpty()){
            // Look at the next path
            String path = openPaths.removeFirst();
            String[] split = path.split(" ");
            // Loop through every possible destination
            for (String cave : edges.get(split[0])){
                // end finishes the path immediately
                if (cave.equals("end")){
                    ++paths;
                    continue;
                }

                // The new path when entering this cave
                String newPath = cave + " " + path;

                // Trim for repeated small caves
                if (Character.isLowerCase(cave.charAt(0)) && path.contains(cave)){
                    // Part 1 enters each small cave at most once
                    if (PART == 1){
                        continue;
                    }
                    
                    // Part 2 can enter up to one small cave twice and the rest once
                    if (PART == 2){
                        // Only trim branches that already have a repeat (signified by "ed")
                        if (split[split.length-1].equals("started")){
                            continue;
                        }

                        // Indicate a duplicate has been entered
                        newPath += "ed";
                    }
                }

                // Add the new path
                openPaths.add(newPath);
            }
        }

        // Print the answer
        System.out.println(paths);
    }
}