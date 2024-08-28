/*
=SWEA 3289. 서로소 집합

=특이사항
서로소 집합 알고리즘
원소는 1부터 시작

=로직
0. 테스트케이스 입력
1. 초기 세팅
	1-1. 원소의 개수와 연산의 개수 입력
	1-2. 변수 초기화
2. 연산 입력을 받으며 서로소 집합 알고리즘 진행
	2-1. 합집합 연산 진행
	2-2. 같은 집합인지 확인 연산 진행
3. 출력 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int SELF = 0;
    static final int CMD_UNION = 0, CMD_FIND = 1;
    static final int SAME = 1, DIFF = 0;
    static int testCase;
    static int elementSize, commandSize;
    static int[] parents, rank;
    static StringBuilder result;
    
    public static void union(int element1, int element2) {
    	int parent1 = find(element1);
    	int parent2 = find(element2);
    	
    	if (parent1 == parent2)
    		return;
    	
    	if (rank[parent1] > rank[parent2]) {
    		parents[parent2] = parent1;
    		return;
    	}
    	
    	if (rank[parent1] == rank[parent2]) {
    		rank[parent2]++;
    	}
    	
    	parents[parent1] = parent2;
    }
    
    public static int find(int element) {
    	if (parents[element] == SELF)
    		return element;
    	
    	return parents[element] = find(parents[element]);
    }

    public static void main(String[] args) throws IOException {
    	//0. 테스트케이스 입력
        testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
        	//1. 초기 세팅
            initTestCase();

            //2. 연산 입력을 받으며 서로소 집합 알고리즘 진행
            for (int idx = 0; idx < commandSize; idx++) {
                st = new StringTokenizer(input.readLine().trim());
                int cmd = Integer.parseInt(st.nextToken());
                int element1 = Integer.parseInt(st.nextToken());
                int element2 = Integer.parseInt(st.nextToken());
                
                //2-1. 합집합 연산 진행
                if (cmd == CMD_UNION)
                	union(element1, element2);
                
                //2-2. 같은 집합인지 확인 연산 진행
                if (cmd == CMD_FIND) {
                	result.append(find(element1) == find(element2) ? SAME : DIFF);
                }
            }

            //3. 출력
            output.append("#").append(tc).append(" ").append(result).append("\n");
        }
        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
        //1-1. 원소의 개수와 연산의 개수 입력
        st = new StringTokenizer(input.readLine().trim());
        elementSize = Integer.parseInt(st.nextToken());
        commandSize = Integer.parseInt(st.nextToken());

        //1-2. 변수 초기화
        parents = new int[elementSize + 1];
        rank = new int[elementSize + 1];
        result = new StringBuilder();
    }
}
