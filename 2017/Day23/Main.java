import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 23: Coprocessor Conflagration";
    public static void main(String[] args){
        // Get the assembly instructions
        String[][] assembly = Library.getAssembly(args);
        // The array of registers
        long[] registers = new long[8];

        // The answer to the problem
        long part1 = 0;
        // Loop through all instructions until the index drops out
        for (int i=0; i<assembly.length && i >= 0; ++i){
            // Take in the instruction
            String[] line = assembly[i];
            // The first value
            long x;
            // Decide its value
            if (Character.isAlphabetic(line[1].charAt(0))){
                // Register
                x = registers[line[1].charAt(0)-'a'];
            }else{
                // Numeric value
                x = Long.parseLong(line[1]);
            }
            // The second value
            long y = 0;
            // Make sure there is a second value
            if (line.length > 2){
                // Decide its value
                if (Character.isAlphabetic(line[2].charAt(0))){
                    // Register
                    y = registers[line[2].charAt(0)-'a'];
                }else{
                    // Numeric value
                    y = Long.parseLong(line[2]);
                }
            }

            // Perform the instruction
            switch (line[0]){
                // Set a register to a value
                case "set" -> {
                    registers[line[1].charAt(0)-'a'] = y;
                }
                // Subtract a value from a register
                case "sub" -> {
                    registers[line[1].charAt(0)-'a'] -= y;
                }
                // Multiply a value to a register
                case "mul" -> {
                    registers[line[1].charAt(0)-'a'] *= y;
                    ++part1;
                }
                // Jump Not Zero
                case "jnz" -> {
                    // If the value is not equal to zero
                    if (x != 0){
                        // Change the instruction index
                        i += y-1;
                    }
                }
            }
        }

        int num = Integer.parseInt(assembly[0][2]);
        int h = 0;
        for (int b=num*100+100000; b<=num*100+117000; b += 17){
            boolean prime = true;
            for (int d=2; d<b; ++d){
                if (b%d == 0){
                    prime = false;
                    break;
                }
            }
            if (!prime){
                ++h;
            }
        }
        int part2 = h;

        // Print the answer
        Library.print(part1,part2,name);
    }
}