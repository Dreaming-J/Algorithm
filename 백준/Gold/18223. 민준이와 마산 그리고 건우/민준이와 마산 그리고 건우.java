/*
= BOJ 18223. 민준이와 마산 그리고 건우
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static final int INF = 50_000_000, START = 1;
    static int vertexSize, edgeSize, targetNode;
    static List<Node>[] graph;
    static Node[] distances;

    //마산으로 이동
    public static void goMasan() {
        boolean[] visited = new boolean[vertexSize + 1];
        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.add(distances[START]);

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            //방문했다면 패스
            if (visited[cur.num])
                continue;
            visited[cur.num] = true;

            //연결된 정점 확인
            for (Node next : graph[cur.num]) {
                int distance = cur.distance + next.distance;
                boolean saveHim = next.num == targetNode | cur.saveHim;

                if (distance <= distances[next.num].distance) {
                    if (distance == distances[next.num].distance)
                        saveHim |= distances[next.num].saveHim;

                    distances[next.num].distance = distance;
                    distances[next.num].saveHim = saveHim;
                    pq.add(distances[next.num]);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        //초기 세팅
        init();

        //마산으로 이동
        goMasan();

        //출력
        System.out.print(distances[vertexSize].saveHim ? "SAVE HIM" : "GOOD BYE");
    }

    public static class Node implements Comparable<Node> {
        int num, distance;
        boolean saveHim;

        public Node(int num, int distance, boolean saveHim) {
            this.num = num;
            this.distance = distance;
            this.saveHim = saveHim;
        }

        @Override
        public int compareTo(Node o) {
            //거리가 다르다면 오름차순으로 정렬
            if (distance != o.distance)
                return distance - o.distance;

            //목표 정점을 지나온 경로 순으로 정렬
            if (saveHim != o.saveHim)
                return saveHim ? -1 : 1;

            return 0;
        }
    }

    //초기 세팅
    public static void init() throws IOException {
        //정점의 개수, 간선의 개수, 타겟 정점 입력
        st = new StringTokenizer(input.readLine());
        vertexSize = Integer.parseInt(st.nextToken());
        edgeSize = Integer.parseInt(st.nextToken());
        targetNode = Integer.parseInt(st.nextToken());

        //그래프 초기화
        graph = new ArrayList[vertexSize + 1];
        for (int idx = 1; idx <= vertexSize; idx++)
            graph[idx] = new ArrayList<>();

        //도로 정보 입력
        for (int idx = 1; idx <= edgeSize; idx++) {
            st = new StringTokenizer(input.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int time = Integer.parseInt(st.nextToken());

            graph[from].add(new Node(to, time, false));
            graph[to].add(new Node(from, time, false));
        }

        //변수 초기화
        distances = new Node[vertexSize + 1];
        for (int idx = 1; idx <= vertexSize; idx++)
            distances[idx] = new Node(idx, INF, false);
        distances[START].distance = 0;
        distances[targetNode].saveHim = true;
    }
}