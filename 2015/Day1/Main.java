import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 1: Not Quite Lisp";
    public static void main(String args[]) {
        // Take in the input
        String line = Library.getString(args);
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