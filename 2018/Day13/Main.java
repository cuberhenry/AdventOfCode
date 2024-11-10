import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    final private static String name = "Day 13: Mine Cart Madness";
    public static void main(String args[]) {
        // The tracks
        char[][] grid = Library.getCharMatrix(args);
        // The carts
        ArrayList<int[]> carts = new ArrayList<>();

        // Take in the map of inputs
        for (int i=0; i<grid.length; ++i){
            // Loop through every character
            for (int j=0; j<grid[0].length; ++j){
                // When a cart is found, add it to carts
                // Replace it with the track that's under it
                if (grid[i][j] == '<'){
                    carts.add(new int[] {j,i,3,0});
                    grid[i][j] = '-';
                } else if (grid[i][j] == '>'){
                    carts.add(new int[] {j,i,1,0});
                    grid[i][j] = '-';
                } else if (grid[i][j] == 'v'){
                    carts.add(new int[] {j,i,2,0});
                    grid[i][j] = '|';
                } else if (grid[i][j] == '^'){
                    carts.add(new int[] {j,i,0,0});
                    grid[i][j] = '|';
                }
            }
        }

        // The first collision
        String part1 = "";

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
                        if (part1.isBlank()){
                            part1 = cart[0] + "," + cart[1];
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
                switch(grid[cart[1]][cart[0]]){
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

        // The last cart after the collision
        String part2 = carts.getFirst()[0] + "," + carts.getFirst()[1];
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}