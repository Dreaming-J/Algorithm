/*
= BOJ 17412. 도시 왕복하기 1
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int SRC = 1, SINK = 2;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int citySize, roadSize;
    static List<Road>[] graph;
    static Road[] previous;

    static class Road {
        int from, to, capaticy, flow;
        Road pairRoad;

        public Road(int from, int to, int capaticy) {
            this.from = from;
            this.to = to;
            this.capaticy = capaticy;
        }

        public Road createPair() {
            Road pair = new Road(to, from, 0);

            this.pairRoad = pair;
            pair.pairRoad = this;

            return pair;
        }

        public int spare() {
            return capaticy - flow;
        }

        public void use() {
            this.flow += 1;
            pairRoad.flow -= 1;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        System.out.println(findMaxRoute());
    }

    private static int findMaxRoute() {
        int total = 0;

        while (true) {
            Arrays.fill(previous, null);

            Deque<Integer> queue = new ArrayDeque<>();
            queue.add(SRC);

            while (!queue.isEmpty() && previous[SINK] == null) {
                int cur = queue.poll();

                for (Road next : graph[cur]) {
                    if (next.spare() > 0 && previous[next.to] == null) {
                        queue.add(next.to);
                        previous[next.to] = next;

                        if (next.to == SINK)
                            break;
                    }
                }
            }

            if (previous[SINK] == null)
                break;

            for (Road road = previous[SINK]; ; road = previous[road.from]) {
                road.use();

                if (road.from == SRC)
                    break;
            }

            total += 1;
        }

        return total;
    }

    private static void init() throws IOException {
        st = new StringTokenizer(input.readLine());
        citySize = Integer.parseInt(st.nextToken());
        roadSize = Integer.parseInt(st.nextToken());

        graph = new ArrayList[citySize + 1];
        for (int idx = 1; idx <= citySize; idx++)
            graph[idx] = new ArrayList<>();
        previous = new Road[citySize + 1];

        for (int idx = 1; idx <= roadSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());

            Road road = new Road(from, to, 1);
            Road pairRoad = road.createPair();

            graph[from].add(road);
            graph[to].add(pairRoad);
        }
    }
}