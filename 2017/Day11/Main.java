import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 11: Hex Ed";
    public static void main(String args[]) {
        // Take in the list of moves
        String[] moves = Library.getStringArray(args,",");
        // The synthetic x and y position of the child process
        int x = 0;
        int y = 0;
        // The maximum distance the child process gets from the start
        int part2 = 0;

        // Loop through every move
        for (String move : moves){
            // Change x and y according to the move
            switch (move){
                case "n" -> {
                    ++x;
                    ++y;
                }
                case "s" -> {
                    --x;
                    --y;
                }
                case "ne" -> {
                    ++x;
                }
                case "sw" -> {
                    --x;
                }
                case "se" -> {
                    --y;
                }
                case "nw" -> {
                    ++y;
                }
            }

            // Calculate the distance based on whether x and y have matching signs
            part2 = Math.max(part2,x > 0 ^ y > 0 ? Math.abs(x) + Math.abs(y) : Math.max(Math.abs(x),Math.abs(y)));
        }

        // The child process' final distance from the start
        int part1 = x > 0 ^ y > 0 ? Math.abs(x) + Math.abs(y) : Math.max(Math.abs(x),Math.abs(y));

        // Print the answer
        Library.print(part1,part2,name);
    }
}