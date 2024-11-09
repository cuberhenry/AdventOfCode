import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 13: Packet Scanners";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // The list of scanners
        ArrayList<int[]> scanners = new ArrayList<>();
        // Add each scanner
        while (sc.hasNext()){
            int[] scanner = Library.intSplit(sc.nextLine(),": ");
            scanners.add(scanner);

            // If the scanner is at position 0 with 0 delay
            if (scanner[0] % ((scanner[1]-1)*2) == 0){
                // Add this layer's severity
                part1 += scanner[0] * scanner[1];
            }
        }

        // Loop through every scanner
        for (int i=0; i<scanners.size(); ++i){
            // Grab the scanner
            int[] scanner = scanners.get(i);

            // If the scanner is at position 0
            if ((scanner[0]+part2) % ((scanner[1]-1)*2) == 0){
                // Increase the delay
                ++part2;
                // Restart the checking
                i = -1;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}