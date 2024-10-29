/*
Henry Anderson
Advent of Code 2018 Day 13 https://adventofcode.com/2018/day/13
Input: https://adventofcode.com/2018/day/13/input
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
        // The tracks
        ArrayList<String> grid = new ArrayList<>();
        // The carts
        ArrayList<int[]> carts = new ArrayList<>();

        // Take in the map of inputs
        while (sc.hasNext()){
            // Take in the next line
            String line = sc.nextLine();

            // Loop through every character
            for (int i=0; i<line.length(); ++i){
                // When a cart is found, add it to carts
                // Replace it with the track that's under it
                if (line.charAt(i) == '<'){
                    carts.add(new int[] {i,grid.size(),3,0});
                    line = line.substring(0,i) + '-' + line.substring(i+1);
                } else if (line.charAt(i) == '>'){
                    carts.add(new int[] {i,grid.size(),1,0});
                    line = line.substring(0,i) + '-' + line.substring(i+1);
                } else if (line.charAt(i) == 'v'){
                    carts.add(new int[] {i,grid.size(),2,0});
                    line = line.substring(0,i) + '|' + line.substring(i+1);
                } else if (line.charAt(i) == '^'){
                    carts.add(new int[] {i,grid.size(),0,0});
                    line = line.substring(0,i) + '|' + line.substring(i+1);
                }
            }

            // Add the line to the grid
            grid.add(line);
        }

        // Continue until there's only one cart
        while (carts.size() > 1){
            // Move every cart
            for (int i=0; i<carts.size(); ++i){
                int[] cart = carts.get(i);
                // Move in the indicated direction
                switch(cart[2]){
                    case 0 -> {--cart[1];}
                    case 1 -> {++cart[0];}
                    case 2 -> {++cart[1];}
                    case 3 -> {--cart[0];}
                }

                // Check for a crash
                int crashed = -1;
                // Loop through every cart
                for (int j=0; j<carts.size(); ++j){
                    int[] other = carts.get(j);
                    // If the carts have the same position and they're not the same
                    if (cart[0] == other[0] && cart[1] == other[1] && i != j){
                        // Part 1 finds the location of the first crash
                        if (PART == 1){
                            System.out.println(cart[0] + "," + cart[1]);
                            return;
                        }

                        // Indicate which cart has been crashed into
                        crashed = j;
                        break;
                    }
                }

                // If there was a crash
                if (crashed != -1){
                    // Remove both carts, adjusting the current iterator
                    carts.remove(crashed);
                    if (crashed < i){
                        --i;
                    }
                    carts.remove(i);
                    --i;
                    // Skip to the next cart
                    continue;
                }

                // Determine next direction
                switch(grid.get(cart[1]).charAt(cart[0])){
                    // Determine next direction based on intersection decider
                    case '+' -> {
                        if (cart[3] == 0){
                            cart[2] = (cart[2]+3) % 4;
                        }else if (cart[3] == 2){
                            cart[2] = (cart[2]+1) % 4;
                        }
                        cart[3] = (cart[3]+1) % 3;
                    }
                    // Rotate
                    case '/' -> {cart[2] ^= 1;}
                    case '\\' -> {cart[2] = 3 - cart[2];}
                }
            }

            // Reorder the carts from top left to bottom right
            Collections.sort(carts,(a,b) -> {
                if (a[1] < b[1]){
                    return -1;
                }
                if (b[1] < a[1]){
                    return 1;
                }
                return a[0] < b[0] ? -1 : 1;
            });
        }

        // Part 2 finds the location of the last cart when all others have crashed
        System.out.println(carts.get(0)[0] + "," + carts.get(0)[1]);
    }
}