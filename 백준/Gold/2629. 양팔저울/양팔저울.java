/*
= BOJ 2629. 양팔저울

= 특이사항
해당 숫자를 조합해서 만들 수 있는지 확인

= 로직
1. 초기 세팅
    1-1. 추의 개수 입력
    1-2. 추의 무게 입력
    1-3. 무게 확인용 구슬의 개수 입력
    1-4. 확인용 구슬의 무게 입력
    1-5. 변수 초기화
2. 최소 비용 계산
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int MAX_WEIGHT = 40_001;
    static int weightSize, beadSize;
    static int[] weights, beads;
    static boolean[][] dpBead;

    public static void find(int weightIdx, int weight) {
        if (dpBead[weightIdx][weight])
            return;
        dpBead[weightIdx][weight] = true;

        if (weightIdx == weightSize)
            return;

        find(weightIdx + 1, weight + weights[weightIdx]);
        find(weightIdx + 1, weight);
        find(weightIdx + 1, Math.abs(weight - weights[weightIdx]));
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 최소 비용 계산
        find(0, 0);

        //3. 출력
        for (int bead : beads) {
            output.append(dpBead[weightSize][bead] ? "Y" : "N").append(" ");
        }
        System.out.println(output);
    }

    public static void init() throws IOException {
        //1-1. 추의 개수 입력
        weightSize = Integer.parseInt(input.readLine());

        //1-2. 추의 무게 입력
        weights = new int[weightSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < weightSize; idx++) {
            weights[idx] = Integer.parseInt(st.nextToken());
        }

        //1-3. 무게 확인용 구슬의 개수 입력
        beadSize = Integer.parseInt(input.readLine());

        //1-4. 확인용 구슬의 무게 입력
        beads = new int[beadSize];
        st = new StringTokenizer(input.readLine());
        for (int idx = 0; idx < beadSize; idx++) {
            beads[idx] = Integer.parseInt(st.nextToken());
        }

        //1-5. 변수 초기화
        dpBead = new boolean[weightSize + 1][MAX_WEIGHT];
    }
}
