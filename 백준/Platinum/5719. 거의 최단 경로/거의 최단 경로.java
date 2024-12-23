/*
= BOJ 5719. 거의 최단 경로
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final String END_COMMAND = "0 0";
    static final int MAX_VALUE = 10_000_000;
    static final int NOT_VISITED = -1;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int nodeSize, edgeSize;
    static int start, end;
    static List<Node>[] graph;
    static boolean[][] blockEdges;
    static List<Integer>[] trace;
    static int[] distances;

    static class Node implements Comparable<Node> {
        int num, distance;

        public Node(int num, int distance) {
            this.num = num;
            this.distance = distance;
        }

        @Override
        public int compareTo(Node o) {
            return distance - o.distance;
        }
    }

    public static void main(String[] args) throws IOException {
        while (init()) {
            findMinDistance();
            blockMinPath();
            findMinDistance();

            output.append(distances[end] != MAX_VALUE ? distances[end] : NOT_VISITED)
                    .append("\n");
        }

        System.out.println(output);
    }

    private static void findMinDistance() {
        Arrays.fill(distances, MAX_VALUE);
        distances[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            for (Node next : graph[cur.num]) {
                if (blockEdges[cur.num][next.num])
                    continue;

                int distance = distances[cur.num] + next.distance;

                if (distance == distances[next.num])
                    trace[next.num].add(cur.num);

                if (distance < distances[next.num]) {
                    trace[next.num] = new ArrayList<>();
                    trace[next.num].add(cur.num);

                    distances[next.num] = distance;
                    pq.add(new Node(next.num, distance));
                }
            }
        }
    }

    private static void blockMinPath() {
        Deque<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[nodeSize];

        queue.add(end);
        visited[end] = true;

        while (!queue.isEmpty()) {
            int to = queue.poll();

            if (trace[to] == null)
                continue;

            for (int from : trace[to]) {
                blockEdges[from][to] = true;

                if (visited[from])
                    continue;

                visited[from] = true;
                queue.add(from);
            }
        }
    }

    private static boolean init() throws IOException {
        String line = input.readLine();
        if (line.equals(END_COMMAND))
            return false;

        st = new StringTokenizer(line);
        nodeSize = Integer.parseInt(st.nextToken());
        edgeSize = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(input.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());

        graph = new ArrayList[nodeSize];
        for (int idx = 0; idx < nodeSize; idx++)
            graph[idx] = new ArrayList<>();

        for (int idx = 0; idx < edgeSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph[from].add(new Node(to, cost));
        }

        blockEdges = new boolean[nodeSize][nodeSize];
        trace = new ArrayList[nodeSize];
        distances = new int[nodeSize];

        return true;
    }
}