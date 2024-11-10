import com.aoc.mylibrary.Library;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 9: Marble Mania";
    public static void main(String args[]) {
        // Dissect the input
        String[] input = Library.getStringArray(args," ");
        int players = Integer.parseInt(input[0]);
        int marbles = Integer.parseInt(input[6]) + 1;
        int moreMarbles = (marbles-1) * 100 + 1;

        // The placed marbles
        LinkedList<Integer> circle = new LinkedList<>();
        circle.add(0);
        // The scores (ordering is irrelevant)
        long[] scores = new long[players];

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // Loop through every marble after the first
        for (int i=1; i<moreMarbles; ++i){
            if (i == marbles){
                for (long score : scores){
                    part1 = Math.max(score,part1);
                }
            }

            // Decide what to do
            if (i % 23 == 0){
                // Loop back 6 times
                circle.addFirst(circle.removeLast());
                circle.addFirst(circle.removeLast());
                circle.addFirst(circle.removeLast());
                circle.addFirst(circle.removeLast());
                circle.addFirst(circle.removeLast());
                circle.addFirst(circle.removeLast());
                // Add the score to the current elf's score
                scores[i%scores.length] += i + circle.removeLast();
            }else{
                // Loop forward twice
                circle.addLast(circle.removeFirst());
                circle.addLast(circle.removeFirst());
                // Add the new marble
                circle.addFirst(i);
            }
        }

        // Find the max score
        for (long score : scores){
            part2 = Math.max(score,part2);
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}