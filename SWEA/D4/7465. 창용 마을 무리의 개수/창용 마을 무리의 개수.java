/*
=SWEA 3289. 서로소 집합

=특이사항
서로소 집합 알고리즘
원소는 1부터 시작

=로직
0. 테스트케이스 입력
1. 초기 세팅
	1-1. 사람의 수와 사람의 관계 수를 나타내는 입력
	1-2. 변수 초기화
2. 관계 수만큼 서로 관계를 맺고 있는 사람의 정보 입력받으며, 서로 관계를 맺고 있는 사람을 하나의 집합으로 합침
3. 모든 사람들의 부모를 확인하여 무리의 개수를 계산한다. 
4. 출력 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int SELF = 0;
    static int testCase;
    static int personSize, relationSize;
    static int[] parents, rank;
    
    public static void union(int person1, int person2) {
    	int parent1 = find(person1);
    	int parent2 = find(person2);
    	
    	if (parent1 == parent2)
    		return;
    	
    	if (rank[parent1] > rank[parent2]) {
    		parents[parent2] = parent1;
    		return;
    	}
    	
    	if (rank[parent1] == rank[parent2])
    		rank[parent2]++;
    	
		parents[parent1] = parent2;
    }
    
    public static int find(int person) {
    	if (parents[person] == SELF)
    		return person;
    	
    	return parents[person] = find(parents[person]);
    }
    
    //3. 모든 사람들의 부모를 확인하여 무리의 개수를 계산한다. 
    public static int countBunch() {
    	int bunchCount = 0;
    	
    	boolean[] visited = new boolean[personSize + 1];
    	
    	for (int idx = 1; idx <= personSize; idx++) {
    		int parent = find(idx);
    		
    		if (!visited[parent]) {
        		visited[parent] = true;
        		bunchCount++;
    		}
    	}
    	
    	return bunchCount;
    }

    public static void main(String[] args) throws IOException {
    	//0. 테스트케이스 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
            initTestCase();

            //2. 관계 수만큼 서로 관계를 맺고 있는 사람의 정보 입력받으며, 서로 관계를 맺고 있는 사람을 하나의 집합으로 합침
            for (int idx = 0; idx < relationSize; idx++) {
                st = new StringTokenizer(input.readLine().trim());
                int person1 = Integer.parseInt(st.nextToken());
                int person2 = Integer.parseInt(st.nextToken());
            	union(person1, person2);
            }

            //4. 출력
            output.append("#").append(tc).append(" ").append(countBunch()).append("\n");
        }
        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
        //1-1. 사람의 수와 사람의 관계 수를 나타내는 입력
        st = new StringTokenizer(input.readLine().trim());
        personSize = Integer.parseInt(st.nextToken());
        relationSize = Integer.parseInt(st.nextToken());

        //1-2. 변수 초기화
        parents = new int[personSize + 1];
        rank = new int[personSize + 1];
    }
}
