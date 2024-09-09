/*
= BOJ 2623. 음악프로그램

= 로직
1. 초기 세팅
	1-1. 가수의 수, PD의 수
	1-2. 순서 입력
2. 위상 정렬
3. 출력
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int singerSize, pdSize;
    static List<Integer>[] orders;
    static int[] inDegree;
    
    public static void topologicalSort() {
    	Deque<Integer> queue = new ArrayDeque<>();
    	
    	for (int singerIdx = 1; singerIdx <= singerSize; singerIdx++) {
    		if (inDegree[singerIdx] == 0)
    			queue.add(singerIdx);
    	}
    	
    	for (int idx = 0; idx < singerSize; idx++) {
            if (queue.isEmpty()) {
                output.setLength(0);
                output.append(0);
                return;
            }

            int cur = queue.poll();

            output.append(cur).append("\n");

            for (int next : orders[cur]) {
                if (--inDegree[next] == 0)
                    queue.add(next);
            }
    	}
    }
    
    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        initTestCase();
        
        //2. 위상 정렬
        topologicalSort();
        
        //3. 출력
        System.out.print(output);
    }

    //1. 초기 세팅
    public static void initTestCase() throws IOException {
    	//1-1. 가수의 수, PD의 수
    	st = new StringTokenizer(input.readLine());
    	singerSize = Integer.parseInt(st.nextToken());
    	pdSize = Integer.parseInt(st.nextToken());
    	
    	//1-2. 순서 입력
    	orders = new ArrayList[singerSize + 1];
    	for (int idx = 1; idx <= singerSize; idx++)
    		orders[idx] = new ArrayList<>();
    	inDegree = new int[singerSize + 1];
    	
    	for (int idx = 0; idx < pdSize; idx++) {
    		st = new StringTokenizer(input.readLine());
    		Integer.parseInt(st.nextToken());
    		
    		int from = Integer.parseInt(st.nextToken());
    		while (st.hasMoreTokens()) {
    			int to = Integer.parseInt(st.nextToken());
    			orders[from].add(to);
    			inDegree[to]++;
    			from = to;
    		}
    	}
    }
}