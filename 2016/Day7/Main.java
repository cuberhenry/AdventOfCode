import com.aoc.mylibrary.Library;
import java.util.Scanner;

public class Main {
    final private static String name = "Day 7: Internet Protocol Version 7";
    public static void main(String args[]) {
        Scanner sc = Library.getScanner(args);

        // The number of IPs that fit the requirements
        int part1 = 0;
        int part2 = 0;

        // Look through every IP
        while (sc.hasNext()){
            // Grab the IP
            String ip = sc.nextLine();
            if (ip.matches("^([^\\[]|\\[[^\\]]*\\])*(\\w)(?!\\2)(\\w)\\3\\2.*$")
                && !ip.matches("^.*\\[[^\\]]*(\\w)(?!\\1)(\\w)\\2\\1.*$")){
                ++part1;
            }
            if (ip.matches("^([^\\[]|\\[[^\\]]*\\])*(\\w)(?!\\2)(\\w)\\2.*\\[[^\\]]*\\3\\2\\3.*$")
                || ip.matches("^.*\\[[^\\]]*(\\w)(?!\\1)(\\w)\\1.*\\][^\\[]*\\2\\1\\2.*$")){
                ++part2;
            }
        }

        // Print the answer
        Library.print(part1,part2,name);
    }
}