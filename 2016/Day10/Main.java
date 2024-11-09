import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;

public class Main {
    final private static String name = "Day 10: Balance Bots";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The list of all bots of the form
        // {val1, val2, low, high}
        HashMap<Integer,int[]> bots = new HashMap<>();
        // The list of all outputs
        HashMap<Integer,Integer> outputs = new HashMap<>();
        // The queue of robots to determine giving
        LinkedList<Integer> queue = new LinkedList<>();
        // Loop through every line of input
        while (sc.hasNext()){
            // Split the line
            String[] line = sc.nextLine().split(" ");
            // Add a value to a bot
            if (line[0].equals("value")){
                // Dissect the input
                int bot = Integer.parseInt(line[5]);
                int value = Integer.parseInt(line[1]);
                // If the bot doesn't exist, add bots until it does
                if (bots.containsKey(bot)){
                    // Give the bot the value
                    if (bots.get(bot)[0] == 0){
                        bots.get(bot)[0] = value;
                    }else{
                        bots.get(bot)[1] = value;
                        // Bot has two values
                        queue.add(bot);
                    }
                }else{
                    // Create a new bot
                    bots.put(bot,new int[] {value,0,0,0});
                }
            // Add a bot's instructions
            }else{
                // Get the bot index
                int bot = Integer.parseInt(line[1]);
                // Get the location to give the lower value
                int low = Integer.parseInt(line[6]);
                // Get the location to give the higher value
                int high = Integer.parseInt(line[11]);
                // Use negative values for output to differentiate
                if (line[5].equals("output")){
                    low *= -1;
                    --low;
                }
                if (line[10].equals("output")){
                    high *= -1;
                    --high;
                }
                // Make sure all the bots exist
                if (!bots.containsKey(bot)){
                    bots.put(bot,new int[] {0,0,low,high});
                }else{
                    bots.get(bot)[2] = low;
                    bots.get(bot)[3] = high;
                }
            }
        }

        // The bot that compares 61 and 17
        int part1 = 0;

        // Continue until all bots have finished deciding
        while (!queue.isEmpty()){
            // Get the index of the bot
            int index = queue.remove();
            // Get the bot
            int[] bot = bots.get(index);
            // Get the chip values
            int high = Math.max(bot[0],bot[1]);
            int low = Math.min(bot[0],bot[1]);
            // Reset the values
            bot[0] = 0;
            bot[1] = 0;

            if (high == 61 && low == 17){
                part1 = index;
            }

            // Give the lower item away
            if (bot[2] < 0){
                // Add the value to output
                outputs.put(bot[2]*-1-1,low);
            }else if (bots.get(bot[2])[0] == 0){
                // Add the value to the bot's first slot
                bots.get(bot[2])[0] = low;
            }else{
                // Add the value to the bot's second slot
                bots.get(bot[2])[1] = low;
                // Add the bot to the queue
                queue.add(bot[2]);
            }

            // Give the higher item away
            if (bot[3] < 0){
                // Add the value to output
                outputs.put(bot[3]*-1-1,high);
            }else if (bots.get(bot[3])[0] == 0){
                // Add the value to the bot's first slot
                bots.get(bot[3])[0] = high;
            }else{
                // Add the value to the bot's second slot
                bots.get(bot[3])[1] = high;
                // Add the bot to the queue
                queue.add(bot[3]);
            }
        }

        // The product of the chip in outputs 0 through 2
        int part2 = outputs.get(0) * outputs.get(1) * outputs.get(2);

        // Print the answer
        Library.print(part1,part2,name);
    }
}