package com.aoc.mylibrary;

/**
 * A container for many useful functions for completing
 * <a href="https://adventofcode.com/">Advent Of Code</a>
 * puzzles.
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.math.BigInteger;
import java.security.MessageDigest;

public class Library {
    public static Scanner getScanner(String[] args){
        try {
            return(new Scanner(new File(args.length == 0 ? "input.txt" : args[0])));
        }catch (Exception e){
            System.out.println("File not found");
            System.exit(1);
            return null;
        }
    }

    public static String getString(String[] args){
        try {
            return Files.readString(Paths.get(args.length == 0 ? "input.txt" : args[0])).trim();
        }catch (Exception e){
            System.out.println("File not found");
            System.exit(1);
            return null;
        }
    }

    public static String[] getStringArray(String[] args, String delim){
        return getString(args).split(delim);
    }

    public static int getInt(String[] args){
        return Integer.parseInt(getString(args));
    }

    public static int[] getIntArray(String[] args, String delim){
        return intSplit(getString(args),delim);
    }

    public static char[] getCharArray(String[] args){
        return getString(args).toCharArray();
    }

    public static boolean[] getBooleanArray(String[] args, char match){
        char[] input = getCharArray(args);
        boolean[] array = new boolean[input.length];
        for (int i=0; i<input.length; ++i){
            array[i] = input[i] == match;
        }
        return array;
    }

    public static char[][] getCharMatrix(String[] args){
        Scanner sc = getScanner(args);
        ArrayList<String> input = new ArrayList<>();
        while (sc.hasNext()){
            input.add(sc.nextLine());
        }
        char[][] grid = new char[input.size()][];
        for (int i=0; i<input.size(); ++i){
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }

    public static String[][] getAssembly(String[] args){
        String[] lines = getStringArray(args,"\n");
        String[][] assembly = new String[lines.length][];
        for (int i=0; i<lines.length; ++i){
            assembly[i] = lines[i].split(" ");
        }
        return assembly;
    }

    public static void print(long part1, long part2, String name){
        print(part1+"",part2+"",name);
    }

    public static void print(long part1, String part2, String name){
        print(part1+"",part2,name);
    }

    public static void print(String part1, long part2, String name){
        print(part1,part2+"",name);
    }

    public static void print(String part1, String part2, String name){
        System.out.println(name + "\nPart 1:\n" + part1 + "\n\nPart 2:\n" + part2 + "\n");
    }

    public static long GCD(long a, long b){
        if (b == 0){
            return a;
        }
        return GCD(b,a%b);
    }

    public static long LCM(long a, long b){
        return (a / GCD(a,b)) * b;
    }

    public static int BFS(HashMap<String,Character> map, int sourceX, int sourceY, int destX, int destY){
        HashSet<String> history = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(sourceX + " " + sourceY);
        int steps = 0;
        while (!queue.isEmpty()){
            ++steps;
            Queue<String> newQueue = new LinkedList<>();
            while (!queue.isEmpty()){
                String line = queue.remove();
                history.add(line);
                String[] split = line.split(" ");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);

                for (int j=0; j<4; ++j){
                    int newX = x;
                    int newY = y;
                    switch(j){
                        case 0 -> ++newY;
                        case 1 -> --newY;
                        case 2 -> ++newX;
                        case 3 -> --newX;
                    }

                    if (newX == destX && newY == destY){
                        return steps;
                    }

                    String newState = newX + " " + newY;
                    if (history.contains(newState) || queue.contains(newState) || newQueue.contains(newState)){
                        continue;
                    }
                    if (map.containsKey(newState) && map.get(newState) == '.'){
                        newQueue.add(newState);
                    }
                }
            }
            queue = newQueue;
        }
        return steps-1;
    }

    public static int BFS(HashMap<String,Character> map, int sourceX, int sourceY){
        return BFS(map,sourceX,sourceY,Integer.MAX_VALUE,Integer.MAX_VALUE);
    }

    public static int[][] distMap(ArrayList<String> nodeNames, ArrayList<String[]> dists, boolean bidirectional){
        int[][] table = new int[nodeNames.size()][nodeNames.size()];

        for (String[] dist : dists){
            int first = nodeNames.indexOf(dist[0]);
            int other = nodeNames.indexOf(dist[1]);

            table[first][other] = Integer.parseInt(dist[2]);
            if (bidirectional){
                table[other][first] = table[first][other];
            }
        }

        return table;
    }

    public static String trim(String string, char c){
        int start = 0;
        while (start < string.length() && string.charAt(start) == c){
            ++start;
        }
        if (start == string.length()){
            return "";
        }
        int end = string.length();
        while (string.charAt(end-1) == c){
            --end;
        }
        return string.substring(start,end);
    }

    public static String pad(String string, char c, int length, boolean left){
        while (string.length() < length){
            if (left){
                string = c + string;
            }else{
                string += c;
            }
        }
        return string;
    }

    public static int[] intSplit(String string, String delim){
        String[] split = string.split(delim);
        int[] intSplit = new int[split.length];

        for (int i=0; i<split.length; ++i){
            intSplit[i] = Integer.parseInt(split[i].trim());
        }

        return intSplit;
    }

    public static long[] longSplit(String string, String delim){
        String[] split = string.split(delim);
        long[] longSplit = new long[split.length];

        for (int i=0; i<split.length; ++i){
            longSplit[i] = Long.parseLong(split[i].trim());
        }

        return longSplit;
    }

    public static int indexOf(char[] array, char value){
        for (int i=0; i<array.length; ++i){
            if (array[i] == value){
                return i;
            }
        }
        return -1;
    }

    public static int count(char[] array, char match){
        int count = 0;
        for (char c : array){
            if (c == match){
                ++count;
            }
        }
        return count;
    }

    public static int count(boolean[] array){
        int count = 0;
        for (boolean b : array){
            if (b){
                ++count;
            }
        }
        return count;
    }

    public static int maxIndex(int[] array){
        int index = 0;
        for (int i=1; i<array.length; ++i){
            if (array[i] > array[index]){
                index = i;
            }
        }
        return index;
    }

    public static int sum(int[] array){
        int sum = 0;
        for (int num : array){
            sum += num;
        }
        return sum;
    }

    public static int absSum(int[] array){
        int sum = 0;
        for (int num : array){
            sum += Math.abs(num);
        }
        return sum;
    }

    public static String md5(String string, boolean fill){
        String hash = md5(string);
        pad(hash,'0',32,true);
        return hash;
    }

    public static String md5(String string){
        // Create the hasher
        MessageDigest md;
        try{
            md = MessageDigest.getInstance("MD5");
        } catch (Exception e){
            return "";
        }
        // Find the hash of the string
        md.update(string.getBytes(),0,string.length());
        return new BigInteger(1,md.digest()).toString(16);
    }

    public static String knotHash(String input){
        // The list of numbers
        int[] list = new int[256];
        for (int i=1; i<list.length; ++i){
            list[i] = i;
        }
        // The values to be carried over between twists
        int skip = 0;
        int position = 0;

        // Add the extra turns
        input += "\021\037I/\027";
        // Create the list of lengths based on the ascii characters
        int[] lengths = input.chars().toArray();
        // Perform the twists 64 times
        for (int i=0; i<64; ++i){
            position = knotHashRotation(list,lengths,position,skip);
            skip += lengths.length;
        }
        
        // Create the hash
        String hash = "";
        // Loop through every dense value
        for (int i=0; i<16; ++i){
            // Create the dense value by XORing each one
            int value = list[i*16];
            for (int j=1; j<16; ++j){
                value ^= list[i*16+j];
            }

            // Add the hex
            hash += pad(Integer.toHexString(value),'0',2,true);
        }

        return hash;
    }

    public static int knotHashRotation(int[] list, int[] lengths, int position, int skip){
        // Loop through every length
        for (int length : lengths){
            // Skip small numbers
            if (length < 2){
                position = (position + length + skip) % 256;
                ++skip;
                continue;
            }
            // Get the start and end positions of the reversed section
            int start = position;
            int end = position + length - 1;

            // Reverse the section
            for (int i=0; i<(end-start+1)/2; ++i){
                // Switch the two numbers
                int help = list[(start + i) % 256];
                list[(start + i) % 256] = list[(end - i) % 256];
                list[(end - i) % 256] = help;
            }

            // Change the current position
            position = (position + length + skip) % 256;
            // Increment the skip
            ++skip;
        }
        return position;
    }

    public static String read(boolean[][] display){
        if (display.length == 6){
            HashMap<Integer,Character> letters = new HashMap<>();
            letters.put(422148690,'A');
            letters.put(959335004,'B');
            letters.put(422068812,'C');
            letters.put(1024344606,'E');
            letters.put(1024344592,'F');
            letters.put(422074958,'G');
            letters.put(623856210,'H');
            letters.put(474091662,'I');
            letters.put(203491916,'J');
            letters.put(625758866,'K');
            letters.put(554189342,'L');
            letters.put(422136396,'O');
            letters.put(959017488,'P');
            letters.put(959017618,'R');
            letters.put(623462988,'U');
            letters.put(588583044,'Y');
            letters.put(1008869918,'Z');
            String text = "";
            for (int i=0; i<display[0].length; i += 5){
                int hash = 0;
                for (int j=0; j<6; ++j){
                    for (int k=0; k<5; ++k){
                        hash <<= 1;
                        if (display[j][i+k]){
                            ++hash;
                        }
                    }
                }
                if (letters.containsKey(hash)){
                    text += letters.get(hash);
                }else{
                    text = "ERROR: New Letter (" + hash + ")\n";
                    for (boolean[] row : display){
                        for (int j=i; j<i+5; ++j){
                            text += row[j] ? '#' : '.';
                        }
                        text += '\n';
                    }
                    break;
                }
            }
            return text;
        }
        HashMap<Long,Character> letters = new HashMap<>();
        letters.put(1126328852231362686L,'B');
        letters.put(549863600932653150L,'C');
        letters.put(1144057308981102624L,'F');
        letters.put(549863601050360029L,'G');
        letters.put(603911296530126945L,'H');
        letters.put(126672675233474716L,'J');
        letters.put(585610922974906431L,'L');
        letters.put(608485791103334625L,'N');
        letters.put(1126328852214319136L,'P');
        letters.put(1126328852281960545L,'R');
        letters.put(1135193120993052735L,'Z');
        String text = "";
        for (int i=0; i<display[0].length; i += 8){
            long hash = 0;
            for (int j=0; j<10; ++j){
                for (int k=0; k<6; ++k){
                    hash <<= 1;
                    if (display[j][i+k]){
                        ++hash;
                    }
                }
            }
            if (letters.containsKey(hash)){
                text += letters.get(hash);
            }else{
                text = "ERROR: New Letter (" + hash + ")\n";
                for (boolean[] row : display){
                    for (int j=i; j<i+6; ++j){
                        text += row[j] ? '#' : '.';
                    }
                    text += '\n';
                }
                break;
            }
        }
        return text;
    }
}