import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 9: Stream Processing";
    public static void main(String args[]) {
        // Get the input
        String input = Library.getString(args);
        // The stream input without the canceled characters
        char[] stream = input.replaceAll("!.","").toCharArray();
        
        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        // The number of containing groups
        int depth = 0;
        // Whether the current index is within garbage
        boolean garbage = false;

        // Loop through every character
        for (char c : stream){
            if (garbage){
                if (c == '>'){
                    garbage = false;
                }else{
                    // If we're in garbage
                    ++part2;
                }
            }else{
                if (c == '{'){
                    // Go deeper
                    ++depth;
                }else if (c == '}'){
                    part1 += depth--;
                }else if (c == '<'){
                    // Start garbage
                    garbage = true;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}