/*
= BOJ 2573. 빙산
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final int WATER = 0;
    static int rowSize, colSize;
    static int[][] map, edgeCount;
    static boolean[][] visited;
    static int glacierSize, time;
    
    public static void simulation() {
        while (glacierSize > 0) {
        	edgeCount = new int[rowSize][colSize];
        	visited = new boolean[rowSize][colSize];
        	
        	for (int pos = 0, end = rowSize * colSize; pos < end; pos++) {
        		int row = pos / colSize;
        		int col = pos % colSize;
        		
        		if (map[row][col] <= WATER)
        			continue;
        		
        		if (findGlacierMass(row, col) != glacierSize)
        			return;
        		
        		break;
        	}
        	
        	for (int pos = 0, end = rowSize * colSize; pos < end; pos++) {
        		int row = pos / colSize;
        		int col = pos % colSize;
        		
        		if (edgeCount[row][col] == 0)
        			continue;
        		
        		map[row][col] -= edgeCount[row][col];
        		
        		if (map[row][col] <= WATER)
        			glacierSize--;
        	}
        	
        	time++;
        }
        
    	time = 0;
    }
    
    public static int findGlacierMass(int startRow, int startCol) {
    	int size = 0;
    	
    	Deque<Integer> queue = new ArrayDeque<>();
    	
    	visited[startRow][startCol] = true;
    	queue.add(startRow * colSize + startCol);
    	
    	while (!queue.isEmpty()) {
    		int cur = queue.poll();
    		int row = cur / colSize;
    		int col = cur % colSize;
    		
    		size++;
    		
    		for (int[] delta : DELTA) {
    			int nr = row + delta[0];
    			int nc = col + delta[1];
    			
    			if (isOut(nr, nc))
    				continue;
    			
    			if (visited[nr][nc])
    				continue;
    			
    			if (map[nr][nc] <= WATER) {
    				edgeCount[row][col]++;
    				continue;
    			}
    			
    			visited[nr][nc] = true;
    			queue.add(nr * colSize + nc);
    		}
    	}
    	
    	return size;
    }
    
    public static void main(String[] args) throws IOException {
        init();
        
        simulation();
        
        System.out.println(time);
    }
    
    public static boolean isOut(int row, int col) {
    	return row < 0 || row >= rowSize || col < 0 || col >= colSize;
    }
    
    public static void init() throws IOException {
    	//맵의 가로 세로 크기 입력
    	st = new StringTokenizer(input.readLine());
    	rowSize = Integer.parseInt(st.nextToken());
    	colSize = Integer.parseInt(st.nextToken());
    	
    	//맵 정보 입력
    	map = new int[rowSize][colSize];
    	for (int row = 0; row < rowSize; row++) {
    		st = new StringTokenizer(input.readLine());
    		for (int col = 0; col < colSize; col++) {
    			map[row][col] = Integer.parseInt(st.nextToken());
    			
    			if (map[row][col] != WATER) {
    				glacierSize++;
    			}
    		}
    	}
    }
}