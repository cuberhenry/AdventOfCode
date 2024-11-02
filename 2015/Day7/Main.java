import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 7: Some Assembly Required";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);
        // The current values of all wires
        HashMap<String,Integer> hashmap = new HashMap<>();
        // The list of wires still needing calculation
        ArrayList<String[]> gates = new ArrayList<>();

        // Take in all input
        while (sc.hasNext()){
            gates.add(sc.nextLine().split(" "));
        }

        // Resolve the gates
        resolve(hashmap,gates);

        // Find the value of wire a
        int part1 = hashmap.get("a");

        // Restart with b as a
        hashmap.clear();
        hashmap.put("b",part1);
        resolve(hashmap,gates);

        // Find the new value of wire a
        int part2 = hashmap.get("a");

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static void resolve(HashMap<String,Integer> hashmap, ArrayList<String[]> gates){
        // Loop through every line
        for (int i=0; i<gates.size();){
            // Get the gate
            String[] gate = gates.get(i);
            if (hashmap.containsKey(gate[gate.length-1])){
                ++i;
                continue;
            }
            // Direct assignment
            if (gate.length == 3){
                // The value of the wire
                int num;
                // Check where to get the value from
                if (Character.isDigit(gate[0].charAt(0))){
                    // Number
                    num = Integer.parseInt(gate[0]);
                }else if (hashmap.containsKey(gate[0])){
                    // Wire
                    num = hashmap.get(gate[0]);
                }else{
                    // Unknown value, delay calculation
                    gates.add(gates.remove(i));
                    continue;
                }
                // Add the value to the hashmap
                hashmap.put(gate[2],num);
            // NOT gate
            }else if (gate.length == 4){
                // The value of the wire
                int num;
                // Check where to get the value from
                if (Character.isDigit(gate[1].charAt(0))){
                    // Number
                    num = Integer.parseInt(gate[1]);
                }else if (hashmap.containsKey(gate[1])){
                    // Wire
                    num = hashmap.get(gate[1]);
                }else{
                    // Unknown value, delay calculation
                    gates.add(gates.remove(i));
                    continue;
                }
                // Add the new value to the hashmap
                hashmap.put(gate[3],~num);
            }else{
                // The value of the first wire
                int one;
                // Check where to get the value from
                if (Character.isDigit(gate[0].charAt(0))){
                    // Number
                    one = Integer.parseInt(gate[0]);
                }else if (hashmap.containsKey(gate[0])){
                    // Wire
                    one = hashmap.get(gate[0]);
                }else{
                    // Unknown value, delay calculation
                    gates.add(gates.remove(i));
                    continue;
                }
                // The value of the second wire
                int two;
                // Check where to get the value from
                if (Character.isDigit(gate[2].charAt(0))){
                    // Number
                    two = Integer.parseInt(gate[2]);
                }else if (hashmap.containsKey(gate[2])){
                    // Wire
                    two = hashmap.get(gate[2]);
                }else{
                    // Unknown value, delay calculation
                    gates.add(gates.remove(i));
                    continue;
                }
                // Check the gate type
                hashmap.put(gate[4],switch (gate[1]){
                    case "AND" -> one & two;
                    case "OR" -> one | two;
                    case "LSHIFT" -> one << two;
                    default -> (one & 65535) >>> two;
                });
            }
            // Value found, proceed to the next input
            ++i;
        }
    }
}