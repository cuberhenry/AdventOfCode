/*
Henry Anderson
Advent of Code 2018 Day 15 https://adventofcode.com/2018/day/15
Input: https://adventofcode.com/2018/day/15/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // A class representing a unit (goblin or elf)
    private static class Unit {
        // The starting coordinates
        private int startX;
        private int startY;
        // The current coordinates
        private int x;
        private int y;
        // Whether this unit is an elf
        private boolean elf;
        // Current health
        private int health = 200;
        // Current attack
        private int attack = 3;

        // Constructor with coordinates and type
        public Unit(int x, int y, boolean e){
            startX = x;
            startY = y;
            this.x = x;
            this.y = y;
            elf = e;
        }

        // Getter for x
        public int getX(){
            return x;
        }

        // Getter for y
        public int getY(){
            return y;
        }

        // Getter for elf
        public boolean isElf(){
            return elf;
        }

        // Getter for health
        public int getHealth(){
            return health;
        }

        // Perform the move section of unit's turn
        public void move(HashSet<String> grid, ArrayList<Unit> units){
            // The list of enemies' locations
            HashSet<String> targets = new HashSet<>();
            // Loop through each living unit
            for (Unit unit : units){
                // If it's a friendly, skip
                if (!(elf ^ unit.isElf())){
                    continue;
                }
                // Add its location
                targets.add(unit.getX() + " " + unit.getY());
            }

            // The first step to be taken if the key is your destination
            HashMap<String,String> firstStep = new HashMap<>();
            // The list of locations to check
            LinkedList<String> queue = new LinkedList<>();
            // The nearest enemy to step towards
            ArrayList<String> nearest = new ArrayList<>();
            // Add the initial locations
            queue.add(x + " " + y);
            firstStep.put(x + " " + y,"0 0");

            // Continue until enemies are found or no enemies can be found
            while (!queue.isEmpty() && nearest.isEmpty()){
                // Copy a new queue
                LinkedList<String> newQueue = new LinkedList<>();
                // Continue until this step has been finished
                while (!queue.isEmpty()){
                    // Get the coordinates
                    String[] split = queue.remove().split(" ");
                    int x = Integer.parseInt(split[0]);
                    int y = Integer.parseInt(split[1]);
                    // Look in each direction
                    for (int i=0; i<4; ++i){
                        int newX = x;
                        int newY = y;
                        switch (i){
                            case 0 -> {--newY;}
                            case 1 -> {--newX;}
                            case 2 -> {++newX;}
                            case 3 -> {++newY;}
                        }
                        // If it's an enemy, add it as an option
                        if (targets.contains(newX + " " + newY)){
                            nearest.add(x + " " + y);
                        // Otherwise, if it's a wall, friendly, or already been searched, skip
                        }else if (grid.contains(newX + " " + newY) || getFromPos(newX,newY,units) != null
                            || firstStep.containsKey(newX + " " + newY)){
                            continue;
                        }
                        // Add the best first step
                        if (firstStep.get(x + " " + y).equals("0 0")){
                            firstStep.put(newX + " " + newY,(newX-x) + " " + (newY-y));
                        }else{
                            firstStep.put(newX + " " + newY,firstStep.get(x + " " + y));
                        }
                        // Add the new location to the queue
                        newQueue.add(newX + " " + newY);
                    }
                }
                queue = newQueue;
            }

            // No possible moves
            if (nearest.isEmpty()){
                return;
            }
            // Get the first in reading order
            String min = nearest.get(0);
            for (int i=1; i<nearest.size(); ++i){
                min = Unit.readingOrder(min,nearest.get(i));
            }

            // Get the first step for that destination
            min = firstStep.get(min);

            // Move in that direction
            if (min.equals("0 -1")){
                --y;
            }else if (min.equals("-1 0")){
                --x;
            }else if (min.equals("1 0")){
                ++x;
            }else if (min.equals("0 1")){
                ++y;
            }
        }

        // Perform the attack section of unit's turn
        public Unit attack(ArrayList<Unit> units){
            // The unit to attack
            Unit target = null;
            // Look in each direction
            for (int i=0; i<4; ++i){
                int newX = x;
                int newY = y;
                switch (i){
                    case 0 -> {--newY;}
                    case 1 -> {--newX;}
                    case 2 -> {++newX;}
                    case 3 -> {++newY;}
                }
                // Get any unit at that location
                Unit newTarget = getFromPos(newX,newY,units);
                // If there's an enemy there with less health than the current target, choose it
                if (newTarget != null && (elf ^ newTarget.isElf()) && (target == null || newTarget.getHealth() < target.getHealth())){
                    target = newTarget;
                }
            }
            // If the target dies, return it
            if (target != null && target.defend(attack)){
                return target;
            }
            // Otherwise, return nothing
            return null;
        }

        // Defend against an attack, returning true if dead
        public boolean defend(int attack){
            health -= attack;
            return health <= 0;
        }

        // Reset the unit to its factory settings
        public void reset(){
            x = startX;
            y = startY;
            health = 200;
        }

        // Reset the unit but with the given attack
        public void reset(int a){
            reset();
            attack = a;
        }

        // Returns the coordinates that are first in reading order
        public static String readingOrder(String left, String right){
            String[] lSplit = left.split(" ");
            String[] rSplit = right.split(" ");
            int lY = Integer.parseInt(lSplit[1]);
            int rY = Integer.parseInt(rSplit[1]);
            if (lY < rY){
                return left;
            }
            if (lY > rY){
                return right;
            }
            return Integer.parseInt(lSplit[0]) < Integer.parseInt(rSplit[0]) ? left : right;
        }

        // Gets a unit from an x and y
        public static Unit getFromPos(int x, int y, ArrayList<Unit> units){
            for (Unit unit : units){
                if (unit.getX() == x && unit.getY() == y){
                    return unit;
                }
            }
            return null;
        }

        // Whether the battle should continue
        public static boolean battleContinues(ArrayList<Unit> units){
            boolean hasElf = false;
            boolean hasGoblin = false;
            for (Unit unit : units){
                if (unit.isElf()){
                    hasElf = true;
                }else{
                    hasGoblin = true;
                }
            }
            return hasElf && hasGoblin;
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
        // Take in the map
        HashSet<String> grid = new HashSet<>();
        // All units
        ArrayList<Unit> units = new ArrayList<>();
        int height = 0;
        // Take in every line
        while (sc.hasNext()){
            String line = sc.nextLine();
            // Loop through every character
            for (int i=0; i<line.length(); ++i){
                // If it's a unit
                if ("EG".contains(""+line.charAt(i))){
                    // Add the newly created unit
                    units.add(new Unit(i,height,line.charAt(i) == 'E'));
                }else if (line.charAt(i) == '#'){
                    // Add the wall
                    grid.add(i + " " + height);
                }
            }
            ++height;
        }

        // Part 1 finds the combat outcome given the start
        if (PART == 1){
            System.out.println(simulate(grid,units));
        }

        // Part 2 finds the outcome from giving the elves the minimum attack
        // boost they need to not let any of them die
        if (PART == 2){
            // Loop through each attack between 3 and every unit's health
            for (int i=4; i<=200; ++i){
                // Get a temporary array containing the currently alive units
                ArrayList<Unit> thisUnits = new ArrayList<>(units);
                // Reset all units
                for (Unit unit : units){
                    // Give elves an attack boost
                    if (unit.isElf()){
                        unit.reset(i);
                    }else{
                        unit.reset();
                    }
                }
                // Simulate the combat
                int answer = simulate(grid,thisUnits);
                // If the elves won, print the answer
                if (answer > 0){
                    System.out.println(answer);
                    break;
                }
            }
        }
    }

    // Simulates combat
    private static int simulate(HashSet<String> grid, ArrayList<Unit> units){
        // Continue until the battle finishes
        for (int rounds=0; true; ++rounds){
            // Sort the units in reading order
            units.sort((a,b) -> {
                int aY = a.getY();
                int bY = b.getY();
                if (aY < bY){
                    return -1;
                }
                if (aY > bY){
                    return 1;
                }
                return a.getX() < b.getX() ? -1 : 1;
            });

            // Loop through each unit
            for (int i=0; i<units.size(); ++i){
                // Stop if there are no enemies to attack
                if (!Unit.battleContinues(units)){
                    // Get the sum of all remaining units' health
                    int answer = 0;
                    for (Unit unit : units){
                        answer += unit.getHealth();
                    }
                    // Multiply by the number of completed rounds
                    return answer * rounds;
                }

                // Get the unit
                Unit unit = units.get(i);
                // Move the unit and record whether it moved
                unit.move(grid,units);
                // Get the result of the unit's attack
                Unit target = unit.attack(units);
                // If the target died
                if (target != null){
                    // Part 2 requires that no elf dies
                    if (PART == 2){
                        // If an elf died
                        if (target.isElf()){
                            // Return failure
                            return -1;
                        }
                    }

                    // Remove the dead unit
                    units.remove(target);
                    i = units.indexOf(unit);
                }
            }
        }
    }
}