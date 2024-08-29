/*
=SWEA 1251. [S/W 문제해결 응용] 4일차 - 하나로

=특이사항
조합, 크루스칼

=로직
0. 테스트 케이스 개수 입력
1. 초기 세팅
	1-1. 섬의 개수 입력
	1-2. 섬 좌표 입력
		1-2-1. 섬들의 x좌표 입력
		1-2-1. 섬들의 y좌표 입력
	1-4. 세율 실수 입력
	1-5. 변수 초기화
2. 섬간에 지을 수 있는 모든 간선 생성
	2-1. 두 개의 섬을 선택했다면 간선 생성 후 탐색 종료
	2-2. 모든 섬을 탐색했다면 탐색 종료
	2-3. 현재 섬 선택 후 다음
	2-4. 현재 섬 미선택 후 다음
3. 모든 섬을 연결하기 위한 해저터널을 최소비용으로 건설 
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int testCase;
    static int islandSize;
    static double tariff;
    static Island[] islands;				//각 섬의 좌표를 관리하는 배열
    static PriorityQueue<Edge> edges;	//각 섬 사이의 간선을 관리하는 배열
    static int[] chooseIsland;			//각 섬 사이의 간선을 만들 때 선택된 섬을 보관하는 배열
    static int[] parents;				//서로소 집합용 배열
    static int[] rank;					//서로소 집합의 랭크를 관리하는 배열
    
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
    
    public static class Edge {
    	int start, end;
    	double weight;

		public Edge(int start, int end) {
			this.start = start;
			this.end = end;
			this.weight = tariff * Island.distance(islands[start], islands[end]);
		}
    }
    
    public static void chooseTwoIsland(int islandIdx, int countIdx) {
    	//2-1. 두 개의 섬을 선택했다면 간선 생성 후 탐색 종료
    	if (countIdx == 2) {
    		edges.add(new Edge(chooseIsland[0], chooseIsland[1]));
    		return;
    	}
    	
    	//2-2. 모든 섬을 탐색했다면 탐색 종료
    	if (islandIdx == islandSize)
    		return;
    	
    	//2-3. 현재 섬 선택 후 다음
    	chooseIsland[countIdx] = islandIdx;
    	chooseTwoIsland(islandIdx + 1, countIdx + 1);
    	//2-4. 현재 섬 미선택 후 다음
    	chooseTwoIsland(islandIdx + 1, countIdx);
    }
    
    public static long makeTunnel() {
    	int count = 0;
    	double cost = 0;
    	
    	while (!edges.isEmpty()) {
    		Edge cur = edges.poll();
    		
    		if (union(cur.start, cur.end)) {
    			cost += cur.weight;
    			if (++count == islandSize - 1)
    				break;
    		}
    	}
    	
    	return Math.round(cost);
    }
    public static void main(String[] args) throws IOException {
    	//0. 테스트 케이스 개수 입력
    	testCase = Integer.parseInt(input.readLine().trim());
    	
        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
            initTestCase();
            
            //2. 섬간에 지을 수 있는 모든 간선 생성
            chooseTwoIsland(0, 0);
            
            //3. 모든 섬을 연결하기 위한 해저터널을 최소비용으로 건설
            long minCost = makeTunnel();
            
            //4. 출력
            output.append("#").append(tc).append(" ").append(minCost).append("\n");
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
    	if (parents[element] == element)
    		return element;
    	
    	return parents[element] = find(parents[element]);
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
    	edges = new PriorityQueue<>((o1, o2) -> Double.compare(o1.weight, o2.weight));
    	chooseIsland = new int[2];
    	parents = new int[islandSize];
    	Arrays.setAll(parents, idx -> idx);
    	rank = new int[islandSize];
    }
}
