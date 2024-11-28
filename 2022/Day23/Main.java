import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 23: Unstable Diffusion";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The number of rows so far in the input
        int numRows = 0;
        // The list of all elf positions
        ArrayList<int[]> elves = new ArrayList<>();
        // The numbers to add to the positions to change direction
        ArrayList<int[]> directions = new ArrayList<>();
        directions.add(new int[] {0,-1});
        directions.add(new int[] {0,1});
        directions.add(new int[] {-1,0});
        directions.add(new int[] {1,0});

        // Take in all the input
        while (sc.hasNext()){
            // The line of input
            String line = sc.nextLine();
            // Increase the number of rows
            ++numRows;
            // Add all elves to the list
            for (int i=0; i<line.length(); ++i){
                if (line.charAt(i) == '#'){
                    elves.add(new int[] {i,numRows,4});
                }
            }
        }

        // Whether an elf moved this round
        boolean moved = true;
        // The answer to the problem
        int part1 = 0;
        int part2;

        // Loop until no elves move
        for (part2 = 0; moved; ++part2){
            // Find the spread at 10 rounds
            if (part2 == 10){
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

                // Save the result
                part1 = (maxY-minY+1) * (maxX-minX+1) - elves.size();
            }

            // No elves have moved this round
            moved = false;

            // A list matching elves that holds the elves in the 8 squares
            // surrounding each elf
            ArrayList<ArrayList<Integer>> touching = new ArrayList<>();
            // Loop through all elves
            for (int i=0; i<elves.size(); ++i){
                // Add an empty list
                touching.add(new ArrayList<>());
                // Loop through all previous elves
                for (int j=0; j<i; ++j){
                    // If they're within one square, add them to each other's touching
                    if (Math.abs(elves.get(i)[0]-elves.get(j)[0]) <= 1 && Math.abs(elves.get(i)[1] - elves.get(j)[1]) <= 1){
                        touching.getLast().add(j);
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
                    for (int j=0; j<touching.get(i).size() && elf[2] < 4; ++j){
                        // The other elf's position and direction
                        int[] other = elves.get(touching.get(i).get(j));

                        // The difference between the two
                        int[] diff = new int[2];
                        diff[0] = other[0] - elf[0];
                        diff[1] = other[1] - elf[1];

                        if (diff[0] != 0 && diff[0] == directions.get(elf[2])[0] && Math.abs(diff[1]) <= 1
                            || diff[1] != 0 && diff[1] == directions.get(elf[2])[1] && Math.abs(diff[0]) <= 1){
                                ++elf[2];
                                j = -1;
                        }
                    }
                }
            }

            // Loop through every elf
            for (int i=0; i<elves.size()-1; ++i){
                // The current elf's position and direction
                int[] elf = elves.get(i);
                // If this elf didn't propose a direction, move on
                if (elf[2] == 4){
                    continue;
                }
                // Loop through every future elf
                for (int j=i+1; j<elves.size(); ++j){
                    // The other elf's position and direction
                    int[] other = elves.get(j);
                    // If the elves are going to move into each other
                    if (other[2] != 4 && elf[0]+directions.get(elf[2])[0] == other[0]+directions.get(other[2])[0]
                            && elf[1]+directions.get(elf[2])[1] == other[1]+directions.get(other[2])[1]){
                        // Neither of them move
                        elf[2] = 4;
                        other[2] = 4;
                        break;
                    }
                }
            }
            
            // Loop through every elf
            for (int[] elf : elves){
                // If the elf is moving
                if (elf[2] != 4){
                    // Move the elf
                    elf[0] += directions.get(elf[2])[0];
                    elf[1] += directions.get(elf[2])[1];
                    elf[2] = 4;
                    // An elf has moved
                    moved = true;
                }
            }
            
            // Change the priority of directions
            directions.add(directions.removeFirst());
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}