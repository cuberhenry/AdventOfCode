import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 2: Inventory Management System";
    public static void main(String args[]) {
        // Get all the boxes
        char[][] boxes = Library.getCharMatrix(args);
        // The number of boxes that contain a letter exactly two or three times
        int two = 0;
        int three = 0;
        // The similar boxes
        String part2 = "";

        // Loop through every box
        for (int i=0; i<boxes.length; ++i){
            // The number of occurences of each letter
            int[] letters = new int[26];

            // Add each letter to letters
            for (char c : boxes[i]){
                ++letters[c-'a'];
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

            if (part2.isBlank()){
                // Loop through every previous box
                for (int j=0; j<i; ++j){
                    // The index of the different character
                    int different = -1;

                    // Loop through every character
                    for (int k=0; k<boxes[0].length; ++k){
                        // If the characters differ
                        if (boxes[i][k] != boxes[j][k]){
                            if (different == -1){
                                // Set the index to the current index
                                different = k;
                            }else{
                                // This is not the first different letter
                                different = -1;
                                break;
                            }
                        }
                    }

                    // If an index has been found, print the answer and return
                    if (different != -1){
                        String box = String.valueOf(boxes[i]);
                        part2 = box.substring(0,different)+box.substring(different+1);
                        break;
                    }
                }
            }
        }

        int part1 = two * three;

        // Print the answer
        Library.print(part1,part2,name);
    }
}