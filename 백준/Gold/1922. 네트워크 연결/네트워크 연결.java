/*
= BOJ 1922. 네트워크 연결

= 특이사항
모든 컴퓨터가 연결되는 케이스 보장
크루스칼

= 로직
1. 초기 세팅
	1-1. 컴퓨터의 수 입력
	1-2. 선의 수 입력
	1-3. 선의 수만큼 각 컴퓨터의 연결 비용 입력
	1-4. 변수 초기화
2. 최소 비용 계산
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder output = new StringBuilder();
	static StringTokenizer st;
	static int computerSize, wireSize;
	static PriorityQueue<Edge> edges;
	static int[] parents, rank;
	
	//2. 최소 비용 계산
	public static int calMST() {
		int count = 1, cost = 0;
		
		while (!edges.isEmpty()) {
			Edge cur = edges.poll();
			
			if (union(cur.start, cur.end)) {
				cost += cur.cost;
				
				if (++count == computerSize)
					break;
			}
		}
		
		return cost;
	}
	
	public static void main(String[] args) throws IOException {
		//1. 초기 세팅
		initTestCase();
		
		//3. 출력
		System.out.println(calMST());
	}
	
	public static class Edge implements Comparable<Edge> {
		int start, end, cost;

		public Edge(int start, int end, int cost) {
			this.start = start;
			this.end = end;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	public static boolean union(int element1, int element2) {
		int parent1 = find(element1);
		int parent2 = find(element2);
		
		if (parent1 == parent2)
			return false;
		
		if (rank[parent1] > rank[parent2]) {
			parents[parent2] = parent1;
			return true;
		}
		
		if (rank[parent1] == rank[parent2])
			rank[parent2]++;
		
		parents[parent1] = parent2;
		return true;
	}
	
	public static int find(int element) {
		if (parents[element] == 0)
			return element;
		
		return parents[element] = find(parents[element]);
	}
	
	public static void initTestCase() throws IOException {
		//1-1. 컴퓨터의 수 입력
		computerSize = Integer.parseInt(input.readLine());
		
		//1-2. 선의 수 입력
		wireSize = Integer.parseInt(input.readLine());
		
		//1-3. 선의 수만큼 각 컴퓨터의 연결 비용 입력
		edges = new PriorityQueue<>();
		for (int idx = 0; idx < wireSize; idx++) {
			st = new StringTokenizer(input.readLine());
			edges.add(new Edge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
		}
		
		//1-4. 변수 초기화
		parents = new int[computerSize + 1];
		rank = new int[computerSize + 1];
	}
}
