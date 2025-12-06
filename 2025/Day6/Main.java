import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 6: Trash Compactor";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        // Part 1 finds the sums and products of the horizontal numbers
        long part1 = 0;
        // Part 2 finds the sums and products of the vertical numbers
        long part2 = 0;

        // The inputs as an array of numbers
        ArrayList<int[]> nums = new ArrayList<>();

        // Take in all the lines with numbers
        String line = sc.nextLine();
        while (!line.contains("+")){
            // Add the array of numbers
            nums.add(Library.intSplit(line.trim()," +"));
            // Get the next line
            line = sc.nextLine();
        }

        // Get the list of operators
        String[] operator = line.split(" +");

        // Loop through each operator
        for (int i=0; i<operator.length; ++i){
            // Whether to multiply for the current problem, default is add
            boolean multiply = operator[i].equals("*");
            // The answer to the current problem
            long subAnswer = multiply ? 1 : 0;
            // Loop through each number in the current problem
            for (int[] num : nums){
                // Combine
                if (multiply){
                    subAnswer *= num[i];
                }else{
                    subAnswer += num[i];
                }
            }
            // Add the problem's result to the total
            part1 += subAnswer;
        }

        // Get the input in a different format for Part 2
        char[][] matrix = Library.getCharMatrix(args);

        // The answer to the current problem
        long subAnswer = 0;
        // Whether to multiply for the current problem, default is add
        boolean multiply = false;
        // Loop through each column
        for (int i=0; i<matrix[0].length; ++i){
            // Check if there's a new problem
            if (matrix[matrix.length-1][i] != ' '){
                // Add the previous sub-answer
                part2 += subAnswer;
                // Check whether to multiply or add
                multiply = matrix[matrix.length-1][i] == '*';
                // Give the new sub-answer a default value
                subAnswer = multiply ? 1 : 0;
            }
            // The current number
            int num = 0;
            // Loop through each number row
            for (int j=0; j<matrix.length-1; ++j){
                // Add the digit if it's not empty
                if (matrix[j][i] != ' '){
                    num *= 10;
                    num += matrix[j][i] - '0';
                }
            }
            // Only combine if the number exists
            if (num > 0){
                if (multiply){
                    subAnswer *= num;
                }else{
                    subAnswer += num;
                }
            }
        }
        // Add the problem's result to the total
        part2 += subAnswer;
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}