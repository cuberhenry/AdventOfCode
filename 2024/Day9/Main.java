import com.aoc.mylibrary.Library;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 9: Disk Fragmenter";
    public static void main(String[] args){
        // Take in the input
        int[] input = Library.getIntArray(args);

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // Collect some values
        int pointerStart = 0;
        int pointerEnd = input.length;
        int counterEnd = 0;

        if (input.length % 2 == 1){
            ++pointerEnd;
        }

        // Continue until all blocks have been counted
        for (int i=0; i < pointerEnd; i += 2){
            // Add the blocks of the current file
            for (int j=0; j<input[i]; ++j){
                // Add this block's checksum
                part1 += pointerStart * (i / 2);
                ++pointerStart;
            }
            // Add all the blocks from files at the end of the line
            for (int j=0; j<input[i+1]; ++j){
                // If the last file ran out
                if (counterEnd == 0){
                    // Move back one
                    pointerEnd -= 2;
                    if (i == pointerEnd){
                        break;
                    }
                    counterEnd = input[pointerEnd];
                }
                // Add this block's checksum
                part1 += pointerStart * (pointerEnd / 2);
                --counterEnd;
                ++pointerStart;
            }
        }

        // Count all the remaining blocks from the middle file
        while (counterEnd > 0){
            // Add this block's checksum
            part1 += pointerStart * (pointerEnd / 2);
            --counterEnd;
            ++pointerStart;
        }

        // Put each file in a list
        int pointer = 0;
        ArrayList<int[]> files = new ArrayList<>();
        for (int i=0; i<input.length; i+=2){
            int[] file = new int[3];
            // The file's starting index
            file[0] = pointer;
            pointer += input[i];
            // The file's open last index
            file[1] = pointer;
            if (i+1 < input.length){
                pointer += input[i+1];
            }
            // The file's index
            file[2] = files.size();
            files.add(file);
        }

        // Loop through each file backwards
        for (int i=files.size()-1; i>0; --i){
            int[] range = files.get(i);
            // Loop through each open space forwards
            for (int j=0; j<i; ++j){
                int[] next = files.get(j);
                // If the later file fits in the current space
                if (files.get(j+1)[0] - next[1] >= range[1] - range[0]){
                    // Move the file into the open space
                    files.add(j+1,files.remove(i));
                    range[1] = next[1] + range[1] - range[0];
                    range[0] = next[1];
                    ++i;

                    break;
                }
            }
        }

        // Loop through each file
        for (int i=0; i<files.size(); ++i){
            // Loop through each block
            for (int j=files.get(i)[0]; j<files.get(i)[1]; ++j){
                // Add the block's checksum
                part2 += j * files.get(i)[2];
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}