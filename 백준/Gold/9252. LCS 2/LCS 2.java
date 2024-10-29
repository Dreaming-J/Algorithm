/*
= BOJ 9252. LCS 2
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static String string1, string2;
    static int[][] lcs;
    static int string1Size, string2Size;
    
    public static void main(String[] args) throws IOException {
    	init();
    	
    	for (int row = 1; row <= string1Size; row++) {
    		for (int col = 1; col <= string2Size; col++) {
    			if (string1.charAt(row - 1) == string2.charAt(col - 1))
    				lcs[row][col] = lcs[row - 1][col - 1] + 1;
    			else
    				lcs[row][col] = Math.max(lcs[row][col - 1], lcs[row - 1][col]);
    		}
    	}
    	
    	for (int row = string1Size, col = string2Size; lcs[row][col] != 0;) {
    		if (lcs[row][col] == lcs[row - 1][col])
    			row--;
    		else if (lcs[row][col] == lcs[row][col - 1])
    			col--;
    		else {
    			output.append(string1.charAt(--row));
    			col--;
    		}
    	}
    	
    	System.out.println(lcs[string1Size][string2Size] + "\n" + output.reverse());
    }

    public static void init() throws IOException {
    	string1 = input.readLine();
    	string2 = input.readLine();
    	
    	string1Size = string1.length();
    	string2Size = string2.length();
    	
    	lcs = new int[string1Size + 1][string2Size + 1];
    }
}
