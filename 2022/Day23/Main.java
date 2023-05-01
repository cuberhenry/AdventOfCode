/*
Henry Anderson
Advent of Code 2022 Day 23 https://adventofcode.com/2022/day/23
Input: https://adventofcode.com/2022/day/23/input
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
        // The number of rows so far in the input
        int numRows = 0;
        // The list of all elf positions
        ArrayList<int[]> elves = new ArrayList<>();
        // The numbers to add to the positions to change direction
        int[][] directions = {{0,-1},{0,1},{-1,0},{1,0}};

        // Take in all the input
        while (sc.hasNext()){
            // The line of input
            String line = sc.nextLine();
            // Increase the number of rows
            ++numRows;
            // Add all elves to the list
            for (int i=0; i<line.length(); ++i){
                if (line.charAt(i) == '#'){
                    elves.add(new int[] {i,numRows,-1});
                }
            }
        }

        // Whether an elf moved this round
        boolean moved = true;
        // The round number
        int round;
        // Loop until no elves move
        for (round = 0; moved; ++round){
            // No elves have moved this round
            moved = false;

            // A list matching elves that holds the elves in the 8 squares
            // surrounding each elf
            ArrayList<ArrayList<Integer>> touching = new ArrayList<>();
            // Loop through all elves
            for (int i=0; i<elves.size(); ++i){
                // Add an empty list
                touching.add(new ArrayList<>());
                // Set the direction to not move
                elves.get(i)[2] = -1;
                // Loop through all previous elves
                for (int j=0; j<i; ++j){
                    // If they're within one square, add them to each other's touching
                    if (Math.abs(elves.get(i)[0]-elves.get(j)[0]) <= 1 && Math.abs(elves.get(i)[1] - elves.get(j)[1]) <= 1){
                        touching.get(i).add(j);
                        touching.get(j).add(i);
                    }
                }
            }

            // Loop through every elf
            for (int i=0; i<elves.size(); ++i){
                // The current elf's position and direction
                int[] elf = elves.get(i);
                // If it's next to at least one elf
                if (!touching.get(i).isEmpty()){
                    // Set its direction to the first check
                    elf[2] = 0;
                    // Loop through all of the close elves
                    for (int j=0; j<touching.get(i).size(); ++j){
                        // The other elf's position and direction
                        int[] other = elves.get(touching.get(i).get(j));
                        // If the other elf is north of the current elf
                        if (elf[2] == (-round%4+4)%4 && elf[1] - other[1] == 1){
                            elf[2] = (-round%4+4)%4+1;
                            j=-1;
                            continue;
                        }
                        // If the other elf is south of the current elf
                        if (elf[2] == (-round%4+5)%4 && other[1] - elf[1] == 1){
                            elf[2] = (-round%4+5)%4+1;
                            j=-1;
                            continue;
                        }
                        // If the other elf is west of the current elf
                        if (elf[2] == (-round%4+6)%4 && elf[0] - other[0] == 1){
                            elf[2] = (-round%4+6)%4+1;
                            j=-1;
                            continue;
                        }
                        // If the other elf is east of the current elf
                        if (elf[2] == (-round%4+7)%4 && other[0] - elf[0] == 1){
                            elf[2] = (-round%4+7)%4+1;
                            j=-1;
                            continue;
                        }
                        // The elf can no longer move
                        if (elf[2] == 4){
                            break;
                        }
                    }
                }
            }

            // Loop through every elf
            for (int i=0; i<elves.size()-1; ++i){
                // The current elf's position and direction
                int[] elf = elves.get(i);
                // If this elf didn't propose a direction, move on
                if (elf[2] == -1 || elf[2] == 4){
                    continue;
                }
                // Loop through every future elf
                for (int j=i+1; j<elves.size(); ++j){
                    // The other elf's position and direction
                    int[] other = elves.get(j);
                    // If the elves are going to move into each other
                    if (other[2] >= 0 && other[2] <= 3 && elf[0]+directions[elf[2]][0] == other[0]+directions[other[2]][0]
                            && elf[1]+directions[elf[2]][1] == other[1]+directions[other[2]][1]){
                        // Neither of them move
                        elf[2] = -1;
                        other[2] = -1;
                        break;
                    }
                }
            }
            
            // Loop through every elf
            for (int[] elf : elves){
                // If the elf is moving
                if (elf[2] >= 0 && elf[2] <= 3){
                    // Move the elf
                    elf[0] += directions[elf[2]][0];
                    elf[1] += directions[elf[2]][1];
                    // An elf has moved
                    moved = true;
                }
            }
            
            // Change the priority of directions
            int[] help = directions[0];
            directions[0] = directions[1];
            directions[1] = directions[2];
            directions[2] = directions[3];
            directions[3] = help;

            // Part 1 only performs 10 rounds
            if (PART == 1){
                if (round == 10){
                    break;
                }
            }
        }

        // Part 1 finds the number of empty squares between elves
        if (PART == 1){
            // The initial minima and maxima
            int minX = elves.get(0)[0];
            int maxX = minX;
            int minY = elves.get(0)[1];
            int maxY = minY;
            
            // Find the minima and maxima
            for (int i=1; i<elves.size(); ++i){
                minX = Math.min(elves.get(i)[0],minX);
                maxX = Math.max(elves.get(i)[0],maxX);
                
                minY = Math.min(elves.get(i)[1],minY);
                maxY = Math.max(elves.get(i)[1],maxY);
            }

            // Print the result
            System.out.println((maxY-minY+1)*(maxX-minX+1)-elves.size());
        }

        // Part 2 finds the number of rounds until no elves move
        if (PART == 2){
            // Print the result
            System.out.println(round);
        }
    }
}