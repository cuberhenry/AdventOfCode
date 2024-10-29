/*
Henry Anderson
Advent of Code 2020 Day 20 https://adventofcode.com/2020/day/20
Input: https://adventofcode.com/2020/day/20/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // A tile of water
    private static class Tile {
        // The actual water of the tile
        private ArrayList<String> tile = new ArrayList<>();
        // The tile's ID
        private int tileID;
        // The edge encodings [top, right, bottom, left]
        private int[] edges = new int[4];
        // The width of the tile
        private int width;

        // Scanner constructor
        public Tile(Scanner sc){
            // Take in the line with the tile ID
            String line = sc.nextLine();
            // Save the tile ID
            tileID = Integer.parseInt(line.substring(5,line.indexOf(':')));
            line = sc.nextLine();
            // Continue until the tile ends
            while (!line.equals("")){
                // Add the next line
                tile.add(line);
                if (sc.hasNext()){
                    line = sc.nextLine();
                }else{
                    break;
                }
            }
            // Create a number representing the edges clockwise
            for (int i=0; i<tile.size(); ++i){
                // Multiply by two
                edges[0] <<= 1;
                edges[1] <<= 1;
                edges[2] <<= 1;
                edges[3] <<= 1;
                // Save the next bit
                // Top left to right
                edges[0] += tile.get(0).charAt(i) == '#' ? 1 : 0;
                // Right top to bottom
                edges[1] += tile.get(i).charAt(tile.size()-1) == '#' ? 1 : 0;
                // Bottom right to left
                edges[2] += tile.get(tile.size()-1).charAt(tile.size()-1-i) == '#' ? 1 : 0;
                // Left bottom to top
                edges[3] += tile.get(tile.size()-1-i).charAt(0) == '#' ? 1 : 0;
            }

            // Save the width of the tile
            width = tile.size();

            // Remove the border
            tile.removeFirst();
            tile.removeLast();
            for (int i=0; i<tile.size(); ++i){
                tile.set(i,tile.get(i).substring(1,tile.get(i).length()-1));
            }
        }

        // Getter for tileID
        public int getID(){
            return tileID;
        }

        // Getter for tile
        public ArrayList<String> getTile(){
            return tile;
        }

        // Finds the tile that goes in the direction of the current tile
        public Tile get(int dir, ArrayList<Tile> tiles){
            // Look for each tile
            for (Tile tile : tiles){
                // If it has an edge that matches this
                if (tile.hasEdge((2+dir)%4,edges[dir])){
                    // Return it
                    return tile;
                }
            }
            // No matching edges, it's an edge or corner
            return null;
        }

        // If it has an edge, orients the piece to line up correctly
        public boolean hasEdge(int dir, int match){
            // Get the reverse of the edge
            int opposite = invert(match);
            // Whether it has an edge that matches either forwards or backwards
            boolean hasOpposite = false;
            boolean hasMatch = false;
            // Look at each edge
            for (int i=0; i<4; ++i){
                // Save if it matches either one
                if (edges[i] == opposite){
                    hasOpposite = true;
                    break;
                }
                if (edges[i] == match){
                    hasMatch = true;
                    break;
                }
            }

            // If the edges both match clockwise, this tile needs to be flipped
            if (hasMatch){
                flip();
            }

            // If it matches
            if (hasMatch || hasOpposite){
                // Rotate until the pieces are lined up
                while (edges[dir] != opposite){
                    rotate();
                }
                // They do match
                return true;
            }

            // No edge here
            return false;
        }

        // Flip the tile top to bottom
        public void flip(){
            // Reverse the rows
            Collections.reverse(tile);
            // Invert the left and right edges
            edges[1] = invert(edges[1]);
            edges[3] = invert(edges[3]);
            // Invert and swap the top and bottom edges
            int helper = invert(edges[0]);
            edges[0] = invert(edges[2]);
            edges[2] = helper;
        }

        // Rotate the tile clockwise
        public void rotate(){
            // Create the new tile
            ArrayList<String> rotated = new ArrayList<>();
            // Loop left to right
            for (int i=0; i<tile.size(); ++i){
                String row = "";
                // Loop bottom to top
                for (int j=tile.size()-1; j>=0; --j){
                    row += tile.get(j).charAt(i);
                }
                rotated.add(row);
            }
            tile = rotated;

            // Rotate the edges clockwise
            int helper = edges[0];
            edges[0] = edges[3];
            edges[3] = edges[2];
            edges[2] = edges[1];
            edges[1] = helper;
        }

        // Invert the number using the number of digits of each edge
        public int invert(int num){
            int inverted = 0;
            // Loop through each digit
            for (int i=0; i<width; ++i){
                // Move digits over
                inverted <<= 1;
                // Add the new digit
                if (num / (int)Math.pow(2,i) % 2 == 1){
                    ++inverted;
                }
            }
            return inverted;
        }
        
        // Merge this tile to another tile to the right of it
        public void mergeHorizontal(Tile other){
            ArrayList<String> otherTile = other.getTile();
            // Add each row
            for (int i=0; i<otherTile.size(); ++i){
                tile.set(i,tile.get(i)+otherTile.get(i));
            }
            // Save the new width
            width = tile.getFirst().length();
        }

        // Merge this tile to another tile below it
        public void mergeVertical(Tile other){
            // Add all the rows at the bottom
            tile.addAll(other.getTile());
        }

        // Count the roughness of the water if there are monsters
        public int count(){
            // The number of #
            int count = 0;
            // The number of monsters
            int monsters = 0;

            // Loop through each square
            for (int i=0; i<width; ++i){
                for (int j=0; j<width; ++j){
                    if (tile.get(i).charAt(j) == '#'){
                        ++count;
                        // If there's room for a monster
                        if (i > 0 && i < tile.size()-1 && j < tile.size()-19){
                            // The 14 other squares that have to be #
                            int[][] monster = {{1,1},{4,1},{5,0},{6,0},
                                               {7,1},{10,1},{11,0},{12,0},
                                               {13,1},{16,1},{17,0},
                                               {18,0},{19,0},{18,-1}};
                            
                            boolean isMonster = true;
                            // Loop through each square
                            for (int[] offset : monster){
                                // If it's not a #, it's not a monster
                                if (tile.get(i+offset[1]).charAt(j+offset[0]) == '.'){
                                    isMonster = false;
                                    break;
                                }
                            }
                            // If it's a monster, add it
                            if (isMonster){
                                ++monsters;
                            }
                        }
                    }
                }
            }

            // If there are monsters
            if (monsters > 0){
                // Return the #s that aren't a part of a monster
                return count - 15 * monsters;
            }
            // Return 0
            return 0;
        }
    }
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
        // The list of all unplaced tiles
        ArrayList<Tile> tiles = new ArrayList<>();
        while (sc.hasNextLine()){
            // Take in all new tiles
            tiles.add(new Tile(sc));
        }

        // Start at the first tile
        ArrayList<Tile> row = new ArrayList<>();
        row.add(tiles.removeFirst());

        // Search to the left
        Tile left = row.getFirst().get(3,tiles);
        // As long as there's a tile to the left
        while (left != null){
            // Remove it from tiles
            tiles.remove(left);
            // Add it to the left
            row.add(0,left);
            left = left.get(3,tiles);
        }

        // Search to the right
        Tile right = row.getLast().get(1,tiles);
        // As long as there's a tile to the right
        while (right != null){
            // Remove it from tiles
            tiles.remove(right);
            // Add it to the right
            row.add(right);
            right = right.get(1,tiles);
        }

        // The two dimensional grid of tiles
        ArrayList<ArrayList<Tile>> grid = new ArrayList<>();
        grid.add(row);
        
        // Search above
        Tile top = row.getFirst().get(0,tiles);
        // As long as there's a tile above
        while (top != null){
            // Remove it from tiles
            tiles.remove(top);
            // Create a new row
            ArrayList<Tile> newRow = new ArrayList<>();
            newRow.add(top);
            // Add the tile above each tile in the current top row
            for (int i=1; i<row.size(); ++i){
                newRow.add(row.get(i).get(0,tiles));
                tiles.remove(newRow.get(i));
            }
            row = newRow;
            // Add the row above
            grid.add(0,row);
            top = row.getFirst().get(0,tiles);
        }

        row = grid.getLast();
        // Search below
        Tile bottom = row.getFirst().get(2,tiles);
        // As long as there's a tile below
        while (bottom != null){
            // Remove it from tiles
            tiles.remove(bottom);
            // Create a new row
            ArrayList<Tile> newRow = new ArrayList<>();
            newRow.add(bottom);
            // Add the tile below each tile in the current bottom row
            for (int i=1; i<row.size(); ++i){
                newRow.add(row.get(i).get(2,tiles));
                tiles.remove(newRow.get(i));
            }
            row = newRow;
            // Add the row below
            grid.add(row);
            bottom = row.getFirst().get(2,tiles);
        }

        // Part 1 finds the product of the corners' tile IDs
        if (PART == 1){
            // Find the product
            long product = grid.getFirst().getFirst().getID();
            product *= grid.getLast().getFirst().getID();
            product *= grid.getFirst().getLast().getID();
            product *= grid.getLast().getLast().getID();

            // Print the answer
            System.out.println(product);
        }

        // Part 2 finds the roughness of the water
        if (PART == 2){
            // Merge all of the tiles into one
            Tile image = null;
            for (ArrayList<Tile> line : grid){
                // Merge each row
                Tile width = line.getFirst();
                for (int i=1; i<line.size(); ++i){
                    width.mergeHorizontal(line.get(i));
                }
                // Merge the rows together
                if (image == null){
                    image = width;
                }else{
                    image.mergeVertical(width);
                }
            }

            // Loop through each orientation
            for (int i=0; i<8; ++i){
                // Flip every fourth, rotate the rest
                if (i % 4 == 3){
                    image.flip();
                }else{
                    image.rotate();
                }
                // Count sea monsters
                int count = image.count();
                // More than 0 means it's the answer
                if (count > 0){
                    // Print the answer
                    System.out.println(count);
                    break;
                }
            }
        }
    }
}