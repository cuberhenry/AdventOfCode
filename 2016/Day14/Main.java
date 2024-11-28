import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    final private static String name = "Day 14: One-Time Pad";
    public static void main(String args[]) {
        // Take in the input
        String input = Library.getString(args);
        // The index at which to start
        int index = 0;

        // The indeces of keys
        ArrayList<Integer> indices1 = new ArrayList<>();
        ArrayList<Integer> indices2 = new ArrayList<>();
        // Possible keys: [index, character]
        ArrayList<int[]> repetitions1 = new ArrayList<>();
        ArrayList<int[]> repetitions2 = new ArrayList<>();

        // Until a break is found
        while (indices1.size() < 64 || !repetitions1.isEmpty() || indices2.size() < 64 || !repetitions2.isEmpty()){
            // Remove past repetitions
            if (!repetitions1.isEmpty() && repetitions1.getFirst()[0] + 1000 < index){
                repetitions1.remove(0);
            }
            if (!repetitions2.isEmpty() && repetitions2.getFirst()[0] + 1000 < index){
                repetitions2.remove(0);
            }
            // The input string
            String hash = Library.md5(input + index,true);
            String stretched = hash;
            for (int i=0; i<2016; ++i){
                // Find the hash of the string
                stretched = Library.md5(stretched,true);
            }

            // The first sequence of three
            int[] sequence1 = null;
            int[] sequence2 = null;
            // Loop through every triplet of characters
            for (int i=0; i<hash.length()-2; ++i){
                char c = hash.charAt(i);
                // If the three characters are the same
                if (c == hash.charAt(i+1) && c == hash.charAt(i+2)){
                    // If the hash contains a sequence of five
                    if (i < hash.length()-4 && c == hash.charAt(i+3) && c == hash.charAt(i+4)){
                        // Loop through every hash that has a triplet
                        for (int j=repetitions1.size()-1; j>=0; --j){
                            // If the triplet matches the current five sequence
                            if (repetitions1.get(j)[1] == c){
                                // Add it to the list of correct values
                                indices1.add(repetitions1.remove(j)[0]);
                            }
                        }
                    }
                    // Save the first triplet sequence
                    if (sequence1 == null && indices1.size() < 64){
                        sequence1 = new int[] {index,c};
                    }
                }

                c = stretched.charAt(i);
                // If the three characters are the same
                if (c == stretched.charAt(i+1) && c == stretched.charAt(i+2)){
                    // If the hash contains a sequence of five
                    if (i < stretched.length()-4 && c == stretched.charAt(i+3) && c == stretched.charAt(i+4)){
                        // Loop through every hash that has a triplet
                        for (int j=repetitions2.size()-1; j>=0; --j){
                            // If the triplet matches the current five sequence
                            if (repetitions2.get(j)[1] == c){
                                // Add it to the list of correct values
                                indices2.add(repetitions2.remove(j)[0]);
                            }
                        }
                    }
                    // Save the first triplet sequence
                    if (sequence2 == null && indices2.size() < 64){
                        sequence2 = new int[] {index,c};
                    }
                }
            }
            // Add the sequence to repetitions
            if (sequence1 != null){
                repetitions1.add(sequence1);
            }
            if (sequence2 != null){
                repetitions2.add(sequence2);
            }

            // Increase the index
            ++index;
        }

        // Sort the list
        Collections.sort(indices1);
        Collections.sort(indices2);

        int part1 = indices1.get(63);
        int part2 = indices2.get(63);

        // Print the 64th key
        Library.print(part1,part2,name);
    }
}