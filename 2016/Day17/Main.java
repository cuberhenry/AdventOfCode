import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 17: Two Steps Forward";
    private static Scanner sc;
    public static void main(String args[]) {
        sc = Library.getScanner(args);

        // The password for the hashes
        String password = sc.next();
        // Breadth first search
        LinkedList<int[]> coordinates = new LinkedList<>();
        LinkedList<String> queue = new LinkedList<>();
        // Add the first state
        coordinates.add(new int[] {0,0});
        queue.add("");
        // The current longest path
        String part1 = "";
        int part2 = 0;

        // Continue until every path has been searched
        while (!queue.isEmpty()){
            // Grab the current state
            String path = queue.remove();
            int[] position = coordinates.remove();
            int x = position[0];
            int y = position[1];

            // If the vault has been reached
            if (x == 3 && y == 3){
                if (part1.isBlank()){
                    part1 = path;
                }
                part2 = path.length();

                continue;
            }

            // Take the current hash string
            String hash = Library.md5(password + path,true);

            // If there is a door up and it's unlocked
            if (hash.charAt(0) > 'a' && y > 0){
                coordinates.add(new int[] {x,y-1});
                queue.add(path + "U");
            }

            // If there is a door down and it's unlocked
            if (hash.charAt(1) > 'a' && y < 3){
                coordinates.add(new int[] {x,y+1});
                queue.add(path + "D");
            }

            // If there is a door left and it's unlocked
            if (hash.charAt(2) > 'a' && x > 0){
                coordinates.add(new int[] {x-1,y});
                queue.add(path + "L");
            }

            // If there is a door right and it's unlocked
            if (hash.charAt(3) > 'a' && x < 3){
                coordinates.add(new int[] {x+1,y});
                queue.add(path + "R");
            }
        }

        // Print the length of the longest path
        Library.print(part1,part2,name);
    }
}