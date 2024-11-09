import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 21: Fractal Art";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        //The rules as a hashmap, including all iterations of a square
        HashMap<String,String> rules = new HashMap<>();
        // Take in every rule
        while (sc.hasNext()){
            // Get the source and result
            String[] rule = sc.nextLine().split(" => ");
            rules.put(rule[0],rule[1]);
            
            // Make the source easily rearrangable
            char[] source = rule[0].toCharArray();
            // If it's a 2x2
            if (rule[0].length() == 5){
                // Rotate it three times
                for (int i=0; i<3; ++i){
                    char helper = source[0];
                    source[0] = source[3];
                    source[3] = source[4];
                    source[4] = source[1];
                    source[1] = helper;
                    // Add it
                    rules.put(String.copyValueOf(source),rule[1]);
                }
            }else{
                // It's a 3x3: rotate 3 times, flip, then rotate 3 times again
                for (int i=0; i<7; ++i){
                    if (i == 3){
                        // Flip left to right
                        char helper = source[0];
                        source[0] = source[2];
                        source[2] = helper;
                        helper = source[4];
                        source[4] = source[6];
                        source[6] = helper;
                        helper = source[8];
                        source[8] = source[10];
                        source[10] = helper;
                    }else{
                        // Rotate clockwise
                        char helper = source[0];
                        source[0] = source[8];
                        source[8] = source[10];
                        source[10] = source[2];
                        source[2] = helper;
                        helper = source[1];
                        source[1] = source[4];
                        source[4] = source[9];
                        source[9] = source[6];
                        source[6] = helper;
                    }
                    // Add it
                    rules.put(String.copyValueOf(source),rule[1]);
                }
            }
        }
        
        // Initialize the grid
        ArrayList<String> grid = new ArrayList<>();
        grid.add(".#.");
        grid.add("..#");
        grid.add("###");

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop through every iteration
        for (int i=0; i<18; ++i){
            // Find the number of pixels after 5 iterations
            if (i == 5){
                for (String row : grid){
                    for (char c : row.toCharArray()){
                        if (c == '#'){
                            ++part1;
                        }
                    }
                }
            }

            // Get the size of each split square based on the size of the grid
            int mod = grid.size() % 2 == 0 ? 2 : 3;
            ArrayList<String> newGrid = new ArrayList<>();
            // Loop through each row of new squares
            for (int j=0; j<grid.size()/mod; ++j){
                // Get the first mod length from the first row
                String square = grid.get(j*mod).substring(0,mod);
                // Add the first mod length from the next mod-1 rows
                for (int k=1; k<mod; ++k){
                    square += "/" + grid.get(j*mod+k).substring(0,mod);
                }
                // Add the first mod+1 length from the resulting square
                String[] newSquare = rules.get(square).split("/");
                for (int k=0; k<newSquare.length; ++k){
                    newGrid.add(newSquare[k]);
                }
                // Loop through each column after the first
                for (int k=1; k<grid.size()/mod; ++k){
                    // Start by looking at the next mod from the first row
                    square = grid.get(j*mod).substring(k*mod,(k+1)*mod);
                    for (int l=1; l<mod; ++l){
                        square += "/" + grid.get(j*mod+l).substring(k*mod,(k+1)*mod);
                    }
                    newSquare = rules.get(square).split("/");
                    // Add the next square to the previous
                    for (int l=0; l<newSquare.length; ++l){
                        newGrid.set(j*(mod+1)+l,newGrid.get(j*(mod+1)+l) + newSquare[l]);
                    }
                }
            }
            grid = newGrid;
        }

        // Count the number of on pixels
        for (String row : grid){
            for (int i=0; i<row.length(); ++i){
                if (row.charAt(i) == '#'){
                    ++part2;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}