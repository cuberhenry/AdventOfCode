/*
Henry Anderson
Advent of Code 2018 Day 2 https://adventofcode.com/2018/day/2
Input: https://adventofcode.com/2018/day/2/input
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
        // Part 1 finds the checksum of all the boxes
        if (PART == 1){
            // The number of boxes that contain a letter exactly two or three times
            int two = 0;
            int three = 0;

            // Loop through every box
            while (sc.hasNext()){
                // Take in the box
                String box = sc.next();

                // The number of occurences of each letter
                int[] letters = new int[26];

                // Add each letter to letters
                for (int i=0; i<box.length(); ++i){
                    ++letters[box.charAt(i)-'a'];
                }

                // Whether a letter was found exactly two or three times
                boolean boolTwo = false;
                boolean boolThree = false;

                // Loop through ever letter
                for (int letter : letters){
                    // The letter exists twice
                    if (letter == 2 && !boolTwo){
                        boolTwo = true;
                        ++two;
                        if (boolThree){
                            break;
                        }
                    }
                    // The letter exists thrice
                    if (letter == 3 && !boolThree){
                        boolThree = true;
                        ++three;
                        if (boolTwo){
                            break;
                        }
                    }
                }
            }

            // Print the answer
            System.out.println(two * three);
        }

        // Part 2 finds two boxes that differ by one letter
        if (PART == 2){
            // The list of all previous boxes
            ArrayList<String> boxes = new ArrayList<>();

            // Loop through every box
            while (sc.hasNext()){
                // Take in the box
                String box = sc.next();

                // Loop through every previous box
                for (String other : boxes){
                    // The index of the different character
                    int different = -1;

                    // Loop through every character
                    for (int i=0; i<box.length(); ++i){
                        // If the characters differ
                        if (box.charAt(i) != other.charAt(i)){
                            if (different == -1){
                                // Set the index to the current index
                                different = i;
                            }else{
                                // This is not the first different letter
                                different = -1;
                                break;
                            }
                        }
                    }

                    // If an index has been found, print the answer and return
                    if (different != -1){
                        System.out.println(box.substring(0,different)+box.substring(different+1));
                        return;
                    }
                }

                // Add the current box to the list
                boxes.add(box);
            }
        }
    }
}