import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    final private static String name = "Day 22: Monkey Map";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The map
        ArrayList<char[]> grid = new ArrayList<>();
        // The total dimensions of the 2D map
        int height = 0;
        int width = 0;

        // Take in the first line
        String line = sc.nextLine();
        // The size of each square on the map
        int size = 0;

        // Continue until the whole map is retrieved
        while (!line.equals("")){
            // Check if we need to extend the map to the right
            if (line.length() > width){
                width = line.length();
                // Extend each previous line
                for (int i=0; i<grid.size(); ++i){
                    char[] newRow = new char[width];
                    for (int j=0; j<grid.get(i).length; ++j){
                        newRow[j] = grid.get(i)[j];
                    }
                    for (int j=grid.get(i).length; j<width; ++j){
                        newRow[j] = ' ';
                    }
                    grid.set(i,newRow);
                }
            }
            // Add extra spaces to match the width
            while (line.length() < width){
                line += " ";
            }
            grid.add(line.toCharArray());
            line = sc.nextLine();

            // Count the total number of spots on the map
            for (int i=0; i<grid.getLast().length; ++i){
                if (grid.getLast()[i] != ' '){
                    ++size;
                }
            }
        }

        // Count the side length of the cube
        size = (int)Math.sqrt(size / 6);
        height = grid.size();

        // The position and facing for both parts
        int y1 = 0;
        int x1 = Library.indexOf(grid.getFirst(),'.');
        int dir1 = 0;
        int y2 = 0;
        int x2 = x1;
        int dir2 = 0;
        
        // Take in the directions
        String directions = sc.nextLine();
        
        // Loop until the directions are followed
        for (int pointer = 0; pointer < directions.length(); ++pointer){
            // Check if the current instruction is a distance
            if (Character.isDigit(directions.charAt(pointer))){
                // Get the number
                String number = directions.substring(pointer,pointer+1);
                while (pointer+1 < directions.length() && Character.isDigit(directions.charAt(pointer+1))){
                    number += directions.charAt(++pointer);
                }
                int num = Integer.parseInt(number);
                // Repeat that many times
                for (int i=0; i<num; ++i){
                    // Part 1 moves around the flat paper
                    int newX = x1;
                    int newY = y1;
                    int newDir = dir1;
                    // Move in that direction once
                    switch (dir1){
                        case 0 -> {newX = (newX + 1) % width;}
                        case 1 -> {newY = (newY + 1) % height;}
                        case 2 -> {newX = (newX - 1 + width) % width;}
                        case 3 -> {newY = (newY - 1 + height) % height;}
                    }

                    // If on an empty space, keep walking until you get to a non-empty space
                    while (grid.get(newY)[newX] == ' '){
                        switch (dir1){
                            case 0 -> {newX = (newX + size) % width;}
                            case 1 -> {newY = (newY + size) % height;}
                            case 2 -> {newX = (newX - size + width) % width;}
                            case 3 -> {newY = (newY - size + height) % height;}
                        }
                    }

                    // If it's not impassable, move to that new spot
                    if (grid.get(newY)[newX] != '#'){
                        x1 = newX;
                        y1 = newY;
                        dir1 = newDir;
                    }

                    // Part 2 moves around the cube
                    newX = x2;
                    newY = y2;
                    newDir = dir2;
                    // Move in that direction once
                    switch (dir2){
                        case 0 -> ++newX;
                        case 1 -> ++newY;
                        case 2 -> --newX;
                        case 3 -> --newY;
                    }

                    // Check if it exists on the 2D map
                    if (!isValidLocation(newX,newY,grid)){
                        // If not, check every single possible configuration to identify which location and direction you end up
                        int[] newLoc = {x2,y2,dir2};
                        if (checkSquare(newLoc,size,grid,"up right")){
                            rotateClockwise(newLoc,size,3);
                        }else if (checkSquare(newLoc,size,grid,"down right")){
                            rotateClockwise(newLoc,size,1);
                        }else if (checkSquare(newLoc,size,grid,"up up right")){
                            rotateClockwise(newLoc,size,2);
                        }else if (checkSquare(newLoc,size,grid,"down down right")){
                            rotateClockwise(newLoc,size,2);
                        }else if (checkSquare(newLoc,size,grid,"left left left")){
                        }else if (checkSquare(newLoc,size,grid,"left up up")){
                            rotateClockwise(newLoc,size,2);
                        }else if (checkSquare(newLoc,size,grid,"left down down")){
                            rotateClockwise(newLoc,size,2);
                        }else if (checkSquare(newLoc,size,grid,"up up up right")){
                            rotateClockwise(newLoc,size,1);
                        }else if (checkSquare(newLoc,size,grid,"down down down right")){
                            rotateClockwise(newLoc,size,3);
                        }else if (checkSquare(newLoc,size,grid,"left up left up")){
                            rotateClockwise(newLoc,size,1);
                        }else if (checkSquare(newLoc,size,grid,"left down left down")){
                            rotateClockwise(newLoc,size,3);
                        }else if (checkSquare(newLoc,size,grid,"up left left left")){
                            rotateClockwise(newLoc,size,3);
                        }else if (checkSquare(newLoc,size,grid,"down left left left")){
                            rotateClockwise(newLoc,size,1);
                        }else if (checkSquare(newLoc,size,grid,"up left up up")){
                            rotateClockwise(newLoc,size,1);
                        }else if (checkSquare(newLoc,size,grid,"down left down down")){
                            rotateClockwise(newLoc,size,3);
                        }else if (checkSquare(newLoc,size,grid,"left left up left")){
                            rotateClockwise(newLoc,size,1);
                        }else if (checkSquare(newLoc,size,grid,"left left down left")){
                            rotateClockwise(newLoc,size,3);
                        }else if (checkSquare(newLoc,size,grid,"up left up left up")){
                            rotateClockwise(newLoc,size,2);
                        }else if (checkSquare(newLoc,size,grid,"down left down left down")){
                            rotateClockwise(newLoc,size,2);
                        }else if (checkSquare(newLoc,size,grid,"up up left up up")){
                        }else if (checkSquare(newLoc,size,grid,"down down left down down")){
                        }else if (checkSquare(newLoc,size,grid,"left up left left up")){
                        }else if (checkSquare(newLoc,size,grid,"left down left left down")){
                        }else if (checkSquare(newLoc,size,grid,"up left left up left")){
                        }else if (checkSquare(newLoc,size,grid,"down left left down left")){
                        }else{
                            throw new RuntimeException("Didn't match a cube configuration at location " + Arrays.toString(newLoc));
                        }
                        newX = newLoc[0];
                        newY = newLoc[1];
                        newDir = newLoc[2];
                    }

                    // If it's not impassable, move to that new spot
                    if (grid.get(newY)[newX] != '#'){
                        x2 = newX;
                        y2 = newY;
                        dir2 = newDir;
                    }
                }
            }else if (directions.charAt(pointer) == 'R'){
                // Turn right
                dir1 = (dir1+1) % 4;
                dir2 = (dir2+1) % 4;
            }else{
                // Turn left
                dir1 = (dir1+3) % 4;
                dir2 = (dir2+3) % 4;
            }
        }

        // Calculate the passwords
        int part1 = (y1+1)*1000 + (x1+1)*4 + dir1;
        int part2 = (y2+1)*1000 + (x2+1)*4 + dir2;
        
        // Print the answer
        Library.print(part1,part2,name);
    }

    // Returns whether the given position is a valid location on the 2D map, whether passable or impassable
    private static boolean isValidLocation(int x, int y, ArrayList<char[]> grid){
        return x >= 0 && y >= 0 && x < grid.getFirst().length && y < grid.size() && grid.get(y)[x] != ' ';
    }

    // Checks if each square passed in "directions" exists on the 2D map
    // If it does, it sets the location to that square **facing the same direction**
    private static boolean checkSquare(int[] newLoc, int size, ArrayList<char[]> grid, String directions){
        int x = newLoc[0];
        int y = newLoc[1];
        int dir = newLoc[2];
        // Loop through each direction
        for (String d : directions.split(" ")){
            // Combine the two directions and move in that direction the size of a square
            switch ((switch (d){
                case "right" -> 0;
                case "down" -> 1;
                case "left" -> 2;
                case "up" -> 3;
                default -> throw new RuntimeException("Unexpected direction: " + d);
            } + dir) % 4){
                case 0 -> x += size;
                case 1 -> y += size;
                case 2 -> x -= size;
                case 3 -> y -= size;
            }

            // If it isn't a valid location, this is not the correct unfolding of the cube
            if (!isValidLocation(x,y,grid)){
                return false;
            }
        }
        // Move to the new location
        newLoc[0] = x;
        newLoc[1] = y;
        // Flip to the other side of the cube as if just moving into it
        if (dir % 2 == 0){
            newLoc[0] = x/size*size + (size - 1 - (x%size));
            newLoc[1] = y;
        }else{
            newLoc[0] = x;
            newLoc[1] = y/size*size + (size - 1 - (y%size));
        }
        return true;
    }

    // Rotates the location within one square of the cube clockwise count times
    // Used for if travelling to a different face along a cut
    private static void rotateClockwise(int[] location, int size, int count){
        for (; count>0; --count){
            // Rotate
            int x = location[0];
            location[0] = x/size*size + (size - 1 - (location[1]%size));
            location[1] = location[1]/size*size + x%size;
            // Turn
            location[2] = (location[2]+1) % 4;
        }
    }
}