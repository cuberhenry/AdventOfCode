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
import java.util.ArrayList;
public class Library {
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

    public static int[][] distMap(ArrayList<String> points, ArrayList<String[]> dists, boolean bidirectional){
        int[][] table = new int[points.size()][points.size()];

        for (String[] dist : dists){
            int first = points.indexOf(dist[0]);
            int other = points.indexOf(dist[1]);

            table[first][other] = Integer.parseInt(dist[2]);
            if (bidirectional){
                table[other][first] = table[first][other];
            }
        }

        return table;
    }
}

/*
 * dimnoqstuvwx
 *
 * 2016 Day 8 Part 2 Main
 * ####   ## #  # ###  #  #  ##  ###  #    #   #  ## 
 *    #    # #  # #  # # #  #  # #  # #    #   #   # 
 *   #     # #### #  # ##   #    #  # #     # #    # 
 *  #      # #  # ###  # #  #    ###  #      #     # 
 * #    #  # #  # # #  # #  #  # #    #      #  #  # 
 * ####  ##  #  # #  # #  #  ##  #    ####   #   ##  
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
 */