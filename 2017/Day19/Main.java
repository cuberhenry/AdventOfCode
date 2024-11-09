import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 19: A Series of Tubes";
    public static void main(String[] args){
        // Get the map
        char[][] map = Library.getCharMatrix(args);

        // The starting position
        int y = 0;
        int x = Library.indexOf(map[0],'|');
        // The starting direction (down)
        int dir = 2;
        // The answers to Parts 1 and 2 respectively
        String part1 = "";
        int part2 = 0;

        // Continue travelling until the end of the path is reached
        while (y >= 0 && x >= 0 && y < map.length && x < map[0].length && map[y][x] != ' '){
            // If the current position is a letter, add it to answer
            if (Character.isLetter(map[y][x])){
                part1 += map[y][x];
            }

            // If the current position is a crossroads
            if (map[y][x] == '+'){
                // Determine the new direction based on adjacent characters and the current direction
                if (y > 0 && map[y-1][x] != ' ' && dir != 2){
                    // Start going up
                    dir = 0;
                }else if (x < map[y].length-1 && map[y][x+1] != ' ' && dir != 3){
                    // Start going right
                    dir = 1;
                }else if (y < map.length-1 && map[y+1][x] != ' ' && dir != 0){
                    // Start going down
                    dir = 2;
                }else{
                    // Start going left
                    dir = 3;
                }
            }

            // Move in the current direction
            switch (dir){
                case 0 -> {--y;}
                case 1 -> {++x;}
                case 2 -> {++y;}
                case 3 -> {--x;}
            }
            // Increment the number of steps taken
            ++part2;
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}