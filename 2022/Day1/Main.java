import com.aoc.mylibrary.Library;
import java.util.ArrayList;
import java.util.Collections;

public class Main {
    final private static String name = "Day 1: Calorie Counting";
    public static void main(String args[]) {
        // Each elf's list of calories
        String[] elves = Library.getStringArray(args,"\n\n");
        // Used to save amount of calories for each elf
        ArrayList<Integer> array = new ArrayList<>();

        // Loop through all lines of input
        for (String elf : elves){
            array.add(Library.sum(Library.intSplit(elf,"\n")));
        }
        // Sort and reverse so the biggest numbers are most easily available
        Collections.sort(array);

        // The answer to the problem
        int part1 = array.getLast();
        int part2 = array.removeLast() + array.removeLast() + array.getLast();
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}
