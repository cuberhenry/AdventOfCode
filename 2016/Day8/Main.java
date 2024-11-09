import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 8: Two-Factor Authentication";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The screen of pixels
        boolean[][] screen = new boolean[6][50];

        // Loop through every instruction
        while (sc.hasNext()){
            // Get the instruction
            String line = sc.nextLine();
            // Light up a rectangle
            if (line.substring(0,4).equals("rect")){
                // Get the dimensions of the rectangle
                int width = Integer.parseInt(line.substring(line.indexOf(' ')+1,line.indexOf('x')));
                int height = Integer.parseInt(line.substring(line.indexOf('x')+1));
                // Light the rectangle
                for (int i=0; i<height; ++i){
                    for (int j=0; j<width; ++j){
                        screen[i][j] = true;
                    }
                }
            // Spin a row to the right
            }else if (line.substring(7,10).equals("row")){
                // Get the numbers from the instruction
                int row = Integer.parseInt(line.split(" ")[2].split("=")[1]);
                int num = Integer.parseInt(line.split(" ")[4]);
                // Repeat num times
                for (int i=0; i<num % 50; ++i){
                    // Save the edge
                    boolean save = screen[row][49];
                    // Move the row
                    for (int j=49; j>0; --j){
                        screen[row][j] = screen[row][j-1];
                    }
                    // Put the edge back
                    screen[row][0] = save;
                }
            // Spin a column down
            }else{
                // Get the numbers from the instruction
                int col = Integer.parseInt(line.split(" ")[2].split("=")[1]);
                int num = Integer.parseInt(line.split(" ")[4]);
                // Repeat num times
                for (int i=0; i<num % 6; ++i){
                    // Save the edge
                    boolean save = screen[5][col];
                    // Move the column
                    for (int j=5; j>0; --j){
                        screen[j][col] = screen[j-1][col];
                    }
                    // Put the edge back
                    screen[0][col] = save;
                }
            }
        }

        // The answer to the problem
        int part1 = 0;
        String part2 = Library.read(screen);

        // Loop through every pixel
        for (boolean[] row : screen){
            for (boolean pixel : row){
                // Add if the pixel is on
                if (pixel){
                    ++part1;
                }
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}