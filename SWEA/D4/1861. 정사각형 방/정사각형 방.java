/*
SWEA 1861. 정사각형 방
 
0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 방의 크기 입력
	1-2. 방의 정보 입력
	1-3. 변수 초기화
2. dfs을 이용해 모든 방의 경우의 수 탐색
	2-1. 방문한 방의 갯수가 최대와 동일하다면 업데이트
	2-2. 방문한 방의 갯수가 최대라면 업데이트
	2-3. 4방 탐색으로 다음 방 이동
    	2-3-1. 범위를 벗어났다면 패스
		2-3-2. 이미 방문한 방이라면 패스
    	2-3-3. 이동할 방이 현재 방 번호보다 1큰게 아니라면 패스
    	2-3-4. 4방으로 이동
3. 출력
 */
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int[][] deltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int testCase;
    static int roomSize;
    static int[][] room;
    static boolean[][] visit;
    static int maxRoomCount, minRoomIdx;
 
    public static void initTestCase() throws IOException {
    	//1-1. 방의 크기 입력
    	roomSize = Integer.parseInt(input.readLine().trim());
    	
    	//1-2. 방의 정보 입력
    	room = new int[roomSize][roomSize];
    	for (int row = 0; row < roomSize; row++) {
    		st = new StringTokenizer(input.readLine().trim());
    		for (int col = 0; col < roomSize; col++)
    			room[row][col] = Integer.parseInt(st.nextToken());
    	}
    	
    	//1-3. 변수 초기화
    	visit = new boolean[roomSize][roomSize];
    	maxRoomCount = Integer.MIN_VALUE;
    	minRoomIdx = Integer.MAX_VALUE;
    }
    
    public static void findBestRoute(int row, int col, int roomCount, int startRoomIdx) {
    	//2-1. 방문한 방의 갯수가 최대와 동일하다면 업데이트
    	if (roomCount == maxRoomCount)
    		minRoomIdx = Math.min(startRoomIdx, minRoomIdx);
    	
    	//2-2. 방문한 방의 갯수가 최대라면 업데이트
    	if (roomCount > maxRoomCount) {
    		maxRoomCount = roomCount;
    		minRoomIdx = startRoomIdx;
    	}
    	
    	//2-3. 4방 탐색으로 다음 방 이동
    	for (int[] delta : deltas) {
    		int nr = row + delta[0];
    		int nc = col + delta[1];
    		
    		//2-3-1. 범위를 벗어났다면 패스
    		if (nr < 0 || nr >= roomSize || nc <0 || nc >= roomSize)
    			continue;
    		
			//2-3-2. 이미 방문한 방이라면 패스
			if (visit[nr][nc])
				continue;
			
			//2-3-3. 이동할 방이 현재 방 번호보다 1큰게 아니라면 패스
			if (room[nr][nc] != room[row][col] + 1)
				continue;
			
			//2-3-4. 4방으로 이동
			visit[nr][nc] = true;
			findBestRoute(nr, nc, roomCount + 1, startRoomIdx);
			visit[nr][nc] = false;
    	}
    }
 
    public static void main(String[] args) throws IOException {
    	//0. 테스트 케이스 입력
        testCase = Integer.parseInt(input.readLine());
        
        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
            initTestCase();
            
            //2. dfs을 이용해 모든 방의 경우의 수 탐색
            for (int row = 0; row < roomSize; row++) {
	    		for (int col = 0; col < roomSize; col++) {
	    			visit[row][col] = true;
	    			findBestRoute(row, col, 1, room[row][col]);
	    			visit[row][col] = false;
	    		}
	    	}
            
            //3. 출력
            output.append("#").append(tc).append(" ").append(minRoomIdx).append(" ").append(maxRoomCount).append("\n");
        }
        System.out.println(output);
    }
}