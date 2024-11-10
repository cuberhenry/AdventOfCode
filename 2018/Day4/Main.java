import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 4: Repose Record";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

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

            timeline.add(new int[] {time,guard});
        }

        // Sort the timeline
        Collections.sort(timeline,(a,b) -> {
            return Integer.compare(a[0],b[0]);
        });

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

        // Get the answer
        int part1 = guard * minute;

        // The minute that was asleep during the most by a guard
        max = 0;
        int part2 = 0;
        // Loop through every guard
        for (int i : schedule.keySet()){
            // Loop through every minute
            for (int j=0; j<60; ++j){
                // If the guard was asleep most, record it
                if (schedule.get(i)[j] > max){
                    max = schedule.get(i)[j];
                    part2 = i*j;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}