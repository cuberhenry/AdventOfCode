import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    final private static String name = "Day 24: Crossed Wires";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        // The initial values of some wires
        HashMap<String,Boolean> values = new HashMap<>();
        // Take in the first line
        String line = sc.nextLine();
        // While there are initial values
        while (!line.isBlank()){
            // Add the value
            String[] split = line.split(": ");
            values.put(split[0],split[1].equals("1"));
            line = sc.nextLine();
        }

        // The gates to resolve
        ArrayList<String[]> gates = new ArrayList<>();
        // Take in all the gates
        while (sc.hasNext()){
            String[] gate = sc.nextLine().split(" ");
            // Make input gates x / y
            if (gate[0].charAt(0) == 'y'){
                String helper = gate[0];
                gate[0] = gate[2];
                gate[2] = helper;
            }
            gates.add(gate);
        }

        // Get the outputs for the gates that have x and y inputs
        String[][] info = new String[45][2];
        // Loop through each gate
        for (String[] gate : gates){
            // If it has x and y inputs
            if (gate[0].charAt(0) == 'x'){
                // Add the output to the corresponding input
                int index = Integer.parseInt(gate[0].substring(1));
                if (gate[1].equals("XOR")){
                    info[index][0] = gate[4];
                }else{
                    info[index][1] = gate[4];
                }
            }
        }

        // The output gates that are swapped
        ArrayList<String> wrong = new ArrayList<>();
        // The carry wire from the previous one
        String carry = info[0][1];

        // Ideal full adder:
        // x__ XOR y__ -> xor
        // x__ AND y__ -> and
        // xor XOR carry -> z__
        // xor AND carry -> d__
        // and OR d__ -> carry+1

        // Loop through each item
        for (int i=1; i<info.length; ++i){
            // The intermediate output gates
            String xor = "";
            String and = "";
            // Whether a swap has been found in this bit
            boolean issue = false;
            // Loop through each gate
            for (String[] gate : gates){
                // Find the two gates with carry as an input
                if (gate[0].equals(carry) || gate[2].equals(carry)){
                    // Look for the two types of gates
                    if (gate[1].equals("XOR")){
                        // Get the other gate
                        String a = gate[0];
                        if (a.equals(carry)){
                            a = gate[2];
                        }
                        // Make sure it matches the other expected output
                        if (!a.equals(info[i][0])){
                            issue = true;
                            wrong.add(info[i][0]);
                            wrong.add(a);
                        }
                        xor = gate[4];
                    }else if (gate[1].equals("AND")){
                        // If xor's inputs weren't an issue, and's won't be
                        and = gate[4];
                    }
                }
            }

            // Make sure and's output is the sum
            if (and.charAt(0) == 'z'){
                String helper = and;
                // This check has to happen separately so and and xor can swap
                and = xor;
                xor = helper;
                wrong.add(and);
                wrong.add(xor);
                issue = true;
            }

            // Check if the sum was swapped with something else
            if (!issue && xor.charAt(0) != 'z'){
                wrong.add(xor);
                wrong.add("z" + (i < 10 ? "0" : "") + i);
                issue = true;
            }

            // Loop through each gate to find the or for the carry out
            for (String[] gate : gates){
                if (gate[0].equals(and) || gate[2].equals(and)){
                    carry = gate[4];
                    break;
                }
            }

            // If carry was the one swapped with sum, update carry to the correct wire
            if (carry.charAt(0) == 'z'){
                carry = xor;
            }
        }

        // Continue until all gates have been resolved
        while (gates.size() > 0){
            // Loop through each gate backwards
            for (int i=gates.size()-1; i>=0; --i){
                // Check if both inputs have been resolved
                if (values.containsKey(gates.get(i)[0]) && values.containsKey(gates.get(i)[2])){
                    // Resolve the output
                    values.put(gates.get(i)[4],switch(gates.get(i)[1]){
                        case "AND" -> values.get(gates.get(i)[0]) && values.get(gates.get(i)[2]);
                        case "OR" -> values.get(gates.get(i)[0]) || values.get(gates.get(i)[2]);
                        default -> values.get(gates.get(i)[0]) ^ values.get(gates.get(i)[2]);
                    });
                    gates.remove(i);
                }
            }
        }

        // Find the binary output from all of the z__ gates
        String binary = "";
        for (int i=0;; ++i){
            String key = "z" + (i < 10 ? "0" : "") + i;
            // When the z__ runs out, the number is done
            if (!values.containsKey(key)){
                break;
            }
            binary = (values.get(key) ? "1" : "0") + binary;
        }

        // Part 1 finds the binary number output
        long part1 = Long.parseLong(binary,2);

        // Part 2 finds the eight mismatched output wires
        String part2 = "";
        Collections.sort(wrong);
        for (String wire : wrong){
            part2 += "," + wire;
        }
        part2 = part2.substring(1);

        // Print the answer
        Library.print(part1,part2,name);
    }
}