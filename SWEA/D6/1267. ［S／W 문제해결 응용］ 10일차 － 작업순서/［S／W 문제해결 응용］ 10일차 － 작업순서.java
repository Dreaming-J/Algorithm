/*
= SWEA 1267. [S/W 문제해결 응용] 10일차 - 작업순서

입력은 비순환 유향 그래프가 보장
작업 순서가 여러 가지가 가능할 경우, 하나만 제시

1. 초기 세팅
	1-1. 정점과 간선의 개수 입력
	1-2. 간선 정보 입력
		1-2-1. 정점 별 진입 차수 개수 보관
2. 위상 정렬
	2-1. 진입 차수가 0인 정점 큐에 삽입
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class Solution {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static final int TEST_CASE = 10;
	static int testCase;
	static int vertexSize, edgeSize;
	static Node[] graph;
	static int[] inDegree;
	static StringJoiner topologyResult;
	
	public static class Node {
		int to;
		Node next;
		
		public Node(int to, Node next) {
			this.to = to;
			this.next = next;
		}
	}
	
	public static void topologySort() {
		Deque<Integer> queue = new ArrayDeque<>();
		
		//2-1. 진입 차수가 0인 정점 큐에 삽입
		for (int idx = 1; idx <= vertexSize; idx++) {
			if (inDegree[idx] == 0)
				queue.add(idx);
		}
		
		//2-2. 모든 노드 방문할 때까지 탐색
		for (int vertexIdx = 1; vertexIdx <= vertexSize; vertexIdx++) {
			int cur = queue.poll();
			
			topologyResult.add(String.valueOf(cur));
			
			for (Node next = graph[cur]; next != null; next = next.next) {
				if (--inDegree[next.to] == 0)
					queue.add(next.to);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		for (int tc = 1; tc <= TEST_CASE; tc++) {
			//1. 초기 세팅
			initTestCase();
			
			//2. 위상 정렬
			topologyResult = new StringJoiner(" ");
			topologySort();
			
			//3. 출력
			output.append("#").append(tc).append(" ").append(topologyResult).append("\n");
		}
		System.out.println(output);
	}
	
	public static void initTestCase() throws IOException {
		//1-1. 정점과 간선의 개수 입력
		st = new StringTokenizer(input.readLine().trim());
		vertexSize = Integer.parseInt(st.nextToken());
		edgeSize = Integer.parseInt(st.nextToken());
		
		//1-2. 간선 정보 입력
		graph = new Node[vertexSize + 1];
		inDegree = new int[vertexSize + 1];
		
		st = new StringTokenizer(input.readLine().trim());
		for (int edgeIdx = 0; edgeIdx < edgeSize; edgeIdx++) {
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			graph[from] = new Node(to, graph[from]);
			
			//1-2-1. 정점 별 진입 차수 개수 보관
			inDegree[to]++;
		}
	}
}
