/*
= BOJ 3665. 최종 순위
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder output = new StringBuilder();
    static final String IMPOSSIBLE = "IMPOSSIBLE";
    static int teamSize;
    static boolean[][] graph;
    static int[] inDegree;

    public static void main(String[] args) throws IOException {
        int testCases = Integer.parseInt(input.readLine());

        while (testCases-- > 0) {
            init();

            output.append(findRank())
                    .append("\n");
        }

        System.out.println(output);
    }

    private static String findRank() {
        StringJoiner rank = new StringJoiner(" ");

        Deque<Integer> queue = new ArrayDeque<>();

        for (int teamIdx = 1; teamIdx <= teamSize; teamIdx++) {
            if (inDegree[teamIdx] == 0)
                queue.add(teamIdx);
        }

        for (int idx = 0; idx < teamSize; idx++) {
            if (queue.isEmpty())
                return IMPOSSIBLE;

            int cur = queue.poll();

            rank.add(String.valueOf(cur));

            for (int next = 1; next <= teamSize; next++) {
                if (cur == next || !graph[cur][next])
                    continue;

                if (--inDegree[next] == 0) {
                    queue.add(next);
                }
            }
        }

        return rank.toString();
    }

    private static void init() throws IOException {
        //팀의 수 입력
        teamSize = Integer.parseInt(input.readLine());

        //변수 초기화
        graph = new boolean[teamSize + 1][teamSize + 1];
        inDegree = new int[teamSize + 1];

        //작년 등수 입력
        st = new StringTokenizer(input.readLine());

        //1등의 기록
        int first = Integer.parseInt(st.nextToken());
        for (int idx = 1; idx <= teamSize; idx++) {
            if (idx == first)
                continue;

            graph[first][idx] = true;
            inDegree[idx]++;
        }

        //나머지 등수의 기록
        for (int idx = 2; idx <= teamSize; idx++) {
            int from = Integer.parseInt(st.nextToken());

            for (int to = 1; to <= teamSize; to++) {
                if (from == to || graph[to][from])
                    continue;

                graph[from][to] = true;
                inDegree[to]++;
            }
        }

        //역전 등수 입력
        int reversalSize = Integer.parseInt(input.readLine());

        for (int idx = 0; idx < reversalSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            graph[to][from] = !graph[to][from];
            inDegree[from] += graph[to][from] ? 1 : -1;

            graph[from][to] = !graph[from][to];
            inDegree[to] += graph[from][to] ? 1 : -1;
        }
    }
}
