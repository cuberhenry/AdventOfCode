import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 1: Trebuchet?!";
    public static void main(String args[]) {
        String[] input = Library.getStringArray(args,"\n");

        // The sum of the calibration values
        int part1 = 0;
        int part2 = 0;
        // Numbers spelled out
        String[] numbers = {"one","two","three","four","five",
                            "six","seven","eight","nine"};

        // Loop through every line of input
        for (String line : input){
            // The calibration value for the current line
            int first = 0;
            int second = 0;

            // Loop through every character starting at the front
            // until the first digit is found
            for (int i=0; i<line.length() && (first == 0 || second == 0); ++i){
                // If a numeral is found
                if (Character.isDigit(line.charAt(i))){
                    // Set the digit to it
                    if (first == 0){
                        first = 10 * (line.charAt(i)-'0');
                        if (second == 0){
                            second = 10 * (line.charAt(i)-'0');
                        }
                    }
                }
                
                if (second == 0){
                    // Loop through every possible spelled out digit
                    for (int j=0; j<numbers.length; ++j){
                        // If the current character begins a spelled out digit
                        if (line.length()-i >= numbers[j].length()
                            && line.substring(i,i+numbers[j].length()).equals(numbers[j])){
                            // Set the digit to it
                            second = 10 * (j+1);
                            break;
                        }
                    }
                }
            }

            // Loop through every character starting at the end
            // until the second digit is found
            for (int i=line.length()-1; i>=0 && (first%10==0 || second%10==0); --i){
                // If a numeral is found
                if (Character.isDigit(line.charAt(i))){
                    // Set the digit to it
                    if (first % 10 == 0){
                        first += line.charAt(i)-'0';
                        if (second % 10 == 0){
                            second += line.charAt(i)-'0';
                        }
                    }
                }

                if (second % 10 == 0){
                    // Loop through every possible spelled out digit
                    for (int j=0; j<numbers.length; ++j){
                        // If the current character begins a spelled out digit
                        if (line.length()-i >= numbers[j].length()
                            && line.substring(i,i+numbers[j].length()).equals(numbers[j])){
                            // Set the digit to it
                            second += j+1;
                            break;
                        }
                    }
                }
            }

            // Add the calibration value to the total
            part1 += first;
            part2 += second;
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}