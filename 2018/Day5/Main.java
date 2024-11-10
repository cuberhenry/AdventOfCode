import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 5: Alchemical Reduction";
    public static void main(String args[]) {
        // Take in the polymer
        String polymer = Library.getString(args);

        // Loop through every pair of characters in reverse
        for (int i=polymer.length()-2; i>=0; --i){
            // Ensure we aren't checking after the end of the polymer
            if (i > polymer.length()-2){
                continue;
            }
            // If the characters are the same letter of different cases
            if (Math.abs(polymer.charAt(i)-polymer.charAt(i+1)) == 32){
                // Remove both
                polymer = polymer.substring(0,i) + polymer.substring(i+2);
            }
        }

        // Save the length
        int part1 = polymer.length();
        int part2 = polymer.length();

        // Loop through every letter
        for (int i=0; i<26; ++i){
            // Save a new polymer
            String newPolymer = polymer;
            // Loop through every character
            for (int j=newPolymer.length()-1; j>=0; --j){
                // If the letter is the current letter
                if ((newPolymer.charAt(j)-i-'a')%32 == 0){
                    // Remove it
                    newPolymer = newPolymer.substring(0,j) + newPolymer.substring(j+1);
                }
            }

            // Loop through every pair of characters in reverse
            for (int j=newPolymer.length()-2; j>=0; --j){
                // Ensure we aren't checking after the end of the polymer
                if (j > newPolymer.length()-2){
                    continue;
                }
                // If the characters are the same letter of different cases
                if (Math.abs(newPolymer.charAt(j)-newPolymer.charAt(j+1)) == 32){
                    // Remove both
                    newPolymer = newPolymer.substring(0,j) + newPolymer.substring(j+2);
                }
            }

            // If the polymer is smaller, save the new length
            if (newPolymer.length() < part2){
                part2 = newPolymer.length();
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}