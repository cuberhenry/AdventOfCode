import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 2: Bathroom Security";
    public static void main(String args[]) {
        // Take in the instructions
        String[] instructions = Library.getStringArray(args,"\n");

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

    private static String code(String[] instructions, char[][] keypad, int x, int y){
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