import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 16: Dragon Checksum";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // Get the input
        char[] input = sc.next().toCharArray();

        // A stored hash map of dragon numbers
        HashMap<Integer,Boolean> dragon = new HashMap<>();

        // Find the answer
        String part1 = dragonChecksum(input,272,dragon);
        String part2 = dragonChecksum(input,35651584,dragon);

        // Print the answer
        Library.print(part1,part2,name);
    }

    // Finds the checksum with the given input and length
    private static String dragonChecksum(char[] start, int size, HashMap<Integer,Boolean> dragon){
        // The number of bits represented by a single checksum bit
        int groupSize = (int)Math.pow(2,Integer.numberOfTrailingZeros(size));
        // The number of checksum bits
        int numGroups = size / groupSize;
        
        // The checksum
        String checksum = "";
        // The size of one repeating chunk (start dragonNumber(i) start' dragonNumber(i+1))
        int chunkSize = start.length * 2 + 2;
        // Loop through each group
        for (int i=0; i<numGroups; ++i){
            // The checksum value
            boolean even = true;
            // The index within the group
            int index = 0;
            // Continue until the start of chunk is found
            while (index < groupSize && (i*groupSize + index) % chunkSize != 0){
                // Include the result of this character
                even ^= dragon(start,i*groupSize + index,dragon);
                ++index;
            }
            // Calculate chunks until another chunk exits the group
            while (index + chunkSize <= groupSize){
                // If the input is an odd length, even toggles
                even ^= start.length % 2 == 1;
                // Move to the first dragon number in the chunk
                index += start.length;
                // Calculate it
                even ^= dragonNumber((i*groupSize + index+1) / (start.length+1),dragon);
                // Move to the other dragon number
                index += chunkSize / 2;
                // Calculate it
                even ^= dragonNumber((i*groupSize + index+1) / (start.length+1),dragon);
                // Proceed to the next chunk
                ++index;
            }
            // Continue until the group is complete
            while (index < groupSize){
                // Include the result of this character
                even ^= dragon(start,i*groupSize + index,dragon);
                ++index;
            }
            // Add the checksum value
            checksum += even ? '1' : '0';
        }
        return checksum;
    }

    // Finds the value in the total dragon number at the given index
    private static boolean dragon(char[] start, int index, HashMap<Integer,Boolean> dragon){
        // Get the chunk size
        int chunkSize = start.length * 2 + 2;
        // The index within the chunk
        int mod = index % chunkSize;
        // It's within start
        if (mod < start.length){
            return start[mod] == '1';
        }
        // It's within start'
        if (mod > start.length && mod < chunkSize - 1){
            // Reverse and invert
            return start[chunkSize - 2 - mod] == '0';
        }
        // It's a dragon number
        return dragonNumber((index+1) * 2 / chunkSize, dragon);
    }

    // Gets the value of the dragon number with the given 1-indexed index
    private static boolean dragonNumber(int dragonIndex, HashMap<Integer,Boolean> dragon){
        // Dragon numbers at powers of two are always 0
        if (Integer.bitCount(dragonIndex) == 1){
            return false;
        }
        // If we already have the answer
        if (dragon.containsKey(dragonIndex)){
            return dragon.get(dragonIndex);
        }
        // The power of two just less than this
        int prevPow = (int)Math.pow(2,31 - Integer.numberOfLeadingZeros(dragonIndex));
        // The opposite of the dragon number flipped over prevPow
        boolean dragonNumber = !dragonNumber(prevPow - (dragonIndex - prevPow), dragon);
        dragon.put(dragonIndex,dragonNumber);
        // Return the opposite of the dragon number flipped over prevPow
        return dragonNumber;
    }
}