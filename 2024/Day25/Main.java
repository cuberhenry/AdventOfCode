import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 25: Code Chronicle";
    public static void main(String[] args){
        // Take in the locks and keys
        char[][][] items = Library.getCharTensor(args,"\n\n");

        // The answer to the problem
        long part1 = 0;

        // Loop through each unique pair of items
        for (int i=0; i<items.length; ++i){
            for (int j=i+1; j<items.length; ++j){
                boolean fits = true;
                // Loop through each character
                for (int k=0; k<items[i].length && fits; ++k){
                    for (int l=0; l<items[i][k].length; ++l){
                        // If both are full, they overlap and don't fit
                        if (items[i][k][l] == '#' && items[j][k][l] == '#'){
                            fits = false;
                            break;
                        }
                    }
                }
                // Add if it fits
                if (fits){
                    ++part1;
                }
            }
        }

        // Part 2 doesn't require code
        String part2 = "Deliver The Chronicle";

        // Print the answer
        Library.print(part1,part2,name);
    }
}