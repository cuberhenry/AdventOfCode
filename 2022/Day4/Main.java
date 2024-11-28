import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 4: Camp Cleanup";
    public static void main(String args[]) {
        // All cleaning assignments
        String[] assignments = Library.getStringArray(args,"\n");
        // Total number of relevant elf pairs
        int part1 = 0;
        int part2 = 0;
        
        // Loop through all lines of input
        for (String assignment : assignments){
            // Take in the four numbers, ignoring the filler characters
            int[] nums = Library.intSplit(assignment,"-|,");

            // If they overlap
            if (nums[0] <= nums[3] && nums[2] <= nums[1]){
                ++part2;

                // If one complete envelopes the other
                if (nums[0] <= nums[2] && nums[1] >= nums[3]
                    || nums[2] <= nums[0] && nums[3] >= nums[1]){
                    ++part1;
                }
            }
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}