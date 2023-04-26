/*
Henry Anderson
Advent of Code 2022 Day 8 https://adventofcode.com/2022/day/8
Input: https://adventofcode.com/2022/day/8/input
1st command line argument is which part of the daily puzzle to solve
2nd command line argument is the file name of the input, defaulted to
    "input.txt"
*/
import java.util.*;
import java.io.*;
public class Main {
    // The desired problem to solve
    static int PART;
    static Scanner sc;
    // The file containing the puzzle input
    static String FILE_NAME = "input.txt";
    public static void main(String args[]) {
        if (args.length < 1 || args.length > 2){
            System.out.println("Wrong number of arguments");
            return;
        }
        // Take in the part and file name
        try {
            PART = Integer.parseInt(args[0]);
        } catch (Exception e){}
        if (!(PART == 1 || PART == 2)){
            System.out.println("Part can only be 1 or 2");
            return;
        }
        if (args.length == 2){
            FILE_NAME = args[1];
        }
        try {
            sc = new Scanner(new File(FILE_NAME));
        }catch (Exception e){
            System.out.println("File not found");
            return;
        }
        // The value to be printed
        int total = 0;
        // The grid of trees
        ArrayList<int[]> grid = new ArrayList<>();
        // Take in the first line of input
        String line = sc.next();
        // The width of the grid of trees
        final int WIDTH = line.length();

        // Fill in the grid with trees
        while (true){
            int[] row = new int[WIDTH];
            for (int i=0; i<WIDTH; ++i){
                row[i] = Character.getNumericValue(line.charAt(i));
            }
            grid.add(row);
            if (!sc.hasNext()){
                break;
            }
            line = sc.next();
        }

        // The height of the grid of trees
        final int HEIGHT = grid.size();
        
        // Part 1 finds how many trees are visible from the perimeter
        if (PART == 1){
            // List of all visible trees except those on the edge
            ArrayList<String> visible = new ArrayList<>();

            // Vertically
            for (int i=1; i<WIDTH-1; ++i){
                // From the top
                int currHeight = grid.get(0)[i];
                for (int j=1; j<HEIGHT-1 && currHeight<9; ++j){
                    // Only do something if there's a new tallest
                    if (grid.get(j)[i] > currHeight){
                        // Add to visible if not already known
                        if (!visible.contains(i+" "+j)){
                            visible.add(i+" "+j);
                        }
                        // Update the current biggest height
                        currHeight = grid.get(j)[i];
                    }
                }

                // From the bottom
                currHeight = grid.get(HEIGHT-1)[i];
                for (int j=HEIGHT-2; j>0 && currHeight<9; --j){
                    // Only do something if there's a new tallest
                    if (grid.get(j)[i] > currHeight){
                        // Add to visible if not already known
                        if (!visible.contains(i+" "+j)){
                            visible.add(i+" "+j);
                        }
                        // Update the current biggest height
                        currHeight = grid.get(j)[i];
                    }
                }
            }

            // Horizontally
            for (int j=1; j<HEIGHT-1; ++j){
                // From the left
                int currHeight = grid.get(j)[0];
                for (int i=1; i<WIDTH-1 && currHeight<9; ++i){
                    // Only do something if there's a new tallest
                    if (grid.get(j)[i] > currHeight){
                        // Add to visible if not already known
                        if (!visible.contains(i+" "+j)){
                            visible.add(i+" "+j);
                        }
                        // Update the current biggest height
                        currHeight = grid.get(j)[i];
                    }
                }

                // From the right
                currHeight = grid.get(j)[WIDTH-1];
                for (int i=WIDTH-2; i>0 && currHeight<9; --i){
                    // Only do something if there's a new tallest
                    if (grid.get(j)[i] > currHeight){
                        // Add to visible if not already known
                        if (!visible.contains(i+" "+j)){
                            visible.add(i+" "+j);
                        }
                        // Update the current biggest height
                        currHeight = grid.get(j)[i];
                    }
                }
            }

            // Add to the number visible all of the edge trees
            total = visible.size() + 2*HEIGHT + 2*WIDTH - 4;
        }

        // Part 2 finds the greatest visibility score of a tree
        if (PART == 2){
            // The visibility scores of all trees, skipping the ends
            // because we already know their scores are 0
            int[][] scores = new int[HEIGHT][WIDTH];
            // Initialize them all to 1
            for (int i=1; i<HEIGHT-1; ++i){
                for (int j=1; j<WIDTH-1; ++j){
                    scores[i][j] = 1;
                }
            }
            
            // Vertically
            for (int i=1; i<WIDTH-1; ++i){
                // The current visible trees
                Stack<Integer> stack = new Stack<>();
                // Go down tree by tree
                for (int j=1; j<HEIGHT-1; ++j){
                    // Update previous trees' southward visibility
                    while (!stack.isEmpty() && grid.get(j)[i] > stack.peek()%10){
                        // If a tree is smaller than the current tree, it can see up
                        // to the current tree but not past
                        scores[stack.peek()/10][i] *= j-stack.peek()/10;
                        if (scores[stack.peek()/10][i] > total){
                            total = scores[stack.peek()/10][i];
                        }
                        stack.pop();
                    }

                    // Update the current tree's northward visibility
                    if (stack.isEmpty()){
                        scores[j][i] *= j;
                    }else{
                        scores[j][i] *= j-stack.peek()/10;

                        // Trees can't see past equally sized trees
                        if (grid.get(j)[i] == stack.peek()%10){
                            scores[stack.peek()/10][i] *= j-stack.peek()/10;
                            if (scores[stack.peek()/10][i] > total){
                                total = scores[stack.peek()/10][i];
                            }
                            stack.pop();
                        }
                    }

                    // Add the current tree to the stack
                    stack.push(j*10+grid.get(j)[i]);
                }

                // Update all remaining trees to be able to see to the south
                // edge of the map
                while (!stack.isEmpty()){
                    scores[stack.peek()/10][i] *= HEIGHT-1-stack.peek()/10;
                    if (scores[stack.peek()/10][i] > total){
                        total = scores[stack.peek()/10][i];
                    }
                    stack.pop();
                }
            }

            // Horizontally
            for (int j=1; j<HEIGHT-1; ++j){
                // The current visible trees
                Stack<Integer> stack = new Stack<>();
                // Go right tree by tree
                for (int i=1; i<WIDTH-1; ++i){
                    // Update previous trees' eastward visibility
                    while (!stack.isEmpty() && grid.get(j)[i] > stack.peek()%10){
                        // If a tree is smaller than the current tree, it can see up
                        // to the current tree but not past
                        scores[j][stack.peek()/10] *= i-stack.peek()/10;
                        if (scores[j][stack.peek()/10] > total){
                            total = scores[j][stack.peek()/10];
                        }
                        stack.pop();
                    }

                    // Update the current tree's westward visibility
                    if (stack.isEmpty()){
                        scores[j][i] *= i;
                    }else{
                        scores[j][i] *= i-stack.peek()/10;

                        // Trees can't see past equally sized trees
                        if (grid.get(j)[i] == stack.peek()%10){
                            scores[j][stack.peek()/10] *= i-stack.peek()/10;
                            if (scores[j][stack.peek()/10] > total){
                                total = scores[j][stack.peek()/10];
                            }
                            stack.pop();
                        }
                    }

                    // Add the current tree to the stack
                    stack.push(i*10+grid.get(j)[i]);
                }

                // Update all remaining trees to be able to see to the east
                // edge of the map
                while (!stack.isEmpty()){
                    scores[j][stack.peek()/10] *= WIDTH-1-stack.peek()/10;
                    if (scores[j][stack.peek()/10] > total){
                        total = scores[j][stack.peek()/10];
                    }
                    stack.pop();
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}