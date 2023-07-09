/*
Henry Anderson
Advent of Code 2016 Day 21 https://adventofcode.com/2016/day/21
Input: https://adventofcode.com/2016/day/21/input
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
        // The code
        String code = "";
        // The list of all instructions
        ArrayList<String> instructions = new ArrayList<>();

        // Add all instructions to the list of instructions
        while (sc.hasNext()){
            instructions.add(sc.nextLine());
        }

        // Part 1 follows the instructions
        if (PART == 1){
            code = "abcdefgh";
        }

        // Part 2 reverses the instructions on a different code
        if (PART == 2){
            code = "fbgdceah";
            Collections.reverse(instructions);
        }

        // Loop through every instruction
        for (String instruction : instructions){
            // Split the instruction
            String[] command = instruction.split(" ");

            // Do something based on the different commands
            switch (command[0]){
                // Swap two letters
                case "swap" -> {
                    int one, two;
                    // Based on position
                    if (command[1].equals("position")){
                        one = Integer.parseInt(command[2]);
                        two = Integer.parseInt(command[5]);
                    // Based on letter
                    }else{
                        one = code.indexOf(command[2].charAt(0));
                        two = code.indexOf(command[5].charAt(0));
                    }

                    // Order the numbers
                    int max = Math.max(one,two);
                    int min = Math.min(one,two);

                    // Copy the code with the swapped numbers
                    code = code.substring(0,min) + code.charAt(max)
                         + code.substring(min+1,max) + code.charAt(min)
                         + code.substring(max+1);
                }
                // Shift the code
                case "rotate" -> {
                    int num;
                    // Rotate based on the position of a letter
                    if (command[1].equals("based")){
                        char c = command[6].charAt(0);

                        if (PART == 1){
                            // Collect the index of the letter
                            num = code.indexOf(c);
                            // Add one if it's at least four
                            if (num >= 4){
                                ++num;
                            }
                            // Add one regardless
                            ++num;

                            // Make sure it's not more than the number of letters
                            num %= code.length();
                            // Connect the new code
                            code = code.substring(code.length()-num) + code.substring(0,code.length()-num);
                        }

                        if (PART == 2){
                            // The number of rotations performed so far
                            int numRots = 0;
                            // Until the correct number of shifts is found
                            while (numRots != 1 + code.indexOf(c) + (code.indexOf(c) >= 4 ? 1 : 0)){
                                // Shift once to the left
                                code = code.substring(1) + code.charAt(0);
                                ++numRots;
                            }
                        }
                    // Rotate based on a direction and a value
                    }else{
                        // Get the value
                        num = Integer.parseInt(command[2]) % code.length();
                        
                        // Turn left shifts into right shifts
                        if (PART == 1){
                            if (command[1].equals("left")){
                                num = code.length() - num;
                            }
                        }

                        // Turn right shifts into left shifts
                        if (PART == 2){
                            if (command[1].equals("right")){
                                num = code.length() - num;
                            }
                        }

                        // Connect the new code
                        code = code.substring(code.length()-num) + code.substring(0,code.length()-num);
                    }
                }
                // Reverse a section of the code
                case "reverse" -> {
                    // The reversed section
                    String reverse = "";
                    // Get the area to be reversed
                    int start = Integer.parseInt(command[2]);
                    int end = Integer.parseInt(command[4]);
                    // Loop through the section and reverse it into reverse
                    for (int i=start; i<=end; ++i){
                        reverse = code.charAt(i) + reverse;
                    }

                    // Connect the new code
                    code = code.substring(0,start) + reverse + code.substring(end+1);
                }
                // Move a character to a different spot
                case "move" -> {
                    // Collect the indeces
                    int from = Integer.parseInt(command[2]);
                    int to = Integer.parseInt(command[5]);

                    if (PART == 2){
                        // Swap the indeces
                        int help = from;
                        from = to;
                        to = help;
                    }

                    // The character being moved
                    char c = code.charAt(from);

                    // Remove the character
                    code = code.substring(0,from) + code.substring(from+1);
                    // Put the character in the new spot
                    code = code.substring(0,to) + c + code.substring(to);
                }
            }
        }

        // Print the answer
        System.out.println(code);
    }
}