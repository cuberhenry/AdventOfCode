/*
Henry Anderson
Advent of Code 2016 Day 22 https://adventofcode.com/2016/day/22
Input: https://adventofcode.com/2016/day/22/input
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
        // Skip the headers
        sc.nextLine();
        sc.nextLine();

        // Part 1 finds the number of viable pairs of nodes
        if (PART == 1){
            // The list of all nodes
            ArrayList<int[]> nodes = new ArrayList<>();
            
            // Loop through every node
            while (sc.hasNext()){
                // Skip the first two entries
                sc.next();
                sc.next();

                // Used and Avail
                int[] node = new int[2];
                for (int i=0; i<2; ++i){
                    String number = sc.next();
                    node[i] = Integer.parseInt(number.substring(0,number.length()-1));
                }

                sc.next();

                nodes.add(node);
            }
        
            // The number of viable paris
            int total = 0;

            // Loop through every unordered pair of nodes
            for (int i=0; i<nodes.size(); ++i){
                for (int j=i+1; j<nodes.size(); ++j){
                    // Check both directions
                    if (nodes.get(i)[0] != 0 && nodes.get(i)[0] <= nodes.get(j)[1]){
                        ++total;
                    }
                    if (nodes.get(j)[0] != 0 && nodes.get(j)[0] <= nodes.get(i)[1]){
                        ++total;
                    }
                }
            }

            // Print the answer
            System.out.println(total);
        }

        if (PART == 2){
            // The list of the states of each node
            ArrayList<ArrayList<Character>> nodes = new ArrayList<>();
            // The coordinates of the empty node
            int emptyX = 0;
            int emptyY = 0;
            // The minimum wall block the goal node
            int minX = Integer.MAX_VALUE;

            // Loop through every node
            while (sc.hasNext()){
                // Organize based on coordinates
                int y = Integer.parseInt(sc.next().split("-")[2].substring(1));
                if (y == 0){
                    nodes.add(new ArrayList<Character>());
                }

                // Take in relevant information
                String size = sc.next();
                String used = sc.next();
                if (size.length() > 3){
                    // Node can't empty
                    nodes.get(nodes.size()-1).add('#');
                    minX = Math.min(minX,nodes.size()-1);
                }else if (used.equals("0T")){
                    // Node is empty
                    emptyX = nodes.size()-1;
                    emptyY = y;
                    nodes.get(nodes.size()-1).add('_');
                }else{
                    // Node can fit in empty
                    nodes.get(nodes.size()-1).add('.');
                }

                sc.next();
                sc.next();
            }

            // The number of steps to move the goal data one spot
            int steps = 0;
            // Get passed the wall
            while (emptyX >= minX){
                --emptyX;
                ++steps;
            }
            // Get to the edge
            while (emptyY > 0){
                --emptyY;
                ++steps;
            }
            // Get to the goal data
            while (emptyX < nodes.size()-1){
                ++emptyX;
                ++steps;
            }

            // The first move plus 5 steps for every additional node
            System.out.println(steps + (5 * (nodes.size()-2)));
        }
    }
}