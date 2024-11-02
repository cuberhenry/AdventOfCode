import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 11: Corporate Policy";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The current password we're examining
        char[] password = sc.next().toCharArray();

        String part1 = nextPassword(password);
        String part2 = nextPassword(password);

        Library.print(part1,part2,name);
    }
    
    private static String nextPassword(char[] password){
        // Continue until a break has been found
        while (true){
            // Used to increment the password
            int i = password.length-1;
            // Until a non-z has been found
            while (i >= 0 && password[i] == 'z'){
                // Set it to a and carry
                password[i] = 'a';
                --i;
            }
            // Increase the next letter
            if (i >= 0){
                ++password[i];
            }

            // Whether there's a straight
            boolean straight = false;
            // Whether there's a forbidden letter
            boolean forbidden = false;
            // The number of pairs of letters in the password
            int pairs = 0;

            // To make sure the pairs aren't overlapping
            int pairIndex = -2;
            // Loop through every character
            for (i=0; i<password.length; ++i){
                // If the next three letters are a straight
                if (i < password.length-2 && password[i] + 1 == password[i+1]
                                          && password[i] + 2 == password[i+2]){
                    straight = true;
                }
                // If the current letter is a forbidden letter
                if (password[i] == 'i' || password[i] == 'o' || password[i] == 'l'){
                    forbidden = true;
                    ++i;
                    while (i < password.length){
                        password[i] = 'z';
                        ++i;
                    }
                    break;
                }
                // If the next two letters are a pair and the current letter
                // isn't part of a pair already
                if (i < password.length-1 && password[i] == password[i+1] && i - pairIndex >= 2){
                    ++pairs;
                    pairIndex = i;
                }
            }

            // If all conditions are met, password found
            if (straight && !forbidden && pairs >= 2){
                return String.valueOf(password);
            }
        }
    }
}