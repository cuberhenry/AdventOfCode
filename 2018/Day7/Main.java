import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.HashSet;

public class Main {
    final private static String name = "Day 7: The Sum of Its Parts";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The steps that can start being completed
        HashSet<Character> ready = new HashSet<>();
        // All of the steps that must be completed before each step
        String[] prevs = new String[26];
        for (int i=0; i<26; ++i){
            prevs[i] = "";
        }

        // Take in all input
        while (sc.hasNext()){
            // Get the step letters
            String line = sc.nextLine();
            char prereq = line.charAt(5);
            char step = line.charAt(36);

            // Update prevs
            prevs[step-'A'] += prereq;
        }

        // Steps with no prerequisites start out ready
        for (int i=0; i<26; ++i){
            if (prevs[i].equals("")){
                ready.add((char)(i+'A'));
            }
        }

        // Make a copy
        HashSet<Character> copy = new HashSet<>(ready);

        // The order
        String part1 = "";

        // While there is a step to be completed
        while (!ready.isEmpty()){
            // Find the first available step alphabetically
            char first = 'Z';
            for (char c : ready){
                if (c < first){
                    first = c;
                }
            }
            // Add it to the order, remove it from ready
            part1 += first;
            ready.remove(first);

            // Loop through every step
            for (int i=0; i<26; ++i){
                // Skip steps that are completed or ready
                if (part1.indexOf((char)(i+'A')) != -1 || ready.contains((char)(i+'A'))){
                    continue;
                }
                // Whether this step is ready
                boolean good = true;
                // If all prerequisites are completed, it's ready
                for (int j=0; j<prevs[i].length(); ++j){
                    if (part1.indexOf(prevs[i].charAt(j)) == -1){
                        good = false;
                        break;
                    }
                }
                if (good){
                    // Add it to ready
                    ready.add((char)(i+'A'));
                }
            }
        }

        // Reset the start
        ready = copy;

        // How complete the steps are
        int[] status = new int[26];
        for (int i=0; i<26; ++i){
            // All steps start as unstarted
            status[i] = -1;
        }

        // The amount of time it takes
        int part2;
        for (part2 = 1;; ++part2){
            // The number of workers actively working
            int numWorkers = 0;
            // Decrease active step timers by one
            for (int i=0; i<26; ++i){
                if (status[i] > 0){
                    if (status[i] > 1){
                        ++numWorkers;
                    }
                    --status[i];
                }
            }

            // Start new steps
            while (numWorkers < 5 && !ready.isEmpty()){
                // Find the first available step alphabetically
                char first = 'Z';
                for (char c : ready){
                    if (c < first){
                        first = c;
                    }
                }
                // Start the timer
                status[first-'A'] = 60 + first-'A';
                // Remove from ready
                ready.remove(first);
                ++numWorkers;
            }

            // Loop through every step
            for (int i=0; i<26; ++i){
                // Skip steps that are completed, in progress, or ready
                if (status[i] != -1 || ready.contains((char)(i+'A'))){
                    continue;
                }
                // Whether this step is ready
                boolean good = true;
                // If all prerequisites are completed, it's ready
                for (int j=0; j<prevs[i].length(); ++j){
                    if (status[prevs[i].charAt(j)-'A'] != 0){
                        good = false;
                        break;
                    }
                }
                if (good){
                    // Add it to ready
                    ready.add((char)(i+'A'));
                }
            }

            // If no one is working and no steps are ready, break
            if (numWorkers == 0 && ready.isEmpty()){
                break;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}