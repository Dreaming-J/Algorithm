/*
= BOJ 2268. 수들의 합 7

= 특이 사항
세그먼트 트리

= 로직
1. 초기 세팅
    1-1. 숫자의 개수, 명령의 개수 입력
    1-2. 명령 입력
    1-3. 변수 초기화
2. 주어진 명령 수행
    2-1. 합 명령이라면 해당 구간의 합 반환
    2-2. 수정 명령이라면 해당 값 수정
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
    static final String SUM = "0", MODIFY = "1";
    static int numberSize, commandSize;
    static long[] number, rangeSum;

    public static long update(int index, long value, int left, int right, int node) {
        if (index < left || index > right)
            return rangeSum[node];

        if (left == right)
            return rangeSum[node] = number[index] = value;

        int mid = (left + right) / 2;
        return rangeSum[node] =
                update(index, value, left, mid, node * 2) + update(index, value, mid + 1, right, node * 2 + 1);
    }

    public static long query(int queryLeft, int queryRight, int left, int right, int node) {
        if (left > queryRight || right < queryLeft)
            return 0;

        if (queryLeft <= left && right <= queryRight)
            return rangeSum[node];

        int mid = (left + right) / 2;
        return query(queryLeft, queryRight, left, mid, node * 2) + query(queryLeft, queryRight, mid + 1, right,
                node * 2 + 1);
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 주어진 명령 수행
        for (int cmdIdx = 0; cmdIdx < commandSize; cmdIdx++) {
            st = new StringTokenizer(input.readLine());

            switch (st.nextToken()) {
                //2-1. 합 명령이라면 해당 구간의 합 반환
                case SUM:
                    int idx1 = Integer.parseInt(st.nextToken());
                    int idx2 = Integer.parseInt(st.nextToken());
                    output.append(
                                    query(Math.min(idx1, idx2), Math.max(idx1, idx2), 0, numberSize, 1))
                            .append("\n");
                    break;
                //2-2. 수정 명령이라면 해당 값 수정
                case MODIFY:
                    int idx = Integer.parseInt(st.nextToken());
                    long value = Long.parseLong(st.nextToken());
                    update(idx, value, 0, numberSize, 1);
            }
        }

        //3. 출력
        System.out.println(output);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 숫자의 개수, 명령의 개수 입력
        st = new StringTokenizer(input.readLine());
        numberSize = Integer.parseInt(st.nextToken());
        commandSize = Integer.parseInt(st.nextToken());

        //1-2. 변수 초기화
        number = new long[numberSize + 1];
        rangeSum = new long[(numberSize + 1) * 4];
    }
}
