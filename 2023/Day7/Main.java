import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 7: Camel Cards";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // The list of all Camel Card hands
        ArrayList<String> hands = new ArrayList<>();
        // Take in every hand
        while (sc.hasNext()){
            hands.add(sc.nextLine());
        }

        // Make a copy for wilds
        ArrayList<String> copy = new ArrayList<>(hands);
        
        // Sort them from worst to best
        hands.sort((a,b) -> {
            // The quantities of each card in each hand
            HashMap<Character,Integer> aCards = new HashMap<>();
            HashMap<Character,Integer> bCards = new HashMap<>();

            // Loop through each card
            for (int i=0; i<5; ++i){
                // Add each card
                if (aCards.containsKey(a.charAt(i))){
                    aCards.put(a.charAt(i),aCards.get(a.charAt(i)) + 1);
                }else{
                    aCards.put(a.charAt(i),1);
                }
                if (bCards.containsKey(b.charAt(i))){
                    bCards.put(b.charAt(i),bCards.get(b.charAt(i)) + 1);
                }else{
                    bCards.put(b.charAt(i),1);
                }
            }

            // The type of hand a
            int aType;
            // Based on the number of unique cards
            switch (aCards.size()){
                // Five of a kind
                case 1 -> aType = 0;
                case 2 -> {
                    if (aCards.containsValue(4)){
                        // Four of a kind
                        aType = 1;
                    }else{
                        // Full house
                        aType = 2;
                    }
                }
                case 3 -> {
                    if (aCards.containsValue(3)){
                        // Three of a kind
                        aType = 3;
                    }else{
                        // Two pair
                        aType = 4;
                    }
                }
                // One pair
                case 4 -> aType = 5;
                // High card
                default -> aType = 6;
            }

            // The type of hand b
            int bType;
            // Based on the number of unique cards
            switch (bCards.size()){
                // Five of a kind
                case 1 -> bType = 0;
                case 2 -> {
                    if (bCards.containsValue(4)){
                        // Four of a kind
                        bType = 1;
                    }else{
                        // Full house
                        bType = 2;
                    }
                }
                case 3 -> {
                    if (bCards.containsValue(3)){
                        // Three of a kind
                        bType = 3;
                    }else{
                        // Two pair
                        bType = 4;
                    }
                }
                // One pair
                case 4 -> bType = 5;
                // High card
                default -> bType = 6;
            }

            // Find if one hand is better than another
            if (aType < bType){
                return 1;
            }
            if (bType < aType){
                return -1;
            }

            // The ranking of each card
            String order = "AKQJT98765432";

            // Compare each card in the hand
            for (int i=0; i<5; ++i){
                if (order.indexOf(a.charAt(i)) < order.indexOf(b.charAt(i))){
                    return 1;
                }
                if (order.indexOf(b.charAt(i)) < order.indexOf(a.charAt(i))){
                    return -1;
                }
            }

            return 0;
        });

        // Sort them from worst to best
        copy.sort((a,b) -> {
            // The quantities of each card in each hand
            HashMap<Character,Integer> aCards = new HashMap<>();
            HashMap<Character,Integer> bCards = new HashMap<>();

            // Loop through each card
            for (int i=0; i<5; ++i){
                // Add each card
                if (aCards.containsKey(a.charAt(i))){
                    aCards.put(a.charAt(i),aCards.get(a.charAt(i)) + 1);
                }else{
                    aCards.put(a.charAt(i),1);
                }
                if (bCards.containsKey(b.charAt(i))){
                    bCards.put(b.charAt(i),bCards.get(b.charAt(i)) + 1);
                }else{
                    bCards.put(b.charAt(i),1);
                }
            }

            // The type of hand a
            int aType;
            // Based on the number of unique cards
            switch (aCards.size()){
                // Five of a kind
                case 1 -> aType = 0;
                case 2 -> {
                    if (aCards.containsValue(4)){
                        // Four of a kind
                        aType = 1;
                    }else{
                        // Full house
                        aType = 2;
                    }
                }
                case 3 -> {
                    if (aCards.containsValue(3)){
                        // Three of a kind
                        aType = 3;
                    }else{
                        // Two pair
                        aType = 4;
                    }
                }
                // One pair
                case 4 -> aType = 5;
                // High card
                default -> aType = 6;
            }

            // The type of hand b
            int bType;
            // Based on the number of unique cards
            switch (bCards.size()){
                // Five of a kind
                case 1 -> bType = 0;
                case 2 -> {
                    if (bCards.containsValue(4)){
                        // Four of a kind
                        bType = 1;
                    }else{
                        // Full house
                        bType = 2;
                    }
                }
                case 3 -> {
                    if (bCards.containsValue(3)){
                        // Three of a kind
                        bType = 3;
                    }else{
                        // Two pair
                        bType = 4;
                    }
                }
                // One pair
                case 4 -> bType = 5;
                // High card
                default -> bType = 6;
            }

            // Only if there exists at least one jack
            if (aCards.containsKey('J')){
                // Based on which type it was
                switch (aType){
                    // High card -> One pair
                    case 6 -> aType = 5;
                    // One pair -> Three of a kind
                    // Three of a kind -> Four of a kind
                    // Full house -> Five of a kind
                    case 5,3,2 -> aType -= 2;
                    // Two pair can become different things depending
                    case 4 -> {
                        // If one of the pairs is jacks
                        if (aCards.get('J') == 2){
                            // Two pair -> Four of a kind
                            aType = 1;
                        }else{
                            // Two pair -> Full house
                            aType = 2;
                        }
                    }
                    // Four of a kind -> Five of a kind
                    // Five of a kind -> no change
                    default -> aType = 0;
                }
            }

            // Only if there exists at least one jack
            if (bCards.containsKey('J')){
                // Based on which type it was
                switch (bType){
                    // High card -> One pair
                    case 6 -> bType = 5;
                    // One pair -> Three of a kind
                    // Three of a kind -> Four of a kind
                    // Full house -> Five of a kind
                    case 5,3,2 -> bType -= 2;
                    // Two pair can become different things depending
                    case 4 -> {
                        // If one of the pairs is jacks
                        if (bCards.get('J') == 2){
                            // Two pair -> Four of a kind
                            bType = 1;
                        }else{
                            // Two pair -> Full house
                            bType = 2;
                        }
                    }
                    // Four of a kind -> Five of a kind
                    // Five of a kind -> no change
                    default -> bType = 0;
                }
            }

            // Find if one hand is better than another
            if (aType < bType){
                return 1;
            }
            if (bType < aType){
                return -1;
            }

            // The ranking of each card
            String order = "AKQT98765432J";

            // Compare each card in the hand
            for (int i=0; i<5; ++i){
                if (order.indexOf(a.charAt(i)) < order.indexOf(b.charAt(i))){
                    return 1;
                }
                if (order.indexOf(b.charAt(i)) < order.indexOf(a.charAt(i))){
                    return -1;
                }
            }

            return 0;
        });

        // The total winnings
        long part1 = 0;
        long part2 = 0;
        // Loop through each hand
        for (int i=0; i<hands.size(); ++i){
            // Multiply the rank by the bid
            part1 += (i + 1) * Integer.parseInt(hands.get(i).split(" ")[1]);
            part2 += (i + 1) * Integer.parseInt(copy.get(i).split(" ")[1]);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}