import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 2: Cube Conundrum";
    public static void main(String args[]) {
        String[] input = Library.getStringArray(args,"\n");

        // The answer to the problem
        int part1 = 0;
        int part2 = 0;
        // Used for Part 1, the suggested amounts of cubes
        int red = 12;
        int green = 13;
        int blue = 14;

        // Loop through every game
        for (String game : input){
            // Used for Part 1, whether the game is possible
            boolean success = true;
            // Used for Part 2, the minimum amount of each cube required
            int thisRed = 0;
            int thisGreen = 0;
            int thisBlue = 0;
            // Take in the game
            String[] line = game.split(" ");

            // Loop through every colored group of cubes
            for (int i=2; i<line.length; i += 2){
                // The number of cubes of a specific type pulled
                int amount = Integer.parseInt(line[i]);

                // Check the color and compare the amount to the given amount
                // On failure, move on to the next game
                if (line[i+1].charAt(0) == 'r' && amount > red){
                    success = false;
                }else if (line[i+1].charAt(0) == 'b' && amount > blue){
                    success = false;
                }else if (line[i+1].charAt(0) == 'g' && amount > green){
                    success = false;
                }

                // Check the color and save the new minimum
                if (line[i+1].charAt(0) == 'r'){
                    thisRed = Math.max(thisRed,amount);
                }else if (line[i+1].charAt(0) == 'b'){
                    thisBlue = Math.max(thisBlue,amount);
                }else{
                    thisGreen = Math.max(thisGreen,amount);
                }
            }

            // Add the game ID if the game was possible
            if (success){
                part1 += Integer.parseInt(line[1].substring(0,line[1].length()-1));
            }

            // Add the power of the minimum set of cubes
            part2 += thisRed * thisGreen * thisBlue;
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}