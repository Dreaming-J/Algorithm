/*
= BOJ 17411. 가장 긴 증가하는 부분 수열 6
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int MODULO = 1000000007;
    static int arraySize;
    static PriorityQueue<Number> array;
    static SegmentTree lisTree;

    static class Number implements Comparable<Number> {
        int num, idx;

        public Number(int num, int idx) {
            this.num = num;
            this.idx = idx;
        }

        @Override
        public int compareTo(Number o) {
            return num == o.num ? o.idx - idx : num - o.num;
        }
    }

    static class SegmentTree {
        Node[] tree;

        public SegmentTree(int arraySize) {
            this.tree = new Node[arraySize * 4];
        }

        static class Node {
            int lisSize, count;

            public Node(int lisSize, int count) {
                this.lisSize = lisSize;
                this.count = count;
            }

            @Override
            public String toString() {
                return "[" + lisSize + " " + count + "]";
            }
        }

        Node max(Node n1, Node n2) {
            if (n1 == null)
                return n2;
            if (n2 == null)
                return n1;

            if (n1.lisSize > n2.lisSize)
                return n1;
            else if (n1.lisSize < n2.lisSize)
                return n2;
            else
                return new Node(n1.lisSize, (n1.count + n2.count) % MODULO);
        }

        Node update(int idx, int value, int left, int right, int node) {
            if (idx < left || idx > right)
                return tree[node];
            
            if (left == right) {
                Node prev = query(1, left - 1, 1, arraySize, 1);

                if (prev == null)
                    return tree[node] = new Node(1, 1);
                else
                    return tree[node] = new Node(prev.lisSize + 1, prev.count);
            }

            int mid = (left + right) / 2;
            return tree[node] = max(update(idx, value, left, mid, node * 2), update(idx, value, mid + 1, right, node * 2 + 1));
        }

        Node query(int queryLeft, int queryRight, int left, int right, int node) {
            if (queryRight < left || right < queryLeft)
                return null;

            if (queryLeft <= left && right <= queryRight)
                return tree[node];

            int mid = (left + right) / 2;
            return tree[node] = max(query(queryLeft, queryRight, left, mid, node * 2), query(queryLeft, queryRight, mid + 1, right, node * 2 + 1));
        }
    }

    public static void main(String[] args) throws IOException {
        init();
        
        while (!array.isEmpty()) {
            Number cur = array.poll();
            
            lisTree.update(cur.idx, cur.num, 1, arraySize, 1);
        }
        
        makeAnswer();
        System.out.println(output);
    }

    private static void makeAnswer() {
        output.append(lisTree.tree[1].lisSize)
                .append(" ")
                .append(lisTree.tree[1].count);
    }

    private static void init() throws IOException {
        arraySize = Integer.parseInt(input.readLine());

        array = new PriorityQueue<>();
        st = new StringTokenizer(input.readLine());
        for (int idx = 1; idx <= arraySize; idx++)
            array.add(new Number(Integer.parseInt(st.nextToken()), idx));

        lisTree = new SegmentTree(arraySize);
    }
}
