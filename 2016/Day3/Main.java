/*
Henry Anderson
Advent of Code 2016 Day 3 https://adventofcode.com/2016/day/3
Input: https://adventofcode.com/2016/day/3/input
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
        // The number of valid triangles
        int total = 0;
        // The number of triangles being read at once
        int numTriangles = 0;

        // Part 1 displays the triangle sides horizontally
        if (PART == 1){
            numTriangles = 1;
        }

        // Part 2 displays the triangle sides vertically
        if (PART == 2){
            numTriangles = 3;
        }

        // While there are more traingles
        while (sc.hasNext()){
            // The first side of each triangle
            int[] a = new int[numTriangles];
            for (int i=0; i<numTriangles; ++i){
                a[i] = sc.nextInt();
            }
            // The second side of each trianlge
            int[] b = new int[numTriangles];
            for (int i=0; i<numTriangles; ++i){
                b[i] = sc.nextInt();
            }
            // The third side of each triangle
            int[] c = new int[numTriangles];
            for (int i=0; i<numTriangles; ++i){
                c[i] = sc.nextInt();

                // Calculate the total perimeter
                int size = a[i] + b[i] + c[i];
                // Find the maximum
                int max = Math.max(a[i],Math.max(b[i],c[i]));
                // Remove the maximum
                size -= max;

                // Compare the largest to the other two
                if (max < size){
                    // If it's valid, add it to the total
                    ++total;
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}