import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Stack;

public class Main {
    final private static String name = "Day 24: Electromagnetic Moat";
    public static void main(String[] args){
        Scanner sc = Library.getScanner(args);

        // All of the available parts
        ArrayList<int[]> parts = new ArrayList<>();
        while (sc.hasNext()){
            parts.add(Library.intSplit(sc.nextLine(),"/"));
        }

        // The pieces currently making up the bridge
        Stack<int[]> bridge = new Stack<>();
        // The strongest bridge
        int part1 = 0;
        int part2 = 0;
        // The longest bridge
        int longest = 0;

        // The current port of the end of the current bridge
        int port = 0;
        // The strength of the current bridge
        int size = 0;
        // The part currently being examined
        int index = 0;
        // Continue until every possible combination has been tried
        while (true){
            // Loop through every part
            while (index < parts.size()){
                // Can't add the same piece twice
                if (bridge.contains(parts.get(index))){
                    ++index;
                    continue;
                }

                // If the piece has a matching port, attach it,
                // starting over with the new open port
                if (parts.get(index)[0] == port){
                    bridge.push(parts.get(index));
                    port = parts.get(index)[1];
                    size += parts.get(index)[0] + parts.get(index)[1];
                    index = 0;
                }else if (parts.get(index)[1] == port){
                    bridge.push(parts.get(index));
                    port = parts.get(index)[0];
                    size += parts.get(index)[0] + parts.get(index)[1];
                    index = 0;
                }else{
                    // Otherwise, look at the next piece
                    ++index;
                }
            }
            // If all starting pieces have been examined, quit
            if (bridge.isEmpty()){
                break;
            }

            // Save the strongest bridge
            part1 = Math.max(part1,size);
            // If the bridge is longer
            if (bridge.size() > longest){
                // Override the strength
                longest = bridge.size();
                part2 = size;
            }else if (bridge.size() == longest){
                // If they're the same, choose the strongest
                part2 = Math.max(part2,size);
            }

            // All pieces have been examined, back out one piece
            index = parts.indexOf(bridge.pop());
            // Go back to looking for the previous port
            if (port == parts.get(index)[0]){
                port = parts.get(index)[1];
            }else{
                port = parts.get(index)[0];
            }
            // Decrease by the strength of the last piece
            size -= parts.get(index)[0] + parts.get(index)[1];
            // Start looking for the next piece after the one you just tried
            ++index;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}