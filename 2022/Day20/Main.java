/*
Henry Anderson
Advent of Code 2022 Day 20 https://adventofcode.com/2022/day/20
Input: https://adventofcode.com/2022/day/20/input
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
        // The answer to the problem
        long total = 0;
        // The actively changing list of values
        ArrayList<Long> list = new ArrayList<>();
        // The order of execution
        ArrayList<Long> order = new ArrayList<>();

        // Take in all input
        while (sc.hasNext()){
            // The value being added
            long value = sc.nextLong();

            // Part 2 multiplies all values by 811,589,153
            if (PART == 2){
                value *= 811589153L;
            }

            // Although this constructor is deprecated, it is the only way
            // to ensure no duplicate object is being used between indeces
            Long number = new Long(value);

            // Add the value to the order
            order.add(number);
            // Add the value to the list
            list.add(number);
        }

        // A constant representing the size of the whole list
        int size = list.size();
        // The number of loops to be performed
        int numLoops = 0;

        // Part 1 finds the values after 1 mix
        if (PART == 1){
            numLoops = size;
        }

        // Part 2 finds the values after 10 mixes
        if (PART == 2){
            numLoops = size * 10;
        }
    
        // Loop numLoops times
        for (int i=0; i<numLoops; ++i){
            // The index of the number being moved
            int index = -1;
            for (int j=0; j<size; ++j){
                if (list.get(j) == order.get(i%size)){
                    index = j;
                    break;
                }
            }
            // The number being moved
            Long num = list.remove(index);
            // The amount the number has to move
            int move = (int)(num.longValue() % (size-1)) + size - 1;
            // The new index for the value
            int newIndex = (index + move) % (size-1);

            // Add the number to its new spot
            list.add(newIndex,num);
        }
        
        // Collect the answer
        total += list.get((list.indexOf(0L)+1000)%size);
        total += list.get((list.indexOf(0L)+2000)%size);
        total += list.get((list.indexOf(0L)+3000)%size);

        // Print the answer
        System.out.println(total);
    }
}