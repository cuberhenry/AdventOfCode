import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 10: Cathode-Ray Tube";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // Total result
        int part1 = 0;
        // Current position of the sprite
        int pos = 1;
        // Current clock cycle
        int clock = 0;
        // The CRT screen
        boolean[][] screen = new boolean[6][40];

        // Loop through all lines of input
        while (sc.hasNextLine()){
            // Take in the line
            String line = sc.nextLine();
            // The number of times to perform a clock cycle
            int num = line.charAt(0) == 'n' ? 1 : 2;

            for (int i=0; i<num; ++i){
                // Find the values
                if (clock%40 != 39){
                    screen[clock/40][clock%40] = Math.abs(pos+1-(clock+1)%40) <= 1;
                }

                // Increase the clock cycle
                clock++;
                
                // Get the specified clock cycle values
                if (clock == 20 || clock == 60 || clock == 100 || clock == 140
                                || clock == 180 || clock == 220){
                    part1 += pos*clock;
                }
            }

            // Add the change to the position
            if (num == 2){
                pos += Integer.parseInt(line.substring(5));
            }
        }

        // Read the screen
        String part2 = Library.read(screen);
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}