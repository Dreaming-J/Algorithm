/*
= BOJ 1238. 파티
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_TIME = 10_000_000;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int studentSize, roadSize, partyTown;
    static int[][] graph;
    static int answer;

    static class Road {
        int idx, time;

        public Road(int idx, int time) {
            this.idx = idx;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        for (int layover = 1; layover <= studentSize; layover++) {
            for (int start = 1; start <= studentSize; start++) {
                for (int end = 1; end <= studentSize; end++) {
                    graph[start][end] = Math.min(graph[start][layover] + graph[layover][end], graph[start][end]);
                }
            }
        }

        for (int idx = 1; idx <= studentSize; idx++)
            answer = Math.max(graph[idx][partyTown] + graph[partyTown][idx], answer);

        System.out.println(answer);
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        studentSize = Integer.parseInt(st.nextToken());
        roadSize = Integer.parseInt(st.nextToken());
        partyTown = Integer.parseInt(st.nextToken());

        graph = new int[studentSize + 1][studentSize + 1];
        for (int row = 1; row <= studentSize; row++)
            Arrays.fill(graph[row], MAX_TIME);
        graph[partyTown][partyTown] = 0;

        for (int idx = 1; idx <= roadSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            graph[from][to] = time;
        }
    }
}