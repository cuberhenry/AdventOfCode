import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 16: The Floor Will Be Lava";
    public static void main(String args[]) {
        // The map of tiles
        char[][] map = Library.getCharMatrix(args);

        // The result of starting from each splitter
        HashMap<ArrayState,boolean[][]> splitters = new HashMap<>();

        // The number of energized tiles
        int part1 = splitters(new HashSet<ArrayState>(),map,splitters,0,0,1);
        long part2 = 0;

        // Check every direction to see which starting point returns the most
        for (int i=0; i<map.length; ++i){
            part2 = Math.max(part2,splitters(new HashSet<ArrayState>(),map,splitters,0,i,1));
            part2 = Math.max(part2,splitters(new HashSet<ArrayState>(),map,splitters,map.length-1,i,3));
        }
        for (int i=0; i<map[0].length; ++i){
            part2 = Math.max(part2,splitters(new HashSet<ArrayState>(),map,splitters,i,0,2));
            part2 = Math.max(part2,splitters(new HashSet<ArrayState>(),map,splitters,i,map[0].length-1,0));
        }

        // Print the answer
        Library.print(part1,part2,name);
    }

    public static int splitters(HashSet<ArrayState> prevSplitters, char[][] map, HashMap<ArrayState,boolean[][]> splitters, int i, int j, int d){
        // Add the current location as a visited location
        prevSplitters.add(new ArrayState(new int[] {i,j}));
        // All of the current energized locations
        boolean[][] energized = new boolean[map.length][map[0].length];
        // The beams to check
        LinkedList<ArrayState> beams = new LinkedList<>();
        beams.add(new ArrayState(new int[] {i,j,d}));
        // The locations and directions of checked beams
        HashSet<ArrayState> history = new HashSet<>();

        // Continue until all beams have been resolved
        while (!beams.isEmpty()){
            // Get the current beam
            int[] beam = beams.remove().getArray();
            int x = beam[0];
            int y = beam[1];
            int dir = beam[2];

            // Continue until the beam falls off
            while (x >= 0 && y >= 0 && y < map.length && x < map[0].length){
                // Skip already discovered locations
                if (history.contains(new ArrayState(new int[] {x,y,dir}))){
                    break;
                }
                // Add the new location
                history.add(new ArrayState(new int[] {x,y,dir}));
                // Energize the new location
                energized[y][x] = true;
                // Move in the direction
                switch (map[y][x]){
                    case '/' -> {
                        switch (dir){
                            case 0 -> dir = 1;
                            case 1 -> dir = 0;
                            case 2 -> dir = 3;
                            case 3 -> dir = 2;
                        }
                    }
                    case '\\' -> {
                        dir = 3 - dir;
                    }
                    case '-' -> {
                        // Check if the splitter has already been resolved
                        if (prevSplitters.contains(new ArrayState(new int[] {x,y}))){
                            // Look in both directions
                            beams.add(new ArrayState(new int[] {x-1,y,3}));
                            dir = 1;
                        }else{
                            // Find the result at the new splitter
                            if (!splitters.containsKey(new ArrayState(new int[] {x,y}))){
                                splitters(prevSplitters,map,splitters,x,y,0);
                            }
                            // Combine the two maps
                            boolean[][] splitter = splitters.get(new ArrayState(new int[] {x,y}));
                            for (int a=0; a<splitter.length; ++a){
                                for (int b=0; b<splitter[a].length; ++b){
                                    energized[a][b] = energized[a][b] || splitter[a][b];
                                }
                            }
                            // Quit
                            x = -2;
                            y = -2;
                        }
                    }
                    case '|' -> {
                        // Check if the splitter has already been resolved
                        if (prevSplitters.contains(new ArrayState(new int[] {x,y}))){
                            // Look in both directions
                            beams.add(new ArrayState(new int[] {x,y-1,0}));
                            dir = 2;
                        }else{
                            // Find the result at the new splitter
                            if (!splitters.containsKey(new ArrayState(new int[] {x,y}))){
                                splitters(prevSplitters,map,splitters,x,y,0);
                            }
                            // Combine the two maps
                            boolean[][] splitter = splitters.get(new ArrayState(new int[] {x,y}));
                            for (int a=0; a<splitter.length; ++a){
                                for (int b=0; b<splitter[a].length; ++b){
                                    energized[a][b] = energized[a][b] || splitter[a][b];
                                }
                            }
                            // Quit
                            x = -2;
                            y = -2;
                        }
                    }
                }

                // Move in the given direction
                switch (dir){
                    case 0 -> --y;
                    case 1 -> ++x;
                    case 2 -> ++y;
                    case 3 -> --x;
                }
            }
        }

        // Add the result to the hash map
        splitters.put(new ArrayState(new int[] {i,j}),energized);

        // Find the number of energized tiles
        int total = 0;
        for (boolean[] row : energized){
            for (boolean bool : row){
                if (bool){
                    ++total;
                }
            }
        }
        // Return it
        return total;
    }
}