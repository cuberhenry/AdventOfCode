/*
Henry Anderson
Advent of Code 2015 Day 10 https://adventofcode.com/2015/day/10
Input: https://adventofcode.com/2015/day/10/input
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
        // Take in the starting number
        String line = sc.next();

        // Create a hashmap for finding the quantity of each element initially
        HashMap<String,String> sequences = new HashMap<>();
        sequences.put("22","H");
        sequences.put("13112221133211322112211213322112","He");
        sequences.put("312211322212221121123222112","Li");
        sequences.put("111312211312113221133211322112211213322112","Be");
        sequences.put("1321132122211322212221121123222112","B");
        sequences.put("3113112211322112211213322112","C");
        sequences.put("111312212221121123222112","N");
        sequences.put("132112211213322112","O");
        sequences.put("31121123222112","F");
        sequences.put("111213322112","Ne");
        sequences.put("123222112","Na");
        sequences.put("3113322112","Mg");
        sequences.put("1113222112","Al");
        sequences.put("1322112","Si");
        sequences.put("311311222112","P");
        sequences.put("1113122112","S");
        sequences.put("132112","Cl");
        sequences.put("3112","Ar");
        sequences.put("1112","K");
        sequences.put("12","Ca");
        sequences.put("3113112221133112","Sc");
        sequences.put("11131221131112","Ti");
        sequences.put("13211312","V");
        sequences.put("31132","Cr");
        sequences.put("111311222112","Mn");
        sequences.put("13122112","Fe");
        sequences.put("32112","Co");
        sequences.put("11133112","Ni");
        sequences.put("131112","Cu");
        sequences.put("312","Zn");
        sequences.put("13221133122211332","Ga");
        sequences.put("31131122211311122113222","Ge");
        sequences.put("11131221131211322113322112","As");
        sequences.put("13211321222113222112","Se");
        sequences.put("3113112211322112","Br");
        sequences.put("11131221222112","Kr");
        sequences.put("1321122112","Rb");
        sequences.put("3112112","Sr");
        sequences.put("1112133","Y");
        sequences.put("12322211331222113112211","Zr");
        sequences.put("1113122113322113111221131221","Nb");
        sequences.put("13211322211312113211","Mo");
        sequences.put("311322113212221","Tc");
        sequences.put("132211331222113112211","Ru");
        sequences.put("311311222113111221131221","Rh");
        sequences.put("111312211312113211","Pd");
        sequences.put("132113212221","Ag");
        sequences.put("3113112211","Cd");
        sequences.put("11131221","In");
        sequences.put("13211","Sn");
        sequences.put("3112221","Sb");
        sequences.put("1322113312211","Te");
        sequences.put("311311222113111221","I");
        sequences.put("11131221131211","Xe");
        sequences.put("13211321","Cs");
        sequences.put("311311","Ba");
        sequences.put("11131","La");
        sequences.put("1321133112","Ce");
        sequences.put("31131112","Pr");
        sequences.put("111312","Nd");
        sequences.put("132","Pm");
        sequences.put("311332","Sm");
        sequences.put("1113222","Eu");
        sequences.put("13221133112","Gd");
        sequences.put("3113112221131112","Tb");
        sequences.put("111312211312","Dy");
        sequences.put("1321132","Ho");
        sequences.put("311311222","Er");
        sequences.put("11131221133112","Tm");
        sequences.put("1321131112","Yb");
        sequences.put("311312","Lu");
        sequences.put("11132","Hf");
        sequences.put("13112221133211322112211213322113","Ta");
        sequences.put("312211322212221121123222113","W");
        sequences.put("111312211312113221133211322112211213322113","Re");
        sequences.put("1321132122211322212221121123222113","Os");
        sequences.put("3113112211322112211213322113","Ir");
        sequences.put("111312212221121123222113","Pt");
        sequences.put("132112211213322113","Au");
        sequences.put("31121123222113","Hg");
        sequences.put("111213322113","Tl");
        sequences.put("123222113","Pb");
        sequences.put("3113322113","Bi");
        sequences.put("1113222113","Po");
        sequences.put("1322113","At");
        sequences.put("311311222113","Rn");
        sequences.put("1113122113","Fr");
        sequences.put("132113","Ra");
        sequences.put("3113","Ac");
        sequences.put("1113","Th");
        sequences.put("13","Pa");
        sequences.put("3","U");

        // A hashmap for what each element turns into after one step
        HashMap<String,String[]> decays = new HashMap<>();
        decays.put("H",new String[] {"H"});
        decays.put("He",new String[] {"Hf","Pa","H","Ca","Li"});
        decays.put("Li",new String[] {"He"});
        decays.put("Be",new String[] {"Ge","Ca","Li"});
        decays.put("B",new String[] {"Be"});
        decays.put("C",new String[] {"B"});
        decays.put("N",new String[] {"C"});
        decays.put("O",new String[] {"N"});
        decays.put("F",new String[] {"O"});
        decays.put("Ne",new String[] {"F"});
        decays.put("Na",new String[] {"Ne"});
        decays.put("Mg",new String[] {"Pm","Na"});
        decays.put("Al",new String[] {"Mg"});
        decays.put("Si",new String[] {"Al"});
        decays.put("P",new String[] {"Ho","Si"});
        decays.put("S",new String[] {"P"});
        decays.put("Cl",new String[] {"S"});
        decays.put("Ar",new String[] {"Cl"});
        decays.put("K",new String[] {"Ar"});
        decays.put("Ca",new String[] {"K"});
        decays.put("Sc",new String[] {"Ho","Pa","H","Ca","Co"});
        decays.put("Ti",new String[] {"Sc"});
        decays.put("V",new String[] {"Ti"});
        decays.put("Cr",new String[] {"V"});
        decays.put("Mn",new String[] {"Cr","Si"});
        decays.put("Fe",new String[] {"Mn"});
        decays.put("Co",new String[] {"Fe"});
        decays.put("Ni",new String[] {"Zn","Co"});
        decays.put("Cu",new String[] {"Ni"});
        decays.put("Zn",new String[] {"Cu"});
        decays.put("Ga",new String[] {"Eu","Ca","Ac","H","Ca","Zn"});
        decays.put("Ge",new String[] {"Ho","Ga"});
        decays.put("As",new String[] {"Ge","Na"});
        decays.put("Se",new String[] {"As"});
        decays.put("Br",new String[] {"Se"});
        decays.put("Kr",new String[] {"Br"});
        decays.put("Rb",new String[] {"Kr"});
        decays.put("Sr",new String[] {"Rb"});
        decays.put("Y",new String[] {"Sr","U"});
        decays.put("Zr",new String[] {"Y","H","Ca","Tc"});
        decays.put("Nb",new String[] {"Er","Zr"});
        decays.put("Mo",new String[] {"Nb"});
        decays.put("Tc",new String[] {"Mo"});
        decays.put("Ru",new String[] {"Eu","Ca","Tc"});
        decays.put("Rh",new String[] {"Ho","Ru"});
        decays.put("Pd",new String[] {"Rh"});
        decays.put("Ag",new String[] {"Pd"});
        decays.put("Cd",new String[] {"Ag"});
        decays.put("In",new String[] {"Cd"});
        decays.put("Sn",new String[] {"In"});
        decays.put("Sb",new String[] {"Pm","Sn"});
        decays.put("Te",new String[] {"Eu","Ca","Sb"});
        decays.put("I",new String[] {"Ho","Te"});
        decays.put("Xe",new String[] {"I"});
        decays.put("Cs",new String[] {"Xe"});
        decays.put("Ba",new String[] {"Cs"});
        decays.put("La",new String[] {"Ba"});
        decays.put("Ce",new String[] {"La","H","Ca","Co"});
        decays.put("Pr",new String[] {"Ce"});
        decays.put("Nd",new String[] {"Pr"});
        decays.put("Pm",new String[] {"Nd"});
        decays.put("Sm",new String[] {"Pm","Ca","Zn"});
        decays.put("Eu",new String[] {"Sm"});
        decays.put("Gd",new String[] {"Eu","Ca","Co"});
        decays.put("Tb",new String[] {"Ho","Gd"});
        decays.put("Dy",new String[] {"Tb"});
        decays.put("Ho",new String[] {"Dy"});
        decays.put("Er",new String[] {"Ho","Pm"});
        decays.put("Tm",new String[] {"Er","Ca","Co"});
        decays.put("Yb",new String[] {"Tm"});
        decays.put("Lu",new String[] {"Yb"});
        decays.put("Hf",new String[] {"Lu"});
        decays.put("Ta",new String[] {"Hf","Pa","H","Ca","W"});
        decays.put("W",new String[] {"Ta"});
        decays.put("Re",new String[] {"Ge","Ca","W"});
        decays.put("Os",new String[] {"Re"});
        decays.put("Ir",new String[] {"Os"});
        decays.put("Pt",new String[] {"Ir"});
        decays.put("Au",new String[] {"Pt"});
        decays.put("Hg",new String[] {"Au"});
        decays.put("Tl",new String[] {"Hg"});
        decays.put("Pb",new String[] {"Tl"});
        decays.put("Bi",new String[] {"Pm","Pb"});
        decays.put("Po",new String[] {"Bi"});
        decays.put("At",new String[] {"Po"});
        decays.put("Rn",new String[] {"Ho","At"});
        decays.put("Fr",new String[] {"Rn"});
        decays.put("Ra",new String[] {"Fr"});
        decays.put("Ac",new String[] {"Ra"});
        decays.put("Th",new String[] {"Ac"});
        decays.put("Pa",new String[] {"Th"});
        decays.put("U",new String[] {"Pa"});

        // A list of all of the elements
        ArrayList<String> elements = new ArrayList<>(Arrays.asList(new String[]
                            {"H","He","Li","Be","B","C","N","O","F","Ne",
                             "Na","Mg","Al","Si","P","S","Cl","Ar","K","Ca",
                             "Sc","Ti","V","Cr","Mn","Fe","Co","Ni","Cu","Zn",
                             "Ga","Ge","As","Se","Br","Kr","Rb","Sr","Y","Zr",
                             "Nb","Mo","Tc","Ru","Rh","Pd","Ag","Cd","In","Sn",
                             "Sb","Te","I","Xe","Cs","Ba","La","Ce","Pr","Nd",
                             "Pm","Sm","Eu","Gd","Tb","Dy","Ho","Er","Tm","Yb",
                             "Lu","Hf","Ta","W","Re","Os","Ir","Pt","Au","Hg",
                             "Tl","Pb","Bi","Po","At","Rn","Fr","Ra","Ac","Th",
                             "Pa","U"}));

        // The number of each element occurring in the number
        int[] numElements = new int[elements.size()];

        // Loop through every element in the input
        for (int i=0; i<line.length();){
            // Loop starting at the end to try to find the biggest element
            for (int j=line.length(); ; --j){
                // If the currently selected substring is an element
                if (sequences.containsKey(line.substring(i,j))){
                    // Add the element to the starting amounts
                    ++numElements[elements.indexOf(sequences.get(line.substring(i,j)))];
                    // Skip to after the element
                    i = j;
                    // Largest element found
                    break;
                }
            }
        }

        // The number of steps to perform
        int numLoops = 40;
        
        // Part 1 finds the size of the result after 40 rounds
        // Part 2 finds the size of the result after 50 rounds
        if (PART == 2){
            numLoops = 50;
        }

        // Perform numLoops times
        for (int i=0; i<numLoops; ++i){
            // The amount of each element in the next step
            int[] newElements = new int[numElements.length];
            // Loop through every currently existing element
            for (int j=0; j<elements.size(); ++j){
                // Add the number of resulting elements from this element
                for (String element : decays.get(elements.get(j))){
                    newElements[elements.indexOf(element)] += numElements[j];
                }
            }
            // Save the next amounts
            numElements = newElements;
        }

        // The number of characters in the result
        int total = 0;

        // Loop through every element
        for (int i=0; i<numElements.length; ++i){
            // Loop through every sequence in the set
            for (String key : sequences.keySet()){
                // If the set is from the current element
                if (sequences.get(key).equals(elements.get(i))){
                    // Add that many times the length of the element
                    total += key.length() * numElements[i];
                    break;
                }
            }
        }

        // Print the answer
        System.out.println(total);
    }
}