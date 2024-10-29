/*
Henry Anderson
Advent of Code 2021 Day 18 https://adventofcode.com/2021/day/18
Input: https://adventofcode.com/2021/day/18/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // An abstraction of a snailfish number
    private static class SnailfishNumber {
        // Whether the number represents an integer
        private boolean isInt;
        // The value of the number, if an integer
        private int value;
        // The two sub-numbers, if not an integer
        private SnailfishNumber left;
        private SnailfishNumber right;
        // The parent snailfish number
        private SnailfishNumber parent;

        // Default constructor for parsing child numbers
        public SnailfishNumber(){}

        // String constructor for outermost numbers
        public SnailfishNumber(String s){
            parse(s,0);
        }

        // Left and right constructor for adding
        public SnailfishNumber(SnailfishNumber l, SnailfishNumber r){
            left = l;
            right = r;
            left.parent = this;
            right.parent = this;
            explode(0);
            while (split(0)){
                explode(0);
            }
        }

        // Value constructor for splitting
        public SnailfishNumber(int v, SnailfishNumber p){
            isInt = true;
            value = v;
            parent = p;
        }

        // Parse the snailfish number, returning the pointer afterwards
        public int parse(String s, int pointer){
            if (s.charAt(pointer) == '['){
                isInt = false;
                left = new SnailfishNumber();
                right = new SnailfishNumber();
                ++pointer;
                pointer = left.parse(s,pointer);
                left.parent = this;
                ++pointer;
                pointer = right.parse(s,pointer);
                right.parent = this;
            }else{
                isInt = true;
                value = s.charAt(pointer) - '0';
            }
            return pointer + 1;
        }

        // Add two snailfish numbers
        public SnailfishNumber add(SnailfishNumber other){
            // Combine them into one bigger number
            return new SnailfishNumber(this,other);
        }

        // Check for and perform an explosion
        public SnailfishNumber[] explode(int depth){
            // Explosions only happen to pairs
            if (!isInt){
                // Depth of 4 means explosion
                if (depth == 4){
                    // Pass the children out
                    SnailfishNumber[] children = {left,right};
                    // Set to an int of 0
                    isInt = true;
                    left = null;
                    right = null;
                    value = 0;
                    return children;
                }else{
                    // Check for an explosion on the left
                    SnailfishNumber[] children = left.explode(depth + 1);
                    // If an explosion happened
                    if (children != null){
                        // Add the left snailfish number
                        parent.addLeft(children[0],this);
                        // Add the right snailfish number
                        right.addRight(children[1]);
                    }
                    // Check for an explosion on the right
                    children = right.explode(depth + 1);
                    if (children != null){
                        left.addLeft(children[0]);
                        parent.addRight(children[1],this);
                    }
                }
            }
            // Didn't explode
            return null;
        }

        // Bring the left value up until there's a left child
        public void addLeft(SnailfishNumber num, SnailfishNumber from){
            // If there's a left child
            if (from == right){
                // Pass it there
                left.addLeft(num);
            }else if (parent != null){
                // Otherwise, pass it up
                parent.addLeft(num,this);
            }
        }

        // Add the value to the next left literal
        public void addLeft(SnailfishNumber num){
            // If it's an integer
            if (isInt){
                // Add the values
                value += num.value;
            }else{
                // Otherwise, pass it down
                right.addLeft(num);
            }
        }

        // Bring the right value up until there's a right child
        public void addRight(SnailfishNumber num, SnailfishNumber from){
            // If there's a right child
            if (from == left){
                // Pass it there
                right.addRight(num);
            }else if (parent != null){
                // Otherwise, pass it up
                parent.addRight(num,this);
            }
        }

        // Add the value to the next right literal
        public void addRight(SnailfishNumber num){
            // If it's an integer
            if (isInt){
                // Add the values
                value += num.value;
            }else{
                // Otherwise, pass it down
                left.addRight(num);
            }
        }

        // Split values that are at least 10, returns whether it should explode
        public boolean split(int depth){
            // If it's a number and it's at least 10
            if (isInt && value >= 10){
                // Left is half rounded down
                left = new SnailfishNumber(value / 2,this);
                // Right is the rest
                right = new SnailfishNumber(value / 2 + value % 2,this);
                // Turn into a pair
                isInt = false;
                value = 0;
                // If there's now a pair at depth 4, it should explode
                if (depth == 4){
                    return true;
                }
            }
            // If it's a pair
            if (!isInt){
                // Check children for splits
                return left.split(depth+1) || right.split(depth+1);
            }
            // No explosion imminent
            return false;
        }

        // Calculate the magnitude
        public int magnitude(){
            // Ints are just their value
            if (isInt){
                return value;
            }
            // Pairs magnitude calculation
            return 3 * left.magnitude() + 2 * right.magnitude();
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
        // Part 1 finds the magnitude of the sum of all snailfish numbers
        if (PART == 1){
            SnailfishNumber number = new SnailfishNumber(sc.nextLine());

            // For every number
            while (sc.hasNext()){
                // Add the new number
                number = number.add(new SnailfishNumber(sc.nextLine()));
            }

            // Print the answer
            System.out.println(number.magnitude());
        }

        // Part 2 finds the max sum magnitude of two numbers
        if (PART == 2){
            // Take in all the snailfish numbers
            ArrayList<String> numbers = new ArrayList<>();
            while (sc.hasNext()){
                numbers.add(sc.nextLine());
            }

            // The max
            int max = 0;
            // Loop through every pair of numbers
            for (int i=0; i<numbers.size(); ++i){
                for (int j=0; j<numbers.size(); ++j){
                    // Skip duplicates
                    if (i == j){
                        continue;
                    }
                    // Save the max
                    max = Math.max(max,new SnailfishNumber(numbers.get(i)).add(new SnailfishNumber(numbers.get(j))).magnitude());
                }
            }
            
            // Print the answer
            System.out.println(max);
        }
    }
}