/*
#SWEA 6730. 장애물 경주 난이도
처음 블록에서 마지막 블록으로 이동하는 장애물 경주가 있다.
이 경주의 난이도는 올라갈 때의 높이 변화와 내려갈 때의 높이 변화에 각각에 대해
가장 높이 변화가 심한 부분을 난이도라고 한다.
장애물이 주어질 때, 해당 경기의 난이도를 구해라

#입력
첫째 줄: 테스트 케이스의 수 T
	=각 테스트 케이스
	첫번째 줄: 직사각형 블록의 개수 N (2 <= N <= 100)
	두번째 줄: 직사각형의 높이 N개의 정수, 공백으로 구분 (1이상 1,000이상)
#출력
"#Ti [올라갈 때 높이 변화 최대] [내려갈 때 높이 변화 최대]"
올라가거나 내려가는 부분이 없는 경우, 0으로 출력

#로직
현재값에 비해 이전 값이 크다면, 내려온 것으로 판단한다.
현재값에 비해 이전 값이 작다면, 올라온 것으로 판단한다.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Stream;

class Solution {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static int testCases;
    static int blockCount;
    static int[] blocks;
    static int upDifficult, downDifficult;

    public static void main(String args[]) throws IOException {
        testCases = Integer.parseInt(input.readLine());
        for(int test_case = 1; test_case <= testCases; test_case++) {
            //입력 및 초기화
        	upDifficult = 0;
        	downDifficult = 0;
        	blockCount = Integer.parseInt(input.readLine());
        	blocks = Stream.of(input.readLine().trim().split(" "))
        			.mapToInt(Integer::parseInt)
        			.toArray();
        	
            //로직
        	for (int idx = 1; idx < blockCount; idx++) {
        		//올라갔는지 확인
        		if (blocks[idx] > blocks[idx - 1])
        			upDifficult = Math.max(upDifficult, blocks[idx] - blocks[idx - 1]);
        		//내려갔는지 확인
        		if (blocks[idx] < blocks[idx - 1])
        			downDifficult = Math.max(downDifficult, blocks[idx - 1] - blocks[idx]);
        	}

            //출력
            System.out.printf("#%d %d %d\n", test_case, upDifficult, downDifficult);
        }
    }
}