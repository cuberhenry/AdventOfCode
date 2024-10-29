package com.aoc.mylibrary;

import java.util.ArrayList;
import java.util.HashMap;

public class IntCode {
    // The current instruction to be run
    private int index = 0;
    // The entire program
    private long[] program;
    // Memory beyond the length of the program
    private HashMap<Long,Long> memory = new HashMap<>();
    // The relative base for Parameter Mode 2
    private int relativeBase = 0;
    // Values to read in to the program
    private ArrayList<Long> inputs = new ArrayList<>();

    // Constructor
    public IntCode(String p){
        // Initialize the program based on the comma-delimited input
        String[] split = p.split(",");
        program = new long[split.length];
        for (int i=0; i<split.length; ++i){
            program[i] = Long.parseLong(split[i]);
        }
    }

    // Update the program at an index to a value
    public void updateProgram(int i, long value){
        program[i] = value;
    }

    // Gets a value from the program
    public long getValue(int i){
        return program[i];
    }

    // Add a value to be read in by the program
    public void addInput(long in){
        inputs.add(in);
    }

    // Add values by ASCII
    public void addInput(String in){
        for (int i=0; i<in.length(); ++i){
            addInput(in.charAt(i));
        }
        addInput(10);
    }

    // Whether the program has halted
    public boolean isFinished(){
        return program[index] == 99;
    }

    // Run the program
    // The program will halt when reaching OP 99 or when trying to
    // read input that doesn't yet exist
    public ArrayList<Long> run(){
        // The output values from this section of the program
        ArrayList<Long> outputs = new ArrayList<>();

        /*
         * Parameter Modes:
         * 
         * 0: Position Mode, position
         * 1: Immediate Mode, value
         * 2: Relative Mode, relative position
         */

        // Halt (OP): OP == 99
        while (program[index] != 99){
            // Perform the operation
            switch((int)(program[index]%100)){
                // Add (BAOP A B C): OP == 01, C = A + B
                case 1 -> {
                    long A = getParameter('A');
                    long B = getParameter('B');
                    assignValue('C',A+B);
                    index += 4;
                }
                // Multiply (BAOP A B C): OP == 02, C = A * B
                case 2 -> {
                    long A = getParameter('A');
                    long B = getParameter('B');
                    assignValue('C',A*B);
                    index += 4;
                }
                // Load (OP A): OP == 03, A = input
                case 3 -> {
                    // If there are no inputs, stop running
                    if (inputs.isEmpty()){
                        return outputs;
                    }
                    assignValue('A',inputs.removeFirst());
                    index += 2;
                }
                // Output (AOP A): OP == 04, output = A
                case 4 -> {
                    long A = getParameter('A');
                    outputs.add(A);
                    index += 2;
                }
                // Jump If True (BAOP A B): OP == 05, if A != 0 jumpto B
                case 5 -> {
                    long A = getParameter('A');
                    long B = getParameter('B');
                    if (A != 0){
                        index = (int)B;
                    }else{
                        index += 3;
                    }
                }
                // Jump If False (BAOP A B): OP == 06, if A == 0 jumpto B
                case 6 -> {
                    long A = getParameter('A');
                    long B = getParameter('B');
                    if (A == 0){
                        index = (int)B;
                    }else{
                        index += 3;
                    }
                }
                // Less Than (BAOP A B C): OP == 07, C = A < B
                case 7 -> {
                    long A = getParameter('A');
                    long B = getParameter('B');
                    assignValue('C',A < B ? 1 : 0);
                    index += 4;
                }
                // Equal To (BAOP A B C): OP == 08, C = A == B
                case 8 -> {
                    long A = getParameter('A');
                    long B = getParameter('B');
                    assignValue('C',A == B ? 1 : 0);
                    index += 4;
                }
                // Adjust Rel Base (AOP A): OP == 09, relativeBase += A
                case 9 -> {
                    long A = getParameter('A');
                    relativeBase += A;
                    index += 2;
                }
            }
        }

        // Return, program halted
        return outputs;
    }

    private long getParameter(char param){
        long value = program[index+1+param-'A'];
        if (program[index] / (int)Math.pow(10,param-'A'+2) % 10 != 1){
            if (program[index] / (int)Math.pow(10,param-'A'+2) % 10 == 2){
                value += relativeBase;
            }
            if (value >= program.length){
                if (memory.containsKey(value)){
                    value = memory.get(value);
                }else{
                    value = 0;
                }
            }else{
                value = program[(int)value];
            }
        }
        return value;
    }

    private void assignValue(char param, long value){
        long pointer = program[index+1+param-'A'];
        if (program[index] / (int)Math.pow(10,param-'A'+2) % 10 == 2){
            pointer += relativeBase;
        }
        if (pointer < program.length){
            program[(int)pointer] = value;
        }else{
            memory.put(pointer,value);
        }
    }
}