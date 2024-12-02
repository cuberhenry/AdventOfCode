import com.aoc.mylibrary.Library;
public class Main {
    final private static String name = "Day 2: Red-Nosed Reports";
    public static void main(String[] args){
        // The list of reports
        int[][] reports = Library.getIntMatrix(args," ");

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;

        // Loop through each report
        for (int[] report : reports){
            // Loop through each level to remove, including no level
            for (int i=-1; i<report.length; ++i){
                // Whether the report is safe
                boolean increasing = true;
                boolean decreasing = true;
                // Loop through each item
                for (int j=0; j<report.length-1; ++j){
                    // Skip current items
                    if (i == j){
                        continue;
                    }
                    // If the next item is the one to skip
                    if (j + 1 == i){
                        // Step over and look at the next one after
                        if (j+2 < report.length && (report[j] - report[j+2] < 1 || report[j] - report[j+2] > 3)){
                            // Report is not gently decreasing
                            decreasing = false;
                        }
                        if (j+2 < report.length && (report[j+2] - report[j] < 1 || report[j+2] - report[j] > 3)){
                            // Report is not gently increasing
                            increasing = false;
                        }
                    }else{
                        // Compare to the next level
                        if (report[j] - report[j+1] < 1 || report[j] - report[j+1] > 3){
                            // Report is not gently decreasing
                            decreasing = false;
                        }
                        if (report[j+1] - report[j] < 1 || report[j+1] - report[j] > 3){
                            // Report is not gently increasing
                            increasing = false;
                        }
                    }
                }
                // If the report is gently increasing or decreasing
                if (increasing || decreasing){
                    // Count if the report is safe without removing any levels
                    if (i == -1){
                        ++part1;
                    }
                    // Count if the report is safe with at most one level removed
                    ++part2;
                    break;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}