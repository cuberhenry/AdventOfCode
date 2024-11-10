import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 19: Go With The Flow";
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

        // The answer to the problem
        long part1 = run(program,ip,false);
        long part2 = run(program,ip,true);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static long run(ArrayList<String[]> program, int ip, boolean part2){
        // The registers during the program
        long[] regs = new long[6];
        if (part2){
            regs[0] = 1;
        }

        // Take in every line of the sample program
        while (regs[ip] < program.size()){
            // Take in the operation as ints
            String[] split = program.get((int)regs[ip]);
            int[] opp = new int[4];
            for (int i=1; i<4; ++i){
                opp[i] = Integer.parseInt(split[i]);
            }

            if (part2){
                // If the registers have been initialized
                if (regs[ip] == 1){
                    // The index of the register that bounds the search
                    int bound = Integer.parseInt(program.get(4)[2]);
                    // Loop through every possible factor of the bound register
                    for (int i=1; i<= regs[bound]; ++i){
                        if (regs[bound] % i == 0){
                            regs[0] += i;
                        }
                    }
    
                    // That's all the code does, so break out
                    return regs[0];
                }
            }

            // Perform the given operation
            // O A B C
            // x represents a value rather than a register
            switch(split[0]){
                // C <- A + B
                case "addr" -> {regs[opp[3]] = regs[opp[1]] + regs[opp[2]];}
                // C <- A + x
                case "addi" -> {regs[opp[3]] = regs[opp[1]] + opp[2];}
                // C <- A * B
                case "mulr" -> {regs[opp[3]] = regs[opp[1]] * regs[opp[2]];}
                // C <- A * x
                case "muli" -> {regs[opp[3]] = regs[opp[1]] * opp[2];}
                // C <- A & B
                case "banr" -> {regs[opp[3]] = (regs[opp[1]] & regs[opp[2]]);}
                // C <- A & x
                case "bani" -> {regs[opp[3]] = (regs[opp[1]] & opp[2]);}
                // C <- A | B
                case "borr" -> {regs[opp[3]] = (regs[opp[1]] | regs[opp[2]]);}
                // C <- A | x
                case "bori" -> {regs[opp[3]] = (regs[opp[1]] | opp[2]);}
                // C <- A
                case "setr" -> {regs[opp[3]] = regs[opp[1]];}
                // C <- x
                case "seti" -> {regs[opp[3]] = opp[1];}
                // C <- x > B
                case "gtir" -> {regs[opp[3]] = opp[1] > regs[opp[2]] ? 1 : 0;}
                // C <- A > x
                case "gtri" -> {regs[opp[3]] = regs[opp[1]] > opp[2] ? 1 : 0;}
                // C <- A > B
                case "gtrr" -> {regs[opp[3]] = regs[opp[1]] > regs[opp[2]] ? 1 : 0;}
                // C <- x == B
                case "eqir" -> {regs[opp[3]] = opp[1] == regs[opp[2]] ? 1 : 0;}
                // C <- A == x
                case "eqri" -> {regs[opp[3]] = regs[opp[1]] == opp[2] ? 1 : 0;}
                // C <- A == B
                case "eqrr" -> {regs[opp[3]] = regs[opp[1]] == regs[opp[2]] ? 1 : 0;}
            }

            // Increment the instruction pointer
            ++regs[ip];
        }

        return regs[0];
    }
}