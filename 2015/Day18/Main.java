import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.GameOfLife;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 18: Like a GIF For Your Yard";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The list of on lights
        GameOfLife part1 = new GameOfLife(2);
        part1.addPersist(2);
        part1.addPersist(3);
        part1.addCreate(3);
        part1.setExpand(false);

        int height = 0;
        int width = 0;
        // Add all of the rest of the input to the input string
        while (sc.hasNext()){
            char[] line = sc.nextLine().toCharArray();
            width = line.length;
            for (int i=0; i<line.length; ++i){
                if (line[i] == '#'){
                    part1.add(new int[] {i,height});
                }
            }
            // Increment the height for every line
            ++height;
        }

        GameOfLife part2 = new GameOfLife(part1);

        // Loop through all 100 steps
        for (int i=0; i<100; ++i){
            // Step
            part1.step();

            // Turn on the corners
            part2.add(new int[] {0,0});
            part2.add(new int[] {0,height-1});
            part2.add(new int[] {width-1,0});
            part2.add(new int[] {width-1,height-1});

            // Step
            part2.step();
        }

        // Turn on the corners
        part2.add(new int[] {0,0});
        part2.add(new int[] {0,height-1});
        part2.add(new int[] {width-1,0});
        part2.add(new int[] {width-1,height-1});

        // Print the answer
        Library.print(part1.size(),part2.size(),name);
    }
}