/*
= BOJ 2565. 전깃줄
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int telephonePoleSize;
    static PriorityQueue<TelephonePole> telephonePoles;
    static int[] lis;
    static int lisSize;
    
    public static int binarySearch(int left, int right, int target) {
    	while (left <= right) {
    		int mid = (left + right) / 2;
    		
    		if (lis[mid] <= target)
    			left = mid + 1;
			else
				right = mid - 1;
    	}
    	
    	return left;
    }

    public static void main(String[] args) throws IOException {
    	init();
    	
    	for (int idx = 0; idx < telephonePoleSize; idx++) {
    		TelephonePole telephonePole = telephonePoles.poll();
    		
    		if (lis[lisSize] < telephonePole.right)
    			lis[++lisSize] = telephonePole.right;
    		else
    			lis[binarySearch(1, lisSize, telephonePole.right)] = telephonePole.right;
    	}
    	
    	System.out.println(telephonePoleSize - lisSize);
    }
    
    public static class TelephonePole implements Comparable<TelephonePole> {
    	int left, right;

		public TelephonePole(int left, int right) {
			this.left = left;
			this.right = right;
		}

		@Override
		public int compareTo(TelephonePole o) {
			return left - o.left;
		}
    }

    public static void init() throws IOException {
    	telephonePoleSize = Integer.parseInt(input.readLine());
    	
    	telephonePoles = new PriorityQueue<>();
    	for (int idx = 0; idx < telephonePoleSize; idx++) {
    		st = new StringTokenizer(input.readLine());
    		int left = Integer.parseInt(st.nextToken());
    		int right = Integer.parseInt(st.nextToken());
    		
    		telephonePoles.add(new TelephonePole(left, right));
    	}
    	
    	lis = new int[telephonePoleSize + 1];
    }
}
