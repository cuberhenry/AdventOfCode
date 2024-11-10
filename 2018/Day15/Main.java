import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 15: Beverage Bandits";

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
        public void move(HashSet<ArrayState> grid, ArrayList<Unit> units){
            // The list of enemies' locations
            HashSet<ArrayState> targets = new HashSet<>();
            // Loop through each living unit
            for (Unit unit : units){
                // If it's a friendly, skip
                if (!(elf ^ unit.isElf())){
                    continue;
                }
                // Add its location
                targets.add(new ArrayState(new int[] {unit.getX(),unit.getY()}));
            }

            // The first step to be taken if the key is your destination
            HashMap<ArrayState,ArrayState> firstStep = new HashMap<>();
            // The list of locations to check
            LinkedList<ArrayState> queue = new LinkedList<>();
            // The nearest enemy to step towards
            ArrayList<ArrayState> nearest = new ArrayList<>();
            // Add the initial locations
            ArrayState initialState = new ArrayState(new int[] {x,y});
            ArrayState zeroState = new ArrayState(new int[] {0,0});
            queue.add(initialState);
            firstStep.put(initialState,zeroState);

            // Continue until enemies are found or no enemies can be found
            while (!queue.isEmpty() && nearest.isEmpty()){
                // Copy a new queue
                LinkedList<ArrayState> newQueue = new LinkedList<>();
                // Continue until this step has been finished
                while (!queue.isEmpty()){
                    // Get the coordinates
                    ArrayState currState = queue.remove();
                    int[] pos = currState.getArray();
                    // Look in each direction
                    for (int i=0; i<4; ++i){
                        int newX = pos[0];
                        int newY = pos[1];
                        switch (i){
                            case 0 -> {--newY;}
                            case 1 -> {--newX;}
                            case 2 -> {++newX;}
                            case 3 -> {++newY;}
                        }
                        ArrayState newState = new ArrayState(new int[] {newX,newY});
                        // If it's an enemy, add it as an option
                        if (targets.contains(newState)){
                            nearest.add(currState);
                        // Otherwise, if it's a wall, friendly, or already been searched, skip
                        }else if (grid.contains(newState) || getFromPos(newX,newY,units) != null
                            || firstStep.containsKey(newState)){
                            continue;
                        }
                        // Add the best first step
                        if (firstStep.get(currState).equals(zeroState)){
                            firstStep.put(newState,new ArrayState(new int[] {newX-pos[0],newY-pos[1]}));
                        }else{
                            firstStep.put(newState,firstStep.get(currState));
                        }
                        // Add the new location to the queue
                        newQueue.add(newState);
                    }
                }
                queue = newQueue;
            }

            // No possible moves
            if (nearest.isEmpty()){
                return;
            }
            // Get the first in reading order
            ArrayState min = nearest.getFirst();
            for (int i=1; i<nearest.size(); ++i){
                min = Unit.readingOrder(min,nearest.get(i));
            }

            // Get the first step for that destination
            int[] change = firstStep.get(min).getArray();

            // Move in that direction
            x += change[0];
            y += change[1];
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
        public static ArrayState readingOrder(ArrayState left, ArrayState right){
            int lY = left.getArray()[1];
            int rY = right.getArray()[1];
            if (lY < rY){
                return left;
            }
            if (lY > rY){
                return right;
            }
            return left.getArray()[0] < right.getArray()[0] ? left : right;
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

    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // Take in the map
        HashSet<ArrayState> grid = new HashSet<>();
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
                    grid.add(new ArrayState(new int[] {i,height}));
                }
            }
            ++height;
        }

        // The answer to the problem
        int part1 = simulate(grid,new ArrayList<>(units),false);
        int part2 = 0;

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
            int answer = simulate(grid,thisUnits,true);
            // If the elves won, print the answer
            if (answer > 0){
                part2 = answer;
                break;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }

    // Simulates combat
    private static int simulate(HashSet<ArrayState> grid, ArrayList<Unit> units, boolean noDeaths){
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
                    if (noDeaths){
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