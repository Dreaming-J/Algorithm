/*
= BOJ 1045. 도로

= 특이사항
최소 스패닝 트리
논의 수는 1부터 시작

= 로직
1. 초기 세팅
    1-1. 논의 개수 입력
    1-2. 논의 개수만큼 우믈 파는 비용 입력
    1-3. 논의 개수만큼 물을 끌어오는 비용 입력
2. 모든 논에 물 대기
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input;
    static StringTokenizer st;
    static int paddySize;
    static PriorityQueue<Path> pathes;
    static int[] parents, rank;
    static int minCost;

    private static void irrigateAllPaddy() {
        int cnt = 0;

        while (!pathes.isEmpty()) {
            Path cur = pathes.poll();

            if (union(cur.start, cur.end)) {
                minCost += cur.cost;

                if (++cnt == paddySize + 1)
                    return;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        input = new BufferedReader(new InputStreamReader(System.in));

        //1. 초기 세팅
        init();

        //2. 모든 논에 물 대기
        irrigateAllPaddy();

        //3. 출력
        System.out.println(minCost);
    }

    private static class Path implements Comparable<Path> {
        int start, end, cost;

        public Path(int start, int end, int cost) {
            this.start = start;
            this.end = end;
            this.cost = cost;
        }

        @Override
        public int compareTo(Path o) {
            return Integer.compare(cost, o.cost);
        }
    }

    private static boolean union(int element1, int element2) {
        int parent1 = find(element1);
        int parent2 = find(element2);

        if (parent1 == parent2)
            return false;

        if (rank[parent1] > rank[parent2]) {
            parents[parent2] = parent1;
            return true;
        }

        if (rank[parent1] == rank[parent2])
            rank[parent2]++;

        parents[parent1] = parent2;
        return true;
    }

    private static int find(int element) {
        if (parents[element] == element)
            return element;

        return parents[element] = find(parents[element]);
    }

    private static void init() throws IOException {
        //1-1. 논의 개수 입력
        paddySize = Integer.parseInt(input.readLine());

        //1-2. 논의 개수만큼 우믈 파는 비용 입력
        pathes = new PriorityQueue<>();
        for (int idx = 1; idx <= paddySize; idx++) {
            pathes.add(new Path(0, idx, Integer.parseInt(input.readLine())));
        }

        //1-3. 논의 개수만큼 물을 끌어오는 비용 입력
        for (int from = 1; from <= paddySize; from++) {
            st = new StringTokenizer(input.readLine());
            for (int to = 1; to <= paddySize; to++) {
                if (to <= from) {
                    st.nextToken();
                    continue;
                }

                pathes.add(new Path(from, to, Integer.parseInt(st.nextToken())));
            }
        }

        //1-4. 변수 초기화
        parents = new int[paddySize + 1];
        Arrays.setAll(parents, idx -> idx);

        rank = new int[paddySize + 1];
    }
}
