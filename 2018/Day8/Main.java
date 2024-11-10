import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 8: Memory Maneuver";
    public static void main(String args[]) {
        // Get the input
        int[] input = Library.getIntArray(args," ");

        // Parse the input
        int[] part = parse(input,0);
        System.out.println(part[1] + " " + part[2]);

        // Print the answer
        Library.print(part[1],part[2],name);
    }

    private static int[] parse(int[] input, int index){
        int[] output = new int[3];

        // The number of children
        int[][] children = new int[input[index++]][];
        // The number of metadata entries
        int numMetadata = input[index++];

        // Loop through every child
        for (int i=0; i<children.length; ++i){
            children[i] = parse(input,index);
            index = children[i][0];
            output[1] += children[i][1];
        }

        // Loop through every metadata entry
        for (int i=0; i<numMetadata; ++i){
            output[1] += input[index];
            if (children.length == 0){
                output[2] += input[index];
            }else if (input[index] <= children.length){
                output[2] += children[input[index]-1][2];
            }
            ++index;
        }

        // Return the index
        output[0] = index;

        return output;
    }
}