import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 1: Not Quite Lisp";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);
        
        // Take in the input
        String line = sc.nextLine();
        // The current floor
        int part1 = 0;
        int part2 = -1;
        // Loop through ever parenthesis
        for (int i=0; i<line.length(); ++i){
            // Go up or down depending on the character
            if (line.charAt(i) == '('){
                ++part1;
            }else{
                --part1;
            }

            // Check for basement
            if (part1 < 0 && part2 == -1){
                part2 = i+1;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}