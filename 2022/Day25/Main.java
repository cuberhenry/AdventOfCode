import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 25: Full of Hot Air";
    public static void main(String args[]) {
        // Take in all the input
        String[] numbers = Library.getStringArray(args,"\n");

        // The sum of all the numbers
        long total = 0;

        // For each number
        for (String number : numbers){
            // The power of the digit
            long power = 1;
            // Work from least significant to most significant digit
            for (int j=number.length()-1; j>=0; --j){
                // The value at this digit
                int num = switch (number.charAt(j)) {
                    case '=' -> -2;
                    case '-' -> -1;
                    default -> Integer.parseInt(number.substring(j,j+1));
                };
                // Add the amount to the total
                total += power * num;
                // Increase the power for the next digit
                power *= 5;
            }
        }
        
        // The result to be printed
        String part1 = "";
        // Until the entire number has been represented
        while (total != 0){
            // If the number requires a minus
            if (total % 5 == 4){
                part1 = "-" + part1;
                // Increase the total for the integer division
                ++total;
            // If the number requires a double-minus
            }else if (total % 5 == 3){
                part1 = "=" + part1;
                // Increase the total for the integer division
                total += 2;
            // Normal base10 digit
            }else{
                part1 = (total % 5) + part1;
            }
            // Integer division
            total /= 5;
        }

        // Part 2 requires no code
        String part2 = "Start The Blender";
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}