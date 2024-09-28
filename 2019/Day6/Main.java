/*
Henry Anderson
Advent of Code 2019 Day 6 https://adventofcode.com/2019/day/6
Input: https://adventofcode.com/2019/day/6/input
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
        // A list of all objects
        ArrayList<String> objects = new ArrayList<>();
        // A lookup to see what an object is orbiting
        HashMap<String,String> orbits = new HashMap<>();

        // Take in every line
        while (sc.hasNextLine()){
            String[] split = sc.nextLine().split("\\)");
            // Add new objects
            if (!objects.contains(split[0])){
                objects.add(split[0]);
            }
            if (!objects.contains(split[1])){
                objects.add(split[1]);
            }
            // Add the orbit info
            orbits.put(split[1],split[0]);
        }

        // A lookup for how many orbits (direct or indirect) an object has
        HashMap<String,Integer> countO = new HashMap<>();
        countO.put("COM",0);
        objects.remove("COM");
        // The total number of direct and indirect orbits
        int total = 0;

        // Continue until every object has been found
        while (!objects.isEmpty()){
            // Loop through all remaining objects
            for (int i=objects.size()-1; i>=0; --i){
                // If a count of orbits has been identified for the orbited object
                if (countO.containsKey(orbits.get(objects.get(i)))){
                    // Increase that number by one for the orbiting object
                    int numOrbits = countO.get(orbits.get(objects.get(i)))+1;
                    // Add the value
                    total += numOrbits;
                    countO.put(objects.remove(i),numOrbits);
                }
            }
        }

        // Part 1 finds the total number of orbits, both direct and indirect
        if (PART == 1){
            System.out.println(total);
        }

        // Part 2 finds the orbital distance between YOU and SAN
        if (PART == 2){
            // Find the orbital path from COM to SAN
            ArrayList<String> san = new ArrayList<>();
            san.add("SAN");
            while (orbits.containsKey(san.get(0))){
                san.add(0,orbits.get(san.get(0)));
            }
            // Find the orbital path from COM to YOU
            ArrayList<String> you = new ArrayList<>();
            you.add("YOU");
            while (orbits.containsKey(you.get(0))){
                you.add(0,orbits.get(you.get(0)));
            }

            // Identify where the paths differ
            while (san.get(0).equals(you.get(0))){
                san.remove(0);
                you.remove(0);
            }

            // Print the answer
            System.out.println(san.size() + you.size() - 2);
        }
    }
}