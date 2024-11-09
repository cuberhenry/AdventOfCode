import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 22: Grid Computing";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // Skip the headers
        sc.nextLine();
        sc.nextLine();

        // The coordinates of the empty node
        int emptyX = 0;
        int emptyY = 0;
        // The minimum wall block the goal node
        int minX = Integer.MAX_VALUE;
        // The number of columns
        int maxX = 0;
        // All nodes
        ArrayList<int[]> nodes = new ArrayList<>();
            
        // Loop through every node
        while (sc.hasNext()){
            String[] node = sc.nextLine().split("/dev/grid/node-x|-y|T     |     |    |T    |   |T   |T  |%");
            maxX = Integer.parseInt(node[1]);
            int y = Integer.parseInt(node[2]);
            // Take in relevant information
            String size = node[3];
            String used = node[4];
            String avail = node[5];

            if (size.length() > 2){
                // Node can't empty
                minX = Math.min(minX,maxX);
            }else if (used.equals("0")){
                // Node is empty
                emptyX = maxX;
                emptyY = y;
            }

            // Used and Avail
            int[] summary = new int[2];
            summary[0] = Integer.parseInt(used);
            summary[1] = Integer.parseInt(avail);

            nodes.add(summary);
        }
        
        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop through every unordered pair of nodes
        for (int i=0; i<nodes.size(); ++i){
            for (int j=i+1; j<nodes.size(); ++j){
                // Check both directions
                if (nodes.get(i)[0] != 0 && nodes.get(i)[0] <= nodes.get(j)[1]){
                    ++part1;
                }
                if (nodes.get(j)[0] != 0 && nodes.get(j)[0] <= nodes.get(i)[1]){
                    ++part1;
                }
            }
        }

        // Get passed the wall
        while (emptyX >= minX){
            --emptyX;
            ++part2;
        }
        // Get to the edge
        while (emptyY > 0){
            --emptyY;
            ++part2;
        }
        // Get to the goal data
        while (emptyX < maxX){
            ++emptyX;
            ++part2;
        }

        // Move the goal data to 0,0
        part2 += 5 * (maxX - 1);

        // Print the answer
        Library.print(part1,part2,name);
    }
}