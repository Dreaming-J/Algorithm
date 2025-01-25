/*
= BOJ 6086. 최대 유량
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int A = 0, Z = 25;
    static final int MAX_PIPE_SIZE = 52, NONE = -1;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int pipeSize;
    static List<Integer>[] graph;
    static int[][] flows, capacities;
    static int[] prevPipe;

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(findMaxFlow());
    }

    private static int findMaxFlow() {
        int totalFlow = 0;

        while (true) {
            Arrays.fill(prevPipe, NONE);

            Deque<Integer> queue = new ArrayDeque<>();
            queue.add(A);

            while (!queue.isEmpty() && prevPipe[Z] == NONE) {
                int cur = queue.poll();

                for (int next : graph[cur]) {
                    if (capacities[cur][next] - flows[cur][next] > 0 && prevPipe[next] == NONE) {
                        queue.add(next);
                        prevPipe[next] = cur;

                        if (next == Z)
                            break;
                    }
                }
            }

            if (prevPipe[Z] == NONE)
                break;

            int flow = Integer.MAX_VALUE;
            for (int idx = Z; idx != A; idx = prevPipe[idx])
                flow = Math.min(capacities[prevPipe[idx]][idx] - flows[prevPipe[idx]][idx], flow);

            for (int idx = Z; idx != A; idx = prevPipe[idx]) {
                flows[prevPipe[idx]][idx] += flow;
                flows[idx][prevPipe[idx]] -= flow;
            }

            totalFlow += flow;
        }

        return totalFlow;
    }

    private static void init() throws IOException {
        pipeSize = Integer.parseInt(input.readLine());

        graph = new ArrayList[MAX_PIPE_SIZE];
        for (int idx = 0; idx < MAX_PIPE_SIZE; idx++)
            graph[idx] = new ArrayList<>();
        flows = new int[MAX_PIPE_SIZE][MAX_PIPE_SIZE];
        capacities = new int[MAX_PIPE_SIZE][MAX_PIPE_SIZE];
        prevPipe = new int[MAX_PIPE_SIZE];

        for (int idx = 0; idx < pipeSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = ctoi(st.nextToken().charAt(0));
            int to = ctoi(st.nextToken().charAt(0));
            int capacity = Integer.parseInt(st.nextToken());

            graph[from].add(to);
            graph[to].add(from);

            capacities[from][to] = capacities[to][from] += capacity;
        }
    }

    private static int ctoi(char c) {
        if (c <= 'Z')
            return c - 'A';

        return c - 'a' + 26;
    }
}