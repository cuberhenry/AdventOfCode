/*
Henry Anderson
Advent of Code 2019 Day 17 https://adventofcode.com/2019/day/17
Input: https://adventofcode.com/2019/day/17/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import com.aoc.mylibrary.IntCode;
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
        // Turn the input into a program
        IntCode program = new IntCode(sc.nextLine());

        // Part 1 finds the alignment parameters
        // Part 2 finds the amount of dust collected by the robot
        if (PART == 2){
            program.updateProgram(0,2);
        }

        // Get the initial map
        ArrayList<Long> output = program.run();

        // Save the scan
        ArrayList<ArrayList<Character>> scan = new ArrayList<>();
        scan.add(new ArrayList<>());

        // The initial position and orientation of the robot
        int x = 0;
        int y = 0;
        int dir = 0;
        // Loop through every output value
        for (long out : output){
            // Newline
            if (out == 10){
                // Double newline means the scan has ended
                if (scan.getLast().size() == 0){
                    scan.removeLast();
                    break;
                }
                // Add a new line
                scan.add(new ArrayList<>());
            }else{
                // The character returned
                char c = (char)out;
                // Add it to the scan
                scan.getLast().add(c);
                // If it's the robot, save the required values
                if ("^>v<".contains(""+c)){
                    x = scan.getLast().size() - 1;
                    y = scan.size() - 1;
                    dir = "^>v<".indexOf(c);
                }
            }
        }

        // Find the sum of the alignment parameters
        if (PART == 1){
            int alignment = 0;
            // Loop through every inner tile in the scan
            for (int j=1; j<scan.size()-1; ++j){
                for (int k=1; k<scan.get(j).size()-1; ++k){
                    // If it is and is surrounded by scaffolding
                    if (scan.get(j).get(k) == '#' &&
                        scan.get(j-1).get(k) == '#' &&
                        scan.get(j+1).get(k) == '#' &&
                        scan.get(j).get(k-1) == '#' &&
                        scan.get(j).get(k+1) == '#'){
                            // Add the parameter
                            alignment += j * k;
                        }
                }
            }

            // Print the answer
            System.out.println(alignment);
        }

        // Find the dust collected
        if (PART == 2){
            // Each individual move by the robot
            String instructions = "";
            // Continue until the end of the scaffolding is found
            while (true){
                // The coordinates of the robot after one move
                int newX = x;
                int newY = y;
                switch(dir){
                    case 0 -> {--newY;}
                    case 1 -> {++newX;}
                    case 2 -> {++newY;}
                    case 3 -> {--newX;}
                }
                // If it would move onto scaffolding
                if (newX >= 0 && newY >= 0 && newX < scan.get(0).size() && newY < scan.size() && scan.get(newY).get(newX) == '#'){
                    // While it would move onto scaffolding
                    while (newX >= 0 && newY >= 0 && newX < scan.get(0).size() && newY < scan.size() && scan.get(newY).get(newX) == '#'){
                        switch(dir){
                            case 0 -> {--newY;}
                            case 1 -> {++newX;}
                            case 2 -> {++newY;}
                            case 3 -> {--newX;}
                        }
                        // Indicate one move forward
                        instructions += "F";
                    }
                    // Save the new x or y value
                    switch(dir){
                        case 0 -> {y = newY + 1;}
                        case 1 -> {x = newX - 1;}
                        case 2 -> {y = newY - 1;}
                        case 3 -> {x = newX + 1;}
                    }
                // Otherwise, look for a turn
                }else if (y > 0 && scan.get(y-1).get(x) == '#' && dir != 2){
                    if (dir == 1){
                        instructions += "L";
                    }else{
                        instructions += "R";
                    }
                    dir = 0;
                }else if (x < scan.get(0).size()-1 && scan.get(y).get(x+1) == '#' && dir != 3){
                    if (dir == 2){
                        instructions += "L";
                    }else{
                        instructions += "R";
                    }
                    dir = 1;
                }else if (y < scan.size()-1 && scan.get(y+1).get(x) == '#' && dir != 0){
                    if (dir == 3){
                        instructions += "L";
                    }else{
                        instructions += "R";
                    }
                    dir = 2;
                }else if (x > 0 && scan.get(y).get(x-1) == '#' && dir != 1){
                    if (dir == 0){
                        instructions += "L";
                    }else{
                        instructions += "R";
                    }
                    dir = 3;
                }else{
                    // Otherwise, the end has been found
                    break;
                }
            }

            // The three movement functions
            String functionA = "";
            String functionB = "";
            String functionC = "";
            // The main movement routine
            String order = "";

            // Whether a valid movement routine has been found
            boolean found = false;
            // Loop through each instruction
            for (int i=0; i<instructions.length() && !found; ++i){
                // Add the instruction to function A
                functionA += instructions.charAt(i);
                functionB = "";
                int jStart = i+1;
                // Skip any repeats of function A
                while (jStart<instructions.length() && instructions.indexOf(functionA,jStart) == jStart){
                    jStart += functionA.length();
                }
                int bSize = 0;
                int bFs = 0;
                // Loop through every following instruction
                for (int j=jStart; j<instructions.length() && !found; ++j){
                    // Calculate the size of the function
                    if (instructions.charAt(j) == 'F'){
                        if (bFs == 0){
                            if (bSize > 0){
                                ++bSize;
                            }
                            ++bSize;
                        }
                        ++bFs;
                        if (bFs == 10){
                            ++bSize;
                        }
                    }else{
                        if (bFs != 0){
                            ++bSize;
                        }
                        ++bSize;
                        bFs = 0;
                    }
                    // If the function is more than 20 characters, it's invalid
                    if (bSize > 20){
                        break;
                    }
                    // Add the instruction to function B
                    functionB += instructions.charAt(j);
                    functionC = "";
                    int kStart = j+1;
                    // Skip any repeats of functions A or B
                    while (kStart<instructions.length()){
                        if (instructions.indexOf(functionA,kStart) == kStart){
                            kStart += functionA.length();
                        }else if (instructions.indexOf(functionB,kStart) == kStart){
                            kStart += functionB.length();
                        }else{
                            break;
                        }
                    }
                    int cSize = 0;
                    int cFs = 0;
                    // Loop through every following instruction
                    for (int k=kStart; k<instructions.length() && !found; ++k){
                        // Calculate the size of the function
                        if (instructions.charAt(k) == 'F'){
                            if (cFs == 0){
                                if (cSize > 0){
                                    ++cSize;
                                }
                                ++cSize;
                            }
                            ++cFs;
                            if (cFs == 10){
                                ++cSize;
                            }
                        }else{
                            if (cFs != 0){
                                ++cSize;
                            }
                            ++cSize;
                            cFs = 0;
                        }
                        // If the function is more than 20 characters, it's invalid
                        if (cSize > 20){
                            break;
                        }
                        // Add the instruction to function C
                        functionC += instructions.charAt(k);

                        // Assume validity
                        found = true;
                        order = "";

                        // Loop through every instruction
                        for (int l=0; l<instructions.length();){
                            // If the function is too long, break
                            if (order.length() == 10){
                                found = false;
                                break;
                            }
                            // Check if it matches a function, and if it does, skip ahead
                            if (instructions.indexOf(functionA,l) == l){
                                l += functionA.length();
                                order += "A";
                            }else if (instructions.indexOf(functionB,l) == l){
                                l += functionB.length();
                                order += "B";
                            }else if (instructions.indexOf(functionC,l) == l){
                                l += functionC.length();
                                order += "C";
                            }else{
                                // No function matched, impossible to perform necessary actions
                                found = false;
                                break;
                            }
                        }
                    }
                }
            }

            // Add the main function
            program.addInput(order.charAt(0));
            for (int i=1; i<order.length(); ++i){
                program.addInput(',');
                program.addInput(order.charAt(i));
            }
            program.addInput(10);

            // Translate each function into ASCII input
            String[] functions = {functionA,functionB,functionC};
            for (int i=0; i<3; ++i){
                int pointer = 0;
                while (pointer < functions[i].length()){
                    // Don't send Fs, translate into the number of Fs
                    if (functions[i].charAt(pointer) == 'F'){
                        int num = 0;
                        while (pointer < functions[i].length() && functions[i].charAt(pointer) == 'F'){
                            ++num;
                            ++pointer;
                        }
                        if (num > 9){
                            program.addInput((num/10) + '0');
                        }
                        program.addInput((num%10) + '0');
                    }else{
                        // Send the turn letter
                        program.addInput(functions[i].charAt(pointer));
                        ++pointer;
                    }
                    if (pointer < functions[i].length()){
                        program.addInput(',');
                    }
                }

                // Replace the last comma with a new line
                program.addInput(10);
            }

            // Don't read out the scan every time
            program.addInput('n');
            program.addInput(10);

            // Run the program
            output = program.run();

            // Print the answer
            System.out.println(output.getLast());
        }
    }
}