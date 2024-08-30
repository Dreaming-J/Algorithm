/*
=SWEA 1251. [S/W 문제해결 응용] 4일차 - 하나로

=특이사항
조합, 프림

=로직
0. 테스트 케이스 개수 입력
1. 초기 세팅
	1-1. 섬의 개수 입력
	1-2. 섬 좌표 입력
		1-2-1. 섬들의 x좌표 입력
		1-2-1. 섬들의 y좌표 입력
	1-4. 세율 실수 입력
	1-5. 변수 초기화
2. 섬간의 지을 수 있는 모든 해저터널 건설 
3. 모든 섬을 연결하기 위한 해저터널을 최소비용으로 건설 
4. 출력
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
    static int islandSize;
    static double tariff;
    static Island[] islands;			//각 섬의 좌표를 관리하는 배열
    static Node[] vertices;				//각 섬 사이의 간선을 관리하는 배열
    static boolean[] visited;
    static long minCost;
  
    public static class Island {
    	int x;
    	int y;
    	
    	public Island(int x) {
    		this.x = x;
    	}
    	
    	public static long distance(Island o1, Island o2) {
    		return (long) (o1.x - o2.x) * (o1.x - o2.x) + (long) (o1.y - o2.y) * (o1.y - o2.y);
    	}
    }
    
    public static class Node {
    	int to;
    	double cost;
    	Node next;

		public Node(int to, long distance, Node next) {
			this.to = to;
			this.cost = tariff * distance;
			this.next = next;
		}
    }
    
    public static void findAllTunnel() {
    	for (int node1 = 0; node1 < islandSize; node1++) {
    		for (int node2 = node1 + 1; node2 < islandSize; node2++) {
    			long distance = Island.distance(islands[node1], islands[node2]);
    			
    			vertices[node1] = new Node(node2, distance, vertices[node1]);
    			vertices[node2] = new Node(node1, distance, vertices[node2]);
    		}
    	}
    }
    
    public static void selectTunnel() {
    	int cnt = 0;
    	double cost = 0;
    	
    	PriorityQueue<Node> queue = new PriorityQueue<>((o1, o2) -> Double.compare(o1.cost, o2.cost));
    	queue.add(new Node(0, 0, null));
    	
    	while (true) {
    		Node cur = queue.poll();
    		
    		if (visited[cur.to])
    			continue;
    		visited[cur.to] = true;
    		
    		cost += cur.cost;
    		
    		if (++cnt == islandSize)
    			break;
    		
    		for (Node next = vertices[cur.to]; next != null; next = next.next) {
    			queue.add(next);
    		}
    	}
    	
    	minCost = Math.min(Math.round(cost), minCost);
    }
    
    
    
    public static void main(String[] args) throws IOException {
    	//0. 테스트 케이스 개수 입력
    	testCase = Integer.parseInt(input.readLine().trim());
    	
        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
            initTestCase();
            
            //2. 섬간의 지을 수 있는 모든 해저터널 건설 
            findAllTunnel();
            
            //3. 모든 섬을 연결하기 위한 해저터널을 최소비용으로 건설
            selectTunnel();
            
            //4. 출력
            output.append("#").append(tc).append(" ").append(minCost).append("\n");
        }
        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
    	//1-1. 섬의 개수 입력
    	islandSize = Integer.parseInt(input.readLine().trim());
    	
    	//1-2. 섬 좌표 입력
    	islands = new Island[islandSize];
    	//1-2-1. 섬들의 x좌표 입력
    	st = new StringTokenizer(input.readLine().trim());
    	for (int idx = 0; idx < islandSize; idx++)
    		islands[idx] = new Island(Integer.parseInt(st.nextToken()));
    	//1-2-2. 섬들의 y좌표 입력
    	st = new StringTokenizer(input.readLine().trim());
    	for (int idx = 0; idx < islandSize; idx++)
    		islands[idx].y =Integer.parseInt(st.nextToken());
    	
    	//1-4. 세율 실수 입력
    	tariff = Double.parseDouble(input.readLine().trim());
    	
    	//1-5. 변수 초기화
    	vertices = new Node[islandSize];
    	visited = new boolean[islandSize];
    	minCost = Long.MAX_VALUE;
    }
}
