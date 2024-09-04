/*
=SWEA 1949. [모의 SW 역량테스트] 등산로 조성

=로직
0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 지도의 길이, 최대 공사 가능 깊이 입력
	1-2. 지도의 정보 입력
		1-2-1. 지도의 가장 높은 높이 보관
	1-3. 변수 초기화
2. 가장 높은 봉우리 탐색
3. 가장 긴 등산로 탐색
	3-1. 가장 긴 등산로 갱신
	3-2. 다음으로 이동
4. 출력
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int[][] DELTA = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int testCase;
    static int mapSize, maxDepth;
    static int[][] map;
    static int maxHeight;
    static List<Point> maxHeightPoints;
    static boolean[][] visited;
    static int maxLength;

    //2. 가장 높은 봉우리 탐색
    public static void searchMaxHeight() {
    	for (int row = 0; row < mapSize; row++) {
	    	for (int col = 0; col < mapSize; col++) {
	    		if (map[row][col] == maxHeight)
	    			maxHeightPoints.add(new Point(row, col));
	    	}
		}
    }

    //3. 가장 긴 등산로 탐색
    public static void findMaxLength(int row, int col, int height, int length, boolean canDrill) {
    	//3-1. 가장 긴 등산로 갱신
    	maxLength = Math.max(length, maxLength);
    	
    	//3-2. 다음으로 이동
    	for (int[] delta : DELTA) {
    		int nr = row + delta[0];
    		int nc = col + delta[1];
    		
    		//범위를 벗어났다면 패스
    		if (!canGo(nr, nc))
    			continue;
    		
    		//방문한 곳이라면 패스
    		if (visited[nr][nc])
    			continue;
    		

    		visited[nr][nc] = true;
    		
    		if (canDrill && map[nr][nc] >= height && map[nr][nc] - maxDepth < height)
    			findMaxLength(nr, nc, height - 1, length + 1, false);
    		else if (map[nr][nc] < height)
    			findMaxLength(nr, nc, map[nr][nc], length + 1, canDrill);
    		
    		visited[nr][nc] = false;
    	}
    }
    
    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스를 입력받는다.
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
            initTestCase();
            
            //2. 가장 높은 봉우리 탐색
            searchMaxHeight();
            
            //3. 가장 긴 등산로 탐색
            for (Point maxHeightPoint : maxHeightPoints) {
            	visited[maxHeightPoint.row][maxHeightPoint.col] = true;
            	findMaxLength(maxHeightPoint.row, maxHeightPoint.col, maxHeight, 1, true);
            	visited[maxHeightPoint.row][maxHeightPoint.col] = false;
            }
            
            //4. 출력
            output.append("#").append(tc).append(" ").append(maxLength).append("\n");
        }
        System.out.println(output);
    }
    
    public static boolean canGo(int row, int col) {
    	return row >= 0 && row < mapSize && col >= 0 && col < mapSize;
    }
    
    public static class Point {
    	int row, col;

		public Point(int row, int col) {
			this.row = row;
			this.col = col;
		}
    }
    
    public static void initTestCase() throws IOException {
        //1-1. 지도의 길이, 최대 공사 가능 깊이 입력
    	st = new StringTokenizer(input.readLine().trim());
    	mapSize = Integer.parseInt(st.nextToken());
    	maxDepth = Integer.parseInt(st.nextToken());
    	
    	//1-2. 지도의 정보 입력
    	map = new int[mapSize][mapSize];
    	maxHeight = 0;
    	for (int row = 0; row < mapSize; row++) {
    		st = new StringTokenizer(input.readLine().trim());
        	for (int col = 0; col < mapSize; col++) {
        		map[row][col] = Integer.parseInt(st.nextToken());
        		
        		//1-2-1. 지도의 가장 높은 높이 보관
        		maxHeight = Math.max(map[row][col], maxHeight);
        	}
    	}
    	
    	//1-3. 변수 초기화
    	maxHeightPoints = new ArrayList<>();
    	visited = new boolean[mapSize][mapSize];
    	maxLength = 0;
    }
}