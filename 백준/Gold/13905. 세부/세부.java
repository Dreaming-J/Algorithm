/*
= BOJ 13905. 세부
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int houseSize;
    static int bridgeSize;
    static int start;
    static int end;
    static List<Node>[] graph;
    static int[] parent, rank;

    static class Node implements Comparable<Node> {
        int num;
        int weight;
        int maxCount;

        public Node(int num, int weight) {
            this.num = num;
            this.weight = weight;
            this.maxCount = Integer.MAX_VALUE;
        }

        public Node(int num, int weight, int maxCount) {
            this.num = num;
            this.weight = weight;
            this.maxCount = maxCount;
        }

        @Override
        public int compareTo(Node o) {
            return o.weight - weight;
        }
    }

    public static int findMaxCount() {
        boolean[] visited = new boolean[houseSize + 1];
        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (visited[cur.num])
                continue;
            visited[cur.num] = true;

            if (cur.num == end)
                return cur.maxCount;

            for (Node next : graph[cur.num])
                pq.add(new Node(next.num, next.weight, Math.min(cur.maxCount, next.weight)));
        }

        return 0;
    }

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(findMaxCount());
    }
    
    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        houseSize = Integer.parseInt(st.nextToken());
        bridgeSize = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(input.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        graph = new ArrayList[houseSize + 1];
        for (int idx = 1; idx <= houseSize; idx++)
            graph[idx] = new ArrayList<>();

        for (int idx = 0; idx < bridgeSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int to = Integer.parseInt(st.nextToken());
            int from = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph[to].add(new Node(from, weight));
            graph[from].add(new Node(to, weight));
        }

        parent = new int[houseSize + 1];
        rank = new int[houseSize + 1];
    }
}
