/*
=SWEA 3124. 최소 스패닝 트리

=특이사항
정점은 1번부터 시작
프림

=로직
0. 테스트 케이스 개수 입력
1. 초기 세팅
	1-1. 정점의 개수와 간선의 개수 입력
	1-2. 간선의 개수만큼 간선 정보 입력
	1-3. 변수 초기화
2. 프림 알고리즘을 적용한 MST 계산
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int testCase;
    static int vertexSize, edgeSize;
    static Node[] vertices;
    
    public static class Node implements Comparable<Node> {
    	int to, weight;
    	Node next;

		public Node(int to, int weight, Node next) {
			this.to = to;
			this.weight = weight;
			this.next = next;
		}

		@Override
		public int compareTo(Node o) {
			return this.weight - o.weight;
		}
    }
    
    //2. 프림 알고리즘을 적용한 MST 계산
    public static long calMST() {
		long cost = 0;
		
		boolean[] visited = new boolean[vertexSize + 1];
		PriorityQueue<Node> queue = new PriorityQueue<>();
		queue.add(new Node(1, 0, null));

		int cnt = 0;
		while (true) {
			Node cur = queue.poll();
			
			if (visited[cur.to])
				continue;
			visited[cur.to]= true;
			
			cost += cur.weight;
			
			if (++cnt == vertexSize)
				break;
			
			for (Node next = vertices[cur.to]; next != null; next = next.next) {
				queue.add(next);
			}
		}
    	
    	return cost;
    }
    public static void main(String[] args) throws IOException {
    	//0. 테스트 케이스 개수 입력
    	testCase = Integer.parseInt(input.readLine().trim());
    	
        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
            initTestCase();
            
            //3. 출력
            output.append("#").append(tc).append(" ").append(calMST()).append("\n");
        }
        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
    	//1-1. 정점의 개수와 간선의 개수 입력
    	st = new StringTokenizer(input.readLine().trim());
    	vertexSize = Integer.parseInt(st.nextToken());
    	edgeSize = Integer.parseInt(st.nextToken());
    	
    	//1-2. 간선의 개수만큼 간선 정보 입력
    	vertices = new Node[vertexSize + 1];
    	for (int idx = 0; idx < edgeSize; idx++) {
    		st = new StringTokenizer(input.readLine().trim());
    		int from = Integer.parseInt(st.nextToken());
    		int to = Integer.parseInt(st.nextToken());
    		int weight = Integer.parseInt(st.nextToken());
    		
    		vertices[from] = new Node(to, weight, vertices[from]);
    		vertices[to] = new Node(from, weight, vertices[to]);
    	}
    }
}
