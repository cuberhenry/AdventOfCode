package com.aoc.mylibrary;

import java.util.ArrayList;
import java.util.Scanner;

public class Assembunny {
    private ArrayList<String[]> origInstructions = new ArrayList<>();
    private ArrayList<String[]> instructions = new ArrayList<>();
    int[] registers = new int[4];
    int index = 0;

    public Assembunny(Scanner sc){
        while (sc.hasNext()){
            String[] instruction = sc.nextLine().split(" ");
            origInstructions.add(instruction);
            instructions.add(instruction.clone());
        }
    }

    public void reset(){
        for (int i=0; i<instructions.size(); ++i){
            instructions.set(i,origInstructions.get(i).clone());
        }
        registers = new int[4];
        index = 0;
    }

    public int getRegister(char index){
        return registers[index - 'a'];
    }

    public int[] getRegisters(){
        return registers;
    }

    public void setRegister(char index, int value){
        registers[index - 'a'] = value;
    }

    public int run(){
        // Until the program ends
        while (index < instructions.size()){
            // Grab the current instruction
            String[] instruction = instructions.get(index);
            // Perform the instruction
            switch (instruction[0]){
                // Copy
                case "cpy" -> {
                    // Skip nonsense instructions
                    if (Character.isDigit(instruction[2].charAt(0))){
                        ++index;
                        continue;
                    }
                    // Set the register to the indicated value
                    registers[instruction[2].charAt(0)-'a'] = getValue(instruction[1]);
                    ++index;
                }
                // Increment or Decrement
                case "inc", "dec" -> {
                    // Get the next instruction
                    String[] next = instructions.get(index+1);
                    // Check to see if it's being changed based on another number
                    if (instructions.size() > index+2 && instructions.get(index+2)[0].equals("jnz")
                        && (next[0].equals("inc") || next[0].equals("dec")
                        && instructions.get(index+2)[2].equals("-2"))){
                        // The register on which the current register is dependant
                        char jump = instructions.get(index+2)[1].charAt(0);
                        // The register being changed
                        char other = instruction[1].charAt(0);
                        // Whether the current register is being increased or decreased
                        int multi = instruction[0].equals("inc") ? 1 : -1;
                        // If the current register is in charge of the jump
                        if (other == jump){
                            // Switch the registers
                            other = next[1].charAt(0);
                            multi = next[0].equals("inc") ? 1 : -1;
                        }
                        // If it's a multiplication
                        if (instructions.size() > index+4 && instructions.get(index+4)[0].equals("jnz")
                            && (instructions.get(index+3)[0].equals("inc") || instructions.get(index+3)[0].equals("dec"))){
                            // Multiply the two registers
                            registers[jump-'a'] *= Math.abs(registers[instructions.get(index+3)[1].charAt(0)-'a']);
                            // Empty the second register
                            registers[instructions.get(index+3)[1].charAt(0)-'a'] = 0;
                            // Move the answer to the right register
                            registers[other-'a'] += registers[jump-'a'] * multi;
                            registers[jump-'a'] = 0;
                            // Skip the instructions that were condensed
                            index += 5;
                            // Continue to the next instruction
                            continue;
                        }
                        // Add the registers
                        registers[other-'a'] += registers[jump-'a'] * multi;
                        // Empty the other register
                        registers[jump-'a'] = 0;
                        // Skip the instructions that were condensed
                        index += 3;
                        // Continue to the next instruction
                        continue;
                    }
                    // Increment or decrement the value at the register
                    registers[instruction[1].charAt(0)-'a'] += instruction[0].equals("inc") ? 1 : -1;
                    ++index;
                }
                // Jump Not Zero
                case "jnz" -> {
                    // The number to be compared to 0
                    int num = getValue(instruction[1]);
                    // If the number isn't 0
                    if (num != 0){
                        // Jump using the given offset
                        int offset = getValue(instruction[2]);
                        index += offset;
                    }else{
                        ++index;
                    }
                }
                // Toggle
                case "tgl" -> {
                    // The offset to the toggled instruction
                    int num = getValue(instruction[1]);

                    // Skips the instruction if tgl tries to toggle a nonexistent instruction
                    if (index+num < 0 || index+num >= instructions.size()){
                        ++index;
                        continue;
                    }
                    // The instruction to be toggled
                    String[] toggled = instructions.get(index + num);

                    // Separates one and two argument instructions
                    if (toggled.length == 2){
                        // inc changes to dec and everything else changes to inc
                        if (toggled[0].equals("inc")){
                            toggled[0] = "dec";
                        }else{
                            toggled[0] = "inc";
                        }
                    }else{
                        // jnz changes to cpy and everything else changes to jnz
                        if (toggled[0].equals("jnz")){
                            toggled[0] = "cpy";
                        }else{
                            toggled[0] = "jnz";
                        }
                    }
                    ++index;
                }
                // Output
                case "out" -> {
                    // Prepare to continue running at the next instruction
                    ++index;
                    // Return the new value
                    return getValue(instruction[1]);
                }
            }
        }

        return 0;
    }

    private int getValue(String source){
        try {
            return Integer.parseInt(source);
        }catch (Exception e){}
        return registers[source.charAt(0)-'a'];
    }
}