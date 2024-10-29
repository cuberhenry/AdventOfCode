/*
Henry Anderson
Advent of Code 2021 Day 19 https://adventofcode.com/2021/day/19
Input: https://adventofcode.com/2021/day/19/input
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
        // The list of beacons for each scanner
        ArrayList<ArrayList<int[]>> scanners = new ArrayList<>();
        // The coordinates of each scanner relative to scanner 0
        ArrayList<int[]> scannerCoords = new ArrayList<>();
        scannerCoords.add(new int[] {0,0,0});
        // Take in all the scanners
        scanners.add(new ArrayList<>());
        sc.nextLine();
        while (sc.hasNextLine()){
            String line = sc.nextLine();
            // New scanner
            if (line.equals("")){
                sc.nextLine();
                line = sc.nextLine();
                scanners.add(new ArrayList<>());
            }
            String[] split = line.split(",");
            // Get the beacon's relative coordinates
            int[] point = new int[3];
            point[0] = Integer.parseInt(split[0]);
            point[1] = Integer.parseInt(split[1]);
            point[2] = Integer.parseInt(split[2]);
            scanners.getLast().add(point);
        }

        // Create a distance map for each scanner's beacons
        ArrayList<String[][]> distances = new ArrayList<>();
        for (ArrayList<int[]> beacons : scanners){
            String[][] distMap = new String[beacons.size()][beacons.size()];
            // Loop through each pair of beacons
            for (int i=0; i<beacons.size(); ++i){
                int[] b1 = beacons.get(i);
                distMap[i][i] = "0 0 0";
                for (int j=i+1; j<beacons.size(); ++j){
                    int[] b2 = beacons.get(j);
                    // Get each dimension's distance
                    int[] dists = {Math.abs(b1[0]-b2[0]),Math.abs(b1[1]-b2[1]),Math.abs(b1[2]-b2[2])};
                    // Sort because they could be rotated
                    Arrays.sort(dists);
                    // Fill in the distance map
                    String orderedDists = dists[0] + " " + dists[1] + " " + dists[2];
                    distMap[i][j] = orderedDists;
                    distMap[j][i] = orderedDists;
                }
            }
            distances.add(distMap);
        }

        // Continue until all beacons have been combined into one group relative to scanner 0
        while (scanners.size() > 1){
            // Loop through each non-zero scanner
            for (int i=scanners.size()-1; i>0; --i){
                // Loop through each beacon in scanner 0
                for (int j=0; j<scanners.getFirst().size(); ++j){
                    // Whether 12 matching beacons were found
                    boolean matched = false;
                    // Loop through each beacon in the other scanner
                    for (int k=0; k<scanners.get(i).size(); ++k){
                        // The IDs of the beacons for each scanner, where each position is the same beacon
                        ArrayList<Integer> scanner0IDs = new ArrayList<>();
                        ArrayList<Integer> otherIDs = new ArrayList<>();
                        // Loop through each distance for each beacon
                        for (int l=0; l<distances.getFirst().length; ++l){
                            for (int m=0; m<distances.get(i).length; ++m){
                                // If the distances are the same
                                if (distances.getFirst()[j][l].equals(distances.get(i)[k][m])){
                                    // Add the one beacon to both lists
                                    scanner0IDs.add(l);
                                    otherIDs.add(m);
                                    // Move on to the next beacon
                                    break;
                                }
                            }
                        }

                        // If at least 12 matches were found
                        if (scanner0IDs.size() >= 12){
                            matched = true;
                            // Get the first beacons from each scanner
                            int[] firstScanner0 = scanners.getFirst().get(scanner0IDs.getFirst());
                            int[] firstOther = scanners.get(i).get(otherIDs.getFirst());
                            // The rotations necessary to match scanner 0
                            int[][] rotation = {{0,1},{1,1},{2,1}};
                            // The translation necessary to match scanner 0
                            int[] translation = new int[3];

                            // Loop through each possible rotation
                            for (int l=0; l<24; ++l){
                                // Set the translation for the first beacon using the rotation
                                for (int m=0; m<3; ++m){
                                    translation[m] = firstScanner0[m] - firstOther[rotation[m][0]] * rotation[m][1];
                                }
                                // Whether the rotation works for all matched beacons
                                boolean fits = true;
                                // Loop through every other beacon
                                for (int m=1; m<scanner0IDs.size() && fits; ++m){
                                    // Get the two comparing beacons
                                    int[] beacon = scanners.getFirst().get(scanner0IDs.get(m));
                                    int[] otherBeacon = scanners.get(i).get(otherIDs.get(m));
                                    // Translate and shift each value
                                    for (int n=0; n<3; ++n){
                                        if (otherBeacon[rotation[n][0]] * rotation[n][1] + translation[n] != beacon[n]){
                                            // This isn't the right rotation
                                            fits = false;
                                            break;
                                        }
                                    }
                                }

                                // The right rotation has been found
                                if (fits){
                                    break;
                                }

                                // To achieve all rotations, do the following 4 times:
                                //     Rotate clockwise 3 times
                                //     Rotate up once
                                //     Rotate counterclockwise 3 times
                                //     Rotate up once
                                switch (l%8){
                                    // Clockwise
                                    case 0,1,2 -> {
                                        int helper = rotation[0][0];
                                        rotation[0][0] = rotation[1][0];
                                        rotation[1][0] = helper;
                                        helper = rotation[0][1];
                                        rotation[0][1] = rotation[1][1];
                                        rotation[1][1] = helper * -1;
                                    }
                                    // Counter-clockwise
                                    case 4,5,6 -> {
                                        int helper = rotation[0][0];
                                        rotation[0][0] = rotation[1][0];
                                        rotation[1][0] = helper;
                                        helper = rotation[0][1];
                                        rotation[0][1] = rotation[1][1] * -1;
                                        rotation[1][1] = helper;
                                    }
                                    // Up
                                    default -> {
                                        int helper = rotation[1][0];
                                        rotation[1][0] = rotation[2][0];
                                        rotation[2][0] = helper;
                                        helper = rotation[2][1];
                                        rotation[2][1] = rotation[1][1] * -1;
                                        rotation[1][1] = helper;
                                    }
                                }
                            }

                            // Add the scanner's coordinates (the shift)
                            scannerCoords.add(translation);

                            // Merge the other scanner into scanner 0
                            distances.remove(i);
                            ArrayList<int[]> toMerge = scanners.remove(i);

                            // Add every beacon that isn't already associated with scanner 0
                            for (int l=0; l<toMerge.size(); ++l){
                                if (otherIDs.contains(l)){
                                    continue;
                                }
                                // The beacon to be merged
                                int[] beacon = toMerge.get(l);
                                // The beacon once it's been merged
                                int[] newBeacon = new int[3];
                                // Rotate and translate the beacon's coordinates
                                for (int m=0; m<3; ++m){
                                    newBeacon[m] = beacon[rotation[m][0]] * rotation[m][1] + translation[m];
                                }
                                // Add the new beacon
                                scanners.getFirst().add(newBeacon);
                            }

                            // Remake the new distmap
                            ArrayList<int[]> beacons = scanners.getFirst();
                            String[][] distMap = new String[beacons.size()][beacons.size()];
                            // Copy over distances already identified
                            for (int l=0; l<distances.getFirst().length; ++l){
                                for (int m=0; m<distances.getFirst().length; ++m){
                                    distMap[l][m] = distances.getFirst()[l][m];
                                }
                            }
                            // Find the new distances
                            for (int l=0; l<beacons.size(); ++l){
                                int[] b1 = beacons.get(l);
                                distMap[l][l] = "0 0 0";
                                for (int m=Math.max(distances.getFirst().length,l+1); m<beacons.size(); ++m){
                                    int[] b2 = beacons.get(m);
                                    // Get each dimension's distance
                                    int[] dists = {Math.abs(b1[0]-b2[0]),Math.abs(b1[1]-b2[1]),Math.abs(b1[2]-b2[2])};
                                    // Sort because they could be rotated
                                    Arrays.sort(dists);
                                    // Fill in the distance map
                                    String orderedDists = dists[0] + " " + dists[1] + " " + dists[2];
                                    distMap[l][m] = orderedDists;
                                    distMap[m][l] = orderedDists;
                                }
                            }
                            distances.set(0,distMap);
                            break;
                        }
                    }

                    // The other scanner has been merged, move on to the next
                    if (matched){
                        break;
                    }
                }
            }
        }

        // Part 1 finds the total number of beacons
        if (PART == 1){
            System.out.println(scanners.getFirst().size());
        }

        // Part 2 finds the max distance between scanners
        if (PART == 2){
            int maxDist = 0;
            // Loop through every pair of scanners
            for (int i=0; i<scannerCoords.size()-1; ++i){
                int[] s1 = scannerCoords.get(i);
                for (int j=i+1; j<scannerCoords.size(); ++j){
                    int[] s2 = scannerCoords.get(j);
                    // Save the maximum distance
                    maxDist = Math.max(Math.abs(s1[0]-s2[0]) + Math.abs(s1[1]-s2[1]) + Math.abs(s1[2]-s2[2]),maxDist);
                }
            }

            // Print the answer
            System.out.println(maxDist);
        }
    }
}