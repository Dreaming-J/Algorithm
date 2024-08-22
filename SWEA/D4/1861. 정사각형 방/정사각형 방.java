/*
SWEA 1861. 정사각형 방
 
0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 방의 크기 입력
	1-2. 방의 정보 입력
	1-3. 변수 초기화
2. dfs을 이용해 모든 방의 경우의 수 탐색
	2-1. 범위를 벗어났다면 패스
	2-2. 이동할 방이 현재 방 번호보다 1큰게 아니라면 패스
	2-3. 4방으로 이동
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
    static int maxRoomCount, minRoomIdx, roomCount;
 
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
    	maxRoomCount = Integer.MIN_VALUE;
    	minRoomIdx = Integer.MAX_VALUE;
    }
    
    public static void findBestRoute(int row, int col) {
    	for (int[] delta : deltas) {
    		int nr = row + delta[0];
    		int nc = col + delta[1];
    		
    		//2-1. 범위를 벗어났다면 패스
    		if (nr < 0 || nr >= roomSize || nc <0 || nc >= roomSize)
    			continue;
			
			//2-2. 이동할 방이 현재 방 번호보다 1큰게 아니라면 패스
			if (room[nr][nc] != room[row][col] + 1)
				continue;
			
			//2-3. 4방으로 이동
			roomCount++;
			findBestRoute(nr, nc);
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
	    			roomCount = 1;
                    
	    			findBestRoute(row, col);
	    			
	    			//2-4. 방문 횟수가 동일하다면, 시작 위치만 업데이트
	    	    	if (roomCount == maxRoomCount)
	    	    		minRoomIdx = Math.min(room[row][col], minRoomIdx);
	    	    	
	    	    	//2-5. 방문 횟수가 더 크다면, 최대 방문 횟수와 시작 위치 업데이트
	    	    	if (roomCount > maxRoomCount) {
	    	    		maxRoomCount = roomCount;
	    	    		minRoomIdx = room[row][col];
	    	    	}
	    		}
	    	}
            
            //3. 출력
            output.append("#").append(tc).append(" ").append(minRoomIdx).append(" ").append(maxRoomCount).append("\n");
        }
        System.out.println(output);
    }
}