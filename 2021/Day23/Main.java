/*
Henry Anderson
Advent of Code 2021 Day 23 https://adventofcode.com/2021/day/23
Input: https://adventofcode.com/2021/day/23/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
import com.aoc.mylibrary.FixedStack;
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
        // The list of rooms
        ArrayList<FixedStack<Character>> rooms = new ArrayList<>();
        // The available hallway spots
        char[] hallway = {' ',' ',' ',' ',' ',' ',' '};
        
        // Skip the extra input
        sc.nextLine();
        sc.nextLine();
        // Take in the 2 (or 4) lines of initial room states
        String top = sc.nextLine();
        String middleTop = "  #D#C#B#A#";
        String middleBottom = "  #D#B#A#C#";
        String bottom = sc.nextLine();
        // Part 1 finds the energy required to sort 2 to each room
        String solutionRooms = "[[A, A], [B, B], [C, C], [D, D]]";

        // Part 2 finds the energy required to sort 4 to each room
        if (PART == 2){
            solutionRooms = "[[A, A, A, A], [B, B, B, B], [C, C, C, C], [D, D, D, D]]";
        }

        // Loop through each room
        for (int i=3; i<10; i+=2){
            // Create the room
            FixedStack<Character> room = null;

            // Add the two amphipods
            if (PART == 1){
                room = new FixedStack<>(2);
                room.push(bottom.charAt(i));
                room.push(top.charAt(i));
            }

            // Add the four amphipods
            if (PART == 2){
                room = new FixedStack<>(4);
                room.push(bottom.charAt(i));
                room.push(middleBottom.charAt(i));
                room.push(middleTop.charAt(i));
                room.push(top.charAt(i));
            }

            rooms.add(room);
        }

        // A hashmap containing the state and the best energy to solve it
        HashMap<String,Integer> bestStates = new HashMap<>();
        // Add the solved state
        bestStates.put(solutionRooms + "[ ,  ,  ,  ,  ,  ,  ]",0);
        // Print the answer of the depth first search
        System.out.println(dfs(rooms,hallway,bestStates));
    }

    // Perform a depth first search
    private static int dfs(ArrayList<FixedStack<Character>> rooms, char[] hallway, HashMap<String,Integer> bestStates){
        // If the state was already solved, return the energy
        if (bestStates.containsKey("" + rooms + Arrays.toString(hallway))){
            return bestStates.get("" + rooms + Arrays.toString(hallway));
        }
        // The best energy expenditure, MAX_VALUE if unsolvable
        int best = Integer.MAX_VALUE;

        // Loop through each room to move the top amphipod into the hallway
        for (int i=0; i<rooms.size(); ++i){
            FixedStack<Character> room = rooms.get(i);
            // If the room is empty or is complete or partially complete, skip it
            if (room.empty() || room.uniform() && room.peek() == (char)(i+'A')){
                continue;
            }
            // Loop through each hallway spot to the left
            for (int j=i+1; j>=0; --j){
                // If it's blocked, can't put anything there or to the left
                if (hallway[j] != ' '){
                    break;
                }

                // Get the distance between the room spot and the hallway spot
                int dist = (i+2-j) * 2 + room.capacity() - room.size();
                if (j == 0){
                    --dist;
                }

                // Move the amphipod
                hallway[j] = room.pop();
                // Convert to energy based on the type of amphipod
                dist *= (int)Math.pow(10,hallway[j]-'A');
                // Solve the new state
                int energy = dfs(rooms,hallway,bestStates);
                // If it was solved
                if (energy != Integer.MAX_VALUE){
                    // Add the current energy to the solved
                    dist += energy;
                }else{
                    // Max it
                    dist = energy;
                }
                // Revert the state change
                room.push(hallway[j]);
                hallway[j] = ' ';

                // Save the best energy expenditure
                best = Math.min(best,dist);
            }
            // Loop through each hallway spot to the right
            for (int j=i+2; j<hallway.length; ++j){
                // If it's blocked, can't put anything there or to the right
                if (hallway[j] != ' '){
                    break;
                }

                // Get the distance between the room spot and the hallway spot
                int dist = (j-1-i) * 2 + room.capacity() - room.size();
                if (j == hallway.length-1){
                    --dist;
                }

                // Move the amphipod
                hallway[j] = room.pop();
                // Convert to energy based on the type of amphipod
                dist *= (int)Math.pow(10,hallway[j]-'A');
                // Solve the new state
                int energy = dfs(rooms,hallway,bestStates);
                // If it was solved
                if (energy != Integer.MAX_VALUE){
                    // Add the current energy to the solved
                    dist += energy;
                }else{
                    // Max it
                    dist = energy;
                }
                // Revert the state change
                room.push(hallway[j]);
                hallway[j] = ' ';

                // Save the best energy expenditure
                best = Math.min(best,dist);
            }
        }

        // Loop through each hallway spot to place the amphipod in its room
        for (int i=0; i<hallway.length; ++i){
            // Skip empty hallway spots
            if (hallway[i] == ' '){
                continue;
            }
            // Get the index of the room
            int j = hallway[i] - 'A';
            FixedStack<Character> room = rooms.get(j);
            // If the room contains incorrect amphipods, skip
            if (!(room.empty() || (room.uniform() && room.peek() == hallway[i]))){
                continue;
            }

            // Whether the amphipod can get into the room
            boolean accessible = true;
            int dist;
            // To the left
            if (i <= j+1){
                // Calculate the distance
                dist = j+2-i;
                // Check the hallway spots in between
                for (int k=j+1; k>i; --k){
                    if (hallway[k] != ' '){
                        accessible = false;
                        break;
                    }
                }
            }else{
                // Calculate the distance
                dist = i-1-j;
                // Check the hallway spots in between
                for (int k=j+2; k<i; ++k){
                    if (hallway[k] != ' '){
                        accessible = false;
                        break;
                    }
                }
            }
            
            // Inaccessible
            if (!accessible){
                continue;
            }

            // Move the amphipod
            room.push(hallway[i]);
            hallway[i] = ' ';

            // Finish calculating the distance
            dist = dist * 2 + room.capacity() - room.size();
            if (i == 0 || i == hallway.length-1){
                --dist;
            }
            // Convert to energy based on the type of amphipod
            dist *= (int)Math.pow(10,room.peek()-'A');
            // Solve the next state
            int energy = dfs(rooms,hallway,bestStates);
            // If it was solved
            if (energy != Integer.MAX_VALUE){
                // Add the current energy to the solved
                dist += energy;
            }else{
                // Max it
                dist = energy;
            }
            // Save the best energy expenditure
            best = Math.min(best,dist);

            // Revert the state change
            hallway[i] = room.pop();
        }

        // Save the solution to this state
        bestStates.put("" + rooms + Arrays.toString(hallway),best);
        // Return the energy expenditure
        return best;
    }
}