import com.aoc.mylibrary.Library;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 15: Lens Library";
    public static void main(String args[]) {
        // Take in the input and split it into each section
        String[] input = Library.getStringArray(args,",");
        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // For every string in the input
        for (String str : input){
            // The current total
            int curr = 0;
            // Loop through every character
            for (char c : str.toCharArray()){
                // Perform the calculations for this character
                curr = (curr + c) * 17 % 256;
            }
            // Add to the total
            part1 += curr;
        }

        // The list of boxes, always 256 long
        ArrayList<ArrayList<String>> boxes = new ArrayList<>();
        for (int i=0; i<256; ++i){
            boxes.add(new ArrayList<String>());
        }

        // Loop through every input string
        for (String str : input){
            // The hash value of the letters
            int curr = 0;
            // The current character in the string
            int index = 0;
            // As long as the index points to a letter
            for (;Character.isAlphabetic(str.charAt(index)); ++index){
                // Perform the calculations for this character
                curr = (curr + str.charAt(index)) * 17 % 256;
            }
            // If the next character is a dash, remove the given lense
            if (str.charAt(index) == '-'){
                // Loop through every existing lense in the curr box
                for (int i=0; i<boxes.get(curr).size(); ++i){
                    // If it has the right label
                    if (boxes.get(curr).get(i).split(" ")[0].equals(str.substring(0,index))){
                        // Remove it
                        boxes.get(curr).remove(i);
                        break;
                    }
                }
            // Otherwise, add or replace the given lense
            }else{
                // Whether an existing lense has been found
                boolean found = false;
                // Loop through every existing lense in the curr box
                for (int i=0; i<boxes.get(curr).size(); ++i){
                    // If it has the same label
                    if (boxes.get(curr).get(i).split(" ")[0].equals(str.substring(0,index))){
                        // Replace the lense with the new lense
                        boxes.get(curr).set(i,str.substring(0,index) + " " + str.substring(index+1));
                        // Existing lense found
                        found = true;
                        break;
                    }
                }
                // Otherwise
                if (!found){
                    // Add the lense at the end
                    boxes.get(curr).add(str.substring(0,index) + " " + str.substring(index+1));
                }
            }
        }

        // Calculate the focusing power of the lenses
        // Loop through every box and lense
        for (int i=0; i<boxes.size(); ++i){
            for (int j=0; j<boxes.get(i).size(); ++j){
                // Add the focusing power of the lense
                part2 += (1 + i) * (1 + j) * Integer.parseInt(boxes.get(i).get(j).split(" ")[1]);
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}