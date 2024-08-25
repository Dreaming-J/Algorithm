import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer st;
    static int citySize, busSize, src, dest;
    static Node[] graph;
    static boolean[] visit;
    static Path[] costs;

    public static class Node implements Comparable<Node> {
        int num;
        int cost;
        Node next;

        public Node(int num, int cost) {
            this.num = num;
            this.cost = cost;
        }

        public Node(int num, int cost, Node next) {
            this.num = num;
            this.cost = cost;
            this.next = next;
        }

        @Override
        public int compareTo(Node o) {
            return this.cost - o.cost;
        }
    }

    public static class Path {
        int cost;
        int citySize;
        String path;

        public Path(int cityNum) {
            cost = Integer.MAX_VALUE;
            citySize = 1;
            path = cityNum + " ";
        }
    }

    public static void findMinDistance() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(src, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (visit[cur.num])
                continue;
            visit[cur.num] = true;

            Node next = graph[cur.num];
            while (next != null) {
                int cost = costs[cur.num].cost + next.cost;

                if (cost < costs[next.num].cost) {
                    costs[next.num].cost = cost;
                    costs[next.num].citySize = costs[cur.num].citySize + 1;
                    costs[next.num].path = costs[cur.num].path + next.num + " ";
                    pq.add(new Node(next.num, cost));
                }

                next = next.next;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        init();

        costs[src].cost = 0;
        findMinDistance();

        output.append(costs[dest].cost).append("\n");
        output.append(costs[dest].citySize).append("\n");
        output.append(costs[dest].path);
        System.out.println(output);
    }

    public static void init() throws IOException {
        citySize = Integer.parseInt(input.readLine().trim());
        busSize = Integer.parseInt(input.readLine().trim());

        graph = new Node[citySize + 1];
        for (int busIdx = 0; busIdx < busSize; busIdx++) {
            st = new StringTokenizer(input.readLine().trim());
            int to = Integer.parseInt(st.nextToken());
            int from = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            graph[to] = new Node(from, cost, graph[to]);
        }

        st = new StringTokenizer(input.readLine().trim());
        src = Integer.parseInt(st.nextToken());
        dest = Integer.parseInt(st.nextToken());

        visit = new boolean[citySize + 1];

        costs = new Path[citySize + 1];
        for (int idx = 0; idx <= citySize; idx++) {
            costs[idx] = new Path(idx);
        }
    }
}
