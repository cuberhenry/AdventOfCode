import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 6: Signals and Noise";
    public static void main(String args[]) {
        // Get all the input
        String[] input = Library.getStringArray(args,"\n");

        // The frequency of each letter
        int[][] chars = new int[input[0].length()][26];
        // While there's input
        for (String line : input){
            // Loop through every character
            for (int i=0; i<line.length(); ++i){
                // Add the character to the frequencies
                ++chars[i][line.charAt(i)-'a'];
            }
        }

        // The message
        String part1 = "";
        String part2 = "";
        // Loop through every character
        for (int i=0; i<chars.length; ++i){
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