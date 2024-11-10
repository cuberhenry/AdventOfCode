import com.aoc.mylibrary.Library;

public class Main {
    final private static String name = "Day 14: Chocolate Charts";
    public static void main(String args[]) {
        // The target
        int num = Library.getInt(args);
        // The ongoing list of recipes
        StringBuilder recipes = new StringBuilder("37");
        // The recipes the two elves are looking at
        int pointer1 = 0;
        int pointer2 = 1;

        // The answer to the problem
        String part1 = "";
        int part2 = 0;

        // Continue until the end condition has been found
        while (part2 == 0){
            // Get each score
            int score1 = recipes.charAt(pointer1) - '0';
            int score2 = recipes.charAt(pointer2) - '0';
            // Add the resulting score sum to recipes
            recipes.append(score1 + score2);
            // Move each elf forward its score amount
            pointer1 = (pointer1 + score1 + 1) % recipes.length();
            pointer2 = (pointer2 + score2 + 1) % recipes.length();

            if (part1.isBlank() && recipes.length() >= num + 10){
                part1 = recipes.substring(num,num+10);
            }

            if (recipes.substring(Math.max(0,recipes.length()-7)).indexOf("" + num) != -1){
                part2 = recipes.indexOf("" + num);
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}