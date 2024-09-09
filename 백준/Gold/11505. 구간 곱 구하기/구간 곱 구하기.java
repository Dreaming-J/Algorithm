/*
= BOJ 11505. 구간 곱 구하기

= 로직
1. 초기 세팅
	1-1. 수의 개수, 변경 횟수, 쿼리 횟수 입력
	1-2. 수 입력
	1-3. 변수 초기화
2. 구간 곱 초기화
3. 명령 입력 및 처리
4. 출력
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int MOD = 1_000_000_007;
    static final String UPDATE = "1", QUERY = "2";
    static int numberSize, updateSize, querySize;
    static long[] numbers, arrayMulti;
    
    public static long init(int left, int right, int node) {
    	if (left == right)
    		return arrayMulti[node] = numbers[left];
    	
    	int mid = (left + right) / 2;
    	return arrayMulti[node] = (init(left, mid, node * 2) * init(mid + 1, right, node * 2 + 1)) % MOD;
    }
    
    public static long update(int idx, long value, int left, int right, int node) {
    	if (idx < left || idx > right)
    		return arrayMulti[node];
    	
    	if (left == right)
    		return arrayMulti[node] = value;
    	
    	int mid = (left + right) / 2;
    	return arrayMulti[node] = (update(idx, value, left, mid, node * 2) * update(idx, value, mid + 1, right, node * 2 + 1)) % MOD;
    }
    
    public static long query(int queryLeft, int queryRight, int left, int right, int node) {
    	if (left > queryRight || right < queryLeft)
    		return 1;
    	
    	if (queryLeft <= left && right <= queryRight)
    		return arrayMulti[node];
    	
    	int mid = (left + right) / 2;
    	return (query(queryLeft, queryRight, left, mid, node * 2) * query(queryLeft, queryRight, mid + 1, right, node * 2 + 1)) % MOD;
    }
    
    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        initTestCase();
        
        //2. 구간 곱 초기화
        init(1, numberSize, 1);
        
        //3. 명령 입력 및 처리
        for (int cmd = 0; cmd < updateSize + querySize; cmd++) {
        	st = new StringTokenizer(input.readLine());
        	
        	switch (st.nextToken()) {
        		case UPDATE:
        			int idx = Integer.parseInt(st.nextToken());
        			long value = Long.parseLong(st.nextToken());
        			update(idx, value, 1, numberSize, 1);
        			break;
        		case QUERY:
        			int idx1 = Integer.parseInt(st.nextToken());
        			int idx2 = Integer.parseInt(st.nextToken());
        			output.append(query(idx1, idx2, 1, numberSize, 1)).append("\n");
        	}
        }

        //4. 출력
        System.out.print(output);
    }

    //1. 초기 세팅
    public static void initTestCase() throws IOException {
    	//1-1. 수의 개수, 변경 횟수, 쿼리 횟수 입력
    	st = new StringTokenizer(input.readLine());
    	numberSize = Integer.parseInt(st.nextToken());
    	updateSize = Integer.parseInt(st.nextToken());
    	querySize = Integer.parseInt(st.nextToken());
    	
    	//1-2. 수 입력
    	numbers = new long[numberSize + 1];
    	for (int idx = 1; idx <= numberSize; idx++) {
    		numbers[idx] = Long.parseLong(input.readLine());
    	}
    	
    	//1-3. 변수 초기화
    	arrayMulti = new long[numbers.length * 4];
    }
}