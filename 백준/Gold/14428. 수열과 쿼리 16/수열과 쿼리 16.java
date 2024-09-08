/*
= BOJ 14428. 수열과 쿼리 16

= 로직
1. 초기 세팅
    1-1. 숫자의 개수 입력
    1-2. 숫자 입력
    1-3. 쿼리의 개수 입력
    1-4. 변수 초기화
2. 세그먼트 트리 초기화
3. 쿼리 입력
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
    static final Number INF = new Number(Integer.MAX_VALUE, Integer.MAX_VALUE);
    static final String UPDATE = "1", QUERY = "2";
    static int numberSize, querySize;
    static int[] numbers;
    static Number[] minIndex;

    public static Number initialize(int left, int right, int node) {
        if (left == right)
            return minIndex[node] = new Number(numbers[left], left);

        int mid = (left + right) / 2;
        return minIndex[node] = Number.min(initialize(left, mid, node * 2),
                initialize(mid + 1, right, node * 2 + 1));
    }

    public static Number update(int idx, int value, int left, int right, int node) {
        if (idx < left || idx > right)
            return minIndex[node];

        if (left == right) {
            return minIndex[node] = new Number(value, idx);
        }

        int mid = (left + right) / 2;
        return minIndex[node] = Number.min(update(idx, value, left, mid, node * 2),
                update(idx, value, mid + 1, right, node * 2 + 1));
    }

    public static Number query(int queryLeft, int queryRight, int left, int right, int node) {
        if (left > queryRight || right < queryLeft)
            return INF;

        if (queryLeft <= left && right <= queryRight)
            return minIndex[node];

        int mid = (left + right) / 2;
        return Number.min(query(queryLeft, queryRight, left, mid, node * 2),
                query(queryLeft, queryRight, mid + 1, right, node * 2 + 1));
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 세그먼트 트리 초기화
        initialize(1, numberSize, 1);

        //3. 쿼리 입력
        for (int queryIdx = 0; queryIdx < querySize; queryIdx++) {
            st = new StringTokenizer(input.readLine());

            switch (st.nextToken()) {
                case UPDATE:
                    int idx = Integer.parseInt(st.nextToken());
                    int value = Integer.parseInt(st.nextToken());
                    update(idx, value, 1, numberSize, 1);
                    break;
                case QUERY:
                    int idx1 = Integer.parseInt(st.nextToken());
                    int idx2 = Integer.parseInt(st.nextToken());
                    output.append(query(idx1, idx2, 1, numberSize, 1).index).append("\n");
            }
        }

        //4.출력
        System.out.print(output);
    }

    public static class Number implements Comparable<Number> {
        int number, index;

        public Number(int number, int index) {
            this.number = number;
            this.index = index;
        }

        public static Number min(Number o1, Number o2) {
            return o1.compareTo(o2) > 0 ? o2 : o1;
        }

        @Override
        public int compareTo(Number o) {
            return number == o.number ? index - o.index : number - o.number;
        }
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 숫자의 개수 입력
        numberSize = Integer.parseInt(input.readLine());

        //1-2. 숫자 입력
        numbers = new int[numberSize + 1];
        st = new StringTokenizer(input.readLine());
        for (int idx = 1; idx <= numberSize; idx++) {
            numbers[idx] = Integer.parseInt(st.nextToken());
        }

        //1-3. 쿼리의 개수 입력
        querySize = Integer.parseInt(input.readLine());

        //1-4. 변수 초기화
        minIndex = new Number[(numberSize + 1) * 4];
    }
}
