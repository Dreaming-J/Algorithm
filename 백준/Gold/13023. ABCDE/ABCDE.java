/*
= BOJ 13023. ABCDE

= 특이사항
특정 그래프를 시작으로 레벨 4가 존재하는지 찾는 문제

= 로직
1. 초기 세팅
	1-1. 사람의 수와 친구 관계의 수 입력
	1-2. 친구 관계의 수만큼 친구 관계 정보 입력
	1-3. 변수 초기화
2. 사람마다 그래프의 최대 깊이 탐색
	2-1. 이미 레벨 4에 도달했다면 탐색 종료
	2-2. 레벨 4에 도달했다면 탐색 종료
	2-3. 다음 노드로 이동
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int LEVEL = 4;
	static int personSize, friendshipSize;
	static Node[] graph;
	static boolean[] visited;
	static boolean reachedLevelFive;
	
	public static class Node {
		int to;
		Node next;
		
		public Node(int to, Node next) {
			this.to = to;
			this.next = next;
		}
	}
	
	public static void search(int level, int cur) {
		//2-1. 이미 레벨 4에 도달했다면 탐색 종료
		if (reachedLevelFive)
			return;
		
		//2-2. 레벨 4에 도달했다면 탐색 종료
		if (level == LEVEL) {
			reachedLevelFive = true;
			return;
		}
		
		//2-3. 다음 노드로 이동
		for (Node next = graph[cur]; next != null; next = next.next) {
			if (visited[next.to])
				continue;
			
			visited[next.to] = true;
			search(level + 1, next.to);
			visited[next.to] = false;
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		initTestCase();
		
		//2. 사람마다 그래프의 최대 깊이 탐색
		for (int idx = 0; idx < personSize; idx++) {
			visited[idx] = true;
			search(0, idx);
			visited[idx] = false;
			
			if (reachedLevelFive)
				break;
		}
		
		//3. 출력
		System.out.println(reachedLevelFive ? 1 : 0);
	}
	
	public static void initTestCase() throws IOException {
		//1-1. 사람의 수와 친구 관계의 수 입력
		st = new StringTokenizer(input.readLine());
		personSize = Integer.parseInt(st.nextToken());
		friendshipSize = Integer.parseInt(st.nextToken());
		
		//1-2. 친구 관계의 수만큼 친구 관계 정보 입력
		graph = new Node[personSize];
		for (int idx = 0; idx < friendshipSize; idx++) {
			st = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			
			graph[from] = new Node(to, graph[from]);
			graph[to] = new Node(from, graph[to]);
		}
		
		//1-3. 변수 초기화
		visited = new boolean[personSize];
		reachedLevelFive = false;
	}
}
