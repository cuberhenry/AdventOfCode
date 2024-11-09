import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 1: Inverse Captcha";
    public static void main(String args[]) {
        // Get the input
        String input = Library.getString(args);
        int length = input.length();
        // The value to be returned
        int part1 = 0;
        int part2 = 0;

        // Loop through every digit in the input
        for (int i=0; i<length; ++i){
            // Adds to the total digits that match the next digit
            if (input.charAt(i) == input.charAt((i+1)%length)){
                part1 += input.charAt(i)-'0';
            }

            // Add to the total digits that match the opposite digit
            if (input.charAt(i) == input.charAt((i+length/2)%length)){
                part2 += input.charAt(i)-'0';
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}