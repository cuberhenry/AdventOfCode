import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 23: Opening the Turing Lock";
    public static void main(String args[]) {
        // All of the instructions
        String[][] assembly = Library.getAssembly(args);
        
        // Run the program
        long part1 = compute(assembly,0);
        long part2 = compute(assembly,1);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static long compute(String[][] instructions, long input){
        // The starting register values
        long[] registers = {input,0};

        // Loop until an instruction that isn't specified is looked for
        for (int index = 0; index >= 0 && index < instructions.length; ++index){
            // Grab the instruction
            String[] instruction = instructions[index];
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