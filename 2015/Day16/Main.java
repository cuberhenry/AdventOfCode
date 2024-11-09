import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.HashMap;

public class Main {
    final private static String name = "Day 16: Aunt Sue";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);
        
        // Used to find the value of the desired Aunt Sue
        HashMap<String,Integer> hashmap = new HashMap<>();
        // Input the values from the MFCSAM
        hashmap.put("children",3);
        hashmap.put("cats",7);
        hashmap.put("samoyeds",2);
        hashmap.put("pomeranians",3);
        hashmap.put("akitas",0);
        hashmap.put("vizslas",0);
        hashmap.put("goldfish",5);
        hashmap.put("trees",3);
        hashmap.put("cars",2);
        hashmap.put("perfumes",1);

        // The index of the Aunt Sue
        int part1 = -1;
        int part2 = -1;
        // Loop through every Aunt Sue
        for (int index=1; part1 == -1 || part2 == -1; ++index){
            // Take in and split the line
            String[] sue = sc.nextLine().split(", |: | ");
            // Whether the correct Aunt Sue has been found
            boolean found1 = true;
            boolean found2 = true;
            // Loop through every attribute of the candidate Aunt Sue
            for (int i=2; i<sue.length && (found1 || found2); i+=2){
                // Check exact matches
                if (hashmap.get(sue[i]) != Integer.parseInt(sue[i+1])){
                    found1 = false;
                }

                // Must be more than the given number of cats or trees
                if (sue[i].equals("cats") || sue[i].equals("trees")){
                    if (hashmap.get(sue[i]) >= Integer.parseInt(sue[i+1])){
                        found2 = false;
                    }
                // Must be less than the given number of pomeranians or goldfish
                }else if (sue[i].equals("pomeranians") || sue[i].equals("goldfish")){
                    if (hashmap.get(sue[i]) <= Integer.parseInt(sue[i+1])){
                        found2 = false;
                    }
                // Must be equal to everything else
                }else{
                    if (hashmap.get(sue[i]) != Integer.parseInt(sue[i+1])){
                        found2 = false;
                    }
                }
            }

            // Save if found
            if (found1){
                part1 = index;;
            }
            if (found2){
                part2 = index;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}