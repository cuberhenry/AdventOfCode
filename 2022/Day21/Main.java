/*
Henry Anderson
Advent of Code 2022 Day 21 https://adventofcode.com/2022/day/21
Input: https://adventofcode.com/2022/day/21/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // The desired problem to solve
    static int PART;
    static Scanner sc;
    // The file containing the puzzle input
    static String FILE_NAME = "input.txt";

    public static void main(String args[]) {
        if (args.length < 1 || args.length > 2){
            System.out.println("Wrong number of arguments");
            return;
        }
        // Take in the part and file name
        try {
            PART = Integer.parseInt(args[0]);
        } catch (Exception e){}
        if (!(PART == 1 || PART == 2)){
            System.out.println("Part can only be 1 or 2");
            return;
        }
        if (args.length == 2){
            FILE_NAME = args[1];
        }
        try {
            sc = new Scanner(new File(FILE_NAME));
        }catch (Exception e){
            System.out.println("File not found");
            return;
        }
        // The monkeys whose number hasn't been calculated
        ArrayList<String> uncalculated = new ArrayList<>();
        // Maps monkey names to the number they yell
        HashMap<String,Long> hash = new HashMap<>();
        // The answer to the problem
        long answer = 0L;

        // Take in all input
        while (sc.hasNext()){
            // Take in the next line
            String line = sc.nextLine();
            // Save the name
            String name = line.substring(0,4);

            // For part 2, this input is irrelevant
            if (PART == 2){
                if (name.equals("humn")){
                    continue;
                }
            }

            // If we know the monkey's number, add it to the hashmap
            if (Character.isDigit(line.charAt(6))){
                long number = Long.parseLong(line.substring(6));
                hash.put(name,number);
            }else{
                // Otherwise add it to the uncalculated list
                if (name.equals("root")){
                    uncalculated.add(0,line);
                }else{
                    uncalculated.add(line);
                }
            }
            
        }
        
        // Continue until we run out of monkeys we can calculate
        for (int i=0; i<uncalculated.size(); ++i){
            // Take the line
            String[] line = uncalculated.get(i).split(" ");
            // Skip the semicolon
            line[0] = line[0].substring(0,4);
            // If we're able to calculate
            if (hash.containsKey(line[1]) && hash.containsKey(line[3])){
                // Take the two numbers
                long num1 = hash.get(line[1]);
                long num2 = hash.get(line[3]);
                // Perform the operation
                if (line[2].equals("+")){
                    num1 += num2;
                }else if (line[2].equals("-")){
                    num1 -= num2;
                }else if (line[2].equals("/")){
                    num1 /= num2;
                }else{
                    num1 *= num2;
                }
                // Add the new monkey's number to the hashmap
                hash.put(line[0],num1);
                // Remove the monkey from uncalculated
                uncalculated.remove(i);
                // Reset the index
                i = -1;
            }
        }

        // Part 1 gets the number the monkey root should yell
        if (PART == 1){
            answer = hash.get("root");
        }

        // Part 2 gets the number humn should yell to get root's two numbers
        // to be equal
        if (PART == 2){
            // The name of the monkey we're looking for
            String monkey;
            // Start at root and look for which monkey is uncalculated
            // and save its required number
            if (hash.containsKey(uncalculated.get(0).substring(6,10))){
                answer = hash.get(uncalculated.get(0).substring(6,10));
                monkey = uncalculated.remove(0).substring(13);
            }else{
                answer = hash.get(uncalculated.get(0).substring(13));
                monkey = uncalculated.remove(0).substring(6,10);
            }
            
            // Go through all monkeys
            for (int i=0; !uncalculated.isEmpty(); ++i){
                // Take the line
                String[] line = uncalculated.get(i).split(" ");
                line[0] = line[0].substring(0,4);
                // If the monkey's name matches the monkey we're looking for
                if (line[0].equals(monkey)){
                    // If we know the number of the first monkey
                    if (hash.containsKey(line[1])){
                        // Perform the operation
                        if (line[2].equals("/")){
                            answer = hash.get(line[1]) / answer;
                        }else if (line[2].equals("-")){
                            answer = hash.get(line[1]) - answer;
                        }else if (line[2].equals("+")){
                            answer -= hash.get(line[1]);
                        }else{
                            answer /= hash.get(line[1]);
                        }
                        // Set the monkey to the remaining uncalculated monkey
                        monkey = line[3];
                    // If we know the number of the second monkey
                    }else{
                        // Perform the operation
                        if (line[2].equals("/")){
                            answer *= hash.get(line[3]);
                        }else if (line[2].equals("-")){
                            answer += hash.get(line[3]);
                        }else if (line[2].equals("+")){
                            answer -= hash.get(line[3]);
                        }else{
                            answer /= hash.get(line[3]);
                        }
                        // Set the monkey to the remaining uncalculated monkey
                        monkey = line[1];
                    }
                    
                    // Remove the now calculated monkey
                    uncalculated.remove(i);
                    // Reset the index
                    i = -1;
                }
            }
        }

        // Print the answer
        System.out.println(answer);
    }
}