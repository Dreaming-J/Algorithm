/*
= SWEA 1263. [S/W 문제해결 응용] 8일차 - 사람 네트워크2
 
0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 사람의 수 입력
	1-2. 사람 네트워크 입력
	1-3. 정답 초기화
2. 최단 경로 찾기
	2-1. 경유한 경로가 기존 경로보다 작다면 갱신
	2-2. 출발 노드의 간선의 합 갱신
	2-3. 정답 갱신
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
    static final int INF = 1_000_000, CONNECTED = 1;
    static int personSize;
    static int[][] network;
    static int answer;
    
    //2. 최단 경로 찾기
    public static void findMinDist() {
    	for (int layOver = 1; layOver <= personSize; layOver++) {
        	for (int start = 1; start <= personSize; start++) {
        		if (start == layOver)
        			continue;

            	for (int end = 1; end <= personSize; end++) {
            		if (end == layOver || end == start)
            			continue;
            		
            		//2-1. 경유한 경로가 기존 경로보다 작다면 갱신
            		int newDist = network[start][layOver] + network[layOver][end];
            		if (newDist < network[start][end]) {
            			//2-2. 출발 노드의 간선의 합 갱신
            			network[start][0] -= network[start][end];
            			network[start][end] = newDist;
            			network[start][0] += newDist;
            			
            			//2-3. 정답 갱신
        				answer = Math.min(network[start][0], answer);
            		}
            	}
        	}
    	}
    }
 
    public static void main(String[] args) throws IOException {
        //0. 테스트 케이스 입력
        int testCase = Integer.parseInt(input.readLine());
 
        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
        	init();
        	
        	//2. 최단 경로 찾기
        	findMinDist();
 
        	//3. 출력
            output.append("#").append(tc).append(" ").append(answer).append("\n");
        }
        System.out.println(output);
    }
 
    //1. 초기 세팅
    public static void init() throws IOException {
    	st = new StringTokenizer(input.readLine().trim());
    	
    	answer = INF;
    	
        //1-1. 사람의 수 입력
    	personSize = Integer.parseInt(st.nextToken());
    	
    	//1-2. 사람 네트워크 입력
    	network = new int[personSize + 1][personSize + 1];
    	for (int row = 1; row <= personSize; row++) {
    		for (int col = 1; col <= personSize; col++) {
    			int isConnected = Integer.parseInt(st.nextToken());
    			
    			if (isConnected == CONNECTED)
    				network[row][0] += network[row][col] = isConnected;
    			else if (row != col)
    				network[row][0] += network[row][col] = INF;
    		}
			
			answer = Math.min(network[row][0], answer);
    	}
    }
}