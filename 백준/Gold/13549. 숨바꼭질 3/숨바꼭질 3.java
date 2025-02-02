/*
= BOJ 13549. 숨바꼭질 3
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_POS = 100_000;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int start, target;
    static int[] record;

    static class Node {
        int pos, time;

        public Node(int pos, int time) {
            this.pos = pos;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        findTarget();

        System.out.println(record[target]);
    }

    private static void findTarget() {
        Deque<Node> queue = new ArrayDeque<>();

        queue.add(new Node(start, 0));

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            if (cur.pos > MAX_POS || cur.pos < 0 || cur.time >= record[cur.pos])
                continue;

            if (cur.pos > target) {
                record[target] = Math.min(cur.time + cur.pos - target, record[target]);
                continue;
            }

            record[cur.pos] = Math.min(cur.time, record[cur.pos]);
            
            queue.add(new Node(cur.pos + 1, cur.time + 1));
            queue.add(new Node(cur.pos - 1, cur.time + 1));
            queue.add(new Node(cur.pos * 2, cur.time));
        }
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        start = Integer.parseInt(st.nextToken());
        target = Integer.parseInt(st.nextToken());

        record = new int[MAX_POS + 1];
        Arrays.fill(record, Integer.MAX_VALUE);
    }
}