import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 3: Lobby";
    public static void main(String[] args){
        String[] input = Library.getStringArray(args);

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // Loop through each bank
        for (String line : input){
            // Create a list of indexes for both parts
            int[][] indexes = new int[2][];
            indexes[0] = new int[2];
            indexes[1] = new int[12];

            // Loop through each part
            for (int[] part : indexes){
                // Loop through each battery to turn on
                for (int i=0; i<part.length; ++i){
                    int index = 0;
                    // Make sure each index is after the previous one
                    if (i > 0){
                        index = part[i-1]+1;
                        part[i] = index;
                    }

                    // Find the first instance of the largest digit between the previous index and
                    // the last valid position for each digit, to ensure a 2/12-digit number
                    for (; index<line.length()-(part.length-i-1); ++index){
                        if (line.charAt(index) > line.charAt(part[i])){
                            part[i] = index;
                        }
                    }
                }
            }

            // Add the new joltage to the total power
            part1 += Integer.parseInt(line.charAt(indexes[0][0]) + "" + line.charAt(indexes[0][1]));
            String p2 = "";
            for (int index : indexes[1]){
                p2 += line.charAt(index);
            }
            part2 += Long.parseLong(p2);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}