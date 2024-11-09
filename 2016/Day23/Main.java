import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.Assembunny;

public class Main {
    final private static String name = "Day 23: Safe Cracking";
    public static void main(String args[]) {
        // The program to be run
        Assembunny program = new Assembunny(args);
        program.setRegister('a',7);
        // Get the value in register a
        program.run();
        int part1 = program.getRegister('a');

        // Reset the program with 12 in register a
        program.reset();
        program.setRegister('a',12);
        // Get the value in register a
        program.run();
        int part2 = program.getRegister('a');

        // Print the answer
        Library.print(part1,part2,name);
    }
}