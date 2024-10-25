/*
= BOJ 10775. 공항
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int gateSize, planeSize;
    static int[] planes;
    static int[] parents;
    static int dockingCount;

    public static void main(String[] args) throws IOException {
    	init();
    	
    	for (int idx = 1; idx <= planeSize; idx++) {
    		int plane = planes[idx];
    		int dockSpot = find(plane);
    		
    		if (dockSpot == 0 || !union(plane, dockSpot - 1))
    			break;
    		
    		dockingCount++;
    	}
    	
    	System.out.println(dockingCount);
    }
    
    public static boolean union(int element1, int element2) {
    	int parent1 = find(element1);
    	int parent2 = find(element2);
    	
    	if (parent1 == parent2)
    		return false;
    	
    	parents[parent1] = parent2;
    	return true;
    }
    
    public static int find(int element) {
    	if (parents[element] == element)
    		return element;
    	
    	return parents[element] = find(parents[element]);
    }

    public static void init() throws IOException {
    	gateSize = Integer.parseInt(input.readLine());
    	
    	planeSize = Integer.parseInt(input.readLine());
    	
    	planes = new int[planeSize + 1];
    	for (int idx = 1; idx <= planeSize; idx++)
    		planes[idx] = Integer.parseInt(input.readLine());
    	
    	parents = new int[gateSize + 1];
    	Arrays.setAll(parents, idx -> idx);
    }
}
