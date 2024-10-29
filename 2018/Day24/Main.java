/*
Henry Anderson
Advent of Code 2018 Day 24 https://adventofcode.com/2018/day/24
Input: https://adventofcode.com/2018/day/24/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // A class representing a group of units
    private static class Group {
        // The number of units in the group
        private final int units;
        // The number of units that have died
        private int deadUnits;
        // The health of each unit
        private final int hp;
        // The attack damage for all units
        private int attack;
        // The type of damage being dealt
        private final String type;
        // An ordering tool for turns
        private final int initiative;
        // Damage types that this group is weak or immune to
        private ArrayList<String> weaknesses = new ArrayList<>();
        private ArrayList<String> immunities = new ArrayList<>();
        // The amount of damage dealt in a normal attack
        private int effectivePower;

        // The group that this group will attack
        private Group target;
        // Whether this group has been selected for attack
        private boolean selected;

        // Create a group from a line of input
        public Group(String line){
            // Split the line, removing extra punctuation
            String[] split = line.split("[)] | [(]|, |; | ");
            // Get the first two values
            units = Integer.parseInt(split[0]);
            hp = Integer.parseInt(split[4]);
            int pointer = 7;
            // Get the immunities and/or weaknesses if they exist
            while (split[pointer].equals("immune") || split[pointer].equals("weak")){
                ArrayList<String> list = new ArrayList<>();
                int index = pointer + 2;
                // Break point for where the damage types end
                while (!"immune weak with".contains(split[index])){
                    list.add(split[index]);
                    ++index;
                }
                // Assign the list
                if (split[pointer].equals("weak")){
                    weaknesses = list;
                }else{
                    immunities = list;
                }
                pointer = index;
            }
            // Get the last three values
            attack = Integer.parseInt(split[pointer + 5]);
            type = split[pointer + 6];
            initiative = Integer.parseInt(split[pointer + 10]);
            // Calculate the initial effective power
            effectivePower = units * attack;
        }

        // Returns how many units are currently alive
        public int getUnits(){
            return units - deadUnits;
        }

        // Getter for initiative
        public int getInitiative(){
            return initiative;
        }

        // Getter for effective power
        public int getEffectivePower(){
            return effectivePower;
        }

        // Whether this group is weak to the attack type s
        public boolean isWeak(String s){
            return weaknesses.contains(s);
        }

        // Whether this group is immune to the attack type s
        public boolean isImmune(String s){
            return immunities.contains(s);
        }

        // Getter for target
        public Group getTarget(){
            return target;
        }

        // Indicates that this group has been selected
        public void select(){
            selected = true;
        }

        // Getter for selected
        public boolean isSelected(){
            return selected;
        }

        // Choose an enemy to target, takes in the list of active enemies
        public void chooseTarget(ArrayList<Group> enemy){
            // The damage that can be dealt to target
            int maxDamage = 0;

            // Loop through every enemy
            for (Group group : enemy){
                // Skip enemies that can't be targeted or damaged
                if (group.isSelected() || group.isImmune(type)){
                    continue;
                }
                // Default damage is effective power
                int damage = effectivePower;
                // Weakness doubles the damage
                if (group.isWeak(type)){
                    damage *= 2;
                }
                // Choose the target that will receive the maximum damage dealt
                if (damage > maxDamage){
                    maxDamage = damage;
                    target = group;
                }
            }
            
            // Let the target know it's been selected
            if (target != null){
                target.select();
            }
        }

        // Deal damage to the target, returns whether target is destroyed
        public boolean attack(){
            // Skip groups that don't have a target
            if (target == null){
                return false;
            }
            // Default damage is effective power
            int damage = effectivePower;
            // Weakness doubles damage
            if (target.isWeak(type)){
                damage *= 2;
            }
            // Damage the target
            return target.defend(damage);
        }

        // Defend against a given amount of damage, returns whether the group was destroyed
        public boolean defend(int damage){
            // Only kill a whole number of units
            int numKilled = damage / hp;
            // If the group has been destroyed, return true
            if (numKilled >= (units - deadUnits)){
                return true;
            }
            // Kill the units
            deadUnits += numKilled;
            // Set the new effective power based on the new number of alive units
            effectivePower = (units - deadUnits) * attack;
            // Wasn't destroyed
            return false;
        }

        // Resets the group for the next target selection sequence
        public void clear(){
            target = null;
            selected = false;
        }

        // Increments attack damage
        public void boost(){
            ++attack;
        }

        // Resets the entire battle by resurrecting units
        public void reset(){
            clear();
            deadUnits = 0;
            effectivePower = units * attack;
        }
    }

    // A comparator for Group, ordering by effective power then initiative descending
    private static class GroupComparator implements Comparator<Group> {
        public int compare(Group a, Group b){
            if (a.getEffectivePower() > b.getEffectivePower()){
                return -1;
            }
            if (a.getEffectivePower() < b.getEffectivePower()){
                return 1;
            }

            if (a.getInitiative() > b.getInitiative()){
                return -1;
            }
            return 1;
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
        // All of the active immune system groups
        ArrayList<Group> immuneSystem = new ArrayList<>();
        // All of the active infection groups
        ArrayList<Group> infection = new ArrayList<>();
        // Comparator for ordering those two lists
        GroupComparator defaultComp = new GroupComparator();
        sc.nextLine();
        // Take in all immune system groups
        String line = sc.nextLine();
        while (!line.equals("")){
            // Parse the line into a group
            immuneSystem.add(new Group(line));
            line = sc.nextLine();
        }
        sc.nextLine();
        // Take in all the infection groups
        while (sc.hasNext()){
            // Parse the line into a group
            infection.add(new Group(sc.nextLine()));
        }

        // All of the active groups, sorted by initiative (for battles)
        ArrayList<Group> allGroups = new ArrayList<>(immuneSystem);
        allGroups.addAll(infection);
        allGroups.sort((a,b) -> {
            return a.getInitiative() < b.getInitiative() ? 1 : -1;
        });

        // Copy all three lists for easy reset
        ArrayList<Group> immuneCopy = new ArrayList<>(immuneSystem);
        ArrayList<Group> infectionCopy = new ArrayList<>(infection);
        ArrayList<Group> allCopy = new ArrayList<>(allGroups);

        // Continue until one group has won
        while (!immuneSystem.isEmpty() && !infection.isEmpty()){
            // Sort both groups
            immuneSystem.sort(defaultComp);
            infection.sort(defaultComp);

            // Have each group choose a target in order
            for (Group group : immuneSystem){
                group.chooseTarget(infection);
            }
            for (Group group : infection){
                group.chooseTarget(immuneSystem);
            }

            // Count the total number of alive units
            int numUnits = 0;
            for (Group group : allGroups){
                numUnits += group.getUnits();
            }

            // Loop through every group
            for (int i=0; i<allGroups.size(); ++i){
                Group group = allGroups.get(i);
                // Attack, if the target was destroyed
                if (group.attack()){
                    int index = allGroups.indexOf(group.getTarget());
                    // Remove it from the alive groups
                    allGroups.remove(group.getTarget());
                    immuneSystem.remove(group.getTarget());
                    infection.remove(group.getTarget());
                    // Update the index if it was a previous group
                    if (index < i){
                        --i;
                    }
                }
                // Clear for the next target selection phase
                group.clear();
            }

            // Count the remaining units
            for (Group group : allGroups){
                numUnits -= group.getUnits();
            }

            // Infinite loop catch, give victory to the infection
            if (numUnits == 0){
                immuneSystem.clear();
            }

            // Part 1 finds the number of remaining units after the battle
            // Part 2 finds the same after giving the immune system a minimum boost to win
            if (PART == 2){
                // If the immune system lost
                if (immuneSystem.isEmpty()){
                    // Resurrect destroyed groups
                    immuneSystem = new ArrayList<>(immuneCopy);
                    infection = new ArrayList<>(infectionCopy);
                    allGroups = new ArrayList<>(allCopy);
                    // Boost all immune system attacks by 1
                    for (Group group : immuneSystem){
                        group.boost();
                    }
                    // Reset each group
                    for (Group group : allGroups){
                        group.reset();
                    }
                }
            }
        }

        // Count all the units
        int numUnits = 0;
        for (Group group : allGroups){
            numUnits += group.getUnits();
        }
        // Print the answer
        System.out.println(numUnits);
    }
}