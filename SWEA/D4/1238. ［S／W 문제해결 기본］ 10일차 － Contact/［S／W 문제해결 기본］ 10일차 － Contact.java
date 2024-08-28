/*
=SWEA 3289. 서로소 집합

=특이사항
테스트 케이스 10개 고정
입력받는 데이터의 길이의 절반이 간선의 길이
그래프 탐색
마지막 연락 중 가장 큰 숫자를 반환

=로직
1. 초기 세팅
	1-1. 연결망의 정보 데이터의 길이와 시작점 입력
	1-2. 데이터의 길이만큼 입력
	1-3. 변수 초기화
2. 가장 마지막에 받는 사람 중 가장 높은 번호 찾기
	2-1. 더 큰 번호로 업데이트
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int TEST_CASE = 10, MAX_PERSON_SIZE = 100;
    static int testCase;
    static int contactSize, start;
    static Node[] contactNetwork;
    static boolean[] visited;
    static int maxNumber;
    
    public static class Node {
    	int to;
    	Node next;
    	
		public Node(int to, Node next) {
			this.to = to;
			this.next = next;
		}
    }
    
    public static void contact() {
    	Queue<Integer> queue = new ArrayDeque<>();
    	queue.add(start);
    	visited[start] = true;
    	
    	while (!queue.isEmpty()) {
    		int size = queue.size();
    		maxNumber = Integer.MIN_VALUE;
    		
    		while (size-- > 0) {
    			int cur = queue.poll();
    			
    			//2-1. 더 큰 번호로 업데이트
    			maxNumber = Math.max(cur, maxNumber);
    			
    			//2-2. 다음으로 이동
    			for (Node next = contactNetwork[cur]; next != null; next = next.next) {
    				if (visited[next.to])
    					continue;

    		    	visited[next.to] = true;
    				queue.add(next.to);
    			}
    		}
    	}
    }

    public static void main(String[] args) throws IOException {
        for (int tc = 1; tc <= TEST_CASE; tc++) {
        	//1. 초기 세팅
            initTestCase();
            
            //2. 가장 마지막에 받는 사람 중 가장 높은 번호 찾기
            contact();
            
            //3. 출력
            output.append("#").append(tc).append(" ").append(maxNumber).append("\n");
        }
        System.out.println(output);
    }

    public static void initTestCase() throws IOException {
    	//1-1. 연결망의 정보 데이터의 길이와 시작점 입력
        st = new StringTokenizer(input.readLine().trim());
        contactSize = Integer.parseInt(st.nextToken()) / 2;
        start = Integer.parseInt(st.nextToken());
        
    	//1-2. 데이터의 길이만큼 입력
        st = new StringTokenizer(input.readLine().trim());
        contactNetwork = new Node[MAX_PERSON_SIZE + 1];
        for (int idx = 0; idx < contactSize; idx++) {
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            
            contactNetwork[from] = new Node(to, contactNetwork[from]);
        }
        
    	//1-3. 변수 초기화
        visited = new boolean[MAX_PERSON_SIZE + 1];
    }
}
