import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 4: Security Through Obscurity";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = -1;
        // The room to look in
        String target = "northpole-object-storage";

        // Loop through every line of input
        while (sc.hasNext()){
            // The number of each letter appearing
            int[] letters = new int[26];
            // Take in the room
            String line = sc.nextLine();
            String[] room = line.split("-|\\[|\\]");

            // Go through every letter and add it to the quantity of that letter
            for (int i=0; i<room.length-2; ++i){
                for (char c : room[i].toCharArray()){
                    ++letters[c - 'a'];
                }
            }

            // Whether the room is a real one
            boolean real = true;
            // Go through each letter in the checksum
            for (int i=0; i<room[room.length-1].length() && real; ++i){
                // The index in letters of the current letter
                int index = room[room.length-1].charAt(i)-'a';
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
                int sectorID = Integer.parseInt(room[room.length-2]);
                part1 += sectorID;

                if (part2 == -1){
                    line = line.substring(0,line.lastIndexOf('-'));
                    boolean matches = true;
                    for (int i=0; i<line.length() && matches; ++i){
                        if (line.charAt(i) == '-' && target.charAt(i) == '-'){
                            continue;
                        }
                        matches = matches && (char)((line.charAt(i)-'a'+sectorID)%26+'a') == target.charAt(i);
                    }
                    if (matches){
                        part2 = sectorID;
                    }
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}