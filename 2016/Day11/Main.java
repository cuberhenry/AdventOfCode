/*
Henry Anderson
Advent of Code 2016 Day 11 https://adventofcode.com/2016/day/11
Input: https://adventofcode.com/2016/day/11/input
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
        ArrayList<ArrayList<String[]>> floors = new ArrayList<>();
        while (sc.hasNext()){
            String line = sc.nextLine();
            ArrayList<String[]> floor = new ArrayList<>();
            while (line.indexOf(" a ") != -1){
                line = line.substring(line.indexOf(" a ")+3);
                String[] item = new String[2];
                item[0] = line.substring(0,line.indexOf(' ')).split("-")[0];
                line = line.substring(line.indexOf(' ')+1);
                item[1] = line.substring(0,9);
                floor.add(item);
            }
            Collections.sort(floor,(a1,a2) -> (a1[0].compareTo(a2[0]) > 0 || a1[1].compareTo(a2[1]) > 0) ? 1 : -1);
            floors.add(floor);
        }

        if (PART == 2){
            ArrayList<String[]> floor =  floors.get(0);
            floor.add(new String[] {"elerium","generator"});
            floor.add(new String[] {"elerium","microchip"});
            floor.add(new String[] {"dilithium","generator"});
            floor.add(new String[] {"dilithium","microchip"});
        }

        ArrayList<String> queue = new ArrayList<>();
        ArrayList<String> allStates = new ArrayList<>();
        String startState = "0 0";
        for (int i=0; i<floors.size(); ++i){
            startState += " " + i;
            for (String[] item : floors.get(i)){
                startState += " " + item[0] + " " + item[1];
            }
        }
        queue.add(startState);
        visited(floors,0,allStates);

        while (!queue.isEmpty()){
            // Populate arraylist
            String[] split = queue.remove(0).split(" ");
            ArrayList<ArrayList<String[]>> state = new ArrayList<>();

            int numSteps = Integer.parseInt(split[0]);
            int elevator = Integer.parseInt(split[1]);
            for (int i=2; i<split.length; ++i){
                ArrayList<String[]> items = new ArrayList<>();
                while (i+1 <split.length && !Character.isDigit(split[i+1].charAt(0))){
                    String[] item = new String[2];
                    item[0] = split[i+1];
                    item[1] = split[i+2];
                    i += 2;
                    items.add(item);
                }
                state.add(items);
            }

            // For each adjacent floor
            for (int i=elevator-1; i<=elevator+1; i+=2){
                // If the floor exists
                if (i >= 0 && i < floors.size()){
                    // For each item on the elevator's floor
                    for (int j=0; j<state.get(elevator).size(); ++j){
                        // Copy the state
                        ArrayList<ArrayList<String[]>> newState = new ArrayList<>();
                        int score = 0;
                        for (int k=0; k<state.size(); ++k){
                            ArrayList<String[]> floorCopy = new ArrayList<>();
                            for (String[] item : state.get(k)){
                                score += k;
                                floorCopy.add(item);
                            }
                            newState.add(floorCopy);
                        }
                        // Move the item
                        String[] item = newState.get(elevator).remove(j);
                        newState.get(i).add(item);
                        score = score - elevator + i;

                        {
                            // Sort each floor
                            for (ArrayList<String[]> floor : newState){
                                Collections.sort(floor,(a1,a2) -> (a1[0].compareTo(a2[0]) > 0 || a1[1].compareTo(a2[1]) > 0) ? 1 : -1);
                            }

                            // Check for win
                            boolean win = true;
                            for (int k=0; k<newState.size()-1; ++k){
                                if (newState.get(k).size() > 0){
                                    win = false;
                                    break;
                                }
                            }
                            if (win){
                                System.out.println(numSteps+1);
                                return;
                            }

                            // Check for disaster
                            if (!disasterCheck(newState) && !visited(newState,i,allStates)){
                                String oneState = numSteps+1 + " " + i;
                                for (int k=0; k<floors.size(); ++k){
                                    oneState += " " + k;
                                    for (String[] object : newState.get(k)){
                                        oneState += " " + object[0] + " " + object[1];
                                    }
                                }
                                queue.add(oneState);
                            }
                        }

                        {
                            // For each item on the elevator's floor
                            for (int k=0; k<newState.get(elevator).size(); ++k){
                                // Copy the state
                                ArrayList<ArrayList<String[]>> newNewState = new ArrayList<>();
                                score = 0;
                                for (int l=0; l<newState.size(); ++l){
                                    ArrayList<String[]> floor = newState.get(l);
                                    ArrayList<String[]> floorCopy = new ArrayList<>();
                                    for (String[] thing : floor){
                                        score += l;
                                        floorCopy.add(thing);
                                    }
                                    newNewState.add(floorCopy);
                                }
                                // Move the item
                                String[] thing = newNewState.get(elevator).remove(k);
                                newNewState.get(i).add(thing);
                                score = score - elevator + i;

                                // Check for win
                                boolean win = true;
                                for (int l=0; l<newNewState.size()-1 && win; ++l){
                                    if (newNewState.get(l).size() > 0){
                                        win = false;
                                    }
                                }
                                if (win){
                                    System.out.println(numSteps+1);
                                    return;
                                }

                                // Check for disaster
                                if (disasterCheck(newNewState) || visited(newNewState,i,allStates)){
                                    continue;
                                }
                                
                                String twoState = numSteps+1 + " " + i;
                                for (int l=0; l<floors.size(); ++l){
                                    twoState += " " + l;
                                    for (String[] object : newNewState.get(l)){
                                        twoState += " " + object[0] + " " + object[1];
                                    }
                                }
                                queue.add(twoState);
                            }
                        }
                    }
                }
            }
        }
    }

    public static boolean disasterCheck(ArrayList<ArrayList<String[]>> floors){
        for (ArrayList<String[]> floor : floors){
            for (String[] object : floor){
                if (object[1].equals("microchip")){
                    boolean ownGenerator = false;
                    boolean otherGenerator = false;
                    for (String[] other : floor){
                        if (other[1].equals("generator")){
                            if (other[0].equals(object[0])){
                                ownGenerator = true;
                            }else{
                                otherGenerator = true;
                            }
                        }
                    }
                    if (otherGenerator && !ownGenerator){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean visited(ArrayList<ArrayList<String[]>> state, int elevator, ArrayList<String> all){
        String visited = elevator + " ";
        for (int i=0; i<state.size(); ++i){
            visited += i+" ";
            int ms = 0;
            int gs = 0;
            for (String[] item : state.get(i)){
                if (item[1].equals("microchip")){
                    ++ms;
                }else{
                    ++gs;
                }
            }
            visited += ms + "m" + gs + "g";
        }

        if (all.contains(visited)){
            return true;
        }
        all.add(visited);
        return false;
    }
}