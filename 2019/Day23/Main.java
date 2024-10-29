/*
Henry Anderson
Advent of Code 2019 Day 23 https://adventofcode.com/2019/day/23
Input: https://adventofcode.com/2019/day/23/input
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
        // Take in the program input
        String line = sc.nextLine();
        // Create the array of 50 computers
        IntCode[] computers = new IntCode[50];
        // Each computer's queue of packets
        ArrayList<ArrayList<long[]>> packets = new ArrayList<>();

        // Initialize each computer
        for (int i=0; i<50; ++i){
            computers[i] = new IntCode(line);
            // Give it its network address
            computers[i].addInput(i);
            packets.add(new ArrayList<>());
        }

        // The last Y value delivered by the NAT
        long lastDelivered = -1;
        // The NAT's packet
        long[] nat = new long[2];

        // Continue until the end condition is met
        while (true){
            // Whether all computers are waiting for new inputs
            boolean idle = true;
            // Loop through each computer
            for (int i=0; i<50; ++i){
                // If there's no waiting packet
                if (packets.get(i).isEmpty()){
                    // Deliver -1
                    computers[i].addInput(-1);
                }else{
                    // The network isn't idle
                    idle = false;
                    // Deliver the first packet in the queue
                    long[] packet = packets.get(i).removeFirst();
                    computers[i].addInput(packet[0]);
                    computers[i].addInput(packet[1]);
                }

                // Get the output of the program
                ArrayList<Long> output = computers[i].run();
                // Loop through each packet
                for (int j=0; j<output.size(); j+=3){
                    // The network isn't idle
                    idle = false;
                    // Get the address and X and Y values
                    int address = (int)(long)(output.get(j));
                    long x = output.get(j+1);
                    long y = output.get(j+2);
                    // NAT address
                    if (address == 255){
                        // Part 1 finds the first Y value sent to the NAT
                        if (PART == 1){
                            // Print the answer
                            System.out.println(output.get(j+2));
                            return;
                        }

                        // Part 2 finds the first Y value sent from the NAT twice in a row
                        // Save the packet
                        nat[0] = x;
                        nat[1] = y;
                    }else{
                        // Add the packet to the given computer's queue
                        packets.get(address).add(new long[] {x,y});
                    }
                }
            }

            // If the network is idle
            if (idle){
                // If the NAT delivers the same value as last time
                if (lastDelivered == nat[1]){
                    // Print the answer
                    System.out.println(nat[1]);
                    return;
                }
                // Update last delivered and deliver the packet to computer 0
                lastDelivered = nat[1];
                packets.get(0).add(nat);
            }
        }
    }
}