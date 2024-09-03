/*
= SWEA 1249. 보급로

= 로직
0. 테스트케이스 입력
1. 초기 세팅
	1-1. 지도의 크기 입력
	1-2. 지도의 크기만큼 지도의 정보 입력
	1-3. 변수 초기화
2. 도착지까지의 최적 경로 탐색
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int testCase;
    static int mapSize;
    static int[][] map, minDist;
    
    static PriorityQueue<Point> pq;
    static boolean[][] visited;
    
    //2. 도착지까지의 최적 경로 탐색
    public static void findBestRoute(int startRow, int startCol, int endRow, int endCol) {
    	visited = new boolean[mapSize][mapSize];
    	pq = new PriorityQueue<>();
    	
    	minDist[0][0] = 0;
    	pq.add(new Point(startRow, startCol, 0));
    	
    	while (!pq.isEmpty()) {
    		Point cur = pq.poll();
    		
    		//방문한 적 있다면 이미 최적를 찾았으므로 패스
    		if (visited[cur.row][cur.col])
    			continue;
    		visited[cur.row][cur.col] = true;
    		
            //도착지에 도착했다면 탐색 종료
    		if (cur.row == endRow && cur.col == endCol)
    			return;
    		
    		//다음으로 이동
    		for (int[] delta : deltas) {
    			int nr = cur.row + delta[0];
    			int nc = cur.col + delta[1];
    			
    			//범위를 벗어났다면 패스
    			if (!canGo(nr, nc))
    				continue;
    			
    			//이동할 거리가 현재 최적 거리보다 작은 값이라면 갱신 후, pq에 삽입
    			int dist = cur.dist + map[nr][nc];
    			if (dist < minDist[nr][nc]) {
    				minDist[nr][nc] = dist;
    				pq.add(new Point(nr, nc, dist));
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
    		
    		//2. 도착지까지의 최적 경로 탐색
    		findBestRoute(0, 0, mapSize - 1, mapSize - 1);
    		
    		//3. 출력
    		output.append("#").append(tc).append(" ").append(minDist[mapSize - 1][mapSize - 1]).append("\n");
    	}
    	System.out.println(output);
    }
    
    public static class Point implements Comparable<Point> {
    	int row, col, dist;

		public Point(int row, int col, int dist) {
			this.row = row;
			this.col = col;
			this.dist = dist;
		}
		
		@Override
		public int compareTo(Point o) {
			return Integer.compare(dist, o.dist);
		}
    }
    
    public static boolean canGo(int row, int col) {
    	return row >= 0 && row < mapSize && col >= 0 && col < mapSize;
    }
    
    //1. 초기 세팅
    public static void initTestCase() throws IOException {
    	//1-1. 지도의 크기 입력
    	mapSize = Integer.parseInt(input.readLine().trim());

    	//1-2. 지도의 크기만큼 지도의 정보 입력
    	//1-3. 변수 초기화
    	map = new int[mapSize][mapSize];
    	minDist = new int[mapSize][mapSize];
    	for (int row = 0; row < mapSize; row++) {
    		String line = input.readLine().trim();
    		for (int col = 0; col < mapSize; col++) {
    			map[row][col] = line.charAt(col) - '0';
    			minDist[row][col] = Integer.MAX_VALUE;
    		}
    	}
    }
}