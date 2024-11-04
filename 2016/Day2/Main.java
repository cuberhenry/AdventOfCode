import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 2: Bathroom Security";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // Take in the instructions
        ArrayList<String> instructions = new ArrayList<>();
        while (sc.hasNext()){
            instructions.add(sc.nextLine());
        }

        // Default keypad
        char[][] keypad = new char[][] {{'1','2','3'},
                                        {'4','5','6'},
                                        {'7','8','9'}};
        String part1 = code(instructions,keypad,1,1);
        // Strange keypad
        keypad = new char[][] {{' ',' ','1',' ',' '},
                               {' ','2','3','4',' '},
                               {'5','6','7','8','9'},
                               {' ','A','B','C',' '},
                               {' ',' ','D',' ',' '}};
        String part2 = code(instructions,keypad,0,2);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static String code(ArrayList<String> instructions, char[][] keypad, int x, int y){
        // The resulting password
        String password = "";
        // Loop through every instruction
        for (String line : instructions){
            // Loop through every character in the line
            for (char c : line.toCharArray()){
                // Move in the desired direction if possible
                switch (c){
                    case 'U' -> {
                        if (y > 0 && keypad[y-1][x] != ' '){
                            --y;
                        }
                    }
                    case 'D' -> {
                        if (y < keypad.length-1 && keypad[y+1][x] != ' '){
                            ++y;
                        }
                    }
                    case 'L' -> {
                        if (x > 0 && keypad[y][x-1] != ' '){
                            --x;
                        }
                    }
                    case 'R' -> {
                        if (x < keypad.length-1 && keypad[y][x+1] != ' '){
                            ++x;
                        }
                    }
                }
            }

            // Add the button from the end of the line to the password
            password += keypad[y][x];
        }

        return password;
    }
}