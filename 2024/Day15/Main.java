import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class Main {
    final private static String name = "Day 15: Warehouse Woes";
    public static void main(String[] args){
        // Take in the two parts of the input
        String[] input = Library.getStringArray(args,"\n\n");

        // The robot's coordinates
        int startX = 0;
        int startY = 0;

        // Get the map from the input
        char[][] map = Library.toCharMatrix(input[0]);
        for (int i=0; i<map.length; ++i){
            int index = Library.indexOf(map[i],'@');
            if (index != -1){
                startX = index;
                startY = i;
                map[i][index] = '.';
                break;
            }
        }

        // Widen the map
        char[][] wide = new char[map.length][map[0].length*2];
        for (int i=0; i<map.length; ++i){
            for (int j=0; j<map[0].length; ++j){
                if (map[i][j] == 'O'){
                    // Widen the boxes
                    wide[i][j*2] = '[';
                    wide[i][j*2+1] = ']';
                }else{
                    wide[i][j*2] = map[i][j];
                    wide[i][j*2+1] = map[i][j];
                }
            }
        }

        // Get the robot's instructions
        char[] instructions = input[1].replace("\n","").toCharArray();

        // The robot's coordinates in the original map
        int x = startX;
        int y = startY;

        // Loop through each instruction for the original map
        for (char i : instructions){
            switch(i){
                // Move up
                case '^' -> {
                    int end = y-1;
                    // Find all the boxes
                    while (map[end][x] == 'O'){
                        --end;
                    }
                    // Only move if there's empty space
                    if (map[end][x] == '.'){
                        // Move the closest box to the end
                        map[end][x] = 'O';
                        map[y-1][x] = '.';
                        // Move the robot
                        --y;
                    }
                }
                // Move down
                case 'v' -> {
                    int end = y+1;
                    // Find all the boxes
                    while (map[end][x] == 'O'){
                        ++end;
                    }
                    // Only move if there's empty space
                    if (map[end][x] == '.'){
                        // Move the closest box to the end
                        map[end][x] = 'O';
                        map[y+1][x] = '.';
                        // Move the robot
                        ++y;
                    }
                }
                // Move right
                case '>' -> {
                    int end = x+1;
                    // Find all the boxes
                    while (map[y][end] == 'O'){
                        ++end;
                    }
                    // Only move if there's empty space
                    if (map[y][end] == '.'){
                        // Move the closest box to the end
                        map[y][end] = 'O';
                        map[y][x+1] = '.';
                        // Move the robot
                        ++x;
                    }
                }
                // Move left
                case '<' -> {
                    int end = x-1;
                    // Find all the boxes
                    while (map[y][end] == 'O'){
                        --end;
                    }
                    // Only move if there's empty space
                    if (map[y][end] == '.'){
                        // Move the closest box to the end
                        map[y][end] = 'O';
                        map[y][x-1] = '.';
                        // Move the robot
                        --x;
                    }
                }
            }
        }

        // Reset the coordinates for the wide map
        x = startX * 2;
        y = startY;

        // Loop through each instruction for the wide map
        for (char i : instructions){
            // The list of all positions that are moving
            LinkedHashSet<ArrayState> moving = new LinkedHashSet<>();
            // Whether the move can happen
            boolean toMove = true;
            // Add the current position
            moving.add(new ArrayState(new int[] {x,y}));
            // Loop through each moving position
            for (int j=0; j<moving.size(); ++j){
                // Get a copy of the position
                int[] pos = new ArrayList<ArrayState>(moving).get(j).getArray();
                // Look in the given direction
                switch(i){
                    case '^' -> --pos[1];
                    case 'v' -> ++pos[1];
                    case '<' -> --pos[0];
                    case '>' -> ++pos[0];
                }
                // Get the new spot
                char c = wide[pos[1]][pos[0]];
                // If it's a wall, no move can happen
                if (wide[pos[1]][pos[0]] == '#'){
                    toMove = false;
                    break;
                }
                // If it's a box, add both parts to move
                if (c != '.'){
                    moving.add(new ArrayState(pos));
                    int[] other = pos.clone();
                    if (c == '['){
                        ++other[0];
                    }else{
                        --other[0];
                    }
                    moving.add(new ArrayState(other));
                }
            }

            // If the move is successful
            if (toMove){
                // Get the positions as a list
                ArrayList<ArrayState> move = new ArrayList<>(moving);
                // Loop through them backwards
                for (int j=move.size()-1; j>=0; --j){
                    // Get the position
                    int[] pos = move.get(j).getArray();
                    // Move it in the given direction
                    switch(i){
                        case '^' -> wide[pos[1]-1][pos[0]] = wide[pos[1]][pos[0]];
                        case 'v' -> wide[pos[1]+1][pos[0]] = wide[pos[1]][pos[0]];
                        case '<' -> wide[pos[1]][pos[0]-1] = wide[pos[1]][pos[0]];
                        case '>' -> wide[pos[1]][pos[0]+1] = wide[pos[1]][pos[0]];
                    }
                    // Empty the current position
                    wide[pos[1]][pos[0]] = '.';
                }
                // Move the robot
                switch(i){
                    case '^' -> --y;
                    case 'v' -> ++y;
                    case '<' -> --x;
                    case '>' -> ++x;
                }
            }
        }

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Add up the coordinates of each box
        for (int i=0; i<map.length; ++i){
            for (int j=0; j<map[i].length; ++j){
                if (map[i][j] == 'O'){
                    part1 += i * 100 + j;
                }
                if (wide[i][j] == '['){
                    part2 += i * 100 + j;
                }
            }

            for (int j=map[i].length; j<wide[i].length; ++j){
                if (wide[i][j] == '['){
                    part2 += i * 100 + j;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}