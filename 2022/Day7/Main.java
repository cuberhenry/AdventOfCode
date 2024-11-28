import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Main {
    final private static String name = "Day 7: No Space Left On Device";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        // The total size of all files
        int sum = 0;
        // Stack representing the current directory hierarchy
        Stack<Integer> numStack = new Stack<>();
        // List of all directory sizes
        ArrayList<Integer> array = new ArrayList<>();

        // Loop through all lines of input
        while (sc.hasNext()){
            // Current command or display
            String line = sc.nextLine();
            
            // Going one directory up
            if (line.equals("$ cd ..")){
                // The size of the current directory
                int num = numStack.pop();
                
                // If the directory meets the size requirement
                if (num < 100000){
                    part1 += num;
                }
                
                // Add to the list of directories
                array.add(num);
                
                // Add current directory size to the parent directory
                if (!numStack.isEmpty()){
                    numStack.push(num+numStack.pop());
                }
            // File display
            }else if (Character.isDigit(line.charAt(0))){
                // Take in file size, ignoring the name
                int num = Integer.parseInt(line.substring(0,line.indexOf(' ')));
                
                // Add to the sum of all files
                sum += num;
                
                // Add file size to the parent directory
                if (!numStack.isEmpty()){
                    numStack.push(num+numStack.pop());
                }
            // Changing directory
            }else if (line.substring(0,3).equals("$ c")){
                numStack.push(0);
            }
        }
        
        // Empty the stack, same as moving up one but in a while loop
        while (!numStack.isEmpty()){
            int num = numStack.pop();
            
            if (num < 100000){
                part1 += num;
            }
            
            array.add(num);
                        
            if (!numStack.isEmpty()){
                numStack.push(num+numStack.pop());
            }
        }

        // Sort the directory sizes
        Collections.sort(array);
        
        // Find the smallest directory to delete
        for (int size : array){
            if (sum - size <= 40000000){
                part2 = size;
                break;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}