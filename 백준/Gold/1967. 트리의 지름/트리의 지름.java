/*
= BOJ 1967. 트리의 지름
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int nodeSize;
    static List<Node>[] graph;
    static boolean[] visited;
    static int maxIdx, maxDiameter;

    static class Node {
        int num, cost;

        public Node(int num, int cost) {
            this.num = num;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        visited = new boolean[nodeSize + 1];
        visited[1] = true;
        dfs(1, 0);

        visited = new boolean[nodeSize + 1];
        visited[maxIdx] = true;
        dfs(maxIdx, 0);

        System.out.println(maxDiameter);
    }

    private static void dfs(int idx, int cost) {
        if (cost > maxDiameter) {
            maxIdx = idx;
            maxDiameter = cost;
        }

        for (Node next : graph[idx]) {
            if (visited[next.num])
                continue;

            visited[next.num] = true;
            dfs(next.num, cost + next.cost);
        }
    }

    private static void init() throws IOException {
        nodeSize = Integer.parseInt(input.readLine());

        graph = new ArrayList[nodeSize + 1];
        for (int idx = 1; idx <= nodeSize; idx++)
            graph[idx] = new ArrayList<>();

        for (int idx = 1; idx <= nodeSize - 1; idx++) {
            st = new StringTokenizer(input.readLine());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph[from].add(new Node(to, cost));
            graph[to].add(new Node(from, cost));
        }

        maxIdx = 1;
    }
}