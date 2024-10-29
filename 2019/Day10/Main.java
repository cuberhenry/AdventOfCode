/*
Henry Anderson
Advent of Code 2019 Day 10 https://adventofcode.com/2019/day/10
Input: https://adventofcode.com/2019/day/10/input
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
        // The list of all asteroids
        ArrayList<String> asteroids = new ArrayList<>();
        // The size of the asteroid field
        int height = 0;
        int width = 0;
        // Take in all the input
        while (sc.hasNext()){
            String line = sc.nextLine();
            width = line.length();
            // Record all of the asteroids
            for (int i=0; i<line.length(); ++i){
                if (line.charAt(i) == '#'){
                    asteroids.add(i + " " + height);
                }
            }
            ++height;
        }

        // The asteroid that can see the most asteroids
        int max = 0;
        int stationX = 0;
        int stationY = 0;
        // Loop through every asteroid
        for (String asteroid : asteroids){
            // Get its coordinates
            String[] split = asteroid.split(" ");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            // Create a list of asteroids that are visible
            ArrayList<String> viewable = new ArrayList<>(asteroids);
            viewable.remove(asteroid);
            // Loop through every visible asteroid
            for (int i=0; i<viewable.size(); ++i){
                // Get its coordinates
                split = viewable.get(i).split(" ");
                int otherX = Integer.parseInt(split[0]);
                int otherY = Integer.parseInt(split[1]);
                // Identify the slope of the line between the two
                int changeX = Math.abs(otherX - x);
                int changeY = Math.abs(otherY - y);
                while (changeY != 0){
                    int helper = changeY;
                    changeY = changeX % changeY;
                    changeX = helper;
                }
                changeY = (otherY-y) / changeX;
                changeX = (otherX-x) / changeX;
                otherX += changeX;
                otherY += changeY;
                // Loop through every asteroid beyond this one
                while (otherX < width && otherX >= 0 && otherY < height && otherY >= 0){
                    int index = viewable.indexOf(otherX + " " + otherY);
                    // If one is found, remove it
                    if (index != -1){
                        if (index < i){
                            --i;
                        }
                        viewable.remove(index);
                    }
                    otherX += changeX;
                    otherY += changeY;
                }
            }
            // If this is the new best station, save its info
            if (viewable.size() > max){
                max = viewable.size();
                stationX = x;
                stationY = y;
            }
        }
        
        // Part 1 finds the number of asteroids that the most visible asteroid sees
        if (PART == 1){
            System.out.println(max);
        }

        // Part 2 finds the 200th asteroid to get destroyed by the laser
        if (PART == 2){
            // Create a list of the asteroids in the order they will be destroyed
            // Each index represents one revolution around the station
            ArrayList<ArrayList<String>> destroyOrder = new ArrayList<>();
            destroyOrder.add(new ArrayList<>());
            asteroids.remove(stationX + " " + stationY);

            // Continue until every asteroid is ordered
            while (!asteroids.isEmpty()){
                // Get its coordinates
                String[] split = asteroids.get(0).split(" ");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                // Find the slope
                int changeX = Math.abs(x - stationX);
                int changeY = Math.abs(y - stationY);
                while (changeY != 0){
                    int helper = changeY;
                    changeY = changeX % changeY;
                    changeX = helper;
                }
                changeY = (y-stationY) / changeX;
                changeX = (x-stationX) / changeX;
                x = stationX + changeX;
                y = stationY + changeY;
                // The number of full revolutions before this asteroid gets destroyed
                int count = 0;
                // Continue for every asteroid in that direction
                while (x < width && x >= 0 && y < height && y >= 0){
                    int index = asteroids.indexOf(x + " " + y);
                    // If it's found, add it to the destroy queue
                    if (index != -1){
                        if (count == destroyOrder.size()){
                            destroyOrder.add(new ArrayList<>());
                        }
                        destroyOrder.get(count).add(x-stationX + " " + (y-stationY));
                        asteroids.remove(index);
                        ++count;
                    }
                    x += changeX;
                    y += changeY;
                }
            }

            // Find the asteroid at index 199
            int destroy = 199;
            for (int i=0; i<destroyOrder.size(); ++i){
                // Keep looping until the revolution that destroys the 200th asteroid
                if (destroyOrder.get(i).size() < destroy){
                    destroy -= destroyOrder.get(i).size();
                    continue;
                }
                // Sort that revolution based on angle from the station
                Collections.sort(destroyOrder.get(i),(a,b) -> {
                    String[] split = ((String)a).split(" ");
                    int firstX = Integer.parseInt(split[0]);
                    int firstY = -1 * Integer.parseInt(split[1]);
                    split = ((String)b).split(" ");
                    int otherX = Integer.parseInt(split[0]);
                    int otherY = -1 * Integer.parseInt(split[1]);
                    if (firstX > 0 && otherX < 0){
                        return -1;
                    }else if (firstX < 0 && otherX > 0){
                        return 1;
                    }else if (firstX == 0){
                        if (firstY > 0){
                            return -1;
                        }
                        return otherX < 0 ? -1 : 1;
                    }else if (otherX == 0){
                        if (otherY > 0){
                            return 1;
                        }
                        return firstX > 0 ? -1 : 1;
                    }

                    return ((double)firstY)/firstX > ((double)otherY)/otherX ? -1 : 1;
                });

                // Find the one that gets destroyed
                String[] split = destroyOrder.get(i).get(destroy).split(" ");
                int x = Integer.parseInt(split[0]) + stationX;
                int y = Integer.parseInt(split[1]) + stationY;

                // Print the answer
                System.out.println(x * 100 + y);
                break;
            }
        }
    }
}