import com.aoc.mylibrary.Library;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 18: Duet";
    
    private static class Duet {
        // The array of registers
        private long[] registers = new long[26];
        // The array of instructions
        private String[][] instructions;
        // The index of the current instruction
        private int index;

        // Instructions constructor
        public Duet(String[][] i){
            instructions = i;
        }

        // Constructor with program ID
        public Duet(String[][] i, int programID){
            this(i);
            registers[15] = programID;
        }

        // Runs the program until it's waiting on input
        public ArrayList<Long> run(ArrayList<Long> input){
            ArrayList<Long> output = new ArrayList<>();
            
            // Loop through all instructions until the index drops out
            while (true){
                // Take in the instruction
                String[] instruction = instructions[index];

                // Perform the instruction
                switch (instruction[0]){
                    // Sound / Send
                    case "snd" -> {
                        output.add(getValue(instruction[1]));
                    }
                    // Set a register to a value
                    case "set" -> {
                        registers[instruction[1].charAt(0)-'a'] = getValue(instruction[2]);
                    }
                    // Add a value to a register
                    case "add" -> {
                        registers[instruction[1].charAt(0)-'a']
                            = getValue(instruction[1])
                            + getValue(instruction[2]);
                    }
                    // Multiply a value to a register
                    case "mul" -> {
                        registers[instruction[1].charAt(0)-'a']
                            = getValue(instruction[1])
                            * getValue(instruction[2]);
                    }
                    // Modulo a value from a register
                    case "mod" -> {
                        registers[instruction[1].charAt(0)-'a']
                            = getValue(instruction[1])
                            % getValue(instruction[2]);
                    }
                    // Recover / Receive
                    case "rcv" -> {
                        if (input.isEmpty()){
                            return output;
                        }
                        registers[instruction[1].charAt(0)-'a'] = input.removeFirst();
                    }
                    // Jump Greater than Zero
                    case "jgz" -> {
                        if (getValue(instruction[1]) > 0){
                            index += getValue(instruction[2]) - 1;
                        }
                    }
                }
                ++index;
            }
        }

        // Gets either the long value or the value in the register
        private long getValue(String s){
            try {
                return Long.parseLong(s);
            }catch (Exception e){}
            return registers[s.charAt(0)-'a'];
        }
    }
    
    public static void main(String args[]) {
        // The list of instructions
        String[][] assembly = Library.getAssembly(args);
        // The program with the misunderstood instructions
        Duet sounds = new Duet(assembly);
        // The last sound played
        long part1 = sounds.run(new ArrayList<>()).getLast();

        // Create two programs that communicate with each other
        Duet first = new Duet(assembly);
        Duet second = new Duet(assembly,1);
        
        // Start each program
        ArrayList<Long> signals = first.run(new ArrayList<>());
        signals = second.run(signals);
        // The number of times program 1 sent a value
        int part2 = signals.size();

        // Run until a deadlock happens
        while (!signals.isEmpty()){
            signals = first.run(signals);
            signals = second.run(signals);
            part2 += signals.size();
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}