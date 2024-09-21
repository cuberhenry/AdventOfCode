/*
Henry Anderson
Advent of Code 2018 Day 7 https://adventofcode.com/2018/day/7
Input: https://adventofcode.com/2018/day/7/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
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
        // The steps that can start being completed
        String ready = "";
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
                ready += (char)(i+'A');
            }
        }

        // Part 1 finds the order the steps should be completed in
        if (PART == 1){
            // The order
            String order = "";

            // While there is a step to be completed
            while (ready.length() > 0){
                // Find the first available step alphabetically
                char first = 'Z';
                for (int i=0; i<ready.length(); ++i){
                    if (ready.charAt(i) < first){
                        first = ready.charAt(i);
                    }
                }
                // Add it to the order, remove it from ready
                order += first;
                ready = ready.substring(0,ready.indexOf(first)) + ready.substring(ready.indexOf(first)+1);

                // Loop through every step
                for (int i=0; i<26; ++i){
                    // Skip steps that are completed or ready
                    if (order.indexOf((char)(i+'A')) != -1 || ready.indexOf((char)(i+'A')) != -1){
                        continue;
                    }
                    // Whether this step is ready
                    boolean good = true;
                    // If all prerequisites are completed, it's ready
                    for (int j=0; j<prevs[i].length(); ++j){
                        if (order.indexOf(prevs[i].charAt(j)) == -1){
                            good = false;
                            break;
                        }
                    }
                    if (good){
                        // Add it to ready
                        ready += (char)(i+'A');
                    }
                }
            }

            // Print the answer
            System.out.println(order);
        }

        // Part 2 finds how long it takes 5 workers to finish all steps
        if (PART == 2){
            // How complete the steps are
            int[] status = new int[26];
            for (int i=0; i<26; ++i){
                // All steps start as unstarted
                status[i] = -1;
            }

            // The amount of time it takes
            int seconds;
            for (seconds = 1;; ++seconds){
                // The number of workers actively working
                int numWorkers = 0;
                // Decrease active step timers by one
                for (int i=0; i<26; ++i){
                    if (status[i] > 1){
                        ++numWorkers;
                    }
                    if (status[i] > 0){
                        --status[i];
                    }
                }

                // Start new steps
                while (numWorkers < 5 && ready.length() > 0){
                    // Find the first available step alphabetically
                    char first = 'Z';
                    for (int i=0; i<ready.length(); ++i){
                        if (ready.charAt(i) < first){
                            first = ready.charAt(i);
                        }
                    }
                    // Start the timer
                    status[first-'A'] = 60 + first-'A';
                    // Remove from ready
                    ready = ready.substring(0,ready.indexOf(first)) + ready.substring(ready.indexOf(first)+1);
                    ++numWorkers;
                }

                // Loop through every step
                for (int i=0; i<26; ++i){
                    // Skip steps that are completed, in progress, or ready
                    if (status[i] != -1 || ready.indexOf((char)(i+'A')) != -1){
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
                        ready += (char)(i+'A');
                    }
                }

                // If no one is working and no steps are ready, break
                if (numWorkers == 0 && ready.length() == 0){
                    break;
                }
            }

            // Print the answer
            System.out.println(seconds);
        }
    }
}