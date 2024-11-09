import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 22: Sporifica Virus";
    public static void main(String[] args){
        // The infected nodes
        HashMap<ArrayState,Integer> infected = new HashMap<>();
        // The current position of the virus
        int y = 0;
        int x = 0;

        // Take in the input
        String[] input = Library.getStringArray(args,"\n");
        for (String line : input){
            x = line.length() / 2;

            // If the node is infected, add it to the list
            for (int i=0; i<line.length(); ++i){
                if (line.charAt(i) == '#'){
                    infected.put(new ArrayState(new int[] {i,y}),1);
                }
            }
            ++y;
        }

        y /= 2;
        // The direction the virus is facing
        int dir = 0;

        // Make a copy of starting values
        HashMap<ArrayState,Integer> copy = new HashMap<>(infected);
        int xStart = x;
        int yStart = y;

        // The number of infected nodes during a burst
        int part1 = 0;
        int part2 = 0;

        // Perform the simple simulation
        for (int i=0; i<10000; ++i){
            // The current state
            ArrayState state = new ArrayState(new int[] {x,y});
            // If the node isn't clean
            if (infected.containsKey(state)){
                // Clean it and turn right
                infected.remove(state);
                dir = (dir + 1) % 4;
            }else{
                // Turn left
                dir = (dir + 3) % 4;
                // Infect
                infected.put(state,0);
                ++part1;
            }

            // Move in the given direction
            switch(dir){
                case 0 -> {--y;}
                case 1 -> {++x;}
                case 2 -> {++y;}
                case 3 -> {--x;}
            }
        }

        // Reset
        infected = copy;
        x = xStart;
        y = yStart;

        // Perform the complicated simulation
        for (int i=0; i<10000000; ++i){
            // The current state
            ArrayState state = new ArrayState(new int[] {x,y});
            // If the node isn't clean
            if (infected.containsKey(state)){
                // Clean it and turn right
                switch(infected.get(state)){
                    // Is weakened, infect it and don't turn
                    case 0 -> {
                        infected.put(state,1);
                        ++part2;
                    }
                    // Is infected, flag it and turn right
                    case 1 -> {
                        dir = (dir + 1) % 4;
                        infected.put(state,2);
                    }
                    // Is flagged, clean it and reverse
                    case 2 -> {
                        dir = (dir + 2) % 4;
                        infected.remove(state);
                    }
                }
            }else{
                // Turn left
                dir = (dir + 3) % 4;
                // Infect (Part 1) or weaken (Part 2)
                infected.put(state,0);
            }

            // Move in the given direction
            switch(dir){
                case 0 -> {--y;}
                case 1 -> {++x;}
                case 2 -> {++y;}
                case 3 -> {--x;}
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}