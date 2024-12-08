/*
= BOJ 1719. 택배
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static final int UNKOWN = 0;
    static int vertexSize, edgeSize;
    static List<Node>[] graph;
    static int[][] route;

    public static class Node implements Comparable<Node> {
        int to;
        int time;
        int first;

        public Node(int to, int time, int first) {
            this.to = to;
            this.time = time;
            this.first = first;
        }

        @Override
        public int compareTo(Node o) {
            return time - o.time;
        }
    }

    public static void findRoute(int vertexIdx) {
        int[] shortestDistance = new int[vertexSize + 1];
        Arrays.fill(shortestDistance, Integer.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>();

        shortestDistance[vertexIdx] = 0;
        pq.add(new Node(vertexIdx, 0, UNKOWN));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            for (Node next : graph[cur.to]) {
                int distance = shortestDistance[cur.to] + next.time;

                if (distance < shortestDistance[next.to]) {
                    shortestDistance[next.to] = distance;

                    route[vertexIdx][next.to] = cur.first == UNKOWN ? next.to : cur.first;

                    pq.add(new Node(next.to, distance, cur.first == UNKOWN ? next.to : cur.first));
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        for (int vertexIdx = 1; vertexIdx <= vertexSize; vertexIdx++)
            findRoute(vertexIdx);

        for (int row = 1; row <= vertexSize; row++) {
            for (int col = 1; col <= vertexSize; col++) {
                if (row == col)
                    output.append("-");
                else
                    output.append(route[row][col]);

                output.append(" ");
            }
            output.append("\n");
        }
        System.out.println(output);
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        vertexSize = Integer.parseInt(st.nextToken());
        edgeSize = Integer.parseInt(st.nextToken());

        graph = new ArrayList[vertexSize + 1];
        for (int idx = 1; idx <= vertexSize; idx++)
            graph[idx] = new ArrayList<>();

        for (int idx = 0; idx < edgeSize; idx++) {
            st = new StringTokenizer(input.readLine());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            graph[from].add(new Node(to, time, UNKOWN));
            graph[to].add(new Node(from, time, UNKOWN));
        }

        route = new int[vertexSize + 1][vertexSize + 1];
    }
}
