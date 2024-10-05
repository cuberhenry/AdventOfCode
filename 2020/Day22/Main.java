/*
Henry Anderson
Advent of Code 2020 Day 22 https://adventofcode.com/2020/day/22
Input: https://adventofcode.com/2020/day/22/input
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
        // Each player's deck
        ArrayDeque<Integer> player1 = new ArrayDeque<>();
        ArrayDeque<Integer> player2 = new ArrayDeque<>();

        // Take in the two decks
        sc.nextLine();
        String line = sc.nextLine();
        while (!line.equals("")){
            player1.add(Integer.parseInt(line));
            line = sc.nextLine();
        }
        sc.nextLine();
        while (sc.hasNext()){
            player2.add(Integer.parseInt(sc.nextLine()));
        }

        // Stacks used for recursive games
        Stack<ArrayDeque<Integer>> recursive = new Stack<>();
        Stack<ArrayList<String>> historyStack = new Stack<>();
        // History to get out of an infinite loop
        ArrayList<String> history = new ArrayList<>();

        // Continue until a player has lost on the outermost game
        while (!(player1.isEmpty() || player2.isEmpty()) || !recursive.isEmpty()){
            // Part 1 finds the score of the winner after a normal game
            if (PART == 1){
                // Compare the top two cards
                int card1 = player1.remove();
                int card2 = player2.remove();

                // The winner gets both cards
                if (card1 > card2){
                    player1.add(card1);
                    player1.add(card2);
                }else{
                    player2.add(card2);
                    player2.add(card1);
                }
            }

            // Part 2 finds the score of the winner after a recursive game
            if (PART == 2){
                // Infinite loop failsafe
                if (history.contains(player1 + " " + player2)){
                    // Force player 2 to lose
                    player2.clear();
                }else{
                    // Add for future checks
                    history.add(player1 + " " + player2);
                }
                
                // Player 2 wins
                if (player1.isEmpty()){
                    // Back out one
                    player2 = recursive.pop();
                    player1 = recursive.pop();
                    // Place the winner's cards
                    player2.add(player2.remove());
                    player2.add(player1.remove());
                    history = historyStack.pop();
                // Player 1 wins
                }else if (player2.isEmpty()){
                    // Back out one
                    player2 = recursive.pop();
                    player1 = recursive.pop();
                    // Place the winner's cards
                    player1.add(player1.remove());
                    player1.add(player2.remove());
                    history = historyStack.pop();
                // If both player's have at least as many cards as the card shown
                }else if (player1.peek() < player1.size() && player2.peek() < player2.size()){
                    // Save the current game
                    recursive.push(player1);
                    recursive.push(player2);

                    // Move into a new recursive game
                    player1 = player1.clone();
                    int card1 = player1.remove();
                    player2 = player2.clone();
                    int card2 = player2.remove();

                    // Ensure each deck is only as large as their card
                    while (player1.size() > card1){
                        player1.removeLast();
                    }
                    while (player2.size() > card2){
                        player2.removeLast();
                    }

                    // Save the history and start anew
                    historyStack.add(history);
                    history = new ArrayList<>();
                }else{
                    // Compare the cards as normal
                    int card1 = player1.remove();
                    int card2 = player2.remove();
                    if (card1 > card2){
                        player1.add(card1);
                        player1.add(card2);
                    }else{
                        player2.add(card2);
                        player2.add(card1);
                    }
                }
            }
        }

        // Only the score matters, not who won
        if (player1.isEmpty()){
            player1 = player2;
        }

        // Calculate the score
        long score = 0;
        for (int i=1; !player1.isEmpty(); ++i){
            score += player1.removeLast() * i;
        }

        // Print the answer
        System.out.println(score);
    }
}