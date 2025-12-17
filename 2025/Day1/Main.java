import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 1: Secret Entrance";
    public static void main(String[] args){
        String[] input = Library.getStringArray(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // The number the dial is currently pointing at
        int dial = 50;

        for (String line : input){
            // Get the direction to turn the dial
            boolean left = line.charAt(0) == 'L';
            // Get the number to turn in that direction
            int num = Integer.parseInt(line.substring(1));

            // Part 1 is the number of times the dial ends up on 0
            // Part 2 is the number of times the dial points to 0
            // Add full rotations as pointing to 0 once
            part2 += num / 100;
            // Remove full rotations
            num %= 100;
            // Set the number negative if turning left
            if (left){
                num *= -1;
            }

            // Check if the dial will pass 0
            if ((num < 0 && num * -1 >= dial && dial != 0) || num > 0 && num + dial >= 100){
                ++part2;
            }

            // Move the dial
            dial = dial + num + 100;
            dial %= 100;

            // Check if the dial is pointing to 0
            if (dial == 0){
                ++part1;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}