import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 21: Chronal Conversion";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The program to be executed
        ArrayList<String[]> program = new ArrayList<>();
        // The instruction pointer
        int ip = Integer.parseInt(sc.nextLine().split(" ")[1]);
        // Take in all the instructions
        while (sc.hasNext()){
            program.add(sc.nextLine().split(" "));
        }

        // The registers during the program
        int[] regs = new int[6];
        // The reg from which to find the answer
        int reg = Integer.parseInt(program.get(28)[1]);

        // History for finding repeats
        HashSet<Integer> history = new HashSet<>();

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Take in every line of the program
        while (regs[ip] < program.size()){
            // Take in the operation as ints
            String[] split = program.get(regs[ip]);
            int[] opp = new int[4];
            for (int i=1; i<4; ++i){
                opp[i] = Integer.parseInt(split[i]);
            }

            // The only line that uses register 0, to decide whether to quit
            if (regs[ip] == 28){
                if (part1 == 0){
                    part1 = regs[reg];
                }

                if (history.contains(regs[reg])){
                    break;
                }
                history.add(regs[reg]);
                part2 = regs[reg];
            }

            // Perform the given operation
            // O A B C
            // x represents a value rather than a register
            switch(split[0]){
                // C <- A + B
                case "addr" -> regs[opp[3]] = regs[opp[1]] + regs[opp[2]];
                // C <- A + x
                case "addi" -> regs[opp[3]] = regs[opp[1]] + opp[2];
                // C <- A * B
                case "mulr" -> regs[opp[3]] = regs[opp[1]] * regs[opp[2]];
                // C <- A * x
                case "muli" -> regs[opp[3]] = regs[opp[1]] * opp[2];
                // C <- A & B
                case "banr" -> regs[opp[3]] = (regs[opp[1]] & regs[opp[2]]);
                // C <- A & x
                case "bani" -> regs[opp[3]] = (regs[opp[1]] & opp[2]);
                // C <- A | B
                case "borr" -> regs[opp[3]] = (regs[opp[1]] | regs[opp[2]]);
                // C <- A | x
                case "bori" -> regs[opp[3]] = (regs[opp[1]] | opp[2]);
                // C <- A
                case "setr" -> regs[opp[3]] = regs[opp[1]];
                // C <- x
                case "seti" -> regs[opp[3]] = opp[1];
                // C <- x > B
                case "gtir" -> regs[opp[3]] = opp[1] > regs[opp[2]] ? 1 : 0;
                // C <- A > x
                case "gtri" -> regs[opp[3]] = regs[opp[1]] > opp[2] ? 1 : 0;
                // C <- A > B
                case "gtrr" -> regs[opp[3]] = regs[opp[1]] > regs[opp[2]] ? 1 : 0;
                // C <- x == B
                case "eqir" -> regs[opp[3]] = opp[1] == regs[opp[2]] ? 1 : 0;
                // C <- A == x
                case "eqri" -> regs[opp[3]] = regs[opp[1]] == opp[2] ? 1 : 0;
                // C <- A == B
                case "eqrr" -> regs[opp[3]] = regs[opp[1]] == regs[opp[2]] ? 1 : 0;
            }

            // Increment the instruction pointer
            ++regs[ip];
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}