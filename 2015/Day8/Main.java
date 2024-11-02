import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 8: Matchsticks";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The number of characters in the input
        int total = 0;
        // The number of characters of the encoding
        int part1 = 0;
        int part2 = 0;
        // The number of characters to ignore
        int ignore = 0;

        // Take in each line of input
        while (sc.hasNext()){
            // Take in the next line
            String line = sc.nextLine();
            // Increase total by the number of characters of this line
            total += line.length();

            // Add six for the quotation marks
            part2 += 6;

            // Loop through every inner character
            for (int i=1; i<line.length()-1; ++i){
                // Increase the size
                if (ignore == 0){
                    ++part1;

                    // If there's an escape character
                    if (line.charAt(i) == '\\'){
                        // An x indicates skipping more characters
                        if (line.charAt(i+1) == 'x'){
                            ignore = 3;
                        }else{
                            ignore = 1;
                        }
                    }
                }else{
                    --ignore;
                }

                // Encode backslashes and quotations marks
                ++part2;
                if (line.charAt(i) == '\\' || line.charAt(i) == '"'){
                    ++part2;
                }
            }
        }

        // Print the answer
        Library.print(total - part1, part2 - total, name);
    }
}