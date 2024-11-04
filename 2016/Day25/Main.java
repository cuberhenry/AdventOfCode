import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.Assembunny;
import com.aoc.mylibrary.ArrayState;
import java.util.Scanner;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 25: Clock Signal";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The program to be run
        Assembunny program = new Assembunny(sc);

        // The answer to the problem
        int part1 = -1;

        // Start at 1, increasing until the input is found
        for (int number = 1; part1 == -1; ++number){
            program.reset();
            program.setRegister('a',number);
            // History to find oscillation
            HashSet<ArrayState> history = new HashSet<>();
            // The expected next output
            boolean high = false;

            // Continue until a signal is confirmed or broken
            while (true){
                // Get the next output
                int out = program.run();
                // If it's expected
                if (high && out == 1 || !high && out == 0){
                    // The state of registers
                    ArrayState state = new ArrayState(program.getRegisters().clone());
                    // Loop found, guaranteed clock signal
                    if (history.contains(state)){
                        part1 = number;
                        break;
                    }
                    // Add the state
                    history.add(state);
                    // Look for alternate clock
                    high = !high;
                }else{
                    break;
                }
            }
        }

        // Part 2 doesn't require code
        String part2 = "Transmit the Signal";

        // Print the answer
        Library.print(part1,part2,name);
    }
}