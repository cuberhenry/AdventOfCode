/*
Henry Anderson
Advent of Code 2018 Day 1 https://adventofcode.com/2018/day/1
Input: https://adventofcode.com/2018/day/1/input
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
        // All events in chronological order
        ArrayList<int[]> timeline = new ArrayList<>();
        // Take in every line
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            // Get a serialized version of the timestamp
            int time = Integer.parseInt(line.substring(6,8));
            time = Integer.parseInt(line.substring(9,11)) + time * 32;
            time = Integer.parseInt(line.substring(12,14)) + time * 24;
            time = Integer.parseInt(line.substring(15,17)) + time * 60;
            // Get the guard if there is one, otherwise the action
            int guard;
            if (line.indexOf("falls asleep") != -1){
                guard = -1;
            }else if (line.indexOf("wakes up") != -1){
                guard = 0;
            }else{
                guard = Integer.parseInt(line.substring(26,line.indexOf(" begins")));
            }

            // Add the item into the timeline chronologically
            int[] item = {time,guard};
            boolean inserted = false;
            for (int i=0; i<timeline.size(); ++i){
                if (item[0] < timeline.get(i)[0]){
                    timeline.add(i,item);
                    inserted = true;
                    break;
                }
            }
            if (!inserted){
                timeline.add(item);
            }
        }

        // The guards' schedules
        HashMap<Integer,int[]> schedule = new HashMap<>();
        // The guard on duty
        int guard = 0;
        // Whether the guard is asleep
        boolean asleep = false;
        // Go through the whole timeline
        for (int i=0; i<timeline.size(); ++i){
            // Get the minute and the action
            int minute = timeline.get(i)[0] % 60;
            int instruction = timeline.get(i)[1];

            // Reduce asleep time if the guard wakes up
            if (instruction == 0 && asleep){
                for (int j=minute; j<60; ++j){
                    --schedule.get(guard)[j];
                }
                asleep = false;
            // Increase asleep time if the guard falls asleep
            }else if (instruction == -1 && !asleep){
                for (int j=minute; j<60; ++j){
                    ++schedule.get(guard)[j];
                }
                asleep = true;
            // Swap guards
            }else if (instruction > 0){
                guard = instruction;
                if (!schedule.containsKey(guard)){
                    schedule.put(guard,new int[60]);
                }
                asleep = false;
            }
        }

        // Part 1 finds the minute that the guard who was asleep
        // the longest was asleep during the most
        if (PART == 1){
            // The number of minutes the guard was asleep
            int max = 0;
            // The guard that was asleep the longest
            guard = 0;
            // Loop through every guard
            for (int i : schedule.keySet()){
                // Get the total number of minutes asleep
                int total = 0;
                for (int j=0; j<60; ++j){
                    total += schedule.get(i)[j];
                }
                // If the guard was asleep the longest, record him
                if (total > max){
                    guard = i;
                    max = total;
                }
            }

            // The number of times the guard was asleep during the minute
            max = 0;
            int minute = 0;
            // Loop through every minute
            for (int i=0; i<60; ++i){
                // If the guard was asleep more, record it
                if (schedule.get(guard)[i] > max){
                    max = schedule.get(guard)[i];
                    minute = i;
                }
            }

            // Print the answer
            System.out.println(guard * minute);
        }

        // Part 2 finds the minute that a guard was most often asleep during
        if (PART == 2){
            // The minute that was asleep during the most by a guard
            int max = 0;
            int answer = 0;
            // Loop through every guard
            for (int i : schedule.keySet()){
                // Loop through every minute
                for (int j=0; j<60; ++j){
                    // If the guard was asleep most, record it
                    if (schedule.get(i)[j] > max){
                        max = schedule.get(i)[j];
                        answer = i*j;
                    }
                }
            }

            // Print the answer
            System.out.println(answer);
        }
    }
}