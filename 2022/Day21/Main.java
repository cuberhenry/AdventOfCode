import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 21: Monkey Math";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The monkeys whose number hasn't been calculated
        ArrayList<String[]> uncalculated = new ArrayList<>();
        // Maps monkey names to the number they yell
        HashMap<String,Long> hash = new HashMap<>();
        // The number humn yells
        long humn = 0;

        // Take in all input
        while (sc.hasNext()){
            // Take in the next line
            String line = sc.nextLine();
            // Save the name
            String name = line.substring(0,4);

            // If we know the monkey's number, add it to the hashmap
            if (Character.isDigit(line.charAt(6))){
                long number = Long.parseLong(line.substring(6));
                if (name.equals("humn")){
                    humn = number;
                }else{
                    hash.put(name,number);
                }
            }else{
                // Otherwise add it to the uncalculated list
                if (name.equals("root")){
                    uncalculated.add(0,line.split(": | "));
                }else{
                    uncalculated.add(line.split(": | "));
                }
            }
            
        }
        
        // Continue until we run out of monkeys we can calculate
        for (int i=1; i<uncalculated.size(); ++i){
            // Take the line
            String[] line = uncalculated.get(i);
            // If we're able to calculate
            if (hash.containsKey(line[1]) && hash.containsKey(line[3])){
                // Take the two numbers
                long num1 = hash.get(line[1]);
                long num2 = hash.get(line[3]);
                // Perform the operation
                switch (line[2]){
                    case "+" -> num1 += num2;
                    case "-" -> num1 -= num2;
                    case "/" -> num1 /= num2;
                    case "*" -> num1 *= num2;
                }
                // Add the new monkey's number to the hashmap
                hash.put(line[0],num1);
                // Remove the monkey from uncalculated
                uncalculated.remove(i);
                // Reset the index
                i = 0;
            }
        }

        // Make a copy of the remaining monkeys
        ArrayList<String[]> copy = new ArrayList<>(uncalculated);

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // The name of the monkey we're looking for
        String monkey;
        // Start at root and look for which monkey is uncalculated
        // and save its required number
        if (hash.containsKey(uncalculated.getFirst()[1])){
            part2 = hash.get(uncalculated.getFirst()[1]);
            monkey = uncalculated.removeFirst()[3];
        }else{
            part2 = hash.get(uncalculated.getFirst()[3]);
            monkey = uncalculated.removeFirst()[1];
        }
        
        // Go through all monkeys
        for (int i=0; !uncalculated.isEmpty(); i=(i+1)%(uncalculated.isEmpty() ? 1 : uncalculated.size())){
            // Take the line
            String[] line = uncalculated.get(i);
            // If the monkey's name matches the monkey we're looking for
            if (line[0].equals(monkey)){
                // If we know the number of the first monkey
                if (hash.containsKey(line[1])){
                    // Perform the operation
                    switch (line[2]){
                        case "/" -> part2 = hash.get(line[1]) / part2;
                        case "-" -> part2 = hash.get(line[1]) - part2;
                        case "+" -> part2 -= hash.get(line[1]);
                        case "*" -> part2 /= hash.get(line[1]);
                    }
                    // Set the monkey to the remaining uncalculated monkey
                    monkey = line[3];
                // If we know the number of the second monkey
                }else{
                    // Perform the operation
                    switch (line[2]){
                        case "/" -> part2 *= hash.get(line[3]);
                        case "-" -> part2 += hash.get(line[3]);
                        case "+" -> part2 -= hash.get(line[3]);
                        case "*" -> part2 /= hash.get(line[3]);
                    }
                    // Set the monkey to the remaining uncalculated monkey
                    monkey = line[1];
                }
                
                // Remove the now calculated monkey
                uncalculated.remove(i);
            }
        }

        // Reset to work forwards
        uncalculated = copy;
        hash.put("humn",humn);

        // Continue until we run out of monkeys we can calculate
        for (int i=0; !uncalculated.isEmpty(); i=(i+1)%(uncalculated.isEmpty() ? 1 : uncalculated.size())){
            // Take the line
            String[] line = uncalculated.get(i);
            // If we're able to calculate
            if (hash.containsKey(line[1]) && hash.containsKey(line[3])){
                // Take the two numbers
                long num1 = hash.get(line[1]);
                long num2 = hash.get(line[3]);
                // Perform the operation
                switch (line[2]){
                    case "+" -> num1 += num2;
                    case "-" -> num1 -= num2;
                    case "/" -> num1 /= num2;
                    case "*" -> num1 *= num2;
                }
                // Add the new monkey's number to the hashmap
                hash.put(line[0],num1);
                // Remove the monkey from uncalculated
                uncalculated.remove(i);
            }
        }

        part1 = hash.get("root");

        // Print the answer
        Library.print(part1,part2,name);
    }
}