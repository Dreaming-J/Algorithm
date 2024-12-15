/*
= BOJ 14438. 수열과 쿼리 17
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int UPDATE = 1, QUERY = 2;
    static SegmentTree lisTree;

    static class SegmentTree {
        int arraySize;
        int[] array;
        int[] tree;

        public SegmentTree(int arraySize, int[] array) {
            this.arraySize = arraySize;
            this.array = array;
            this.tree = new int[arraySize * 4];
        }

        int init(int left, int right, int node) {
            if (left == right)
                return tree[node] = array[left];

            int mid = (left + right) / 2;
            return tree[node] = Integer.min(init(left, mid, node * 2), init(mid + 1, right, node * 2 + 1));
        }

        int update(int idx, int target, int left, int right, int node) {
            if (idx < left || idx > right)
                return tree[node];

            if (left == right)
                return tree[node] = array[idx] = target;

            int mid = (left + right) / 2;
            return tree[node] = Integer.min(update(idx, target, left, mid, node * 2), update(idx, target, mid + 1, right, node * 2 + 1));
        }

        int query(int queryLeft, int queryRight, int left, int right, int node) {
            if (right < queryLeft || queryRight < left)
                return Integer.MAX_VALUE;

            if (queryLeft <= left && right <= queryRight)
                return tree[node];

            int mid = (left + right) / 2;
            return Integer.min(query(queryLeft, queryRight, left, mid, node * 2), query(queryLeft, queryRight, mid + 1, right, node * 2 + 1));
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        lisTree.init(1, lisTree.arraySize, 1);

        int querySize = Integer.parseInt(input.readLine());
        while (querySize-- > 0) {
            st = new StringTokenizer(input.readLine());

            switch (Integer.parseInt(st.nextToken())) {
                case UPDATE:
                    int idx = Integer.parseInt(st.nextToken());
                    int value = Integer.parseInt(st.nextToken());

                    lisTree.update(idx, value, 1, lisTree.arraySize, 1);
                    break;
                case QUERY:
                    int queryLeft = Integer.parseInt(st.nextToken());
                    int queryRight = Integer.parseInt(st.nextToken());

                    int result = lisTree.query(queryLeft, queryRight, 1, lisTree.arraySize, 1);

                    output.append(result)
                            .append("\n");
                    break;
            }
        }

        System.out.println(output);
    }

    private static void init() throws IOException {
        int arraySize = Integer.parseInt(input.readLine());

        int[] array = new int[arraySize + 1];
        st = new StringTokenizer(input.readLine());
        for (int idx = 1; idx <= arraySize; idx++)
            array[idx] = Integer.parseInt(st.nextToken());

        lisTree = new SegmentTree(arraySize, array);
    }
}
