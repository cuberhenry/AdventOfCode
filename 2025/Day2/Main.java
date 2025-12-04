import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 2: Gift Shop";
    public static void main(String[] args){
        long[][] sc = Library.getLongMatrix(args,",","-");

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // Loop through each range
        for (long[] range : sc){
            // Loop through every number in the range
            for (long i=range[0]; i<=range[1]; ++i){
                // Get the number as a string
                String str = i + "";

                // Check if the string has any repeats
                if (str.matches("^(\\d+)\\1+$")){
                    // Part 1 only finds the IDs that repeat exactly twice
                    if (str.matches("^(\\d+)\\1$")){
                        part1 += i;
                    }

                    // Part 2 finds the IDs that repeat any number of times
                    part2 += i;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}