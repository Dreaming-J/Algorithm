/*
=SWEA 3124. 최소 스패닝 트리

=특이사항
정점은 1번부터 시작
크루스칼

=로직
0. 테스트 케이스 개수 입력
1. 초기 세팅
	1-1. 정점의 개수와 간선의 개수 입력
	1-2. 간선의 개수만큼 간선 정보 입력
	1-3. 변수 초기화
2. 크루스칼 알고리즘을 적용한 MST 계산
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
    static PriorityQueue<Edge> edges;	//각 섬 사이의 간선을 관리하는 배열
    static int[] parents;				//서로소 집합용 배열
    static int[] rank;					//서로소 집합의 랭크를 관리하는 배열
    
    public static class Edge {
    	int start, end, weight;

		public Edge(int start, int end, int weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
		}
    }
    
    //2. 크루스칼 알고리즘을 적용한 MST 계산
    public static long calMST() {
    	int count = 0;
    	long cost = 0;
    	
    	while (!edges.isEmpty()) {
    		Edge cur = edges.poll();
    		
    		if (union(cur.start, cur.end)) {
    			cost += cur.weight;
    			
    			if (++count == vertexSize - 1)
    				break;
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
    	//1-1. 정점의 개수와 간선의 개수 입력
    	st = new StringTokenizer(input.readLine().trim());
    	vertexSize = Integer.parseInt(st.nextToken());
    	edgeSize = Integer.parseInt(st.nextToken());
    	
    	//1-2. 간선의 개수만큼 간선 정보 입력
    	edges = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.weight, o2.weight));
    	for (int idx = 0; idx < edgeSize; idx++) {
    		st = new StringTokenizer(input.readLine().trim());
    		edges.add(new Edge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
    	}

    	//1-3. 변수 초기화
    	parents = new int[vertexSize + 1];
    	rank = new int[vertexSize + 1];
    }
}
