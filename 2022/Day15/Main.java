/*
Henry Anderson
Advent of Code 2022 Day 15 https://adventofcode.com/2022/day/15
Input: https://adventofcode.com/2022/day/15/input
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
        // The list of relative sensors
        ArrayList<String> sensors = new ArrayList<>();
        // The x location of known beacons at y=2_000_000
        ArrayList<Integer> beacons = new ArrayList<>();

        // While there's more input
        while (sc.hasNext()){
            // Take in the next sensor
            String line = sc.nextLine();
            // Split the line up and save the values
            line = line.substring(line.indexOf('=')+1);
            int sx = Integer.parseInt(line.substring(0,line.indexOf(',')));
            line = line.substring(line.indexOf('=')+1);
            int sy = Integer.parseInt(line.substring(0,line.indexOf(':')));
            line = line.substring(line.indexOf('=')+1);
            int bx = Integer.parseInt(line.substring(0,line.indexOf(',')));
            line = line.substring(line.indexOf('=')+1);
            int by = Integer.parseInt(line);
            // Calculate the distance between the sensor and the beacon
            int distance = Math.abs(sx-bx) + Math.abs(sy-by);

            // Save only the sensors that reach the relevant line
            if (PART == 1){
                // Save the beacon if it's a new beacon
                if (by == 2_000_000 && !beacons.contains(bx)){
                    beacons.add(bx);
                }

                if (sy+distance > 2_000_000 && sy-distance < 2_000_000){
                    // Save the values of the leftmost and rightmost reach of
                    // the sensor at y=2_000_000
                    sensors.add(sx-distance+Math.abs(2_000_000-sy) + " " + (sx+distance-Math.abs(2000000-sy)));
                }
            }

            // Save all of the sensors
            if (PART == 2){
                sensors.add(sx+" "+sy+" "+distance);
            }
        }
        
        // Part 1 finds the number of locations at y=2_000_000 that can't have a beacon
        if (PART == 1){
            // The number of locations
            long total = 0;

            // Loop through every beacon
            for (int i=1; i<sensors.size(); ++i){
                // Loop through every earlier beacon
                for (int j=0; j<i; ++j){
                    // Grab the values
                    String[] first = sensors.get(i).split(" ");
                    String[] second = sensors.get(j).split(" ");
                    int left1 = Integer.parseInt(first[0]);
                    int right1 = Integer.parseInt(first[1]);
                    int left2 = Integer.parseInt(second[0]);
                    int right2 = Integer.parseInt(second[1]);
                    // If the sensor reaches are overlapping
                    if (left1 <= right1 && left2 <= right2){
                        // Combine into one set
                        sensors.remove(i);
                        sensors.remove(j);
                        sensors.add(Math.min(left1,left2)+" "+Math.max(right1,right2));

                        // All of the beacons have been conglomerated
                        if (sensors.size() == 1){
                            break;
                        }
                        // Back up to recheck
                        i = 1;
                        j = -1;
                    }
                }
            }
            
            // Go through every remaining disconnected set and add the total
            for (int i=0; i<sensors.size(); ++i){
                int right = Integer.parseInt(sensors.get(i).split(" ")[1]);
                int left = Integer.parseInt(sensors.get(i).split(" ")[0]);
                total += right - left + 1;
                
                // If a beacon is within the limits, subtract one
                for (int j=0; j<beacons.size(); ++j){
                    if (beacons.get(j) >= left && beacons.get(j) <= right){
                        --total;
                    }
                }
            }

            // Print the total
            System.out.println(total);
        }
        
        // Part 2 finds the tuning frequency of the distress beacon
        if (PART == 2){
            // Go through every position on the map
            for (int i=0; i<=4_000_000; ++i){
                for (int j=0; j<=4_000_000; ++j){
                    // Represents whether this is where the beacon is
                    boolean good = true;
                    // Go through every sensor
                    for (int k=0; k<sensors.size() && good; ++k){
                        // Collect the values from the sensor
                        String[] sensor = sensors.get(k).split(" ");
                        int x = Integer.parseInt(sensor[0]);
                        int y = Integer.parseInt(sensor[1]);
                        int dist = Integer.parseInt(sensor[2]);
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
                        System.out.println((long)j*4_000_000L+(long)i);
                        return;
                    }
                }
            }
        }
    }
}