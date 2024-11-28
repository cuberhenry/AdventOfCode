import com.aoc.mylibrary.Library;
import com.aoc.mylibrary.UniqueLong;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 20: Grove Positioning System";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The actively changing list of values
        ArrayList<UniqueLong> list = new ArrayList<>();
        // The order of execution
        ArrayList<UniqueLong> order = new ArrayList<>();
        UniqueLong zero = null;

        // Take in all input
        while (sc.hasNext()){
            // The value being added
            UniqueLong value = new UniqueLong(sc.nextLong());

            if (value.getValue() == 0){
                zero = value;
            }

            // Add the value to the order
            order.add(value);
            // Add the value to the list
            list.add(value);
        }

        // Mix
        mix(list,order);
        
        // Find the result
        long part1 = list.get((list.indexOf(zero)+1000)%list.size()).getValue();
        part1 += list.get((list.indexOf(zero)+2000)%list.size()).getValue();
        part1 += list.get((list.indexOf(zero)+3000)%list.size()).getValue();
        
        // Reset
        list.clear();
        for (UniqueLong l : order){
            UniqueLong newLong = new UniqueLong(l.getValue() * 811589153L);
            if (newLong.getValue() == 0){
                zero = newLong;
            }
            list.add(newLong);
        }
        order = new ArrayList<>(list);

        // Mix ten times
        for (int i=0; i<10; ++i){
            mix(list,order);
        }
        
        // Find the result
        long part2 = list.get((list.indexOf(zero)+1000)%list.size()).getValue();
        part2 += list.get((list.indexOf(zero)+2000)%list.size()).getValue();
        part2 += list.get((list.indexOf(zero)+3000)%list.size()).getValue();

        // Print the answer
        Library.print(part1,part2,name);
    }

    private static void mix(ArrayList<UniqueLong> list, ArrayList<UniqueLong> order){
        // Loop through each item
        for (UniqueLong l : order){
            // The index of the number being moved
            int index = list.indexOf(l);
            // The number being moved
            UniqueLong num = list.remove(index);
            // The amount the number has to move
            int move = (int)(num.getValue() % (order.size()-1)) + order.size() - 1;
            // The new index for the value
            int newIndex = (index + move) % (order.size()-1);

            // Add the number to its new spot
            list.add(newIndex,num);
        }
    }
}