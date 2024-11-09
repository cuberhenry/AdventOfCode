import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 21: Scrambled Letters and Hash";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The code
        char[] scramble = {'a','b','c','d','e','f','g','h'};
        char[] unscramble = {'f','b','g','d','c','e','a','h'};
        // The list of all instructions
        ArrayList<String[]> instructions = new ArrayList<>();

        // Add all instructions to the list of instructions
        while (sc.hasNext()){
            instructions.add(sc.nextLine().split(" "));
        }

        // Loop through every instruction
        for (int i=0; i<instructions.size(); ++i){
            // Perform the instruction
            operation(scramble,instructions.get(i),false);
            operation(unscramble,instructions.get(instructions.size()-1-i),true);
        }

        // The answer to the problem
        String part1 = String.valueOf(scramble);
        String part2 = String.valueOf(unscramble);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static void operation(char[] password, String[] instruction, boolean unscramble){
        // Do something based on the different commands
        switch (instruction[0]){
            // Swap two letters
            case "swap" -> {
                int one, two;
                // Based on position
                if (instruction[1].equals("position")){
                    one = Integer.parseInt(instruction[2]);
                    two = Integer.parseInt(instruction[5]);
                // Based on letter
                }else{
                    one = Library.indexOf(password,instruction[2].charAt(0));
                    two = Library.indexOf(password,instruction[5].charAt(0));
                }

                // Swap the letters
                char helper = password[one];
                password[one] = password[two];
                password[two] = helper;
            }
            // Shift the code
            case "rotate" -> {
                // Rotate based on the position of a letter
                if (instruction[1].equals("based")){
                    char c = instruction[6].charAt(0);
                    if (unscramble){
                        // The number of rotations performed so far
                        int rotations = 0;
                        // Until the correct number of shifts is found
                        while (rotations != 1 + Library.indexOf(password,c) + (Library.indexOf(password,c) >= 4 ? 1 : 0)){
                            char helper = password[0];
                            for (int j=0; j<password.length-1; ++j){
                                password[j] = password[j+1];
                            }
                            password[password.length-1] = helper;
                            ++rotations;
                        }
                    }else{
                        // Get the starting index
                        int rotations = Library.indexOf(password,c) + 1;
                        // Add if more than 4
                        if (rotations > 4){
                            ++rotations;
                        }
                        // Make sure it's not more than the number of letters
                        rotations %= password.length;
                        // Rotate
                        for (int i=0; i<rotations; ++i){
                            char helper = password[password.length-1];
                            for (int j=password.length-1; j>0; --j){
                                password[j] = password[j-1];
                            }
                            password[0] = helper;
                        }
                    }
                // Rotate based on a direction and a value
                }else{
                    // Get the value
                    int rotations = Integer.parseInt(instruction[2]) % password.length;

                    if (unscramble ^ instruction[1].equals("left")){
                        rotations = password.length - rotations;
                    }

                    // Rotate
                    for (int i=0; i<rotations; ++i){
                        char helper = password[password.length-1];
                        for (int j=password.length-1; j>0; --j){
                            password[j] = password[j-1];
                        }
                        password[0] = helper;
                    }
                }
            }
            // Reverse a section of the code
            case "reverse" -> {
                // Get the area to be reversed
                int start = Integer.parseInt(instruction[2]);
                int end = Integer.parseInt(instruction[4]);
                // Loop through the section and reverse it into reverse
                for (int i=0; i<(end+1-start)/2; ++i){
                    char helper = password[start + i];
                    password[start + i] = password[end - i];
                    password[end - i] = helper;
                }
            }
            // Move a character to a different spot
            case "move" -> {
                // Collect the indeces
                int from = Integer.parseInt(instruction[2]);
                int to = Integer.parseInt(instruction[5]);

                if (unscramble){
                    // Swap the indeces
                    int helper = from;
                    from = to;
                    to = helper;
                }

                boolean forwards = to > from;

                // The character being moved
                char helper = password[from];
                for (int i=from; i!=to; i += forwards ? 1 : -1){
                    password[i] = password[i + (forwards ? 1 : -1)];
                }
                password[to] = helper;
            }
        }
    }
}