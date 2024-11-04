import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 3: Squares With Three Sides";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The number of valid triangles
        int part1 = 0;
        int part2 = 0;

        // While there are more traingles
        while (sc.hasNext()){
            // The next 9 sides
            int[] sides = new int[9];
            for (int i=0; i<9; ++i){
                sides[i] = sc.nextInt();
            }

            // Loop through each triangle
            for (int i=0; i<3; ++i){
                // Horizontally
                if (validTriangle(sides[i*3],sides[i*3+1],sides[i*3+2])){
                    ++part1;
                }

                // Vertically
                if (validTriangle(sides[i],sides[i+3],sides[i+6])){
                    ++part2;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static boolean validTriangle(int a, int b, int c){
        // The perimeter of the triangle
        int perimeter = a + b + c;
        // The length of the longest side
        int longest = Math.max(a,Math.max(b,c));

        // Return whether the trianlge is valid
        return longest < perimeter - longest;
    }
}