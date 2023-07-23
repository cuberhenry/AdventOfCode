/*
Henry Anderson
Advent of Code 2017 Day 4 https://adventofcode.com/2017/day/4
Input: https://adventofcode.com/2017/day/4/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // The desired problem to solve
    static int PART;
    static Scanner sc;
    // The file containing the puzzle input
    static String FILE_NAME = "input.txt";
    public static void main(String args[]) {
        if (args.length < 1 || args.length > 2){
            System.out.println("Wrong number of arguments");
            return;
        }
        // Take in the part and file name
        try {
            PART = Integer.parseInt(args[0]);
        } catch (Exception e){}
        if (!(PART == 1 || PART == 2)){
            System.out.println("Part can only be 1 or 2");
            return;
        }
        if (args.length == 2){
            FILE_NAME = args[1];
        }
        try {
            sc = new Scanner(new File(FILE_NAME));
        }catch (Exception e){
            System.out.println("File not found");
            return;
        }
        // The number of valid passphrases
        int valid = 0;

        // Continue through every line of input
        while (sc.hasNext()){
            // Take in the passphrase
            String[] line = sc.nextLine().split(" ");
            // Collect all of the words
            ArrayList<String> words = new ArrayList<>();
            // Loop throughe very word in the passphrase
            for (int i=0; i<line.length; ++i){
                // Part 1 finds the number of passphrases with all unique words
                if (PART == 1){
                    // If the word hasn't been found
                    if (!words.contains(line[i])){
                        // Add it
                        words.add(line[i]);
                    }else{
                        // Break
                        break;
                    }
                }
                
                // Part 2 finds the number of passphrases that don't contain anagrams
                if (PART == 2){
                    // Whether an anagram has been found
                    boolean anagram = false;
                    // The current word
                    String word = line[i];
                    // Loop through every previous word
                    for (int j=0; j<words.size() && !anagram; ++j){
                        // The other word
                        String other = words.get(j);

                        // If the words aren't the same length, they aren't anagrams
                        if (word.length() != other.length()){
                            continue;
                        }

                        // Loop through every letter in the word
                        for (int k=0; k<word.length(); ++k){
                            // The index of letter k of word in other
                            int index = other.indexOf(word.charAt(k));
                            // If the letter doesn't exist in other, they aren't anagrams
                            if (index == -1){
                                break;
                            }
                            // Remove that letter from other
                            other = other.substring(0,index) + other.substring(index+1);
                        }

                        // If all of the letters exist in both, they're anagrams
                        if (other.length() == 0){
                            anagram = true;
                            break;
                        }
                    }

                    // If it's not an anagram of any previous word
                    if (!anagram){
                        // Add the word
                        words.add(line[i]);
                    }else{
                        // Break
                        break;
                    }
                }
            }
            // If every word has been added
            if (words.size() == line.length){
                // It's a valid passphrase
                ++valid;
            }
        }

        // Print the answer
        System.out.println(valid);
    }
}