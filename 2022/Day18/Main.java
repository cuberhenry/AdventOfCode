/*
Henry Anderson
Advent of Code 2022 Day 18 https://adventofcode.com/2022/day/18
Input: https://adventofcode.com/2022/day/18/input
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
        // The input as parsed integers
        ArrayList<int[]> input = new ArrayList<>();
        // The checked empty locations for part 2
        ArrayList<String> checked = new ArrayList<>();
        // The total surface area
        int total = 0;
        // The maximum coordinate value
        int max = 0;

        // Take in all lines of input
        while (sc.hasNext()){
            // Parse the line of input
            String[] line = sc.nextLine().split(",");
            // Add one for sentinels
            int x = Integer.parseInt(line[0]) + 1;
            int y = Integer.parseInt(line[1]) + 1;
            int z = Integer.parseInt(line[2]) + 1;
            // Update max
            max = Math.max(Math.max(x,y),Math.max(z,max));
            input.add(new int[]{x,y,z});
        }

        ++max;

        // The grid of all positions including sentinels
        boolean[][][] grid = new boolean[max+1][max+1][max+1];

        // Put the coordinates into the grid
        for (int i=0; i<input.size(); ++i){
            grid[input.get(i)[0]][input.get(i)[1]][input.get(i)[2]] = true;
        }
        
        // Part 1 finds the surface area of the droplets

        // Part 2 finds the outer surface area of the droplets
        if (PART == 2){
            // Loop through every non-sentinel coordinate
            for (int a=1; a<max; ++a){
                for (int b=1; b<max; ++b){
                    for (int c=1; c<max; ++c){
                        // If it's an empty spot that hasn't been checked
                        if (!grid[a][b][c] && !checked.contains(a+" "+b+" "+c)){
                            // Whether the spot is connected to air
                            boolean touchingAir = false;
                            // Save the index so it can be filled in later if needed
                            int index = checked.size();
                            checked.add(a+" "+b+" "+c);
                            // Breadth first search
                            for (int l=index; l<checked.size(); ++l){
                                // Parse the coordinates
                                String[] help = checked.get(l).split(" ");
                                int i = Integer.parseInt(help[0]);
                                int j = Integer.parseInt(help[1]);
                                int k = Integer.parseInt(help[2]);

                                if (i > 0 && !grid[i-1][j][k] && !checked.contains(i-1+" "+j+" "+k)){
                                    checked.add(i-1+" "+j+" "+k);
                                }
                                if (i < max && !grid[i+1][j][k] && !checked.contains(i+1+" "+j+" "+k)){
                                    checked.add(i+1+" "+j+" "+k);
                                }
                                if (j > 0 && !grid[i][j-1][k] && !checked.contains(i+" "+(j-1)+" "+k)){
                                    checked.add(i+" "+(j-1)+" "+k);
                                }
                                if (j < max && !grid[i][j+1][k] && !checked.contains(i+" "+(j+1)+" "+k)){
                                    checked.add(i+" "+(j+1)+" "+k);
                                }
                                if (k > 0 && !grid[i][j][k-1] && !checked.contains(i+" "+j+" "+(k-1))){
                                    checked.add(i+" "+j+" "+(k-1));
                                }
                                if (k < max && !grid[i][j][k+1] && !checked.contains(i+" "+j+" "+(k+1))){
                                    checked.add(i+" "+j+" "+(k+1));
                                }
                                if (i == 0 || j == 0 || k == 0 || i == max || j == max || k == max){
                                    touchingAir = true;
                                }

                                // If it's touching the air, no need to continue
                                if (i == 0 || j == 0 || k == 0 || i == max || j == max || k == max){
                                    touchingAir = true;
                                }
                            }
                            
                            // If it's not touching air, fill it in
                            if (!touchingAir){
                                for (int l=index; l<checked.size(); ++l){
                                    String[] help = checked.get(l).split(" ");
                                    int i = Integer.parseInt(help[0]);
                                    int j = Integer.parseInt(help[1]);
                                    int k = Integer.parseInt(help[2]);
                                    grid[i][j][k] = true;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        // Check every other square
        for (int i=0; i<max+1; ++i){
            for (int j=0; j<max+1; ++j){
                for (int k=(i+j)%2; k<max+1; k+=2){
                    // Check every direction, if there's a face between them add one
                    if (i > 0 && grid[i][j][k] ^ grid[i-1][j][k]){
                        ++total;
                    }
                    if (i < max && grid[i][j][k] ^ grid[i+1][j][k]){
                        ++total;
                    }
                    if (j > 0 && grid[i][j][k] ^ grid[i][j-1][k]){
                        ++total;
                    }
                    if (j < max && grid[i][j][k] ^ grid[i][j+1][k]){
                        ++total;
                    }
                    if (k > 0 && grid[i][j][k] ^ grid[i][j][k-1]){
                        ++total;
                    }
                    if (k < max && grid[i][j][k] ^ grid[i][j][k+1]){
                        ++total;
                    }
                }
            }
        }

        // Print the result
        System.out.println(total);
    }
}