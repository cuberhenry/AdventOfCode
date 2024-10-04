/*
Henry Anderson
Advent of Code 2020 Day 16 https://adventofcode.com/2020/day/16
Input: https://adventofcode.com/2020/day/16/input
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
        // The names of the fields and their respective ranges
        ArrayList<String> fields = new ArrayList<>();
        ArrayList<int[]> ranges = new ArrayList<>();
        // The possible field for each outer index
        ArrayList<ArrayList<String>> possibilities = new ArrayList<>();
        String line = sc.nextLine();
        
        // Take in all the fields
        while (!line.equals("")){
            // Get the ranges
            String[] split = line.substring(line.indexOf(':')+2).split(" or |\\-");
            ranges.add(new int[] {Integer.parseInt(split[0]),Integer.parseInt(split[1])});
            ranges.add(new int[] {Integer.parseInt(split[2]),Integer.parseInt(split[3])});

            // Part 1 finds the number of invalid tickets
            // Part 2 finds which field represents which index
            if (PART == 2){
                // Get and add the field's name
                String field = line.substring(0,line.indexOf(':'));
                fields.add(field);

                // Add the new field name as a possibility to every index
                if (possibilities.size() == 0){
                    possibilities.add(new ArrayList<>());
                    possibilities.get(0).add(field);
                }else{
                    for (int i=0; i<possibilities.size(); ++i){
                        possibilities.get(i).add(field);
                    }
                    possibilities.add(new ArrayList<>(possibilities.get(0)));
                }
            }

            line = sc.nextLine();
        }

        // Skip extra lines, but save your ticket
        sc.nextLine();
        String[] ticket = sc.nextLine().split(",");
        sc.nextLine();
        sc.nextLine();

        // The total sum of invalid values
        int invalid = 0;
        // Take in each other ticket
        while (sc.hasNext()){
            // Take in its values
            String[] split = sc.nextLine().split(",");
            int[] values = new int[split.length];
            for (int i=0; i<values.length; ++i){
                values[i] = Integer.parseInt(split[i]);
            }

            // Whether the ticket is valid
            boolean valid = true;
            // Loop through every value
            for (int num : values){
                // Whether the value falls into a range
                boolean found = false;
                // Loop through every range
                for (int[] range : ranges){
                    // If it falls in the range
                    if (num <= range[1] && num >= range[0]){
                        found = true;
                        break;
                    }
                }
                // If it didn't fall into any ranges
                if (!found){
                    invalid += num;
                    valid = false;
                }
            }

            // If the ticket is valid, use it to identify the fields
            if (PART == 2){
                if (valid){
                    // Loop through every present value
                    for (int i=0; i<values.length; ++i){
                        // Loop through every field
                        for (int j=0; j<fields.size(); ++j){
                            // If the value doesn't fall within either range
                            if ((values[i] < ranges.get(j*2)[0] || values[i] > ranges.get(j*2)[1])
                                && (values[i] < ranges.get(j*2+1)[0] || values[i] > ranges.get(j*2+1)[1])){
                                    // This value's index cannot represent this field
                                    possibilities.get(i).remove(fields.get(j));
                                }
                        }
                    }
                }
            }
        }

        // Print the answer
        if (PART == 1){
            System.out.println(invalid);
        }

        if (PART == 2){
            // The index for each field
            String[] fieldNames = new String[fields.size()];
            // Loop through every index
            for (int i=0; i<possibilities.size(); ++i){
                // If there is only one possibility
                if (possibilities.get(i).size() == 1){
                    fieldNames[i] = possibilities.get(i).get(0);
                    // No other index can be this field
                    for (int j=0; j<possibilities.size(); ++j){
                        possibilities.get(j).remove(fieldNames[i]);
                    }
                    // Start the search over
                    i = -1;
                }
            }

            // The product of all fields on your ticket starting with "departure"
            long total = 1;
            // Loop through every field
            for (int i=0; i<fieldNames.length; ++i){
                // Save the value
                if (fieldNames[i].startsWith("departure")){
                    total *= Integer.parseInt(ticket[i]);
                }
            }

            // Print the answer
            System.out.println(total);
        }
    }
}