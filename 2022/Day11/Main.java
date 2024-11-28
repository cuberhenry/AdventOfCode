import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 11: Monkey in the Middle";

    // The monkey class helps to abstract the Day 11 problem
    private static class Monkey {
        // The list of items the monkey has
        private LinkedList<Long> items;
        final private LinkedList<Long> initial;
        // What this monkey does to the worry level of the item
        final private char operation;
        final private int change;
        // The number to test the worry level against
        final private int test;
        // The monkey to give it to if the test is true
        final private int trueDest;
        // The monkey to give it to if the test is false
        final private int falseDest;
        // The number of items the monkey has inspected
        private long numInspected = 0;
        
        // Constructor
        public Monkey(String op, int t, int y, int n, LinkedList<Long> i){
            operation = op.charAt(0);
            change = op.substring(2).equals("old") ? -1 : Integer.parseInt(op.substring(2));
            test = t;
            trueDest = y;
            falseDest = n;
            items = i;
            initial = new LinkedList<>(i);
        }
        
        // Give the monkey an item with the worry level i
        public void giveMonkey(long i){
            items.add(i);
        }
        
        // Let the monkey inspect all of its items
        public void inspect(ArrayList<Monkey> monkeys, long modulo, boolean divide){
            // Loop through all of its items
            while (!items.isEmpty()){
                // Grab the item to inspect it
                long num = items.remove();
                // Perform operation on the item's worry level
                if (operation == '+'){
                    num += change;
                }else if (change == -1){
                    num *= num;
                }else{
                    num *= change;
                }
                
                // Keep worry levels manageable
                if (divide){
                    num /= 3;
                }
                num %= modulo;
                
                // Perform the test and pass the item off to a different monkey
                if (num % test == 0){
                    monkeys.get(trueDest).giveMonkey(num);
                }else{
                    monkeys.get(falseDest).giveMonkey(num);
                }
                
                // Increase the number of items inspected
                ++numInspected;
            }
        }
        
        // Return the number of items the monkey has inspected
        public long getInspected(){
            return numInspected;
        }

        // Reset the initial state of the monkey
        public void reset(){
            items = initial;
            numInspected = 0;
        }
    }

    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

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
            LinkedList<Long> list = new LinkedList<>();
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
            monkeys.add(new Monkey(operation, test, trueMonkey, falseMonkey, list));
        }

        // The answer to the problem
        long part1 = monkeyBusiness(20,true,monkeys,modulo);
        for (Monkey monkey : monkeys){
            monkey.reset();
        }
        long part2 = monkeyBusiness(10000,false,monkeys,modulo);

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static long monkeyBusiness(int numRounds, boolean divide, ArrayList<Monkey> monkeys, long modulo){
        // Loop through the number of rounds
        for (int i=0; i<numRounds; ++i){
            // Loop through each monkey
            for (Monkey monkey : monkeys){
                // Allow the monkey to inspect all of the items
                monkey.inspect(monkeys,modulo,divide);
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
        
        return max * two;
    }
}