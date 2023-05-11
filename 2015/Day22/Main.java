/*
Henry Anderson
Advent of Code 2015 Day 22 https://adventofcode.com/2015/day/22
Input: https://adventofcode.com/2015/day/22/input
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
        // Get rid of excess information
        sc.next();
        sc.next();
        
        // The list of spells
        ArrayList<int[]> spells = new ArrayList<>();
        // {cost, damage, heal, effect}
        spells.add(new int[] {53,4,0,-1});
        spells.add(new int[] {73,2,2,-1});
        spells.add(new int[] {113,0,0,1,6});
        spells.add(new int[] {173,0,0,2,6});
        spells.add(new int[] {229,0,0,3,5});

        // The states left to check in a depth first search
        Stack<int[]> states = new Stack<>();
        // {cost, shield, poison, recharge, mana, youHP, bossHP}
        states.add(new int[] {0,0,0,0,500,50,sc.nextInt()});

        // Excess information
        sc.next();
        // Save the boss' attack
        int bossAttack = sc.nextInt();

        // The best solution so far
        int best = Integer.MAX_VALUE;

        // Until all states have been checked
        while (!states.isEmpty()){
            // Get the state
            int[] state = states.pop();

            // Pre player turn

            // Part 1 finds the least amount of mana necessary to win
            // Part 2 does the same but you lose one health every round
            if (PART == 2){
                // Lose 1 HP
                --state[5];
                // If you lose, don't check this state anymore
                if (state[5] <= 0){
                    continue;
                }
            }

            // Effects
            // Shield has no effect on player turn
            if (state[1] > 0){
                --state[1];
            }
            // Poison
            if (state[2] > 0){
                // Damage the boss
                state[6] -= 3;
                --state[2];
                // If the player wins, stop this state
                if (state[6] <= 0){
                    if (state[0] < best){
                        best = state[0];
                    }
                    continue;
                }
            }
            // Recharge
            if (state[3] > 0){
                state[4] += 101;
                --state[3];
            }

            // Loop through every spell
            for (int[] spell : spells){
                // Cast spell
                // If the effect is already happening
                if (spell[3] != -1 && state[spell[3]] != 0){
                    continue;
                }
                // If you can't afford the spell
                if (spell[0] > state[4]){
                    continue;
                }
                // Create a new state
                int[] newState = Arrays.copyOf(state,7);
                // Add the spell cost to total mana spent
                newState[0] += spell[0];
                // Clip the branch if it passes the best
                if (newState[0] > best){
                    continue;
                }
                // Pay for the spell
                newState[4] -= spell[0];
                // Damage the boss
                newState[6] -= spell[1];
                // If the player wins, stop this state
                if (newState[6] <= 0){
                    if (newState[0] < best){
                        best = newState[0];
                    }
                    continue;
                }
                // Heal the player
                newState[5] += spell[2];
                // Start the effect
                if (spell[3] != -1){
                    newState[spell[3]] = spell[4];
                }

                // Pre-Boss turn
                // The player's armor, defaulted to zero
                int armor = 0;
                // Effects
                // Shield
                if (newState[1] > 0){
                    armor = 7;
                    --newState[1];
                }
                // Poison
                if (newState[2] > 0){
                    // Damage the boss
                    newState[6] -= 3;
                    --newState[2];
                    // If the player wins, stop this state
                    if (newState[6] <= 0){
                        if (newState[0] < best){
                            best = newState[0];
                        }
                        continue;
                    }
                }
                // Recharge
                if (newState[3] > 0){
                    newState[4] += 101;
                    --newState[3];
                }

                // Boss attacks
                int damage = bossAttack - armor;
                if (damage < 1){
                    damage = 1;
                }
                newState[5] -= damage;
                // If you lose, clip the state
                if (newState[5] <= 0){
                    continue;
                }

                // Add the state to the stack of states
                states.add(newState);
            }
        }

        // Print the answer to the problem
        System.out.println(best);
    }
}