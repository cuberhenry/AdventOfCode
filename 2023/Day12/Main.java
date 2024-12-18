import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 12: Hot Springs";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;
        // A map containing all known patterns
        HashMap<String,Long> map = new HashMap<>();

        // Continue through each plot
        while (sc.hasNext()){
            // Take in the line
            String line = sc.nextLine();
            // The list of springs
            String plot = line.substring(0,line.indexOf(' '));
            // The groups of damaged springs
            String numbers = line.substring(line.indexOf(' ')+1);

            // Remove unnecessary extra characters
            while (plot.contains("..")){
                plot = plot.replace("..",".");
            }

            // Get the total
            long thisTotal = plot(plot,numbers,map);
            // Add it to the map
            map.put(plot+numbers,thisTotal);
            // Increase the total
            part1 += thisTotal;

            plot = plot+"?"+plot+"?"+plot+"?"+plot+"?"+plot;
            numbers = numbers+","+numbers+","+numbers+","+numbers+","+numbers;

            // Get the total
            thisTotal = plot(plot,numbers,map);
            // Add it to the map
            map.put(plot+numbers,thisTotal);
            // Increase the total
            part2 += thisTotal;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }

    // A method which recursively counts the possibilities of plots and numbers
    public static long plot(String plot, String numbers, HashMap<String,Long> map){
        // Remove extra characters from the beginning
        while (plot.length() > 0 && plot.charAt(0) == '.'){
            plot = plot.substring(1);
        }
        // Remove extra characters from the end
        while (plot.length() > 0 && plot.charAt(plot.length()-1) == '.'){
            plot = plot.substring(0,plot.length()-1);
        }
        // If there are no groups of damaged springs
        if (numbers.length() == 0){
            // And there is no damaged spring
            if (!plot.contains("#")){
                // There is only one solution
                return 1;
            }else{
                // Impossible scenario
                return 0;
            }
        }

        // An array representation of the numbers
        String[] split = numbers.split(",");
        int[] array = new int[split.length];
        {
            // The number of buffer characters required between groups of damaged springs
            int total = split.length-1;
            // Add the size of each group of damaged springs
            for (int i=0; i<split.length; ++i){
                array[i] = Integer.parseInt(split[i]);
                total += array[i];
            }
            // If the required space doesn't fit on the plot, fail
            if (total > plot.length()){
                return 0;
            }

            // Set total to the sum of all group sizes
            total -= split.length-1;
            // Loop through and count the total number of damaged springs
            for (int i=0; i<plot.length(); ++i){
                if (plot.charAt(i) == '#'){
                    --total;
                }
            }
            // If there are more damaged springs than the numbers account for
            if (total < 0){
                // Fail
                return 0;
            }
        }

        // If this situation has been calculated before
        if (map.containsKey(plot+numbers)){
            // Return the result
            return map.get(plot+numbers);
        }

        // The maximum value in numbers
        int max = array[0];
        // The index of that value
        int index = 0;
        // Loop through each value
        for (int i=1; i<array.length; ++i){
            // If the new value is bigger
            if (array[i] > max){
                // Save it
                max = array[i];
                index = i;
            }
        }
        // The index of the max in the string of numbers
        int stringIndex = 0;
        // Loop through until the string index is found
        for (int commas = 0; commas < index;){
            if (numbers.charAt(stringIndex) == ','){
                ++commas;
            }
            ++stringIndex;
        }

        // The number of possibilities for this plot and number combination
        long total = 0;
        // Loop through each possible position for the biggest group
        for (int i=0; i<=plot.length()-max; ++i){
            // Invalid if first number misses a broken spring
            if (index == 0 && plot.substring(0,i).contains("#")){
                // Can not place anymore
                break;
            }
            // Invalid if there's a working spring within the bounds
            if (plot.substring(i,i+max).contains(".")){
                continue;
            }
            // Invalid if last number misses a broken spring
            if (index == array.length-1 && plot.substring(i+max).contains("#")){
                continue;
            }
            // Invalid if previous character is a broken spring
            if (i > 0 && plot.charAt(i-1) == '#'){
                continue;
            }
            // Invalid if next character is a broken spring
            if (i + max < plot.length()-1 && plot.charAt(i+max) == '#'){
                continue;
            }

            // The total number of possible solutions by placing the largest group at i
            long thisTotal = 1;
            // If this number is not the first number
            if (index != 0){
                // If the largest group is being placed with extra space on the left
                if (i > 0){
                    // This group being placed here allows for a certain number of combinations on the left
                    // by the plot remaining to the left, not including the buffer space, and the numbers
                    // to the left of the max
                    thisTotal *= plot(plot.substring(0,i-1),numbers.substring(0,stringIndex-1),map);
                }else{
                    // There is no room for numbers to be placed to the left
                    thisTotal = 0;
                }
            }
            // If this is not the last number
            if (thisTotal != 0 && index != array.length-1){
                // If the largest group is being placed with extra space on the right
                if (i+max < plot.length()){
                    // This group being placed here allows for a certain number of combinations on the right
                    // by the plot remaining to the right, not including the buffer space, and the numbers
                    // to the right of the max
                    String newNumbers = numbers.substring(stringIndex);
                    thisTotal *= plot(plot.substring(i+max+1),newNumbers.substring(newNumbers.indexOf(',')+1),map);
                }else{
                    // There is no room for numbers to be placed to the right
                    thisTotal = 0;
                }
            }
            // Increase the total by this possibility
            total += thisTotal;
        }

        // Put the total into the map
        map.put(plot+numbers,total);
        // Return the total
        return total;
    }
}