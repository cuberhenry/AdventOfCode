import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 5: Print Queue";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // The list of pages that must come before each page
        HashMap<Integer,HashSet<Integer>> before = new HashMap<>();
        String rule = sc.nextLine();
        // Take in each rule
        while (!rule.isBlank()){
            int[] split = Library.intSplit(rule,"\\|");
            // Add a new set if it doesn't exist yet
            if (!before.containsKey(split[1])){
                before.put(split[1],new HashSet<Integer>());
            }

            // Add the new rule
            before.get(split[1]).add(split[0]);
            rule = sc.nextLine();
        }

        // Take in each update
        while (sc.hasNext()){
            int[] update = Library.intSplit(sc.nextLine(),",");
            // Whether the update is in order
            boolean good = true;
            // Loop through each pair of pages
            for (int i=0; i<update.length; ++i){
                for (int j=i+1; j<update.length; ++j){
                    // If they're not in order, reorder
                    if (before.containsKey(update[i]) && before.get(update[i]).contains(update[j])){
                        // Grab the last item
                        int helper = update[j];
                        // Move all the items back
                        while (j > i){
                            update[j] = update[j-1];
                            --j;
                        }
                        // Put the last item at the front
                        update[i] = helper;

                        // Not in order
                        good = false;
                    }
                }
            }

            // Add if they're in order
            if (good){
                part1 += update[update.length/2];
            }else{
                part2 += update[update.length/2];
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}