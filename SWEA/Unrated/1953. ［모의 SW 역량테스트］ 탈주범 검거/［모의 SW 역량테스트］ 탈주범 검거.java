/*
= SWEA 1953. [모의 SW 역량테스트] 탈주범 검거

= 로직
0. 테스트케이스 입력
1. 초기 세팅
	1-1. 지도의 행열 크기, 시작 행열 위치, 소요 시간 입력
	1-2. 지도의 정보 입력
		1-2-1. 터널의 개수 카운트
2. 시작위치부터 탈주범 이동
	2-1. 주어진 소요 시간만큼 가능한 방향으로 이동
	2-2. 해당 시간에 도달할 수 있는 칸으로 모두 이동
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //상하좌우 배열
    static final int[] TUNNEL_DIRECTION = {0, 0b1111, 0b0011, 0b1100, 0b1001, 0b1010, 0b0110, 0b0101};
    static final int EMPTY = 0;
    static int testCase;
    static int rowSize, colSize, durationTime;
    static Point start;
    static int[][] map;
    static int tunnelSize, tunnelCount;
    
    //2. 시작위치부터 탈주범 이동
    public static void moveFugitive() {
    	tunnelCount = 0;
    	
    	boolean[][] visited = new boolean[rowSize][colSize];
    	Deque<Point> queue = new ArrayDeque<>();
    	
    	visited[start.row][start.col] = true;
    	queue.add(start);
    	
    	//2-1. 주어진 소요 시간만큼 가능한 방향으로 이동
    	for (int time = 0; time < durationTime; time++) {
    		int size = queue.size();
    		
    		//2-2. 해당 시간에 도달할 수 있는 칸으로 모두 이동
    		while (size-- > 0) {
    			Point cur = queue.poll();
    			
    			tunnelCount++;
    			
    			for (int deltaIdx = 0; deltaIdx < deltas.length; deltaIdx++) {
    				int nr = cur.row + deltas[deltaIdx][0];
    				int nc = cur.col + deltas[deltaIdx][1];
    				
    				//범위를 벗어났다면 패스
    				if (!canGo(nr, nc))
    					continue;
    				
    				//이미 방문한 위치라면 패스
    				if (visited[nr][nc])
    					continue;
    				
    				//현재 위치에서 해당 방향으로 갈 수 없다면 패스
    				if ((map[cur.row][cur.col] & 1 << deltaIdx) == 0)
    					continue;
    				
    				//들어올 방향이 이동할 위치에 열려있지 않다면 패스
    				int inDirection = deltaIdx + (deltaIdx % 2 == 0 ? 1 : -1);
    				if ((map[nr][nc] & 1 << inDirection) == 0)
    					continue;
    				
    				
    				visited[nr][nc] = true;
    				queue.add(new Point(nr, nc));
    			}
    		}
    	}
    }
    
    public static void main(String[] args) throws IOException{
    	//0. 테스트케이스 입력
    	testCase = Integer.parseInt(input.readLine().trim());
    	
    	for (int tc = 1; tc <= testCase; tc++) {
    		//1. 초기 세팅
    		initTestCase();
    		
    		//2. 시작위치부터 탈주범 이동
    		moveFugitive();
    		
    		//3. 출력
    		output.append("#").append(tc).append(" ").append(tunnelCount).append("\n");
    	}
    	System.out.println(output);
    }
    
    public static class Point {
    	int row, col;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
    }
    
    public static boolean canGo(int row, int col) {
    	return row >= 0 && row < rowSize && col >= 0 && col < colSize;
    }
    
    //1. 초기 세팅
    public static void initTestCase() throws IOException {
    	//1-1. 지도의 행열 크기, 시작 행열 위치, 소요 시간 입력
    	st = new StringTokenizer(input.readLine().trim());
    	rowSize = Integer.parseInt(st.nextToken());
    	colSize = Integer.parseInt(st.nextToken());
    	start = new Point(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
    	durationTime = Integer.parseInt(st.nextToken());
    	
    	//1-2. 지도의 정보 입력
    	map = new int[rowSize][colSize];
    	tunnelSize = 0;
    	for (int row = 0; row < rowSize; row++) {
    		st = new StringTokenizer(input.readLine().trim());
        	for (int col = 0; col < colSize; col++) {
        		map[row][col] = TUNNEL_DIRECTION[Integer.parseInt(st.nextToken())];
            	
        		//1-2-1. 터널의 개수 카운트
        		if (map[row][col] != EMPTY)
        			tunnelSize++;
        	}
    	}
    }
}