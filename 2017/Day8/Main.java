import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 8: I Heard You Like Registers";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // The collection of all registers and their values
        HashMap<String,Integer> registers = new HashMap<>();
        // The maximum value
        int part2 = Integer.MIN_VALUE;
        // For every instruction
        while (sc.hasNextLine()){
            String[] instruction = sc.nextLine().split(" ");
            int compare = registers.containsKey(instruction[4]) ? registers.get(instruction[4]) : 0;
            int value = Integer.parseInt(instruction[6]);

            // If the condition is true
            if (switch (instruction[5]){
                case ">" -> compare > value;
                case "<" -> compare < value;
                case ">=" -> compare >= value;
                case "<=" -> compare <= value;
                case "==" -> compare == value;
                default -> compare != value;
            }){
                // The starting value of the register
                int starting = registers.containsKey(instruction[0]) ? registers.get(instruction[0]) : 0;
                // Collect the value of the change
                value = Integer.parseInt(instruction[2]);
                // Reverse it if the instruction is to decrease
                if (instruction[1].equals("dec")){
                    value *= -1;
                }
                // Update the register's value
                registers.put(instruction[0],starting+value);

                // Compare and update the current register to the max
                part2 = Math.max(part2,registers.get(instruction[0]));
            }
        }
        
        // Find the maximum ending value
        int part1 = 0;
        for (int i : registers.values()){
            // Compare and update the current register to the max
            part1 = Math.max(part1,i);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}