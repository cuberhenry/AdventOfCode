import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 3: No Matter How You Slice It";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        // The x, y, width, and height of each claim
        ArrayList<int[]> claims = new ArrayList<>();
        // The number of claims for each square of fabric
        int[][] fabric = new int[1000][1000];

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Repeat for each claim
        while (sc.hasNext()){
            // Collect the information from the line of input
            int[] claim = Library.intSplit(sc.nextLine().substring(1)," @ |,|: |x");

            // Increase the number of claims for each square
            for (int i=claim[1]; i<claim[1]+claim[3]; ++i){
                for (int j=claim[2]; j<claim[2]+claim[4]; ++j){
                    if (fabric[i][j] == 1){
                        ++part1;
                    }
                    ++fabric[i][j];
                }
            }

            // Add the claim's info
            claims.add(claim);
        }

        // Loop through every claim
        for (int i=0; i<claims.size(); ++i){
            int[] claim = claims.get(i);
            boolean found = true;
            // Loop through every square in the claim
            for (int j=claim[1]; j<claim[1]+claim[3] && found; ++j){
                for (int k=claim[2]; k<claim[2]+claim[4]; ++k){
                    // If claimed more than once, break out
                    if (fabric[j][k] > 1){
                        found = false;
                        break;
                    }
                }
            }

            // If this is the claim, print the ID
            if (found){
                part2 = claim[0];
                break;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}