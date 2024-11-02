package com.aoc.mylibrary;

/**
 * A container for many useful functions for completing
 * <a href="https://adventofcode.com/">Advent Of Code</a>
 * puzzles.
 */
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;
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

    public static int[] intSplit(String string, String delim){
        String[] split = string.split(delim);
        int[] intSplit = new int[split.length];

        for (int i=0; i<split.length; ++i){
            intSplit[i] = Integer.parseInt(split[i]);
        }

        return intSplit;
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
}

/*
 * dmnqstvwx
 *
 * 2016 Day 8 Part 2 Main
 * ####   ## #  # ###  #  #  ##  ###  #    #   #  ## 
 *    #    # #  # #  # # #  #  # #  # #    #   #   # 
 *   #     # #### #  # ##   #    #  # #     # #    # 
 *  #      # #  # ###  # #  #    ###  #      #     # 
 * #    #  # #  # # #  # #  #  # #    #      #  #  # 
 * ####  ##  #  # #  # #  #  ##  #    ####   #   ##  
 * 
 * 2016 Day 8 Part 2 Alt
 * ###  #  # ###  #  #  ##  ####  ##  ####  ### #    
 * #  # #  # #  # #  # #  # #    #  # #      #  #    
 * #  # #  # #  # #  # #    ###  #  # ###    #  #    
 * ###  #  # ###  #  # #    #    #  # #      #  #    
 * # #  #  # # #  #  # #  # #    #  # #      #  #    
 * #  #  ##  #  #  ##   ##  ####  ##  ####  ### #### 
 * 
 * 2018 Day 10 Part 1 Main
 * #####   #        ####   #    #  #    #  #####      ###   #### 
 * #    #  #       #    #  ##   #  #    #  #    #      #   #    #
 * #    #  #       #       ##   #  #    #  #    #      #   #     
 * #    #  #       #       # #  #  #    #  #    #      #   #     
 * #####   #       #       # #  #  ######  #####       #   #     
 * #    #  #       #  ###  #  # #  #    #  #           #   #     
 * #    #  #       #    #  #  # #  #    #  #           #   #     
 * #    #  #       #    #  #   ##  #    #  #       #   #   #     
 * #    #  #       #   ##  #   ##  #    #  #       #   #   #    #
 * #####   ######   ### #  #    #  #    #  #        ###     #### 
 * 
 * 2018 Day 10 Part 1 Alt
 *    ###  #       #####   ######  ######     ###  #####   #    #
 *     #   #       #    #       #  #           #   #    #  #    #
 *     #   #       #    #       #  #           #   #    #  #    #
 *     #   #       #    #      #   #           #   #    #  #    #
 *     #   #       #####      #    #####       #   #####   ######
 *     #   #       #         #     #           #   #  #    #    #
 *     #   #       #        #      #           #   #   #   #    #
 * #   #   #       #       #       #       #   #   #   #   #    #
 * #   #   #       #       #       #       #   #   #    #  #    #
 *  ###    ######  #       ######  #        ###    #    #  #    #
 * 
 * 2019 Day 8 Part 2 Main
 * #  # #### #   # ##  #  # 
 * #  # #    #   ##  # # #  
 * #### ###   # # #  # ##   
 * #  # #      #  #### # #  
 * #  # #      #  #  # # #  
 * #  # #      #  #  # #  #
 * 
 * 2019 Day 11 Part 2 Main
 *  ##  #    ###  #### ###    ## #### ### 
 * #  # #    #  # #    #  #    #    # #  #
 * #    #    ###  ###  #  #    #   #  #  #
 * # ## #    #  # #    ###     #  #   ### 
 * #  # #    #  # #    #    #  # #    #      
 *  ### #### ###  #### #     ##  #### #      
 * 
 * 2021 Day 13 Part 2 Alt
 * #### #  #   ## #  #  ##  #### #  # ###  
 *    # #  #    # #  # #  # #    #  # #  # 
 *   #  #  #    # #  # #  # ###  #### #  # 
 *  #   #  #    # #  # #### #    #  # ###  
 * #    #  # #  # #  # #  # #    #  # #    
 * ####  ##   ##   ##  #  # #    #  # #    
 */