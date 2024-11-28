import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.ArrayState;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 15: Beacon Exclusion Zone";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The list of relative sensors
        ArrayList<ArrayState> sensors = new ArrayList<>();
        ArrayList<String> relatives = new ArrayList<>();
        // The x location of known beacons at y=2_000_000
        ArrayList<Integer> beacons = new ArrayList<>();

        // While there's more input
        while (sc.hasNext()){
            // Take in the next sensor
            int[] coordinates = Library.intSplit(sc.nextLine().substring(12),", y=|: closest beacon is at x=");
            // Split the line up and save the values
            int sx = coordinates[0];
            int sy = coordinates[1];
            int bx = coordinates[2];
            int by = coordinates[3];
            // Calculate the distance between the sensor and the beacon
            int distance = Math.abs(sx-bx) + Math.abs(sy-by);

            // Save the beacon if it's a new beacon
            if (by == 2_000_000 && !beacons.contains(bx)){
                beacons.add(bx);
            }

            if (sy+distance > 2_000_000 && sy-distance < 2_000_000){
                // Save the values of the leftmost and rightmost reach of
                // the sensor at y=2_000_000
                relatives.add(sx-distance+Math.abs(2_000_000-sy) + " " + (sx+distance-Math.abs(2000000-sy)));
            }

            // Save all of the sensors
            sensors.add(new ArrayState(new int[] {sx,sy,distance}));
        }

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;
        
        // Loop through every beacon
        for (int i=1; i<relatives.size(); ++i){
            // Loop through every earlier beacon
            for (int j=0; j<i; ++j){
                // Grab the values
                String[] first = relatives.get(i).split(" ");
                String[] second = relatives.get(j).split(" ");
                int left1 = Integer.parseInt(first[0]);
                int right1 = Integer.parseInt(first[1]);
                int left2 = Integer.parseInt(second[0]);
                int right2 = Integer.parseInt(second[1]);
                // If the sensor reaches are overlapping
                if (left1 <= right1 && left2 <= right2){
                    // Combine into one set
                    relatives.remove(i);
                    relatives.remove(j);
                    relatives.add(Math.min(left1,left2)+" "+Math.max(right1,right2));

                    // All of the beacons have been conglomerated
                    if (relatives.size() == 1){
                        break;
                    }
                    // Back up to recheck
                    i = 1;
                    j = -1;
                }
            }
        }
        
        // Go through every remaining disconnected set and add the total
        for (int i=0; i<relatives.size(); ++i){
            int right = Integer.parseInt(relatives.get(i).split(" ")[1]);
            int left = Integer.parseInt(relatives.get(i).split(" ")[0]);
            part1 += right - left + 1;
            
            // If a beacon is within the limits, subtract one
            for (int j=0; j<beacons.size(); ++j){
                if (beacons.get(j) >= left && beacons.get(j) <= right){
                    --part1;
                }
            }
        }
        
        // Go through every position on the map
        for (int i=0; i<=4_000_000; ++i){
            for (int j=0; j<=4_000_000; ++j){
                // Represents whether this is where the beacon is
                boolean good = true;
                // Go through every sensor
                for (int k=0; k<sensors.size() && good; ++k){
                    // Collect the values from the sensor
                    int[] sensor = sensors.get(k).getArray();
                    int x = sensor[0];
                    int y = sensor[1];
                    int dist = sensor[2];
                    // If the sensor can't be relevant anymore, remove it
                    if (y + dist < i){
                        sensors.remove(k);
                        --k;
                        continue;
                    }
                    // If it's within a sensor's range
                    if (Math.abs(x-j)+Math.abs(y-i) <= dist){
                        // It's not the spot we're looking for
                        good = false;
                        // Skip past the horizontal reach of the sensor
                        j = x+dist-Math.abs(i-y);
                    }
                }
                // This is the spot we're looking for
                if (good){
                    // Print the tuning frequency
                    part2 = (long)j*4_000_000L+i;
                    i = 4000000;
                    break;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}