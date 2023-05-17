/*
Henry Anderson
Advent of Code 2016 Day 4 https://adventofcode.com/2016/day/4
Input: https://adventofcode.com/2016/day/4/input
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
        // The answer to the problem
        int total = 0;

        // Part 1 finds the sum of sector ids of real rooms
        if (PART == 1){
            // Loop through every line of input
            while (sc.hasNext()){
                // The number of each letter appearing
                int[] letters = new int[26];
                // Take in the room
                String room = sc.nextLine();
                // Disect the checksum
                String checksum = room.substring(room.indexOf('[')+1,room.length()-1);

                // Remove the checksum
                room = room.substring(0,room.length()-7);
                // Split the room on dashes
                String[] split = room.split("-");

                // Go through every letter and add it to the quantity of that letter
                for (int i=0; i<split.length-1; ++i){
                    for (int j=0; j<split[i].length(); ++j){
                        ++letters[split[i].charAt(j)-'a'];
                    }
                }

                // Whether the room is a real one
                boolean real = true;
                // Go through each letter in the checksum
                for (int i=0; i<checksum.length() && real; ++i){
                    // The index in letters of the current letter
                    int index = checksum.charAt(i)-'a';
                    // Loop through every alphabetically previous number
                    for (int j=0; j<index; ++j){
                        // If the letter should've come first
                        if (letters[j] >= letters[index]){
                            // The room isn't real
                            real = false;
                            break;
                        }
                    }
                    // Loop through every alphabetically subsequent number
                    for (int j=index+1; j<letters.length; ++j){
                        // If the letter should've come first
                        if (letters[j] > letters[index]){
                            // The room isn't real
                            real = false;
                            break;
                        }
                    }
                    // Reset that letter
                    letters[index] = 0;
                }

                // If it's real, add the sector ID to total
                if (real){
                    total += Integer.parseInt(split[split.length-1]);
                }
            }
        }

        if (PART == 2){
            // Loop through every line of input
            while (sc.hasNext()){
                // Take in the room
                String line = sc.nextLine();
                // Remove the checksum
                line = line.substring(0,line.indexOf('['));
                // Save the sector
                int sector = Integer.parseInt(line.substring(line.lastIndexOf('-')+1));
                // Remove the sector
                line = line.substring(0,line.lastIndexOf('-'));
                // The decrypted room
                String newLine = "";

                // Loop through every character
                for (int i=0; i<line.length(); ++i){
                    if (line.charAt(i) == '-'){
                        // Replace dashes with spaces
                        newLine += ' ';
                    }else{
                        // Shift the character
                        newLine += (char)((line.charAt(i)-'a'+sector)%26+'a');
                    }
                }

                // Find the answer
                if (newLine.equals("northpole object storage")){
                    // Save the sector
                    total = sector;
                    break;
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}