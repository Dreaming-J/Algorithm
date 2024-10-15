/*
= BOJ 10999. 구간 합 구하기 2
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final String UPDATE_RANGE = "1", QUERY = "2";
    static int arraySize, updateSize, querySize, cmdSize;
    static long[] array;
    static long[] rangeSum;
    static long[] lazy;

    public static long initialize(int left, int right, int node) {
        if (left == right)
            return rangeSum[node] = array[left];

        int mid = (left + right) / 2;
        return rangeSum[node] = initialize(left, mid, node * 2) + initialize(mid + 1, right, node * 2 + 1);
    }

    public static long update(int updateLeft, int updateRight, long value, int left, int right, int node, long lazyValue) {
        if (lazyValue != 0) {
            lazy[node] += lazyValue;
            rangeSum[node] += lazyValue * (right - left + 1);
        }

        if (updateLeft > right || updateRight < left)
            return rangeSum[node];

        if (updateLeft <= left && right <= updateRight) {
            lazy[node] += value;
            return rangeSum[node] += value * (right - left + 1);
        }

        int mid = (left + right) / 2;
        lazyValue = lazy[node];
        lazy[node] = 0;
        return rangeSum[node] = update(updateLeft, updateRight, value, left, mid, node * 2, lazyValue) +
                update(updateLeft, updateRight, value, mid + 1, right, node * 2 + 1, lazyValue);
    }

    public static long query(int queryLeft, int queryRight, int left, int right, int node, long lazyValue) {
        if (lazyValue != 0) {
            lazy[node] += lazyValue;
            rangeSum[node] += lazyValue * (right - left + 1);
        }

        if (right < queryLeft || queryRight < left)
            return 0;

        if (queryLeft <= left && right <= queryRight) {
            return rangeSum[node];
        }

        int mid = (left + right) / 2;
        lazyValue = lazy[node];
        lazy[node] = 0;
        return query(queryLeft, queryRight, left, mid, node * 2, lazyValue) +
                query(queryLeft, queryRight, mid + 1, right, node * 2 + 1, lazyValue);
    }

    public static void main(String[] args) throws IOException {
        init();

        initialize(1, arraySize, 1);

        while (cmdSize-- > 0) {
            st = new StringTokenizer(input.readLine());

            switch (st.nextToken()) {
                case UPDATE_RANGE:
                    int updateLeft = Integer.parseInt(st.nextToken());
                    int updateRight = Integer.parseInt(st.nextToken());
                    long value = Long.parseLong(st.nextToken());

                    update(updateLeft, updateRight, value, 1, arraySize, 1, 0);
                    break;
                case QUERY:
                    int queryLeft = Integer.parseInt(st.nextToken());
                    int queryRight = Integer.parseInt(st.nextToken());

                    output.append(query(queryLeft, queryRight, 1, arraySize, 1, 0))
                            .append("\n");
            }
        }

        System.out.println(output);
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        arraySize = Integer.parseInt(st.nextToken());
        updateSize = Integer.parseInt(st.nextToken());
        querySize = Integer.parseInt(st.nextToken());

        array = new long[arraySize + 1];
        for (int i = 1; i <= arraySize; i++) {
            array[i] = Long.parseLong(input.readLine());
        }

        cmdSize = updateSize + querySize;
        rangeSum = new long[4 * (arraySize + 1)];
        lazy = new long[4 * (arraySize + 1)];
    }
}
