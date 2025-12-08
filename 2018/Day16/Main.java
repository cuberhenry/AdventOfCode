import com.aoc.mylibrary.Library;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    final private static String name = "Day 16: Chronal Classification";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The answer to the problem
        int part1 = 0;
        // The current line of input
        String line;

        // A list of every possible opp for each oppcode
        ArrayList<ArrayList<String>> opps = new ArrayList<>();
        {
            // Without any tests, no assumptions can be made
            ArrayList<String> opp = new ArrayList<>();
            opp.add("addr");
            opp.add("addi");
            opp.add("mulr");
            opp.add("muli");
            opp.add("banr");
            opp.add("bani");
            opp.add("borr");
            opp.add("bori");
            opp.add("setr");
            opp.add("seti");
            opp.add("gtir");
            opp.add("gtri");
            opp.add("gtrr");
            opp.add("eqir");
            opp.add("eqri");
            opp.add("eqrr");
            opps.add(opp);
            for (int i=1; i<16; ++i){
                opps.add(new ArrayList<>(opp));
            }
        }

        // A list of all opps
        String[] oppcodes = {"addr","addi","mulr","muli",
                             "banr","bani","borr","bori",
                             "setr","seti","gtir","gtri",
                             "gtrr","eqir","eqri","eqrr"};

        // Continue through all of the test operations
        while ((line = sc.nextLine()).length() > 0){
            // The three sets of values
            int[] start = new int[4];
            int[] opp = new int[4];
            int[] end = new int[4];

            // Take in each of the three sets of values as ints
            String[] split = line.substring(9,19).split(", ");
            for (int i=0; i<4; ++i){
                start[i] = Integer.parseInt(split[i]);
            }

            split = sc.nextLine().split(" ");
            for (int i=0; i<4; ++i){
                opp[i] = Integer.parseInt(split[i]);
            }

            split = sc.nextLine().substring(9,19).split(", ");
            for (int i=0; i<4; ++i){
                end[i] = Integer.parseInt(split[i]);
            }

            // Skip the buffer line
            sc.nextLine();

            // The number of operations this example could be
            int count = 0;

            // Loop through every operation
            for (int i=0; i<16; ++i){
                // Attempt the given operation
                boolean bool;
                switch(i){
                    case 0 -> {bool = end[opp[3]] == start[opp[1]] + start[opp[2]];}
                    case 1 -> {bool = end[opp[3]] == start[opp[1]] + opp[2];}
                    case 2 -> {bool = end[opp[3]] == start[opp[1]] * start[opp[2]];}
                    case 3 -> {bool = end[opp[3]] == start[opp[1]] * opp[2];}
                    case 4 -> {bool = end[opp[3]] == (start[opp[1]] & start[opp[2]]);}
                    case 5 -> {bool = end[opp[3]] == (start[opp[1]] & opp[2]);}
                    case 6 -> {bool = end[opp[3]] == (start[opp[1]] | start[opp[2]]);}
                    case 7 -> {bool = end[opp[3]] == (start[opp[1]] | opp[2]);}
                    case 8 -> {bool = end[opp[3]] == start[opp[1]];}
                    case 9 -> {bool = end[opp[3]] == opp[1];}
                    case 10 -> {bool = end[opp[3]] == (opp[1] > start[opp[2]] ? 1 : 0);}
                    case 11 -> {bool = end[opp[3]] == (start[opp[1]] > opp[2] ? 1 : 0);}
                    case 12 -> {bool = end[opp[3]] == (start[opp[1]] > start[opp[2]] ? 1 : 0);}
                    case 13 -> {bool = end[opp[3]] == (opp[1] == start[opp[2]] ? 1 : 0);}
                    case 14 -> {bool = end[opp[3]] == (start[opp[1]] == opp[2] ? 1 : 0);}
                    default -> {bool = end[opp[3]] == (start[opp[1]] == start[opp[2]] ? 1 : 0);}
                }

                // If the operation succeeded
                if (bool){
                    // Increase the number
                    ++count;
                }else{
                    // This oppcode cannot be this operation
                    opps.get(opp[0]).remove(oppcodes[i]);
                }
            }

            if (count >= 3){
                ++part1;
            }
        }

        // Loop through every opp
        for (int i=0; i<opps.size(); ++i){
            // The oppcode can only be one opp
            if (opps.get(i).size() == 1){
                // Save the opp name
                String opp = opps.get(i).getFirst();
                // Remove it from every oppcode
                for (int j=0; j<opps.size(); ++j){
                    opps.get(j).remove(opp);
                }
                // Assign the oppcode to the proper opp
                oppcodes[i] = opp;
                // Start over
                i = -1;
            }
        }
        
        // The registers during the program
        int[] regs = new int[4];
        // Skip the buffer line
        sc.nextLine();

        // Take in every line of the sample program
        while (sc.hasNextLine()){
            // Take in the operation as ints
            int[] opp = Library.intSplit(sc.nextLine());

            // Perform the given operation
            // O A B C
            // x represents a value rather than a register
            switch(oppcodes[opp[0]]){
                // C <- A + B
                case "addr" -> {regs[opp[3]] = regs[opp[1]] + regs[opp[2]];}
                // C <- A + x
                case "addi" -> {regs[opp[3]] = regs[opp[1]] + opp[2];}
                // C <- A * B
                case "mulr" -> {regs[opp[3]] = regs[opp[1]] * regs[opp[2]];}
                // C <- A * x
                case "muli" -> {regs[opp[3]] = regs[opp[1]] * opp[2];}
                // C <- A & B
                case "banr" -> {regs[opp[3]] = (regs[opp[1]] & regs[opp[2]]);}
                // C <- A & x
                case "bani" -> {regs[opp[3]] = (regs[opp[1]] & opp[2]);}
                // C <- A | B
                case "borr" -> {regs[opp[3]] = (regs[opp[1]] | regs[opp[2]]);}
                // C <- A | x
                case "bori" -> {regs[opp[3]] = (regs[opp[1]] | opp[2]);}
                // C <- A
                case "setr" -> {regs[opp[3]] = regs[opp[1]];}
                // C <- x
                case "seti" -> {regs[opp[3]] = opp[1];}
                // C <- x > B
                case "gtir" -> {regs[opp[3]] = opp[1] > regs[opp[2]] ? 1 : 0;}
                // C <- A > x
                case "gtri" -> {regs[opp[3]] = regs[opp[1]] > opp[2] ? 1 : 0;}
                // C <- A > B
                case "gtrr" -> {regs[opp[3]] = regs[opp[1]] > regs[opp[2]] ? 1 : 0;}
                // C <- x == B
                case "eqir" -> {regs[opp[3]] = opp[1] == regs[opp[2]] ? 1 : 0;}
                // C <- A == x
                case "eqri" -> {regs[opp[3]] = regs[opp[1]] == opp[2] ? 1 : 0;}
                // C <- A == B
                case "eqrr" -> {regs[opp[3]] = regs[opp[1]] == regs[opp[2]] ? 1 : 0;}
            }
        }

        // Get the result after executing the code
        int part2 = regs[0];

        // Print the answer
        Library.print(part1,part2,name);
    }
}