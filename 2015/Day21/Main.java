import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 21: RPG Simulator 20XX";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // Take in all input, skipping unneeded information
        sc.next();
        sc.next();
        // The boss' health points
        int inputHP = sc.nextInt();
        sc.next();
        // The boss' attack
        int inputDamage = sc.nextInt();
        sc.next();
        // The boss' armor
        int inputArmor = sc.nextInt();

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
        // No armor is an option
        armors.add(new int[] {0,0,0});

        // The list of all ring options
        ArrayList<int[]> rings = new ArrayList<>();
        // Add all of the rings
        rings.add(new int[] {25,1,0});
        rings.add(new int[] {50,2,0});
        rings.add(new int[] {100,3,0});
        rings.add(new int[] {20,0,1});
        rings.add(new int[] {40,0,2});
        rings.add(new int[] {80,0,3});
        // Can choose 0 or 1 rings
        rings.add(new int[] {0,0,0});
        rings.add(new int[] {0,0,0});

        // The list of all combinations
        ArrayList<int[]> stats = new ArrayList<>();

        // Loop through ever weapon option
        for (int[] weapon : weapons){
            // Loop through every armor
            for (int[] armor : armors){
                for (int i=0; i<rings.size(); ++i){
                    int[] ring1 = rings.get(i);
                    for (int j=i+1; j<rings.size(); ++j){
                        int[] ring2 = rings.get(j);
                        // Add the combo
                        stats.add(new int[] {weapon[0]+armor[0]+ring1[0]+ring2[0],
                                            weapon[1]+armor[1]+ring1[1]+ring2[1],
                                            weapon[2]+armor[2]+ring1[2]+ring2[2]});
                    }
                }
            }
        }

        // The answer to the problem, default to big or small depending
        int part1 = Integer.MAX_VALUE;
        int part2 = 0;

        // Loop through every stat
        for (int i=0; i<stats.size(); ++i){
            // Get the variables
            int youHP = 100;
            int bossHP = inputHP;
            int youDamage = stats.get(i)[1] - inputArmor;
            if (youDamage < 1){
                youDamage = 1;
            }
            int bossDamage = inputDamage - stats.get(i)[2];
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

            // If you won and it was cheaper
            if (bossHP <= 0 && stats.get(i)[0] < part1){
                // Save the cost
                part1 = stats.get(i)[0];
            }

            // If the boss won and it was more expensive
            if (youHP <= 0 && stats.get(i)[0] > part2){
                // Save the cost
                part2 = stats.get(i)[0];
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}