import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 4: Scratchcards";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        // Used for Part 2, the number of copies of each scratch card
        ArrayList<Integer> copies = new ArrayList<>();

        // Loop through every scratch card
        while (sc.hasNext()){
            // Increase the number of the current card by one for the original
            if (copies.isEmpty()){
                copies.add(1);
            }else{
                copies.set(0,copies.getFirst()+1);
            }

            // Add a space to standardize number checking to three characters
            String line = sc.nextLine() + " ";
            // The score of the card
            int score1 = 0;
            int score2 = 0;
            // Loop through every number that you have
            for (int i = line.indexOf('|') + 2; i < line.length(); i += 3){
                // If there's a matching number
                if (line.indexOf(line.substring(i,i+3)) < line.indexOf('|')){
                    // Increase the score
                    if (score1 == 0){
                        score1 = 1;
                    }else{
                        score1 *= 2;
                    }

                    // Increase the score
                    ++score2;
                }
            }
            
            // Add the score to the total
            part1 += score1;

            // Add the duplicate cards
            for (int i=1; i<=score2; ++i){
                if (i == copies.size()){
                    copies.add(copies.getFirst());
                }else{
                    copies.set(i,copies.get(i)+copies.getFirst());
                }
            }

            // Add the number of this scratch card owned
            part2 += copies.removeFirst();
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}