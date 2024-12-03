import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 3: Mull It Over";
    public static void main(String[] args){
        // Take in the input, cleaning newline characters
        String input = Library.getString(args);
        input = input.replace("\n"," ");

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Whether the mul() instruction is enabled
        boolean enabled = true;

        // Loop through each character
        for (int i=0; i<input.length(); ++i){
            // If it starts a mul instruction
            if (input.substring(i).matches("^mul\\(\\d*,\\d*\\).*")){
                // Skip "mul("
                i += 4;
                // Gather the digits of the first number
                String num1 = "";
                while (Character.isDigit(input.charAt(i))){
                    num1 += input.charAt(i);
                    ++i;
                }
                // Skip the comma
                ++i;
                // Gather the digits of the second number
                String num2 = "";
                while (Character.isDigit(input.charAt(i))){
                    num2 += input.charAt(i);
                    ++i;
                }

                // Multiply the two numbers together
                int result = Integer.parseInt(num1) * Integer.parseInt(num2);
                // Add it
                part1 += result;
                // Add it if mul() is enabled
                if (enabled){
                    part2 += result;
                }
            }else if (input.substring(i).matches("do\\(\\).*")){
                // Enable mul()
                enabled = true;
            }else if (input.substring(i).matches("don't\\(\\).*")){
                // Disable mul()
                enabled = false;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}