import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 5: Doesn't He Have Intern-Elves For This?";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // Constants for finding matches
        String vowels = "aeiou";
        String forbidden = "ab cd pq xy";
        // The number of nice strings
        int part1 = 0;
        int part2 = 0;

        // While there's input
        while (sc.hasNext()){
            // Take in the next input
            String string = sc.next();
            // The number of vowels in the string
            int numVowels = 0;
            // The two conditions
            boolean pair = false;
            boolean hasForbidden = false;
            boolean repeated = false;
            boolean sandwich = false;

            // All pairs of characters
            ArrayList<String> pairs = new ArrayList<>();

            // Loop through every character
            for (int i=0; i<string.length(); ++i){
                // Contains at least three vowels
                if (vowels.indexOf(string.charAt(i)) != -1){
                    ++numVowels;
                }

                if (i < string.length()-1){
                    // Contains a letter pair
                    if (string.charAt(i) == string.charAt(i+1)){
                        pair = true;
                    }

                    // Doesn't contain one of the forbidden pairs
                    if (forbidden.indexOf(string.substring(i,i+2)) != -1){
                        hasForbidden = true;
                    }
                }

                // Contains a duplicate pair not overlapping
                if (!repeated && i < string.length()-1){
                    int index = pairs.indexOf(string.substring(i,i+2));
                    if (index != -1 && index != pairs.size()-1){
                        repeated = true;
                    }else{
                        pairs.add(string.substring(i,i+2));
                    }
                }

                // Contains a single-layer sandwich
                if (!sandwich && i < string.length()-2){
                    if (string.charAt(i) == string.charAt(i+2)){
                        sandwich = true;
                    }
                }
            }

            // Increase total if the string is nice
            if (numVowels >= 3 && pair && !hasForbidden){
                ++part1;
            }

            if (repeated && sandwich){
                ++part2;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}