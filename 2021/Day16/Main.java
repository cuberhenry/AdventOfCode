/*
Henry Anderson
Advent of Code 2021 Day 16 https://adventofcode.com/2021/day/16
Input: https://adventofcode.com/2021/day/16/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {

    // A Packet class to help abstract parsing the packets
    private static class Packet {
        // Header
        private int version;
        private int type;
        // Value if the type is 4
        private long number = 0;
        // Length type if the type is not 4
        private int lengthTypeID;
        // Subpackets
        private ArrayList<Packet> subpackets = new ArrayList<>();

        // Take in a string and pointer, parse from the pointer
        // Return the pointer's new position after parsing
        public int parse(String binary, int pointer){
            // Version is the first three bits
            version = Integer.parseInt(binary.substring(pointer,pointer+3),2);
            pointer += 3;
            // Type is the next three bits
            type = Integer.parseInt(binary.substring(pointer,pointer+3),2);
            pointer += 3;

            // Type 4 means literal value
            if (type == 4){
                // If it's a one, there's at least one more chunk
                while (binary.charAt(pointer++) == '1'){
                    // Shift left
                    number <<= 4;
                    // Add the next four bits
                    number += Integer.parseInt(binary.substring(pointer,pointer+4),2);
                    pointer += 4;
                }
                // Shift left
                number <<= 4;
                // Add the next four bits
                number += Integer.parseInt(binary.substring(pointer,pointer+4),2);
                pointer += 4;
            }else{
                // Length Type ID indicates length or size of the subpackets
                lengthTypeID = binary.charAt(pointer++) - '0';
                if (lengthTypeID == 0){
                    // The length in bits of all of the subpackets
                    int length = Integer.parseInt(binary.substring(pointer,pointer+15),2);
                    pointer += 15;

                    // Continue until that length has been satisfied
                    int start = pointer;
                    while (pointer < start + length){
                        // Add the subpacket
                        Packet packet = new Packet();
                        pointer = packet.parse(binary,pointer);
                        subpackets.add(packet);
                    }
                }else{
                    // The length in packets of the subpackets array
                    int length = Integer.parseInt(binary.substring(pointer,pointer+11),2);
                    pointer += 11;
                    
                    // Continue through each packet
                    for (int i=0; i<length; ++i){
                        // Add the subpacket
                        Packet packet = new Packet();
                        pointer = packet.parse(binary,pointer);
                        subpackets.add(packet);
                    }
                }
            }

            // Return the new pointer
            return pointer;
        }

        // Part 1 finds the sum of the versions of all packets
        public int sumVersions(){
            // Add this packet's version
            int sum = version;
            // Add all subpackets' versions
            for (Packet packet : subpackets){
                sum += packet.sumVersions();
            }
            // Return the sum
            return sum;
        }

        // Part 2 finds the value indicated by the outermost packet
        public long calculate(){
            // Each type performs a different operation
            switch(type){
                // Sum
                case 0 -> {
                    long sum = 0;
                    for (Packet packet : subpackets){
                        sum += packet.calculate();
                    }
                    return sum;
                }
                // Product
                case 1 -> {
                    long product = 1;
                    for (Packet packet : subpackets){
                        product *= packet.calculate();
                    }
                    return product;
                }
                // Minimum
                case 2 -> {
                    long minimum = subpackets.get(0).calculate();
                    for (int i=1; i<subpackets.size(); ++i){
                        minimum = Math.min(minimum,subpackets.get(i).calculate());
                    }
                    return minimum;
                }
                // Maximum
                case 3 -> {
                    long maximum = subpackets.get(0).calculate();
                    for (int i=1; i<subpackets.size(); ++i){
                        maximum = Math.max(maximum,subpackets.get(i).calculate());
                    }
                    return maximum;
                }
                // Greater Than
                case 5 -> {
                    return subpackets.getFirst().calculate() > subpackets.getLast().calculate() ? 1 : 0;
                }
                // Less Than
                case 6 -> {
                    return subpackets.getFirst().calculate() < subpackets.getLast().calculate() ? 1 : 0;
                }
                // Equal to
                case 7 -> {
                    return subpackets.getFirst().calculate() == subpackets.getLast().calculate() ? 1 : 0;
                }
                // Literal
                default -> {return number;}
            }
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
        // Take in the initial hexadecimal
        String hexadecimal = sc.nextLine();
        // Translate into binary
        String binary = "";
        for (int i=0; i<hexadecimal.length(); ++i){
            binary += switch(hexadecimal.charAt(i)){
                case '0' -> "0000";
                case '1' -> "0001";
                case '2' -> "0010";
                case '3' -> "0011";
                case '4' -> "0100";
                case '5' -> "0101";
                case '6' -> "0110";
                case '7' -> "0111";
                case '8' -> "1000";
                case '9' -> "1001";
                case 'A' -> "1010";
                case 'B' -> "1011";
                case 'C' -> "1100";
                case 'D' -> "1101";
                case 'E' -> "1110";
                default -> "1111";
            };
        }

        // Create the outermost packet
        Packet packet = new Packet();
        packet.parse(binary,0);

        // Print the answer
        if (PART == 1){
            System.out.println(packet.sumVersions());
        }

        if (PART == 2){
            System.out.println(packet.calculate());
        }
    }
}