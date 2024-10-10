/*
= BOJ 9205. 맥주 마시면서 걸어가기

0. 테스트 케이스 입력
1. 초기 세팅
	1-1. 편의점 개수 입력
	1-2. 집, 편의점, 페스티벌 좌표 입력
	1-3. 변수 초기화
2. 그래프 생성
3. 페스티벌 장소를 향해 이동
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int MAX_DIST = 1_000;
    static int storeSize, festivalIdx;
    static Position[] positions;
    static List<Integer>[] graph;
    
    //2. 그래프 생성
    public static void makeGraph() {
    	graph = new ArrayList[positions.length];
    	for (int idx = 0; idx < graph.length; idx++)
    		graph[idx] = new ArrayList<>();
    	
    	for (int from = 0; from < graph.length; from++) {
    		for (int to = from + 1; to < graph.length; to++) {
    			int dist = Math.abs(positions[from].row - positions[to].row) +
    					Math.abs(positions[from].col - positions[to].col);
    			
    			if (dist > MAX_DIST)
    				continue;
    			
    			graph[from].add(to);
    			graph[to].add(from);
    		}
    	}
    }
    
    //3. 페스티벌 장소를 향해 이동
    public static boolean moveFestival() {
    	boolean[] visited = new boolean[graph.length];
    	Deque<Integer> queue = new ArrayDeque<>();
    	
    	visited[0] = true;
    	queue.add(0);
    	
    	while (!queue.isEmpty()) {
    		int cur = queue.poll();
    		
    		if (cur == festivalIdx)
    			return true;
    		
    		for (int next : graph[cur]) {
    			if (visited[next])
    				continue;
    			
    			visited[next] = true;
    			queue.add(next);
    		}
    	}
    	
    	
    	return false;
    }
    
    public static void main(String[] args) throws IOException {
    	int testCases = Integer.parseInt(input.readLine());
    	
    	for (int tc = 0; tc < testCases; tc++) {
        	//1. 초기 세팅
        	init();
        	
        	//2. 그래프 생성
        	makeGraph();
        	
        	//3. 페스티벌 장소를 향해 이동
        	output.append(moveFestival() ? "happy" : "sad").append("\n");
    	}
    	System.out.println(output);
    }
    
    public static class Position {
    	int row, col;

		public Position(int row, int col) {
			this.row = row;
			this.col = col;
		}
    }
 
    //1. 초기 세팅
    public static void init() throws IOException {
    	//1-1. 편의점 개수 입력
    	storeSize = Integer.parseInt(input.readLine());
    	
    	//1-2. 집, 편의점, 페스티벌 좌표 입력
    	positions = new Position[storeSize + 2];
    	for (int idx = 0; idx < storeSize + 2; idx++) {
    		st = new StringTokenizer(input.readLine());
    		int row = Integer.parseInt(st.nextToken());
    		int col = Integer.parseInt(st.nextToken());
    		
    		positions[idx] = new Position(row, col);
    	}
    	
    	//1-3. 변수 초기화
    	festivalIdx = storeSize + 1;
    }
}
