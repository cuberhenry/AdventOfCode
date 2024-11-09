import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.Assembunny;

public class Main {
    final private static String name = "Day 12: Leonardo's Monorail";
    public static void main(String args[]) {
        // The program to be run
        Assembunny program = new Assembunny(args);
        // Get the value in register a
        program.run();
        int part1 = program.getRegister('a');

        // Reset the program with 2 in register c
        program.reset();
        program.setRegister('c',1);
        // Get the value in register a
        program.run();
        int part2 = program.getRegister('a');

        // Print the answer
        Library.print(part1,part2,name);
    }
}