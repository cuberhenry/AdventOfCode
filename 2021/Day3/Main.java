/*
Henry Anderson
Advent of Code 2021 Day 3 https://adventofcode.com/2021/day/3
Input: https://adventofcode.com/2021/day/3/input
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
        // Part 1 finds the product of gamma and epsilon
        if (PART == 1){
            // Take in the first line and initialize counts
            String line = sc.nextLine();
            int[][] counts = new int[line.length()][2];
            // For every number, add the counts
            for (int i=0; i<line.length(); ++i){
                ++counts[i][line.charAt(i)-'0'];
            }
            while (sc.hasNext()){
                line = sc.nextLine();
                for (int i=0; i<line.length(); ++i){
                    ++counts[i][line.charAt(i)-'0'];
                }
            }

            // The most common bits
            int gamma = 0;
            // The least common bits
            int epsilon = 0;
            for (int i=0; i<counts.length; ++i){
                gamma <<= 1;
                epsilon <<= 1;
                if (counts[i][1] > counts[i][0]){
                    ++gamma;
                }else{
                    ++epsilon;
                }
            }

            // Print the answer
            System.out.println(gamma * epsilon);
        }

        // Part 2 finds the product of the OGR and the CSR
        if (PART == 2){
            // Initialize both lists
            ArrayList<String> ogr = new ArrayList<>();
            while (sc.hasNext()){
                ogr.add(sc.nextLine());
            }
            ArrayList<String> co2 = new ArrayList<>(ogr);

            // Loop through every bit
            for (int i=0; i<ogr.getFirst().length(); ++i){
                // Ignore if there's only one number left
                if (ogr.size() > 1){
                    // Count the number of 0s at position i
                    int count0s = 0;
                    for (String number : ogr){
                        if (number.charAt(i) == '0'){
                            ++count0s;
                        }
                    }

                    // If count0s beats this threshold, remove 1s, otherwise 0s
                    int threshold = ogr.size()/2;
                    for (int j=ogr.size()-1; j>=0; --j){
                        // If the number has the wrong bit, remove it
                        if (ogr.get(j).charAt(i) == '0' ^ count0s > threshold){
                            ogr.remove(j);
                        }
                    }
                }


                // Ignore if there's only one number left
                if (co2.size() > 1){
                    // Count the number of 0s at position i
                    int count0s = 0;
                    for (String number : co2){
                        if (number.charAt(i) == '0'){
                            ++count0s;
                        }
                    }
                    // If count0s beats this threshold, remove 0s, otherwise 1s
                    int threshold = co2.size()/2;
                    for (int j=co2.size()-1; j>=0; --j){
                        // If the number has the wrong bit, remove it
                        if (!(co2.get(j).charAt(i) == '0' ^ count0s > threshold)){
                            co2.remove(j);
                        }
                    }
                }
            }

            // Print the answer
            System.out.println(Integer.parseInt(ogr.getFirst(),2) * Integer.parseInt(co2.getFirst(),2));
        }
    }
}