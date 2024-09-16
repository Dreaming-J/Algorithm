/*
= BOJ 14907. 프로젝트 스케줄링

= 로직
1. 초기 세팅
    1-1. 변수 초기화
    1-2. 작업 순서 입력
2. 위상 정렬
3. 출력
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    private static final int MAX_SIZE = 26, NO_USE = -1;
    static List<Integer>[] orders;
    static int[] inDegree, cost, time;
    static int totalTime;

    public static void topologicalSort() {
        Deque<Integer> queue = new ArrayDeque<>();

        for (int idx = 0; idx < MAX_SIZE; idx++) {
            if (inDegree[idx] == 0) {
                time[idx] = cost[idx];
                totalTime = Math.max(time[idx], totalTime);
                queue.add(idx);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (int next : orders[cur]) {
                time[next] = Math.max(time[cur] + cost[next], time[next]);
                totalTime = Math.max(time[next], totalTime);

                if (--inDegree[next] == 0)
                    queue.add(next);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //1. 초기 세팅
        init();

        //2. 위상 정렬
        topologicalSort();

        //3. 출력
        System.out.print(totalTime);
    }

    //1. 초기 세팅
    public static void init() throws IOException {
        //1-1. 변수 초기화
        orders = new ArrayList[MAX_SIZE];
        for (int idx = 0; idx < MAX_SIZE; idx++) {
            orders[idx] = new ArrayList<>();
        }

        inDegree = new int[MAX_SIZE];
        Arrays.fill(inDegree, NO_USE);

        cost = new int[MAX_SIZE];
        time = new int[MAX_SIZE];

        //1-2. 작업 순서 입력
        String line;
        while ((line = input.readLine()) != null) {
            st = new StringTokenizer(line);

            int to = st.nextToken().charAt(0) - 'A';
            cost[to] = Integer.parseInt(st.nextToken());
            inDegree[to] = 0;

            while (st.hasMoreTokens()) {
                String prevTasks = st.nextToken();
                inDegree[to] = prevTasks.length();
                for (int idx = 0; idx < prevTasks.length(); idx++) {
                    int from = prevTasks.charAt(idx) - 'A';
                    orders[from].add(to);
                }
            }
        }
    }
}
