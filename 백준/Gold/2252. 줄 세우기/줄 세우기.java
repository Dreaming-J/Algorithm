/*
= BOJ 2252. 줄 세우기

=특이 사항
위상 정렬 문제

=로직
1. 초기 세팅
	1-1. 학생의 수, 키를 비교한 횟수 입력
	1-2. 키를 비교한 횟수만큼 입력
2. 위상정렬
	2-1. 진입 차수가 0인 노드를 큐에 삽입
	2-2. 정점의 개수만큼 노드 방문
3, 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in)); 
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static int studentSize, compareSize;
	static Node[] graph;
	static int[] inDegree;
	
	public static class Node {
		int to;
		Node next;
		
		public Node(int to, Node next) {
			this.to = to;
			this.next = next;
		}
	}
	
	public static void topologicalSort() {
		Deque<Integer> queue = new ArrayDeque<>();
		
		//2-1. 진입 차수가 0인 노드를 큐에 삽입
		for (int idx = 1; idx <= studentSize; idx++) {
			if (inDegree[idx] == 0)
				queue.add(idx);
		}
		
		//2-2. 정점의 개수만큼 노드 방문
		for (int idx = 1; idx <= studentSize; idx++) {
			int cur = queue.poll();
			
			output.append(cur).append(" ");
			
			for (Node next = graph[cur]; next != null; next = next.next) {
				if (--inDegree[next.to] == 0)
					queue.add(next.to);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		init();
		
		//2. 위상정렬
		topologicalSort();
		
		//3. 출력
		System.out.println(output);
	}
	
	public static void init() throws IOException {
		//1-1. 학생의 수, 키를 비교한 횟수 입력
		st = new StringTokenizer(input.readLine());
		studentSize = Integer.parseInt(st.nextToken());
		compareSize = Integer.parseInt(st.nextToken());
		
		//1-2. 키를 비교한 횟수만큼 입력
		graph = new Node[studentSize + 1];
		inDegree = new int[studentSize + 1];
		for (int idx = 0; idx < compareSize; idx++) {
			st = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			
			graph[from] = new Node(to, graph[from]);
			inDegree[to]++;
		}
	}
}
