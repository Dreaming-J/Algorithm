/*
= BOJ 2473. 세 용액

= 로직
1. 초기 세팅
	1-1. 용액의 수 입력
	1-2. 용액의 수만큼 용액의 정보 입력
	1-3. 변수 초기화
2. 용액의 특성값 오름차순 정렬
3. 첫 용액 선택하기
    3-1. 남은 용액은 투포인터를 활용하여 0에 가까운 두 용액 찾기
4. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int solutionSize;
    static long[] solutions, selectSolution;
    static long minValue;

    public static void findTwoSolutions(int start) {
        //3-1. 남은 용액은 투포인터를 활용하여 0에 가까운 두 용액 찾기
        for (int leftIdx = start + 1, rightIdx = solutionSize - 1; leftIdx != rightIdx; ) {
            long value = solutions[start] + solutions[leftIdx] + solutions[rightIdx];

            if (Math.abs(value) < minValue) {
                minValue = Math.abs(value);
                selectSolution[0] = solutions[start];
                selectSolution[1] = solutions[leftIdx];
                selectSolution[2] = solutions[rightIdx];
            }

            if (value > 0)
                rightIdx--;
            else if (value < 0)
                leftIdx++;
            else
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 용액의 특성값 오름차순 정렬
        Arrays.sort(solutions);

        //3. 첫 용액 선택하기
        for (int idx = 0; idx < solutionSize - 2; idx++) {
            findTwoSolutions(idx);
        }

        //4. 출력
        System.out.println(selectSolution[0] + " " + selectSolution[1] + " " + selectSolution[2]);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 용액의 수 입력
        solutionSize = Integer.parseInt(input.readLine());

        //1-2. 용액의 수만큼 용액의 정보 입력
        solutions = new long[solutionSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < solutionSize; idx++) {
            solutions[idx] = Long.parseLong(st.nextToken());
        }

        //1-3. 변수 초기화
        selectSolution = new long[3];
        minValue = Long.MAX_VALUE;
    }
}
