import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 2: I Was Told There Would Be No Math";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        // For every present
        while (sc.hasNext()){
            // Take in and split the next present
            String[] line = sc.nextLine().split("x");
            int l = Integer.parseInt(line[0]);
            int w = Integer.parseInt(line[1]);
            int h = Integer.parseInt(line[2]);

            // Surface area
            part1 += 2 * (l*w + w*h + h*l);
            // Extra wrapping paper the size of the smallest size
            part1 += Math.min(Math.min(l*w,w*h),h*l);

            // The bow
            part2 += l*w*h;
            // Add two times the sum of the smallest dimensions
            part2 += 2 * (l+w+h - Math.max(Math.max(l,w),h));
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}