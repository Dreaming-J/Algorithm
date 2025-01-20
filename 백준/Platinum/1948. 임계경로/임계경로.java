/*
= BOJ 1948. 임계경로
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int citySize, roadSize;
    static int start, end;
    static City[] cities;

    static class City {
        List<Road> goRoads;
        int maxDist;
        Set<Integer> maxDistComponent;

        public City(List<Road> goRoads, int maxDist, Set<Integer> maxDistComponent) {
            this.goRoads = goRoads;
            this.maxDist = maxDist;
            this.maxDistComponent = maxDistComponent;
        }
    }

    static class Road {
        int dest, time;

        public Road(int dest, int time) {
            this.dest = dest;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        findMaxDistance();

        output.append(cities[end].maxDist)
                .append("\n")
                .append(calRoadCount());
        System.out.println(output);
    }

    private static void findMaxDistance() {
        PriorityQueue<Road> pq = new PriorityQueue<>((o1, o2) -> o2.time - o1.time);
        pq.add(new Road(start, 0));

        while (!pq.isEmpty()) {
            Road cur = pq.poll();

            for (Road next : cities[cur.dest].goRoads) {
                int dist = cities[cur.dest].maxDist + next.time;

                if (dist == cities[next.dest].maxDist) {
                    cities[next.dest].maxDistComponent.add(cur.dest);
                }

                if (dist > cities[next.dest].maxDist) {
                    cities[next.dest].maxDistComponent.clear();
                    cities[next.dest].maxDistComponent.add(cur.dest);

                    cities[next.dest].maxDist = dist;
                    pq.add(new Road(next.dest, dist));
                }
            }
        }
    }

    private static int calRoadCount() {
        int roadCount = 0;

        boolean[] visited = new boolean[citySize];
        Deque<Integer> queue = new ArrayDeque<>();

        queue.add(end);

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (int next : cities[cur].maxDistComponent) {
                roadCount++;

                if (visited[next])
                    continue;

                visited[next] = true;

                queue.add(next);
            }
        }

        return roadCount;
    }

    private static void init() throws IOException {
        citySize = Integer.parseInt(input.readLine());
        roadSize = Integer.parseInt(input.readLine());

        cities = new City[citySize + 1];
        for (int idx = 1; idx <= citySize; idx++)
            cities[idx] = new City(new ArrayList<>(), 0, new HashSet<>());

        for (int idx = 1; idx <= roadSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            cities[from].goRoads.add(new Road(to, time));
        }

        st = new StringTokenizer(input.readLine());
        start = Integer.parseInt(st.nextToken());
        end = Integer.parseInt(st.nextToken());
    }
}