import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 6: Probably a Fire Hazard";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The grid of lights
        boolean[][] digital = new boolean[1000][1000];
        int[][] analog = new int[1000][1000];
        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // While there's input
        while (sc.hasNext()){
            // Parse the input
            String instruction = sc.next();
            if (instruction.equals("turn")){
                instruction = sc.next();
            }
            int[] start = Library.intSplit(sc.next(),",");
            sc.next();
            int[] end = Library.intSplit(sc.next(),",");

            // Loop through every light within the limits
            for (int i=start[0]; i<=end[0]; ++i){
                for (int j=start[1]; j<=end[1]; ++j){
                    switch(instruction){
                        case "on" -> {
                            // Turn on
                            digital[i][j] = true;
                            // Increase brightness
                            ++analog[i][j];
                        }
                        case "off" -> {
                            // Turn off
                            digital[i][j] = false;
                            // Decrease brightness
                            if (analog[i][j] > 0){
                                --analog[i][j];
                            }
                        }
                        case "toggle" -> {
                            // Toggle
                            digital[i][j] = !digital[i][j];
                            // Increase brightness twice
                            analog[i][j] += 2;
                        }
                    }
                }
            }
        }

        // Part 1 counts the number of lights that are on
        // Part 2 finds the total brightness of all of the lights
        for (int i=0; i<1000; ++i){
            for (int j=0; j<1000; ++j){
                part1 += digital[i][j] ? 1 : 0;
                part2 += analog[i][j];
            }
        }

        // Print the result
        Library.print(part1,part2,name);
    }
}