/*
Henry Anderson
Advent of Code 2015 Day 21 https://adventofcode.com/2015/day/21
Input: https://adventofcode.com/2015/day/21/input
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
        // Take in all input, skipping unneeded information
        sc.next();
        sc.next();
        // The boss' health points
        int hp = sc.nextInt();
        sc.next();
        // The boss' attack
        int damage = sc.nextInt();
        sc.next();
        // The boss' armor
        int armor = sc.nextInt();

        // The list of all weapon options
        ArrayList<int[]> weapons = new ArrayList<>();
        // Add all of the weapons
        // {cost, damage, armor}
        weapons.add(new int[] {8,4,0});
        weapons.add(new int[] {10,5,0});
        weapons.add(new int[] {25,6,0});
        weapons.add(new int[] {40,7,0});
        weapons.add(new int[] {74,8,0});

        // The list of all armor options
        ArrayList<int[]> armors = new ArrayList<>();
        // Add all of the armors
        armors.add(new int[] {13,0,1});
        armors.add(new int[] {31,0,2});
        armors.add(new int[] {53,0,3});
        armors.add(new int[] {75,0,4});
        armors.add(new int[] {102,0,5});

        // The list of all ring options
        ArrayList<int[]> rings = new ArrayList<>();
        // Add all of the rings
        rings.add(new int[] {25,1,0});
        rings.add(new int[] {50,2,0});
        rings.add(new int[] {100,3,0});
        rings.add(new int[] {20,0,1});
        rings.add(new int[] {40,0,2});
        rings.add(new int[] {80,0,3});

        // The list of all combinations
        ArrayList<int[]> stats = new ArrayList<>();

        // Loop through ever weapon option
        for (int i=0; i<weapons.size(); ++i){
            // Retrieve the stats for the weapon
            int[] weapon = Arrays.copyOf(weapons.get(i),3);
            // Add the weapon by itself (no armor or rings)
            stats.add(weapon);
            // Loop through every armor
            for (int j=0; j<armors.size(); ++j){
                // Add the armor to the weapon
                int[] sum = Arrays.copyOf(armors.get(j),3);
                for (int k=0; k<3; ++k){
                    sum[k] += weapon[k];
                }
                // Add the weapon armor combo (no rings)
                stats.add(sum);
            }
        }

        // Loop through every ring
        for (int i=0; i<rings.size(); ++i){
            // Retrieve the stats for the ring
            int[] ring = Arrays.copyOf(rings.get(i),3);
            // Loop through every weapon armor combo
            for (int j=0; j<weapons.size()*(armors.size()+1); ++j){
                // Add the ring to the combo
                int[] stat = Arrays.copyOf(ring,3);
                for (int k=0; k<3; ++k){
                    stat[k] += stats.get(j)[k];
                }
                // Add the combo (only one ring)
                stats.add(stat);
            }
            // Loop through every other future ring
            for (int j=i+1; j<rings.size(); ++j){
                // Loop through every weapon armor combo
                for (int k=0; k<weapons.size()*(armors.size()+1); ++k){
                    // Add the ring to the combo
                    int[] stat = Arrays.copyOf(ring,3);
                    for (int l=0; l<3; ++l){
                        stat[l] += stats.get(k)[l] + rings.get(j)[l];
                    }
                    // Add the combo (all pieces)
                    stats.add(stat);
                }
            }
        }

        // Loop through every pair of stats bidirectionally
        for (int i=0; i<stats.size(); ++i){
            for (int j=0; j<stats.size(); ++j){
                // While i is at least as good as j in everything
                while (i != j && j < stats.size() && i < stats.size()
                && stats.get(i)[1] >= stats.get(j)[1]
                && stats.get(i)[2] >= stats.get(j)[2]
                && stats.get(i)[0] <= stats.get(j)[0]){
                    // Part 1 finds the cheapest win
                    if (PART == 1){
                        // Remove the worse one
                        stats.remove(j);
                    }

                    // Part 2 finds the most expensive loss
                    if (PART == 2){
                        // Remove the better one
                        stats.remove(i);
                    }
                }
            }
        }

        // The answer to the problem, default to big or small depending
        int cost = Integer.MAX_VALUE;

        if (PART == 2){
            cost = 0;
        }

        // Loop through every stat
        for (int i=0; i<stats.size(); ++i){
            // Get the variables
            int youHP = 100;
            int bossHP = hp;
            int youDamage = stats.get(i)[1] - armor;
            if (youDamage < 1){
                youDamage = 1;
            }
            int bossDamage = damage - stats.get(i)[2];
            if (bossDamage < 1){
                bossDamage = 1;
            }
            // While the battle isn't done
            while (youHP > 0){
                // You attack
                bossHP -= youDamage;
                // If the boss loses, break
                if (bossHP <= 0){
                    break;
                }
                // The boss attacks
                youHP -= bossDamage;
            }

            if (PART == 1){
                // If you won and it was cheaper
                if (bossHP <= 0 && stats.get(i)[0] < cost){
                    // Save the cost
                    cost = stats.get(i)[0];
                }
            }

            if (PART == 2){
                // If the boss won and it was more expensive
                if (youHP <= 0 && stats.get(i)[0] > cost){
                    // Save the cost
                    cost = stats.get(i)[0];
                }
            }
        }

        // Print the answer
        System.out.println(cost);
    }
}