import com.aoc.mylibrary.Library;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 21: Keypad Conundrum";
    public static void main(String[] args){
        // Take in the five codes
        String[] codes = Library.getStringArray(args);

        // The answer to the problems
        long part1 = 0;
        long part2 = 0;

        // Loop through each code
        for (String code : codes){
            // The number of each set of (A,A] keypresses
            HashMap<String,Long> counts = new HashMap<>();
            // Start over the A in the number pad
            int x = 2;
            int y = 0;
            // Loop through each character in the code
            for (int i=0; i<code.length(); ++i){
                // The instructions for the first direction keypad
                String instrs = "";
                // Get the character
                char c = code.charAt(i);
                // A is in the bottom right
                if (c == 'A'){
                    // Prioritize going down first only if you can go all the way
                    if (x != 0){
                        while (y > 0){
                            --y;
                            instrs += "v";
                        }
                    }
                    // Go right all the way
                    while (x < 2){
                        ++x;
                        instrs += ">";
                    }
                    // Go down all the way
                    while (y > 0){
                        --y;
                        instrs += "v";
                    }
                }else{
                    // Prioritize going left to the column if possible
                    if (c % 3 != '1' % 3 || y != 0){
                        while (x > (c-'0'+2) % 3 && c != '0' || x == 2 && c == '0'){
                            --x;
                            instrs += "<";
                        }
                    }
                    // Prioritize going down to the row if possible
                    if (c >= '1' && c <= '9' || x != 0){
                        while (y > (c-'0'+2) / 3){
                            --y;
                            instrs += "v";
                        }
                    }
                    // Go up to the digit's row
                    while (y < (c-'0'+2) / 3){
                        ++y;
                        instrs += "^";
                    }
                    // Go right to the digit's column
                    while (x < (c-'0'+2) % 3 && c != '0' || x == 0 && c == '0'){
                        ++x;
                        instrs += ">";
                    }
                    // Go left to the digit's column
                    while (x > (c-'0'+2) % 3 && c != '0' || x == 2 && c == '0'){
                        --x;
                        instrs += "<";
                    }
                    // Go down to the digit's row
                    while (y > (c-'0'+2) / 3){
                        --y;
                        instrs += "v";
                    }
                }
                // Press the A to press the corresponding character
                instrs += "A";
                // Add this set of instructions to count
                if (counts.containsKey(instrs)){
                    counts.put(instrs,counts.get(instrs)+1);
                }else{
                    counts.put(instrs,1L);
                }
            }

            // Find 25 robot keypads deep
            for (int i=0; i<25; ++i){
                // Calculate the length at 2 robot keypads deep
                if (i == 2){
                    // The total number of button presses
                    long size = 0;
                    // Loop through each set type of keypresses
                    for (String key : counts.keySet()){
                        // Add the length times the count
                        size += key.length() * counts.get(key);
                    }
                    // Add the complexity at two keypads
                    part1 += size * Integer.parseInt(code.substring(0,3));
                }
                // The counts of sets for the next level
                HashMap<String,Long> newCounts = new HashMap<>();
                // Loop through each set
                for (String num : counts.keySet()){
                    // A is in the top right
                    x = 2;
                    y = 1;
                    // Loop through each character
                    for (char c : num.toCharArray()){
                        // The instructions for the next robot
                        String instrs = "";
                        // Prioritize left unless going from A to <
                        if (x == 2 && (c == 'v' || c == '^')){
                            instrs += "<";
                            --x;
                        }
                        // Go down
                        if (y == 1 && c != '^' && c != 'A'){
                            instrs += "v";
                            --y;
                        }
                        // Go right
                        if (x == 0 && c != '<'){
                            ++x;
                            instrs += ">";
                            // Double up on rights
                            if (c == 'A'){
                                ++x;
                                instrs += ">";
                            }
                        }
                        // Go left
                        if (x == 2 && c != 'A' && c != '>'){
                            --x;
                            instrs += "<";
                        }
                        if (x == 1 && c == '<'){
                            --x;
                            instrs += "<";
                        }
                        // Go up
                        if (y == 0 && (c == '^' || c == 'A')){
                            instrs += "^";
                            ++y;
                        }
                        // Go right
                        if (x == 1 && (c == 'A' || c == '>')){
                            ++x;
                            instrs += ">";
                        }
                        // Add an A to push the c button
                        instrs += "A";
                        // Add to the new counts
                        if (newCounts.containsKey(instrs)){
                            newCounts.put(instrs,newCounts.get(instrs)+counts.get(num));
                        }else{
                            newCounts.put(instrs,counts.get(num));
                        }
                    }
                }
                counts = newCounts;
            }

            // The total number of button presses
            long size = 0;
            // Loop through each set type of keypresses
            for (String key : counts.keySet()){
                // Add the length times the count
                size += key.length() * counts.get(key);
            }
            // Add the complexity at two keypads
            part2 += size * Integer.parseInt(code.substring(0,3));
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}