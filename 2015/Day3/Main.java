import com.aoc.mylibrary.Library;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 3: Perfectly Spherical Houses in a Vacuum";
    public static void main(String args[]) {
        // Take in the input
        String input = Library.getString(args);

        // Print the answer
        Library.print(part1(input),part2(input),name);
    }

    private static int part1(String input){
        // All of the houses that received at least one present
        HashSet<String> delivered = new HashSet<>();
        // Santa's position
        int x = 0;
        int y = 0;
        // The current house gets a present
        delivered.add("0 0");

        // For every instruction
        for (char c : input.toCharArray()){
            // Move Santa
            switch (c){
                case '^' -> {--y;}
                case 'v' -> {++y;}
                case '<' -> {--x;}
                case '>' -> {++x;}
            }

            // Deliver a present
            delivered.add(x+" "+y);
        }

        return delivered.size();
    }

    private static int part2(String input){
        // All of the houses that received at least one present
        HashSet<String> delivered = new HashSet<>();
        // Santa's position
        int x = 0;
        int y = 0;
        // Robot Santa's position
        int robX = 0;
        int robY = 0;
        // The current house gets a present
        delivered.add("0 0");

        // For every instruction
        for (int i=0; i<input.length(); i+=2){
            // Move Santa
            switch (input.charAt(i)){
                case '^' -> {--y;}
                case 'v' -> {++y;}
                case '<' -> {--x;}
                case '>' -> {++x;}
            }

            // Deliver a present
            delivered.add(x+" "+y);

            // Move Robo-Santa
            
            switch (input.charAt(i+1)){
                case '^' -> {--robY;}
                case 'v' -> {++robY;}
                case '<' -> {--robX;}
                case '>' -> {++robX;}
            }

            // Deliver a present
            delivered.add(robX+" "+robY);
        }

        return delivered.size();
    }
}