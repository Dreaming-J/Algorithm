/*
= BOJ 10971. 외판원 순회 2

= 특이사항
도시 번호는 1번부터 시작

= 로직
1. 초기 세팅
	1-1. 도시의 수 입력
	1-2. 도시의 이동 비용을  인접 행렬 방식으로 입력
2. 최소 비용 탐색
	2-1. 모든 도시 방문했다면, 원점으로 돌아오는 비용 반환
	2-2. 방문하지 않은 도시의 최적 경로를 이미 알고 있다면, 해당 값 반환
	2-3. 현재 상태에서 남은 도시까지의 모든 경로 최적화 후, 찾은 값 반환
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input;
    static StringTokenizer st;
    static final int INF = 987654321, START = 1;
    static int citySize;
    static int[][] cities;
    static int[][] dpCost;
    
    //2. 최소 비용 탐색
    public static int findRoute(int cityIdx, int visitedBit) {
    	//2-1. 모든 도시 방문했다면, 원점으로 돌아오는 비용 반환
    	if (visitedBit == (1 << citySize + 1) - 1)
    		return cities[cityIdx][START] == 0 ? INF : cities[cityIdx][START];
    	
    	//2-2. 방문하지 않은 도시의 최적 경로를 이미 알고 있다면, 해당 값 반환
    	if (dpCost[cityIdx][visitedBit] != INF)
    		return dpCost[cityIdx][visitedBit];
    	
    	//2-3. 현재 상태에서 남은 도시까지의 모든 경로 최적화 후, 찾은 값 반환
    	for (int nextIdx = 2; nextIdx <= citySize; nextIdx++) {
    		//이미 방문했다면 패스
    		if ((visitedBit & 1 << nextIdx) != 0)
    			continue;
    		
    		//길이 존재하지 않는다면 패스
    		if (cities[cityIdx][nextIdx] == 0)
    			continue;
    		
    		dpCost[cityIdx][visitedBit] = Math.min(findRoute(nextIdx, visitedBit | 1 << nextIdx) + cities[cityIdx][nextIdx], dpCost[cityIdx][visitedBit]);
    	}
    	return dpCost[cityIdx][visitedBit];
    }

    public static void main(String[] args) throws IOException {
    	//1. 초기 세팅
        init();
        
        //2. 최소 비용 탐색
        //3. 출력
        System.out.println(findRoute(START, 0b11));
    }
    
	//1. 초기 세팅
    public static void init() throws IOException {
    	input = new BufferedReader(new InputStreamReader(System.in));
    	
    	//1-1. 도시의 수 입력
    	citySize = Integer.parseInt(input.readLine());
    	
    	//1-2. 도시의 이동 비용을  인접 행렬 방식으로 입력
    	cities = new int[citySize + 1][citySize + 1];
    	for (int from = 1; from <= citySize; from++) {
    		st = new StringTokenizer(input.readLine());
        	for (int to = 1; to <= citySize; to++) {
        		cities[from][to] = Integer.parseInt(st.nextToken());
        	}
    	}
    	
    	//1-3. 변수 초기화
    	dpCost = new int[citySize + 1][1 << (citySize + 1)];
    	for (int idx = 1; idx <= citySize; idx++)
    		Arrays.fill(dpCost[idx], INF);
    }
}
