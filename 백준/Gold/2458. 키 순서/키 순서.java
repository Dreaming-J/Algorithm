/*
= SWEA 2458. 키 순서

= 특이사항
학생 번호는 1번부터 시작
플로이드 워샬 알고리즘

= 로직
1. 초기 세팅
	1-1. 학생의 수, 키 비교 횟수 입력
	1-2. 키 비교 결과 입력
2. 키 순서 확인
3. 연결된 학생의 개수 확인하기
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
    static int studentSize, compareSize;
    static int[][] orderGraph;
    static int answer;

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        initTestCase();
        
        //2. 키 순서 확인
        for (int layover = 1; layover <= studentSize; layover++) {
            for (int from = 1; from <= studentSize; from++) {
                for (int to = 1; to <= studentSize; to++) {
                	if (orderGraph[from][layover] + orderGraph[layover][to] == 2 && orderGraph[from][to] == 0) {
                		orderGraph[from][to] = 1;
                		orderGraph[from][0]++;
                		orderGraph[to][0]++;
                	}
                }
            }
        }
        
        //3. 연결된 학생의 개수 확인하기
        for (int studentIdx = 1; studentIdx <= studentSize; studentIdx++) {
        	if (orderGraph[studentIdx][0] == studentSize - 1)
        		answer++;
        }

        //4. 출력
        System.out.println(answer);
    }

    //1. 초기 세팅
    public static void initTestCase() throws IOException {
        //1-1. 학생의 수, 키 비교 횟수 입력
    	st = new StringTokenizer(input.readLine());
        studentSize = Integer.parseInt(st.nextToken());
        compareSize = Integer.parseInt(st.nextToken());

        //1-2. 키 비교 결과 입력
        orderGraph = new int[studentSize + 1][studentSize + 1];
        for (int compareIdx = 0; compareIdx < compareSize; compareIdx++) {
            st = new StringTokenizer(input.readLine());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            orderGraph[from][to] = 1;
            orderGraph[from][0]++;
            orderGraph[to][0]++;
        }
    }
}