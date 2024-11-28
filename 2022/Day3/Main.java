import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 3: Rucksack Reorganization";
    public static void main(String args[]) {
        // Get all rucksacks
        String[] rucksacks = Library.getStringArray(args,"\n");
        // Total priority value of all requested items
        int part1 = 0;
        int part2 = 0;

        // Loop through every set of three rucksacks
        for (int i=0; i<rucksacks.length; i+=3){
            for (int j=0; j<3; ++j){
                int half = rucksacks[i+j].length() / 2;
                String secondHalf = rucksacks[i+j].substring(half);
                for (char c : rucksacks[i+j].substring(0,half).toCharArray()){
                    if (secondHalf.contains(""+c)){
                        part1 += (c - 'A' + 27) % 58;
                        break;
                    }
                }
            }

            for (char c : rucksacks[i].toCharArray()){
                if (rucksacks[i+1].contains(""+c) && rucksacks[i+2].contains(""+c)){
                    part2 += (c - 'A' + 27) % 58;
                    break;
                }
            }
        }
        
        // Print the answer
        Library.print(part1,part2,name);
    }
}