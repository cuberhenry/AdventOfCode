import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 2: Gift Shop";
    public static void main(String[] args){
        long[][] sc = Library.getLongMatrix(args,",","-");

        // The answer to the problem
        long part1 = 0;
        long part2 = 0;

        // Loop through each range
        for (long[] range : sc){
            // Loop through every number in the range
            for (long i=range[0]; i<=range[1]; ++i){
                // Get the number as a string
                String str = i + "";
                // Get half the size of the string
                int half = str.length() / 2;
                // Loop through every valid equal group size
                for (int j=half; j>0; --j){
                    if (str.length() % j == 0){
                        // Whether the pattern repeats
                        boolean repeats = true;
                        // The pattern to match, the first group of size j
                        String match = str.substring(0,j);
                        
                        // Loop through each group
                        for (int k=1; k<str.length()/j; ++k){
                            // If they don't match, it's not repeating
                            if (!match.equals(str.substring(k*j,(k+1)*j))){
                                repeats = false;
                                break;
                            }
                        }

                        // Add the ID if it repeats
                        if (repeats){
                            // Part 1 only finds the IDs that repeat twice
                            if (str.length() % 2 == 0 && j == half){
                                part1 += i;
                            }
                            // Part 2 finds the IDs that repeat any number of times
                            part2 += i;
                            break;
                        }
                    }
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}