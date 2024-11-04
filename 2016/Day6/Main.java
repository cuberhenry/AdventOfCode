import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 6: Signals and Noise";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The first line of input
        String line = sc.next();
        // The frequency of each letter
        int[][] chars = new int[line.length()][26];
        // While there's input
        while (sc.hasNext()){
            // Loop through every character
            for (int i=0; i<line.length(); ++i){
                // Add the character to the frequencies
                ++chars[i][line.charAt(i)-'a'];
            }
            // Take in the next line
            line = sc.next();
        }
        // Perform frequency check on the last line of input
        for (int i=0; i<line.length(); ++i){
            ++chars[i][line.charAt(i)-'a'];
        }

        // The message
        String part1 = "";
        String part2 = "";
        // Loop through every character
        for (int i=0; i<line.length(); ++i){
            // The index of the character
            int most = 0;
            int least = 0;
            // Loop through every other character
            for (int j=1; j<26; ++j){
                // If the character occurs more than the current character
                if (chars[i][j] > chars[i][most]){
                    most = j;
                }

                // If the character occurs less than the current character
                if (chars[i][j] < chars[i][least] && chars[i][j] != 0){
                    least = j;
                }
            }

            // Add the character to the message
            part1 += (char)(most + 'a');
            part2 += (char)(least + 'a');
        }

        // Print the message
        Library.print(part1,part2,name);
    }
}