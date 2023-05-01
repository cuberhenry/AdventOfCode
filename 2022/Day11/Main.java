/*
Henry Anderson
Advent of Code 2022 Day 11 https://adventofcode.com/2022/day/11
Input: https://adventofcode.com/2022/day/11/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {

    // The monkey class helps to abstract the Day 11 problem
    private static class Monkey {
        // The list of items the monkey has
        ArrayList<Long> items = new ArrayList<>();
        // What this monkey does to the worry level of the item
        String operation;
        // The number to test the worry level against
        int test;
        // The monkey to give it to if the test is true
        int trueDest;
        // The monkey to give it to if the test is false
        int falseDest;
        // The number of items the monkey has inspected
        long numInspected = 0;
        
        // Constructor
        public Monkey(String op, int t, int y, int n){
            operation = op;
            test = t;
            trueDest = y;
            falseDest = n;
        }
        
        // Give the monkey an item with the worry level i
        public void giveMonkey(long i){
            items.add(i);
        }
        
        // Let the monkey inspect all of its items
        public void inspect(ArrayList<Monkey> monkeys, long modulo){
            // Loop through all of its items
            while (!items.isEmpty()){
                // Grab the item to inspect it
                long num = items.remove(0);
                // Perform operation on the item's worry level
                if (operation.charAt(0) == '+'){
                    num += Integer.parseInt(operation.substring(2));
                }else{
                    if (operation.substring(2).equals("old")){
                        num *= num;
                    }else{
                        num *= Integer.parseInt(operation.substring(2));
                    }
                }
                
                // Part 1 decreases all worry levels by a factor of 3
                if (PART == 1){
                    num /= 3;
                }
                
                // Part 2 decreases all worry levels without affecting any numbers
                if (PART == 2){
                    num %= modulo;
                }
                
                // Perform the test and pass the item off to a different monkey
                if (num % test == 0){
                    monkeys.get(trueDest).giveMonkey(num);
                }else{
                    monkeys.get(falseDest).giveMonkey(num);
                }
                
                // Increase the number of items inspected
                numInspected++;
            }
        }
        
        // Return the number of items the monkey has inspected
        public long getInspected(){
            return numInspected;
        }
    }

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
        // The list of monkeys
        ArrayList<Monkey> monkeys = new ArrayList<>();
        // The modulo value for Part 2
        long modulo = 1;
        
        // Loop through all of the monkeys
        while (sc.hasNext()){
            // Take in the next line
            String line = sc.nextLine();
            // Skip the space between monkeys
            if (line.equals("")){
                line = sc.nextLine();
            }

            // List of current items, to be added later
            ArrayList<Long> list = new ArrayList<>();
            // Skip monkey number line and take in the list of items
            line = sc.nextLine();
            // Add the items to list
            for (int j=18; j<line.length(); j+=4){
                list.add(Long.parseLong(line.substring(j,j+2)));
            }
            // Take in the operation to be performed on the worry level
            String operation = sc.nextLine().substring(23);
            // Take in the number to test the worry level against
            int test = Integer.parseInt(sc.nextLine().substring(21));
            modulo *= test;
            // Take in the monkey to throw to if the test is true
            int trueMonkey = Integer.parseInt(sc.nextLine().substring(29));
            // Take in the monkey to throw to if the test is false
            int falseMonkey = Integer.parseInt(sc.nextLine().substring(30));
            // Create the monkey
            monkeys.add(new Monkey(operation, test, trueMonkey, falseMonkey));
            // Give the monkey the items
            while (!list.isEmpty()){
                monkeys.get(monkeys.size()-1).giveMonkey(list.remove(0));
            }
        }
        
        // The number of rounds
        int numRounds = 0;
        
        // Part 1 performs 20 rounds
        if (PART == 1){
            numRounds = 20;
        }
        
        // Part 2 performs 10,000 rounds
        if (PART == 2){
            numRounds = 10000;
        }
        
        // Loop through the number of rounds
        for (int i=0; i<numRounds; ++i){
            // Loop through each monkey
            for (Monkey monkey : monkeys){
                // Allow the monkey to inspect all of the items
                monkey.inspect(monkeys,modulo);
            }
        }
        
        // The two amounts to get multiplied
        long max = 0;
        long two = 0;
        // Find the two most active monkeys
        for (Monkey monkey : monkeys){
            if (monkey.getInspected() > max){
                two = max;
                max = monkey.getInspected();
            }else if (monkey.getInspected() > two){
                two = monkey.getInspected();
            }
        }
        System.out.println(max * two);
    }
}