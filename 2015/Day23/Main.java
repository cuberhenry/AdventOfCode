import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 23: Opening the Turing Lock";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // All of the instructions
        ArrayList<String[]> instructions = new ArrayList<>();
        // Take in all of the instructions
        while (sc.hasNext()){
            instructions.add(sc.nextLine().split(", | "));
        }
        
        // Run the program
        long part1 = compute(instructions,0);
        long part2 = compute(instructions,1);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static long compute(ArrayList<String[]> instructions, long input){
        // The starting register values
        long[] registers = {input,0};

        // Loop until an instruction that isn't specified is looked for
        for (int index = 0; index >= 0 && index < instructions.size(); ++index){
            // Grab the instruction
            String[] instruction = instructions.get(index);
            // Switch based on the instruction
            switch (instruction[0]){
                // Half the value of the specified register
                case "hlf" -> {
                    registers[instruction[1].charAt(0) - 'a'] /= 2;
                }
                // Triple the value of the specified register
                case "tpl" -> {
                    registers[instruction[1].charAt(0) - 'a'] *= 3;
                }
                // Increment the value of the specified register
                case "inc" -> {
                    ++registers[instruction[1].charAt(0) - 'a'];
                }
                // Jump to the specified offset
                case "jmp" -> {
                    index += Integer.parseInt(instruction[1])-1;
                }
                // Jump to the specified offset if the specified
                // register's value is even
                case "jie" -> {
                    if (registers[instruction[1].charAt(0)-'a'] % 2 == 0){
                        index += Integer.parseInt(instruction[2])-1;
                    }
                }
                // Jump to the specified offset if the specified
                // register's value is 1
                case "jio" -> {
                    if (registers[instruction[1].charAt(0)-'a'] == 1){
                        index += Integer.parseInt(instruction[2])-1;
                    }
                }
            }
        }

        // Return the value of register b
        return registers[1];
    }
}