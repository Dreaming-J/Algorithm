import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

/*
= BOJ 3055. 탈출

1. 초기 세팅
	1-1. 티떱숲의 크기 입력
	1-2. 티떱숲의 정보 입력
2. 고슴도치 이동
3. 물 범람
4. 출력
 */

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static final char HEDGEHOG = 'S', BEAVER = 'D', WATER = '*', ROCK = 'X', EMPTY = '.';
    static int rowSize, colSize;
    static char[][] map;
    static Deque<Integer> waters;
    static int hedgehogPosition;
    static String bestTime;
    
    //2. 고슴도치 이동
    public static void moveHedgehog() {
    	boolean[][] visited = new boolean[rowSize][colSize];
    	Deque<Integer> queue = new ArrayDeque<>();
    	
    	visited[hedgehogPosition / colSize][hedgehogPosition % colSize] = true;
    	queue.add(hedgehogPosition);
    	
    	
    	int time = 0;
    	while (!queue.isEmpty()) {
    		int size = queue.size();
    		
    		//시간 별로 고슴도치 이동
    		while (size-- > 0) {
    			int cur = queue.poll();
    			int row = cur / colSize;
    			int col = cur % colSize;
    			
    			if (map[row][col] == WATER)
    				continue;
    			
    			if (map[row][col] == BEAVER) {
    				bestTime = String.valueOf(time);
    				return;
    			}
    			
    			for (int[] delta : DELTA) {
    				int nr = row + delta[0];
    				int nc = col + delta[1];
    				
    				if (isOut(nr, nc))
    					continue;
    				
    				if (visited[nr][nc])
    					continue;
    				
    				if (map[nr][nc] == WATER)
    					continue;
    				
    				if (map[nr][nc] == ROCK)
    					continue;
    				
    				visited[nr][nc] = true;
    				queue.add(nr * colSize + nc);
    			}
    		}
    		
    		//3. 물 범람
    		flood();
    		
    		time++;
    	}
    	
    	bestTime = "KAKTUS";
    }
    
    //3. 물 범람
    public static void flood() {
    	int size = waters.size();
    	
    	while(size-- > 0) {
    		int cur = waters.poll();
    		int row = cur / colSize;
    		int col = cur % colSize;
    		
    		for (int[] delta : DELTA) {
    			int nr = row + delta[0];
    			int nc = col + delta[1];
    			
    			if (isOut(nr, nc))
    				continue;
    			
    			if (map[nr][nc] != EMPTY)
    				continue;
    			
    			map[nr][nc] = WATER;
    			waters.add(nr*colSize + nc);
    		}
    	}
    }
    
    public static void main(String[] args) throws IOException {
    	//1. 초기 세팅
    	init();
    	
    	//2. 고슴도치 이동
    	moveHedgehog();
    	
    	//4. 출력
        System.out.println(bestTime);
    }
    
    public static boolean isOut(int row, int col) {
    	return row < 0 || row >= rowSize || col < 0 || col >= colSize;
    }
 
    //1. 초기 세팅
    public static void init() throws IOException {
    	//1-1. 티떱숲의 크기 입력
    	st = new StringTokenizer(input.readLine());
    	rowSize = Integer.parseInt(st.nextToken());
    	colSize = Integer.parseInt(st.nextToken());
    	
    	//1-2. 티떱숲의 정보 입력
    	map = new char[rowSize][colSize];
    	waters = new ArrayDeque<>();
    	for (int row = 0; row < rowSize; row++) {
    		String line = input.readLine();
    		for (int col = 0; col < colSize; col++) {
    			map[row][col] = line.charAt(col);
    			
    			if (map[row][col] == HEDGEHOG) {
    				map[row][col] = EMPTY;
    				hedgehogPosition = row * colSize + col;
    			}
    			
    			else if (map[row][col] == WATER)
    				waters.add(row * colSize + col);
    		}
    	}
    }
}
