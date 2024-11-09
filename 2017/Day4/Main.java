import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.Arrays;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 4: High-Entropy Passphrases";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // The number of valid passphrases
        int part1 = 0;
        int part2 = 0;

        // Continue through every line of input
        while (sc.hasNext()){
            // Take in the passphrase
            String[] line = sc.nextLine().split(" ");
            // Collect all of the words
            HashSet<String> words = new HashSet<>(Arrays.asList(line));

            // If no items were lost in the unique set
            if (line.length == words.size()){
                ++part1;
            }

            // Sort all words to find anagrams
            HashSet<String> sorted = new HashSet<>();
            for (String word : words){
                char[] array = word.toCharArray();
                Arrays.sort(array);
                // If that anagram already exists
                if (!sorted.add(String.valueOf(array))){
                    // Isn't valid
                    break;
                }
            }

            // If no items were lost in the unique set
            if (line.length == sorted.size()){
                ++part2;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}