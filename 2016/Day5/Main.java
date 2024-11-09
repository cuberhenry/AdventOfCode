import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 5: How About a Nice Game of Chess?";
    public static void main(String args[]) {
        // Take in the input
        String input = Library.getString(args);
        // The index at which to start
        int index = 0;
        // The password
        String part1 = "";
        char[] second = new char[8];
        int filled = 0;
        
        // Until the password has been discovered
        while (part1.length() < 8 || filled < 8){
            String hash = Library.md5(input + index);

            // Add leading zeroes up to where relevant
            while (hash.length() < 27){
                hash = "0" + hash;
            }

            // If there are 5 leading zeroes
            if (hash.length() < 28){
                if (part1.length() < 8){
                    // Add the character to the password
                    part1 += hash.charAt(0);
                }

                if (filled < 8){
                    // The index in the password
                    int first = hash.charAt(0)-'0';
                    // If the index is within the password's range and is not yet found
                    if (first >= 0 && first < 8 && second[first] == 0){
                        // Add the character to the password
                        second[first] = hash.charAt(1);
                        ++filled;
                    }
                }
            }
            
            // Increase the index
            ++index;
        }

        String part2 = String.valueOf(second);
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}