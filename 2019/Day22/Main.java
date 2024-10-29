/*
Henry Anderson
Advent of Code 2019 Day 22 https://adventofcode.com/2019/day/22
Input: https://adventofcode.com/2019/day/22/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import java.math.BigInteger;
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
        // The first card in the deck
        long offset = 0;
        // The difference between each card and the next (positive with modulo)
        long increment = 1;

        // The number of cards in the deck
        long deckSize = 10007;

        if (PART == 2){
            deckSize = 119315717514047L;
        }

        // Continue through each suffle instruction
        while (sc.hasNext()){
            String line = sc.nextLine();
            if (line.equals("deal into new stack")){
                // Reverse the deck after the first card
                increment = deckSize - increment;
                // Move the top card to the bottom
                offset = (offset + increment) % deckSize;
            }else if (line.charAt(0) == 'c'){
                // Get the card at position c
                offset = (Integer.parseInt(line.split(" ")[1]) * increment + offset) % deckSize;
                // Make sure the offset is positive
                if (offset < 0){
                    offset += deckSize;
                }
            }else{
                int num = Integer.parseInt(line.split(" ")[3]);
                // Multiply the two increments together
                increment = (new BigInteger(""+increment).multiply(new BigInteger(""+modInverse(num,deckSize))).mod(new BigInteger(""+deckSize))).longValue();
            }
        }

        // Part 1 finds the position of card 2019
        if (PART == 1){
            // Loop through each position
            for (int i=0; i<deckSize; ++i){
                // If the card there is 2019
                if ((offset + increment * i) % deckSize == 2019){
                    // Print the answer
                    System.out.println(i);
                    return;
                }
            }
        }

        // Part 2 finds the card at position 2020
        if (PART == 2){
            // Shuffle many times
            long shuffles = 101741582076661L;
            // Calculate the new offset and increment
            offset = geometricSum(offset,increment,shuffles,deckSize);
            increment = modExponent(increment,shuffles,deckSize);
            // Print the answer
            System.out.println((offset + increment * 2020) % deckSize);
        }
    }

    // A quick method to return the modular inverse assuming a and b are coprime
    private static long modInverse(long a, long b){
        return a == 1 ? 1 : ((a - modInverse(b%a,a)) * b + 1) / a;
    }

    // A wrapper of BigInteger.modPow() for longs
    private static long modExponent(long base, long exponent, long mod){
        BigInteger b = new BigInteger("" + base);
        BigInteger e = new BigInteger("" + exponent);
        BigInteger m = new BigInteger("" + mod);

        return b.modPow(e,m).longValue();
    }

    // Calculates a massive geometric sum
    private static long geometricSum(long coefficient, long base, long exponent, long mod){
        // Turn the values into BigIntegers
        BigInteger c = new BigInteger("" + coefficient);
        BigInteger b = new BigInteger("" + base);
        BigInteger e = new BigInteger("" + exponent);
        BigInteger m = new BigInteger("" + mod);

        // Get the top and bottom
        BigInteger numerator = c.multiply(b.modPow(e,m).subtract(BigInteger.ONE)).mod(m);
        BigInteger denominator = b.subtract(BigInteger.ONE).mod(m).modInverse(m);

        // Return the result
        return numerator.multiply(denominator).mod(m).longValue();
    }
}