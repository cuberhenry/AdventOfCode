import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    final private static String name = "Day 17: Chronospatial Computer";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        String part1 = "";
        long part2 = 0;

        // The three registers
        long[] registers = new long[3];
        // Initialize the values
        for (int i=0; i<3; ++i){
            registers[i] = Long.parseLong(sc.nextLine().substring(12));
        }
        sc.nextLine();
        // Take in the program
        int[] program = Library.intSplit(sc.nextLine().substring(9),",");

        // Run the program
        for (int i=0; i<program.length; i+=2){
            // Find the parameter
            int literal = program[i+1];
            long combo = literal;
            if (literal > 3 && literal < 7){
                combo = registers[literal - 4];
            }
            // Perform the operation
            switch(program[i]){
                // adv, A = A / 2^combo
                case 0 -> registers[0] >>= combo;
                // bxl, B = B ^ literal
                case 1 -> registers[1] ^= literal;
                // bst, B = combo % 8
                case 2 -> registers[1] = combo % 8;
                // jnz, if A != 0 then i = literal
                case 3 -> {
                    if (registers[0] != 0){
                        i = literal - 2;
                    }
                }
                // bxc, B = B ^ C
                case 4 -> registers[1] ^= registers[2];
                // out, output combo % 8
                case 5 -> {
                    // Add to the output string
                    if (!part1.isBlank()){
                        part1 += ",";
                    }
                    part1 += combo % 8;
                }
                // bdv, B = A / 2^combo
                case 6 -> registers[1] = registers[0] >> combo;
                // cdv, C = A / 2^combo
                case 7 -> registers[2] = registers[0] >> combo;
            }
        }

        // The list of possible starting values for register A
        ArrayList<Long> possibilities = new ArrayList<>();
        // Can only end at 0
        possibilities.add(0L);
        registers = new long[3];

        // Loop through each target output backwards
        for (int i=program.length-1; i>=0; --i){
            ArrayList<Long> next = new ArrayList<>();
            // Loop through each possible previous value
            for (long value : possibilities){
                // Loop through each possible new value
                for (int j=0; j<8; ++j){
                    // Use the new value
                    registers = new long[3];
                    long testA = value * 8 + j;
                    registers[0] = testA;
                    
                    // Run this loop of the program
                    for (int k=0; k<program.length; k+=2){
                        // Find the parameter
                        int literal = program[k+1];
                        long combo = literal;
                        if (literal > 3 && literal < 7){
                            combo = registers[literal - 4];
                        }
                        // Perform the operation
                        switch(program[k]){
                            // adv, A = A / 2^combo
                            case 0 -> registers[0] >>= combo;
                            // bxl, B = B ^ literal
                            case 1 -> registers[1] ^= literal;
                            // bst, B = combo % 8
                            case 2 -> registers[1] = combo % 8;
                            // Don't jump, only one iteration
                            case 3 -> {}
                            // bxc, B = B ^ C
                            case 4 -> registers[1] ^= registers[2];
                            // out, output combo % 8
                            case 5 -> {
                                // If the output is as expected, add the possible value
                                int output = (int)(combo % 8);
                                if (output == program[i]){
                                    next.add(testA);
                                }
                            }
                            // bdv, B = A / 2^combo
                            case 6 -> registers[1] = registers[0] >> combo;
                            // cdv, C = A / 2^combo
                            case 7 -> registers[2] = registers[0] >> combo;
                        }
                    }
                }
            }
            possibilities = next;
        }

        // Find the minimum possible value
        Collections.sort(possibilities);
        part2 = possibilities.getFirst();

        // Print the answer
        Library.print(part1,part2,name);
    }
}