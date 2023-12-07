/*
Henry Anderson
Advent of Code 2023 Day 7 https://adventofcode.com/2023/day/7
Input: https://adventofcode.com/2023/day/7/input
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
        // The list of all Camel Cards hands
        ArrayList<String> hands = new ArrayList<>();
        // The total winnings
        int total = 0;
        // The hierarchy of cards
        String order = "";

        // Part 1 treats J as a jack
        if (PART == 1){
            order = "AKQJT98765432";
        }

        // Part 2 treats J as a joker
        if (PART == 2){
            order = "AKQT98765432J";
        }

        // Loop through each hand
        while (sc.hasNext()){
            // Take in the hand
            String hand = sc.nextLine();

            // The list of different cards in this hand
            ArrayList<Character> cards = new ArrayList<>();
            // The quantity of each card
            ArrayList<Integer> count = new ArrayList<>();
            // Loop through each card
            for (int i=0; i<5; ++i){
                // If the card is a duplicate
                if (cards.contains(hand.charAt(i))){
                    // Increase the count of that card
                    count.set(cards.indexOf(hand.charAt(i)),count.get(cards.indexOf(hand.charAt(i)))+1);
                }else{
                    // Add a single card
                    cards.add(hand.charAt(i));
                    count.add(1);
                }
            }

            // The type of hand
            int type;
            // Based on the number of unique cards
            switch (count.size()){
                // Five of a kind
                case 1 -> type = 0;
                case 2 -> {
                    if (count.contains(4)){
                        // Four of a kind
                        type = 1;
                    }else{
                        // Full house
                        type = 2;
                    }
                }
                case 3 -> {
                    if (count.contains(3)){
                        // Three of a kind
                        type = 3;
                    }else{
                        // Two pair
                        type = 4;
                    }
                }
                // One pair
                case 4 -> type = 5;
                // High card
                default -> type = 6;
            }

            // Change jacks
            if (PART == 2){
                // Only if there exists at least one jack
                if (cards.contains('J')){
                    // Based on which type it was
                    switch (type){
                        // High card -> One pair
                        case 6 -> type = 5;
                        // One pair -> Three of a kind
                        // Three of a kind -> Four of a kind
                        // Full house -> Five of a kind
                        case 5,3,2 -> type -= 2;
                        // Two pair can become different things depending
                        case 4 -> {
                            // If one of the pairs is jacks
                            if (count.get(cards.indexOf('J')) == 2){
                                // Two pair -> Four of a kind
                                type = 1;
                            }else{
                                // Two pair -> Full house
                                type = 2;
                            }
                        }
                        // Four of a kind -> Five of a kind
                        // Five of a kind -> no change
                        default -> type = 0;
                    }
                }
            }

            // Add a differentiator at the beginning of the hand
            hand = order.charAt(type) + hand;

            // Insertion sort
            boolean inserted = false;
            for (int i=0; i<hands.size(); ++i){
                // Comparator
                boolean greaterThan = false;
                // Loop through the type followed by each card
                for (int j=0; j<6; ++j){
                    // If the new hand is better than the other hand
                    if (order.indexOf(hand.charAt(j)) < order.indexOf(hands.get(i).charAt(j))){
                        // Get ready to continue on
                        greaterThan = true;
                        break;
                    }else if (order.indexOf(hand.charAt(j)) > order.indexOf(hands.get(i).charAt(j))){
                        // Break if the comparison fails
                        break;
                    }
                }
                
                // If the new hand is not greater
                if (!greaterThan){
                    // Insert
                    hands.add(i,hand);
                    inserted = true;
                    break;
                }
            }
            // If the new hand is the greatest
            if (!inserted){
                // Insert it at the end
                hands.add(hand);
            }
        }

        // Loop through every hand
        for (int i=0; i<hands.size(); ++i){
            // Add the hand's winnings
            total += (i+1) * Integer.parseInt(hands.get(i).substring(7));
        }

        // Print the answer
        System.out.println(total);
    }
}