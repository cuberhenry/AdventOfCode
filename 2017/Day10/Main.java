import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 10: Knot Hash";
    public static void main(String args[]) {
        String input = Library.getString(args);

        // Collect the list of lengths
        int[] lengths = Library.intSplit(input,",");
        // The list of numbers
        int[] list = new int[256];
        for (int i=0; i<list.length; ++i){
            list[i] = i;
        }

        // Perform the set of rotations
        Library.knotHashRotation(list, lengths, 0, 0);

        // The answer to the problem
        int part1 = list[0] * list[1];
        String part2 = Library.knotHash(input);
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}