/*
Henry Anderson
Advent of Code 2020 Day 4 https://adventofcode.com/2020/day/4
Input: https://adventofcode.com/2020/day/4/input
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
        // The required fields for a passport
        String[] fields = {"byr","iyr","eyr","hgt","hcl","ecl","pid"};
        // Whether the current passport has the required fields
        boolean[] has = new boolean[7];
        // The number of valid passports
        int valid = 0;

        // Loop through every line
        while (sc.hasNext()){
            // Take in the next line
            String line = sc.nextLine();
            // If the next line is blank
            if (line.equals("")){
                // Whether the current passport is valid
                boolean good = true;
                // A passport is valid if it has all the required fields
                for (boolean bool : has){
                    if (!bool){
                        good = false;
                        break;
                    }
                }
                if (good){
                    ++valid;
                }
                // Start over for the next passport
                has = new boolean[7];
                continue;
            }

            // Part 1 finds the number of passports that have the required fields
            if (PART == 1){
                // Loop through every field
                for (int i=0; i<fields.length; ++i){
                    // If it's in the current line, the passport has it
                    if (line.contains(fields[i] + ":")){
                        has[i] = true;
                    }
                }
            }

            // Part 2 requires certain values for each field
            if (PART == 2){
                // Loop through every present field
                for (String split : line.split(" ")){
                    String[] keyValue = split.split(":");
                    // Match based on the key
                    switch (keyValue[0]){
                        // Birth Year
                        case "byr" -> {
                            // Between 1920 and 2002
                            has[0] = keyValue[1].matches("19[2-9]\\d|200[0-2]");
                        }
                        // Issue Year
                        case "iyr" -> {
                            // Between 2010 and 2020
                            has[1] = keyValue[1].matches("201\\d|2020");
                        }
                        // Expiration Year
                        case "eyr" -> {
                            // Between 2020 and 2030
                            has[2] = keyValue[1].matches("202\\d|2030");
                        }
                        // Height
                        case "hgt" -> {
                            // Between 150 and 193 cm or 59 and 76 in
                            has[3] = keyValue[1].matches("1([5-8]\\d|9[0-3])cm|(59|6\\d|7[0-6])in");
                        }
                        // Hair Color
                        case "hcl" -> {
                            // A six digit hexadecmial value (with a # in front)
                            has[4] = keyValue[1].matches("#[0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f][0-9a-f]");
                        }
                        // Eye Color
                        case "ecl" -> {
                            // One of the given values
                            has[5] = keyValue[1].matches("amb|blu|brn|gry|grn|hzl|oth");
                        }
                        // Passport ID
                        case "pid" -> {
                            // A 9 digit number
                            has[6] = keyValue[1].matches("\\d\\d\\d\\d\\d\\d\\d\\d\\d");
                        }
                    }
                }
            }
        }

        // Check the last present passport
        boolean good = true;
        for (boolean bool : has){
            if (!bool){
                good = false;
                break;
            }
        }
        if (good){
            ++valid;
        }

        // Print the answer
        System.out.println(valid);
    }
}