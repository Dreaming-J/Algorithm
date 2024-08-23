import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int vertexSize, edgeSize, startNodeIdx;
    static Node[] graph;
    static boolean[] visit;
    static int[] MST;

    public static class Node implements Comparable<Node> {
        int num;
        int weight;
        Node next;

        public Node(int num, int weight) {
            this.num = num;
            this.weight = weight;
        }

        public Node(int num, int weight, Node next) {
            this.num = num;
            this.weight = weight;
            this.next = next;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }
    }

    public static void findMinDistance(int nodeIdx) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(nodeIdx, 0));

        while (!pq.isEmpty()) {
            Node node = pq.poll();

            if (visit[node.num])
                continue;
            visit[node.num] = true;

            Node cur = graph[node.num];
            while (cur != null) {
                int distance = MST[node.num] + cur.weight;

                if (distance < MST[cur.num]) {
                    MST[cur.num] = distance;
                    pq.add(new Node(cur.num, distance));
                }

                cur = cur.next;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        findMinDistance(startNodeIdx);

        for (int idx = 1; idx <= vertexSize; idx++) {
            output.append(MST[idx] == Integer.MAX_VALUE ? "INF" : MST[idx]).append("\n");
        }
        System.out.println(output);
    }

    public static void init() throws IOException {
        st = new StringTokenizer(input.readLine().trim());
        vertexSize = Integer.parseInt(st.nextToken());
        edgeSize = Integer.parseInt(st.nextToken());

        startNodeIdx = Integer.parseInt(input.readLine().trim());

        graph = new Node[vertexSize + 1];
        for (int edgeIdx = 0; edgeIdx < edgeSize; edgeIdx++) {
            st = new StringTokenizer(input.readLine().trim());
            int to = Integer.parseInt(st.nextToken());
            int from = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            graph[to] = new Node(from, weight, graph[to]);
        }

        MST = new int[vertexSize + 1];
        visit = new boolean[vertexSize + 1];
        for (int idx = 0; idx <= vertexSize; idx++) {
            MST[idx] = idx == startNodeIdx ? 0 : Integer.MAX_VALUE;
        }
    }
}
