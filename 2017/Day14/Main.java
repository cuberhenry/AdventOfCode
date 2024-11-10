import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.DisjointSet;
import com.aoc.mylibrary.ArrayState;
import java.math.BigInteger;

public class Main {
    final private static String name = "Day 14: Disk Defragmentation";
    public static void main(String args[]) {
        // The string used to create the hash
        String key = Library.getString(args);

        // The number of used squares
        int part1 = 0;
        // The regions in the disk
        DisjointSet<ArrayState> regions = new DisjointSet<>();

        // Loop through every row
        for (int i=0; i<128; ++i){
            // Collect the input as a string
            String hash = Library.knotHash(key + "-" + i);
            // Convert the hexadecimal to binary
            char[] row = Library.pad(new BigInteger(hash,16).toString(2),'0',128,true).toCharArray();

            // Loop through each bit
            for (int j=0; j<row.length; ++j){
                // If the bit is active
                if (row[j] == '1'){
                    ++part1;
                    // The current position
                    ArrayState pos = new ArrayState(new int[] {j,i});
                    regions.add(pos);
                    
                    // Check left and up for connected sets
                    ArrayState left = new ArrayState(new int[] {j-1,i});
                    if (regions.contains(left)){
                        regions.union(pos,left);
                    }
                    ArrayState up = new ArrayState(new int[] {j,i-1});
                    if (regions.contains(up)){
                        regions.union(pos,up);
                    }
                }
            }
        }

        // The number of regions of used squares
        int part2 = regions.size();

        // Print the answer
        Library.print(part1,part2,name);
    }
}