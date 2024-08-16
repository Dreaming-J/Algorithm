/*
=SWEA 4013. [모의 SW 역량테스트] 특이한 자석

1. 테스트 케이스 개수 입력
2. 자석 회전 횟수 입력
3. 4개의 자석 각각의 8개 날의 자성정보 입력
4. K개의 (자석의 번호, 회전 방향)으로 이루어진 자석 회전 정보 입력
5. 자석 회전 정보에 맞춰 자석 회전
6. 최종 상태에 자석 위치에 대한 점수 계산

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int MAGNET_SIZE = 4, MAGNET_TOOTH_SIZE = 8;
    static final int CLOCK_WISE = 1, COUNTER_CLOCK_WISE = -1, NO_TURN = 0;
    static final int N_POLE = 0, S_POLE = 1;
    static final int CENTER_IDX = 0, LEFT_IDX = 6, RIGHT_IDX = 2; 
    static int turnSize;
    static int[] magnet; //8비트의 비트마스킹을 이용해 자성 정보 저장(12시 = 0번, 3시 = 2번)
    static int score;
    
    public static void initTestCase() throws IOException {
    	score = 0;
        magnet = new int[MAGNET_SIZE];
    	
    	//2. 자석 회전 횟수 입력
    	turnSize = Integer.parseInt(input.readLine());
    	
    	//3. 4개의 자석 각각의 8개 날의 자성정보 입력
    	for (int magnetIdx = 0; magnetIdx < MAGNET_SIZE; magnetIdx++) {
    		st = new StringTokenizer(input.readLine().trim());
    		for (int toothIdx = 0; toothIdx < MAGNET_TOOTH_SIZE; toothIdx++) {
    			int pole = Integer.parseInt(st.nextToken());
        		magnet[magnetIdx] |= pole << toothIdx;
    		}
    		magnet[magnetIdx] |= 1 << MAGNET_TOOTH_SIZE;
    	}
    	
    	//4. K개의 (자석의 번호, 회전 방향)으로 이루어진 자석 회전 정보 입력
    	for (int idx = 0; idx < turnSize; idx++) {
    		st = new StringTokenizer(input.readLine().trim());
    		//5. 자석 회전 정보에 맞춰 자석 회전
    		int magnetIdx = Integer.parseInt(st.nextToken()) - 1;
    		int turnDir = Integer.parseInt(st.nextToken());
    		turnMagnet(magnetIdx, turnDir);
    	}
    }
    
    //자석 회전
    public static void turnMagnet(int magnetIdx, int turnDir) {
    	int[] turnDirs = new int[MAGNET_SIZE];
    	turnDirs[magnetIdx] = turnDir;
    	
    	//시작 자석 기준 왼쪽 회전 여부 판단
    	for (int idx = magnetIdx - 1; idx >= 0; idx--) {
    		if (getPole(idx, RIGHT_IDX) == getPole(idx + 1, LEFT_IDX))
    			break;
			turnDirs[idx] = turnDirs[idx + 1] * -1;
    	}
    	
    	//시작 자석 기준 오른쪽 회전 여부 판단
    	for (int idx = magnetIdx + 1; idx < MAGNET_SIZE; idx++) {
    		if (getPole(idx, LEFT_IDX) == getPole(idx - 1, RIGHT_IDX))
    			break;
			turnDirs[idx] = turnDirs[idx - 1] * -1;
    	}
    	
    	//자석 회전
    	for (int idx = 0; idx < MAGNET_SIZE; idx++) {
    		if (turnDirs[idx] == CLOCK_WISE)
    			leftShift(idx);

    		if (turnDirs[idx] == COUNTER_CLOCK_WISE)
    			rightShift(idx);
    	}
    }
    
    //특정 인덱스의 자성 추출
    public static int getPole(int magnetIdx, int toothIdx) {
    	return (magnet[magnetIdx] >> toothIdx) & 1;
    }
    
    //왼쪽 이동
    public static void leftShift(int magnetIdx) {
    	magnet[magnetIdx] &= ~(1 << MAGNET_TOOTH_SIZE);
    	magnet[magnetIdx] = magnet[magnetIdx] << 1 | ((magnet[magnetIdx] >> MAGNET_TOOTH_SIZE - 1) & 0x01);
    	magnet[magnetIdx] |= 1 << MAGNET_TOOTH_SIZE;
    }
    
    //오른쪽 이동
    public static void rightShift(int magnetIdx) {
    	magnet[magnetIdx] &= ~(1 << MAGNET_TOOTH_SIZE);
    	magnet[magnetIdx] = magnet[magnetIdx] >> 1 | (magnet[magnetIdx] << MAGNET_TOOTH_SIZE - 1 & 0x80);
    	magnet[magnetIdx] |= 1 << MAGNET_TOOTH_SIZE;
    }

    public static void main(String[] args) throws IOException {
    	//1. 테스트 케이스 입력
        int testCase = Integer.parseInt(input.readLine());

        for (int tc = 1; tc <= testCase; tc++) {
            initTestCase();
            
            //6. 최종 상태에 자석 위치에 대한 점수 계산
            for (int idx = 0; idx < MAGNET_SIZE; idx++) {
            	if (getPole(idx, CENTER_IDX) == S_POLE)
            		score += 1 << idx;
            }
            
            output.append("#").append(tc).append(" ").append(score).append("\n");
        }
        System.out.println(output);
    }
}